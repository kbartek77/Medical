package com.bartek.Medical.Service;

import com.bartek.Medical.Mapper.PatientMapper;
import com.bartek.Medical.Model.Patient;
import com.bartek.Medical.Model.PatientDto;
import com.bartek.Medical.PatientRepositoryImpl.PatientRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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
        when(patientMapper.toDtoList(patients)).thenReturn(Arrays.asList(
                new PatientDto(),
                new PatientDto()
        ));

        List<PatientDto> result = patientService.getAllPatients();
        var result1 = patientService.getAllPatients();
        Assertions.assertEquals(2, result.size());
    }

    @Test
    void GetPatientByEmail_patientExist_returnPatientByEmail() {
        String email = "bartek123@gmail.com";
        Patient patient = new Patient(1L, "bartek123@gmail.com", "bartek123", "123", "Bartek", "Kkk", "12343567", LocalDate.of(2000, 10, 10));
        when(patientRepository.findByEmail(email)).thenReturn(Optional.of(patient));
        PatientDto expectedDto = new PatientDto();
        when(patientMapper.toDto(patient)).thenReturn(new PatientDto());
        PatientDto result = patientService.getPatientByEmail(email);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedDto, result);

    }
    @Test
    void AddPatient_Success() {
        PatientDto patientDto = new PatientDto();
        patientDto.setEmail("Dawid@gmail.com");
        patientDto.setFirstName("Dawid");
        patientDto.setLastName("Xxx");

        when(patientRepository.findByEmail(patientDto.getEmail())).thenReturn(Optional.empty());

        PatientDto result = patientService.addPatient(patientDto);

        Assertions.assertEquals(patientDto, result);
    }
    @Test
    void testDeletePatientByEmail_Successful() {
        String email = "Dawid@gmail.com";
        Patient patient = new Patient(2L, "Dawid@gmail.com", "Dawid123", "1234", "Dawid", "Xxx", "98751513", LocalDate.of(1990, 1, 1));
        when(patientRepository.findByEmail(email)).thenReturn(Optional.of(patient));
        boolean result = patientService.deletePatientByEmail(email);
        Assertions.assertTrue(result);

    }
    @Test
    void UpdatePatient_Successful() {
        String email = "Bartek123@gmail.com";
        PatientDto updatedPatientDto = new PatientDto();
        updatedPatientDto.setEmail("Bartek123@gmail.com");
        updatedPatientDto.setFirstName("Bartek");
        updatedPatientDto.setLastName("Xxx");
        Patient existingPatient = new Patient(1L, "bartek123@gmail.com", "bartek123", "123", "Bartek", "Kkk", "12343567", LocalDate.of(2000, 10, 10));
        when(patientRepository.findByEmail(email)).thenReturn(Optional.of(existingPatient));
        patientService.updatePatient(email, updatedPatientDto);
        Assertions.assertEquals(updatedPatientDto, existingPatient);
    }

    @Test
    void testUpdatePassword_Successful() {
        String email = "Bartek123@gmail.com";
        String newPassword = "newpassword";
        Patient existingPatient = new Patient(1L, "bartek123@gmail.com", "bartek123", "123", "Bartek", "Kkk", "12343567", LocalDate.of(2000, 10, 10));
        when(patientRepository.findByEmail(email)).thenReturn(Optional.of(existingPatient));
        boolean result = patientService.updatePassword(email, newPassword);
        Assertions.assertTrue(result);
        Assertions.assertEquals(newPassword, existingPatient.getPassword());
    }






}
