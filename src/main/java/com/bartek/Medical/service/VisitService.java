package com.bartek.Medical.service;

import com.bartek.Medical.exception.*;
import com.bartek.Medical.mapper.VisitMapper;
import com.bartek.Medical.model.Patient;
import com.bartek.Medical.model.Visit;
import com.bartek.Medical.model.VisitDto;
import com.bartek.Medical.patientRepositoryImpl.PatientRepository;
import com.bartek.Medical.visitRepostioryImpl.VisitRespository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VisitService {
    private final VisitRespository visitRespository;
    private final PatientRepository patientRepository;
    private final VisitMapper visitMapper;


    public VisitDto createVisit(VisitDto visitDto) {
        if (visitDto.getDateTime().isBefore(LocalDateTime.now())) {
            throw new InvalidDateTimeException("Nie można utworzyć wizyty dla przeszłych dat.");
        }

        if (visitDto.getDateTime().getMinute() % 15 != 0) {
            throw new InvalidDateTimeException("Wizyty są dostępne tylko w pełnych kwadransach godziny.");
        }

        List<Visit> existingVisits = visitRespository.findByDateTimeRange(visitDto.getDateTime(), visitDto.getEndDateTime());
        if (!existingVisits.isEmpty()) {
            throw new VisitConflictException("Wizyta w tym terminie już istnieje.");
        }
        if (visitDto.getEndDateTime().isBefore(visitDto.getDateTime())) {
            throw new InvalidDateTimeException("Wizyta nie może się zakończyć przed rozpoczęciem");
        }
        if (visitDto.getEndDateTime().getMinute() % 15 != 0) {
            throw new InvalidDateTimeException("Wizyty kończą się tylko w pełnych kwadransach godziny");
        }
        visitRespository.save(visitMapper.toEntity(visitDto));
        return visitDto;
    }

    public VisitDto assignPatientToVisit(Long visitId, Long patientId) {
        Visit visit = visitRespository.findById(visitId)
                .orElseThrow(() -> new VisitNotFoundException("Wizyta o podanym identyfikatorze nie istnieje."));

        if (visit.getPatient() != null) {
            throw new VisitAlreadyAssignedException("Wizyta już ma przypisanego pacjenta.");
        }

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new PatientNotFoundException("Pacjent o podanym identyfikatorze nie istnieje."));

        visit.setPatient(patient);

        Visit updatedVisit = visitRespository.save(visit);

        return visitMapper.toDto(updatedVisit);
    }

    public List<VisitDto> getPatientVisits(Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new PatientNotFoundException("Pacjent o podanym identyfikatorze nie istnieje."));

        List<Visit> patientVisits = visitRespository.findByPatient(patient);

        return visitMapper.toDtos(patientVisits);
    }
}
