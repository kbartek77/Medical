package com.bartek.Medical.model.controller;

import com.bartek.Medical.doctorRepository.DoctorRepository;
import com.bartek.Medical.hospitalRepository.HospitalRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

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
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Hospital A"));
    }
}
