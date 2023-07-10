package com.bartek.Medical.Model;

import com.bartek.Medical.Exception.InvalidDateTimeException;
import com.bartek.Medical.Exception.PatientNotFoundException;
import com.bartek.Medical.Exception.VisitAlreadyAssignedException;
import com.bartek.Medical.Exception.VisitNotFoundException;
import com.bartek.Medical.Mapper.VisitMapper;
import com.bartek.Medical.PatientRepositoryImpl.PatientRepository;
import com.bartek.Medical.Service.VisitService;
import com.bartek.Medical.VisitRepostioryImpl.VisitRespository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
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
    void createVisit_ValidDateTime_ReturnCreatedVisit() {
        LocalDateTime dateTime = LocalDateTime.now().plusDays(1).withHour(12);

        VisitDto expectedVisitDto = new VisitDto();
        expectedVisitDto.setId(1L);
        expectedVisitDto.setDateTime(dateTime);
        expectedVisitDto.setPatientId(null);

        when(visitRespository.findByDateTime(dateTime)).thenReturn(Collections.emptyList());
        when(visitRespository.save(any(Visit.class))).thenReturn(new Visit());
        when(visitMapper.toDto(any(Visit.class))).thenReturn(expectedVisitDto);

        VisitDto createdVisitDto = visitService.createVisit(dateTime);

        Assertions.assertEquals(expectedVisitDto, createdVisitDto);

        verify(visitRespository, times(1)).findByDateTime(dateTime);
        verify(visitRespository, times(1)).save(any(Visit.class));
        verify(visitMapper, times(1)).toDto(any(Visit.class));
    }

    @Test
    void createVisit_InvalidDateTime_ThrowInvalidDateTimeException() {
        LocalDateTime dateTime = LocalDateTime.now().minusDays(1);

        var exception = Assertions.assertThrows(InvalidDateTimeException.class, () ->
                visitService.createVisit(dateTime)
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

