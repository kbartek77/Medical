package com.bartek.Medical.model.service;

import com.bartek.Medical.exception.InvalidNameHospitalException;
import com.bartek.Medical.hospitalRepository.HospitalRepository;
import com.bartek.Medical.mapper.DoctorMapper;
import com.bartek.Medical.mapper.HospitalMapper;
import com.bartek.Medical.model.Doctor;
import com.bartek.Medical.model.DoctorDTO;
import com.bartek.Medical.model.Hospital;
import com.bartek.Medical.model.HospitalDto;
import com.bartek.Medical.service.HospitalService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HospitalServiceTest {
    @InjectMocks
    HospitalService hospitalService;
    @Mock
    DoctorMapper doctorMapper;
    @Mock
    HospitalRepository hospitalRepository;
    @Mock
    HospitalMapper hospitalMapper;

    @Test
    void getAllHospitals_HospitalExist_returnedAllHospitals(){
        Set<Doctor> doctors = new HashSet<>();
        List<HospitalDto> hospitalDtoList = new ArrayList<>();
        hospitalDtoList.add(new HospitalDto(1L, "Example Hospital", "Example Town", "12345", "Example Street", "42B"));
        List<Hospital> hospitals = new ArrayList<>();
        hospitals.add(new Hospital(1L, "Example Hospital", "Example Town", "12345", "Example Street", "42B", doctors));
        when(hospitalRepository.findAll()).thenReturn(hospitals);
        when(hospitalMapper.toDtoList(eq(hospitals))).thenReturn(hospitalDtoList);

        List<HospitalDto> result = hospitalService.getAllHospitals();

        Assertions.assertEquals(hospitalDtoList, result);
    }
    @Test
    void getAllHospital_NoHospitalInDataBase_EmptyListReturned() {
        when(hospitalRepository.findAll()).thenReturn(Collections.emptyList());

        List<HospitalDto> result = hospitalService.getAllHospitals();

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void addHospital_HospitalExist_HospitalAdded() {

        HospitalDto hospitalDto = new HospitalDto(1L,"xxx","xxx","xxx","xxx","xxx");
        hospitalDto.setName("Existing Hospital");

        Hospital existingHospital = new Hospital();
        existingHospital.setName("Existing Hospital");

        Mockito.when(hospitalRepository.findByName(hospitalDto.getName())).thenReturn(Optional.of(existingHospital));


        Assertions.assertThrows(InvalidNameHospitalException.class, () -> hospitalService.addHospital(hospitalDto));
        Mockito.verify(hospitalRepository, Mockito.times(1)).findByName(hospitalDto.getName());
        Mockito.verify(hospitalRepository, Mockito.never()).save(Mockito.any(Hospital.class));
    }
    @Test
    public void getAllDoctorsFromHospital_HospitalExist_ReturnedDoctor() {
        String hospitalName = "Example Hospital";

        Hospital hospitalEntity = new Hospital();
        hospitalEntity.setName(hospitalName);

        Doctor doctor1 = new Doctor();
        doctor1.setHospital(hospitalEntity);

        Doctor doctor2 = new Doctor();
        doctor2.setHospital(hospitalEntity);

        Set<Doctor> doctors = new HashSet<>();
        doctors.add(doctor1);
        doctors.add(doctor2);

        hospitalEntity.setDoctors(doctors);

        Mockito.when(hospitalRepository.findByName(hospitalName)).thenReturn(Optional.of(hospitalEntity));

        List<DoctorDTO> result = hospitalService.getAllDoctorsFromHospital(hospitalName);

        Assertions.assertEquals(1, result.size());
        Mockito.verify(hospitalRepository, Mockito.times(1)).findByName(hospitalName);
    }

    @Test
    public void getAllDoctorsFromHospital_HospitalDosentExist_ReturnException() {
        String hospitalName = "Non-existent Hospital";

        Mockito.when(hospitalRepository.findByName(hospitalName)).thenReturn(Optional.empty());

        Assertions.assertThrows(InvalidNameHospitalException.class, () -> hospitalService.getAllDoctorsFromHospital(hospitalName));
        Mockito.verify(hospitalRepository, Mockito.times(1)).findByName(hospitalName);
    }
}



