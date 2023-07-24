package com.bartek.Medical.model.service;

import com.bartek.Medical.hospitalRepository.HospitalRepository;
import com.bartek.Medical.mapper.DoctorMapper;
import com.bartek.Medical.mapper.HospitalMapper;
import com.bartek.Medical.model.Doctor;
import com.bartek.Medical.model.Hospital;
import com.bartek.Medical.model.HospitalDto;
import com.bartek.Medical.service.HospitalService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

//    @Test
//    void getAllHospitals_HospitalExist_returneAllHospitals(){
//        List<Hospital> hospitals = new ArrayList<>();
//        hospitals.add(new Hospital(1L, "Example Hospital", "Example Town", "12345", "Example Street", "42B"))
//
//
//    }
    @Test
    void addHospital_HospitalNameNotExists_ShouldAddHospital() {

    }

    @Test
    void addHospital_HospitalNameExists_ShouldThrowException() {

    }

}
