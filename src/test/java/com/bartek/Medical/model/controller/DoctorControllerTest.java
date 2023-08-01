package com.bartek.Medical.model.controller;

import com.bartek.Medical.doctorRepository.DoctorRepository;
import com.bartek.Medical.model.DoctorDTO;
import com.bartek.Medical.model.Hospital;
import com.bartek.Medical.service.DoctorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class DoctorControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    DoctorRepository doctorRepository;
    @Autowired
    DoctorService doctorService;

    @Rollback
    @Test
    void showDoctors_DoctorExist_AllDoctorsReturned() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/doctors"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(2));
    }
    @Rollback
    @Test
    void showDoctorById_DoctorExist_DoctorReturned() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/doctors/1"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("James"));
    }
    @Rollback
    @Test
    void addDoctor_ValidData_DoctorAdded() throws Exception {
        DoctorDTO doctorDTO = new DoctorDTO(3L, "Bartek", "Xxx", "xxx", "xxx", "xxx", null);
        mockMvc.perform(MockMvcRequestBuilders.post("/doctors")
                        .content(objectMapper.writeValueAsString(doctorDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Bartek"));
    }
    @Rollback
    @Test
    void deleteDoctor_ValidData_DoctorDeleted()throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/doctors/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Rollback
    @Test
    void updateDoctorPassword_ValidData_DoctorPasswordChanged() throws Exception{
        String newPassword = "newPassword";
        mockMvc.perform(MockMvcRequestBuilders.patch("/doctors/1/password")
                .content(newPassword)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Rollback
    @Test
    void addHospitalToDoctor_ValidData_HospitalAddedToDoctor() throws Exception {
        String hospitalName = "HospitalA";
        Hospital hospital = new Hospital();
        hospital.setName(hospitalName);
        Long doctorId = 1L;

        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.setId(doctorId);
        doctorDTO.setHospital(hospital);

        mockMvc.perform(MockMvcRequestBuilders.post("/doctors/1/hospitals")
                .content(hospitalName)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}

