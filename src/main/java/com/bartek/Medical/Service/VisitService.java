package com.bartek.Medical.Service;

import com.bartek.Medical.Exception.*;
import com.bartek.Medical.Mapper.VisitMapper;
import com.bartek.Medical.Model.Patient;
import com.bartek.Medical.Model.Visit;
import com.bartek.Medical.Model.VisitDto;
import com.bartek.Medical.PatientRepositoryImpl.PatientRepository;
import com.bartek.Medical.VisitRepostioryImpl.VisitRespository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class VisitService {
    VisitRespository visitRespository;
    PatientRepository patientRepository;
    private final VisitMapper visitMapper;


    public VisitDto createVisit(VisitDto visitDto) {
        if (visitDto.getDateTime().isBefore(LocalDateTime.now())) {
            throw new InvalidDateTimeException("Nie można utworzyć wizyty dla przeszłych dat.");
        }

        if (visitDto.getDateTime().getMinute() % 15 != 0) {
            throw new InvalidDateTimeException("Wizyty są dostępne tylko w pełnych kwadransach godziny.");
        }

        List<Visit> existingVisits = visitRespository.findByDateTime(visitDto.getDateTime());
        if (!existingVisits.isEmpty()) {
            throw new VisitConflictException("Wizyta w tym terminie już istnieje.");
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
