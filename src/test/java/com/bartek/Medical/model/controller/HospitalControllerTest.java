package com.bartek.Medical.model.controller;

import com.bartek.Medical.doctorRepository.DoctorRepository;
import com.bartek.Medical.hospitalRepository.HospitalRepository;
import com.bartek.Medical.model.Doctor;
import com.bartek.Medical.model.DoctorDTO;
import com.bartek.Medical.model.Hospital;
import com.bartek.Medical.model.HospitalDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.hibernate.engine.jdbc.Size;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class HospitalControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    DoctorRepository doctorRepository;
    @Autowired
    HospitalRepository hospitalRepository;

    @Test
    @Rollback
    void showAllHospital_HospitalFound_HospitalsReturned() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/hospitals"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("HospitalA"));
    }
    @Rollback
    @Test
    void addHospital_ValidData_HospitalAdded() throws Exception{
        HospitalDto hospitalDto = new HospitalDto();
        hospitalDto.setName("hospital xyz");
        mockMvc.perform(MockMvcRequestBuilders.post("/hospitals")
                        .content(objectMapper.writeValueAsString(hospitalDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("hospital xyz"));

    }
    @Rollback
    @Test
    void getAllDoctorsFromHospital_HospitalAndDoctorFound_ReturnedDoctors()throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/hospitals/HospitalA"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(1));
    }
}
