package com.bartek.Medical.Model.Service;

import com.bartek.Medical.Exception.InvalidEmailException;
import com.bartek.Medical.Exception.PatientNotFoundException;
import com.bartek.Medical.Mapper.PatientMapper;
import com.bartek.Medical.Model.Patient;
import com.bartek.Medical.Model.PatientDto;
import com.bartek.Medical.PatientRepositoryImpl.PatientRepository;

import com.bartek.Medical.Service.PatientService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {
    @InjectMocks
    PatientService patientService;
    @Mock
    PatientMapper patientMapper;
    @Mock
    PatientRepository patientRepository;

    @Test
    public void getAllPatients_patientExist_returnAllPatients() {
        List<Patient> patients = new ArrayList<>();
        patients.add(new Patient(1L, "bartek123@gmail.com", "bartek123", "123", "Bartek", "Kkk", "12343567", LocalDate.of(2000, 10, 10)));
        patients.add(new Patient(2L, "Dawid@gmail.com", "Dawid123", "1234", "Dawid", "Xxx", "98751513", LocalDate.of(1990, 1, 1)));

        when(patientRepository.findAll()).thenReturn(patients);
        when(patientMapper.toDtoList(patients)).thenReturn(Arrays.asList(new PatientDto(), new PatientDto()));

        List<PatientDto> result = patientService.getAllPatients();

        Assertions.assertEquals(2, result.size());
    }

    @Test
    void getAllPatients_NoPatientsInDatabase_EmptyListReturned() {
        when(patientRepository.findAll()).thenReturn(Collections.emptyList());

        List<PatientDto> result = patientService.getAllPatients();

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void getPatientByEmail_patientExist_returnPatientByEmail() {
        String email = "bartek123@gmail.com";
        Patient patient = new Patient(1L, "bartek123@gmail.com", "bartek123", "123", "Bartek", "Kkk", "12343567", LocalDate.of(2000, 10, 10));
        when(patientRepository.findByEmail(email)).thenReturn(Optional.of(patient));

        PatientDto expectedDto = new PatientDto();
        when(patientMapper.toDto(patient)).thenReturn(new PatientDto());
        PatientDto result = patientService.getPatientByEmail(email);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedDto.getEmail(), result.getEmail());

    }

    @Test
    void getPatientByEmail_PatientNotFound_ExceptionThrown() {
        String email = "nonexistent@example.com";
        when(patientRepository.findByEmail(email)).thenReturn(Optional.empty());

        var exception = Assertions.assertThrows(PatientNotFoundException.class, () -> patientService.getPatientByEmail(email));
        Assertions.assertEquals("Pacjent o podanym adresie e-mail nie został odnaleziony.", exception.getMessage());
    }

    @Test
    void addPatient_PatientExist_PatientAdded() {
        PatientDto patientDto = new PatientDto();
        patientDto.setEmail("Dawid@gmail.com");
        patientDto.setFirstName("Dawid");
        patientDto.setLastName("Xxx");

        when(patientRepository.findByEmail(eq(patientDto.getEmail()))).thenReturn(Optional.empty());

        PatientDto result = patientService.addPatient(patientDto);

        Assertions.assertEquals(patientDto.getEmail(), result.getEmail());
        verify(patientRepository, times(1)).save(any());
    }

    @Test
    void addPatient_PatientWithEmailAlreadyExists_ExceptionThrown() {
        PatientDto patientDto = new PatientDto();
        patientDto.setEmail("existing@example.com");

        when(patientRepository.findByEmail(patientDto.getEmail())).thenReturn(Optional.of(new Patient()));

        var exception = Assertions.assertThrows(InvalidEmailException.class, () -> patientService.addPatient(patientDto));
        Assertions.assertEquals("Pacjent o podanym adresie e-mail już istnieje.", exception.getMessage());
    }

    @Test
    void testDeletePatientByEmail_PatientExist_PatientDeleted() {
        String email = "Dawid@gmail.com";
        Patient patient = new Patient(2L, "Dawid@gmail.com", "Dawid123", "1234", "Dawid", "Xxx", "98751513", LocalDate.of(1990, 1, 1));

        when(patientRepository.findByEmail(eq(email))).thenReturn(Optional.of(patient));
        boolean result = patientService.deletePatientByEmail(email);

        Assertions.assertTrue(result);
        verify(patientRepository, times(1)).delete(eq(patient));

    }

    @Test
    void deletePatientByEmail_PatientNotFound_ExceptionThrown() {
        String email = "nonexistent@example.com";
        when(patientRepository.findByEmail(email)).thenReturn(Optional.empty());

        var exception = Assertions.assertThrows(PatientNotFoundException.class, () -> patientService.deletePatientByEmail(email));
        Assertions.assertEquals("Pacjent o podanym adresie e-mail nie został odnaleziony.", exception.getMessage());
    }

    @Test
    void updatePatient_DataCorret_DataUpdated() {
        String email = "Bartek123@gmail.com";
        PatientDto updatedPatientDto = new PatientDto();
        updatedPatientDto.setEmail("Bartek123@gmail.com");
        updatedPatientDto.setFirstName("Bartek");
        updatedPatientDto.setLastName("Kkk");

        Patient existingPatient = new Patient(1L, "Bartek123@gmail.com", "bartek123", "123", "Bartek", "Kkk", "12343567", LocalDate.of(2000, 10, 10));
        when(patientRepository.findByEmail(email)).thenReturn(Optional.of(existingPatient));
        patientService.updatePatient(email, updatedPatientDto);

        Assertions.assertEquals(updatedPatientDto.getEmail(), existingPatient.getEmail());
        Assertions.assertEquals(updatedPatientDto.getFirstName(), existingPatient.getFirstName());
        Assertions.assertEquals(updatedPatientDto.getLastName(), existingPatient.getLastName());
    }

    @Test
    void updatePatient_PatientNotFound_ExceptionThrown() {
        String email = "Bartek123@gmail.com";
        PatientDto updatedPatientDto = new PatientDto();

        when(patientRepository.findByEmail(email)).thenReturn(Optional.empty());

        var exception = Assertions.assertThrows(PatientNotFoundException.class, () -> patientService.updatePatient(email, updatedPatientDto));
        Assertions.assertEquals("Pacjent o podanym adresie e-mail nie został odnaleziony.", exception.getMessage());
    }

    @Test
    void testUpdatePassword_SetPasswordCorrect_PasswordUpdated() {
        String email = "Bartek123@gmail.com";
        String newPassword = "newpassword";
        Patient existingPatient = new Patient(1L, "bartek123@gmail.com", "bartek123", "123", "Bartek", "Kkk", "12343567", LocalDate.of(2000, 10, 10));

        when(patientRepository.findByEmail(email)).thenReturn(Optional.of(existingPatient));
        boolean result = patientService.updatePassword(email, newPassword);

        Assertions.assertTrue(result);
        Assertions.assertEquals(newPassword, existingPatient.getPassword());
    }

    @Test
    void updatePassword_PatientNotFound_ExceptionThrown() {
        String email = "Dawid@gmail.com";
        String newPassword = "newpassword";

        when(patientRepository.findByEmail(email)).thenReturn(Optional.empty());

        var exception = Assertions.assertThrows(PatientNotFoundException.class, () -> patientService.updatePassword(email, newPassword));
        Assertions.assertEquals("Pacjent o podanym adresie e-mail nie został odnaleziony.", exception.getMessage());
    }
}
