package com.bartek.Medical.model.service;

import com.bartek.Medical.exception.InvalidDateTimeException;
import com.bartek.Medical.exception.PatientNotFoundException;
import com.bartek.Medical.exception.VisitAlreadyAssignedException;
import com.bartek.Medical.exception.VisitNotFoundException;
import com.bartek.Medical.mapper.VisitMapper;
import com.bartek.Medical.model.Patient;
import com.bartek.Medical.model.Visit;
import com.bartek.Medical.model.VisitDto;
import com.bartek.Medical.patientRepositoryImpl.PatientRepository;
import com.bartek.Medical.service.VisitService;
import com.bartek.Medical.visitRepostioryImpl.VisitRespository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VisitServiceTest {
    @Mock
    private VisitRespository visitRespository;
    @Mock
    private PatientRepository patientRepository;
    @Mock
    private VisitMapper visitMapper;
    @InjectMocks
    private VisitService visitService;

    @Test
    void createVisit_ValidData_ReturnCreatedVisitDto() {
        LocalDateTime dateTime = LocalDateTime.of(2024, 10, 10, 10, 30, 00);
        LocalDateTime endDateTime = LocalDateTime.of(2024, 10, 10, 11, 30, 00);

        VisitDto visitDto = new VisitDto();
        visitDto.setDateTime(dateTime);
        visitDto.setEndDateTime(endDateTime);

        Visit createdVisit = new Visit();
        createdVisit.setDateTime(dateTime);
        createdVisit.setEndDateTime(endDateTime);

        VisitDto expectedVisitDto = new VisitDto();
        expectedVisitDto.setDateTime(dateTime);
        expectedVisitDto.setEndDateTime(endDateTime);

        VisitDto resultDto = visitService.createVisit(visitDto);

        Assertions.assertEquals(expectedVisitDto, resultDto);
    }

    @Test
    void createVisit_InvalidDateTime_ThrowInvalidDateTimeException() {
        LocalDateTime dateTime = LocalDateTime.now().minusDays(1);
        VisitDto visitDto = new VisitDto();
        visitDto.setDateTime(dateTime);

        var exception = Assertions.assertThrows(InvalidDateTimeException.class, () ->
                visitService.createVisit(visitDto)
        );
        Assertions.assertEquals("Nie można utworzyć wizyty dla przeszłych dat.", exception.getMessage());
    }

    @Test
    void assignPatientToVisit_VisitNotFound_ThrowVisitNotFoundException() {
        Long visitId = 1L;
        Long patientId = 1L;

        when(visitRespository.findById(visitId)).thenReturn(Optional.empty());

        var exception = Assertions.assertThrows(VisitNotFoundException.class, () ->
                visitService.assignPatientToVisit(visitId, patientId)
        );
        Assertions.assertEquals("Wizyta o podanym identyfikatorze nie istnieje.", exception.getMessage());
    }

    @Test
    void assignPatientToVisit_VisitAlreadyAssigned_ThrowVisitAlreadyAssignedException() {
        Long visitId = 1L;
        Long patientId = 1L;

        Visit visit = new Visit();
        visit.setPatient(new Patient());

        when(visitRespository.findById(visitId)).thenReturn(Optional.of(visit));

        var exception = Assertions.assertThrows(VisitAlreadyAssignedException.class, () ->
                visitService.assignPatientToVisit(visitId, patientId)
        );
        Assertions.assertEquals("Wizyta już ma przypisanego pacjenta.", exception.getMessage());

    }

    @Test
    void getPatientVisits_InvalidPatientId_ThrowPatientNotFoundException() {
        Long patientId = 1L;

        when(patientRepository.findById(patientId)).thenReturn(Optional.empty());

        var exception = Assertions.assertThrows(PatientNotFoundException.class, () ->
                visitService.getPatientVisits(patientId)
        );
        Assertions.assertEquals("Pacjent o podanym identyfikatorze nie istnieje.", exception.getMessage());
    }
}

