package com.bartek.Medical.model.service;

import com.bartek.Medical.doctorRepository.DoctorRepository;
import com.bartek.Medical.exception.DoctorNotFoundException;
import com.bartek.Medical.hospitalRepository.HospitalRepository;
import com.bartek.Medical.mapper.DoctorMapper;
import com.bartek.Medical.model.Doctor;
import com.bartek.Medical.model.DoctorDTO;
import com.bartek.Medical.model.Hospital;
import com.bartek.Medical.model.MessageDto;
import com.bartek.Medical.service.DoctorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DoctorServiceTest {
    @InjectMocks
    DoctorService doctorService;
    @Mock
    DoctorRepository doctorRepository;
    @Mock
    DoctorMapper doctorMapper;
    @Mock
    HospitalRepository hospitalRepository;

    @Test
    public void getAllDcotors_DoctorsExist_ReturnedDoctorList() {
        List<Doctor> doctors = new ArrayList<>();
        doctors.add(new Doctor(1L,"xxx","xxx","xxx","xxx","xxx",null));
        doctors.add(new Doctor());
        List<DoctorDTO> doctorDTOS = new ArrayList<>();
        doctorDTOS.add(new DoctorDTO());
        doctorDTOS.add(new DoctorDTO());

        when(doctorRepository.findAll()).thenReturn(doctors);
        when(doctorMapper.toDtosList(eq(doctors))).thenReturn(doctorDTOS);

        List<DoctorDTO> result = doctorService.getAllDoctors();

        Assertions.assertEquals(doctors.size(), result.size());
    }

    @Test
    public void getDoctorById_doctorExist_doctorReturned() {
        Long doctorId = 1L;
        Doctor doctor = new Doctor();
        doctor.setId(doctorId);
        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.setId(doctorId);

        when(doctorRepository.findById(doctorId)).thenReturn(Optional.of(doctor));
        when(doctorMapper.toDto(eq(doctor))).thenReturn(doctorDTO);

        DoctorDTO result = doctorService.getDoctorById(doctorId);

        Assertions.assertEquals(doctorId, result.getId());
    }

    @Test
    public void getDoctorById_doctorDosentExist_ExceptionReturned() {
        Long doctorId = 1L;

        when(doctorRepository.findById(doctorId)).thenReturn(Optional.empty());

        Assertions.assertThrows(DoctorNotFoundException.class, () -> doctorService.getDoctorById(doctorId));
    }

    @Test
    public void addDoctor_correctDoctor_DoctorAdded() {
        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.setFirstName("John");

        Doctor doctor = new Doctor();
        doctor.setFirstName("Dawid");

        when(doctorRepository.save(Mockito.any(Doctor.class))).thenReturn(doctor);
        when(doctorMapper.toEntity(eq(doctorDTO))).thenReturn(doctor);
        DoctorDTO result = doctorService.addDoctor(doctorDTO);

        Assertions.assertEquals(doctorDTO.getFirstName(), result.getFirstName());
        verify(doctorRepository, Mockito.times(1)).save(Mockito.any(Doctor.class));
    }

    @Test
    public void deleteDoctorById_DoctorExist_DeletedDoctor() {
        Long doctorId = 1L;
        Doctor doctor = new Doctor();
        doctor.setId(doctorId);

        Mockito.when(doctorRepository.findById(doctorId)).thenReturn(Optional.of(doctor));

        MessageDto result = doctorService.deleteDoctorById(doctorId);

        Assertions.assertEquals(HttpStatus.OK, result.getHttpStatus());
        Assertions.assertEquals("Doktor został usunięty", result.getMessage());
        Mockito.verify(doctorRepository, Mockito.times(1)).delete(doctor);
    }

    @Test
    public void DeleteDoctorById_DoctorDosentExist_ReturnedException() {
        Long doctorId = 1L;

        Mockito.when(doctorRepository.findById(doctorId)).thenReturn(Optional.empty());

        Assertions.assertThrows(DoctorNotFoundException.class, () -> doctorService.deleteDoctorById(doctorId));
        Mockito.verify(doctorRepository, Mockito.never()).delete(Mockito.any(Doctor.class));
    }

    @Test
    public void updatePassword_DoctorExist_PasswordUpdated() {
        Long doctorId = 1L;
        String newPassword = "newPassword";
        Doctor doctor = new Doctor();
        doctor.setId(doctorId);

        Mockito.when(doctorRepository.findById(doctorId)).thenReturn(Optional.of(doctor));

        MessageDto result = doctorService.updatePassoword(doctorId, newPassword);

        Assertions.assertEquals(HttpStatus.OK, result.getHttpStatus());
        Assertions.assertEquals("Twoje hasło zostało zmienione", result.getMessage());
        Assertions.assertEquals(newPassword, doctor.getPassword());
        Mockito.verify(doctorRepository, Mockito.times(1)).save(doctor);
    }

    @Test
    public void updatePassword_DoctorDosentExist_PasswordNotUpdated() {
        Long doctorId = 1L;
        String newPassword = "newPassword";

        Mockito.when(doctorRepository.findById(doctorId)).thenReturn(Optional.empty());

        Assertions.assertThrows(DoctorNotFoundException.class, () -> doctorService.updatePassoword(doctorId, newPassword));
        Mockito.verify(doctorRepository, Mockito.never()).save(Mockito.any(Doctor.class));
    }


    @Test
    public void addHospitalToDoctor_WhenDoctorAndHospitalExist_ReturnedDoctors() {
        Long doctorId = 1L;
        String hospitalName = "Hospital A";

        Doctor doctor = new Doctor();
        doctor.setId(doctorId);

        Hospital hospital = new Hospital();
        hospital.setName(hospitalName);

        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.setId(doctorId);
        doctorDTO.setHospital(hospital);


        when(doctorRepository.findById(doctorId)).thenReturn(Optional.of(doctor));
        when(hospitalRepository.findByName(hospitalName)).thenReturn(Optional.of(hospital));
        when(doctorRepository.save(doctor)).thenReturn(doctor);
        when(doctorMapper.toDto(eq(doctor))).thenReturn(doctorDTO);

        DoctorDTO result = doctorService.addHospitalToDoctor(doctorId, hospitalName);


        Assertions.assertEquals(hospitalName, result.getHospital().getName());
        Mockito.verify(doctorRepository, Mockito.times(1)).save(doctor);
    }
}
