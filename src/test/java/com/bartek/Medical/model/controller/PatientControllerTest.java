package com.bartek.Medical.model.controller;

import com.bartek.Medical.model.PatientDto;
import com.bartek.Medical.patientRepositoryImpl.PatientRepository;
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

import javax.sql.DataSource;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class PatientControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    DataSource dataSource;
    @Rollback
    @Test
    void showPatient_PatientsExist_AllPatientsReturned() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/patients"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
    }

    @Rollback
    @Test
    void addPatient_DataCorrect_PatientCreated() throws Exception {
        PatientDto patient = new PatientDto();
        patient.setFirstName("Bartek");
        patient.setLastName("KKk");
        patient.setEmail("test");
        patient.setBirthday(LocalDate.of(2000, 10, 10));
        patient.setPassword("Xxx");
        patient.setIdCardNo("202");
        patient.setPhoneNumber("998");
        mockMvc.perform(MockMvcRequestBuilders.post("/patients")
                        .content(objectMapper.writeValueAsString(patient))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("test"));
    }

    @Rollback
    @Test
    void addPatient_PatientExist_ReturnedPatient() throws Exception {
        PatientDto patientDto = new PatientDto();
        patientDto.setEmail("bartek@gmail.com");
        mockMvc.perform(MockMvcRequestBuilders.get("/patients/john.doe@example.com"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("john.doe@example.com"));
    }

    @Rollback
    @Test
    void deletePatientbyEmail_PatientExist_PatientDeleted() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/patients/john.doe@example.com"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Rollback
    @Test
    void updatePatient_PatientExist_PatientUpdated() throws Exception {
        String email = "bartek@gmail.com";
        PatientDto updatedPatientDto = new PatientDto();
        updatedPatientDto.setEmail(email);
        updatedPatientDto.setFirstName("Bartek");
        updatedPatientDto.setLastName("kkk");

        mockMvc.perform(MockMvcRequestBuilders.put("/patients/john.doe@example.com")
                        .content(objectMapper.writeValueAsString(updatedPatientDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(email))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Bartek"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("kkk"));
    }

    @Rollback
    @Test
    void editPassword_PatientExist_PasswordUpdated() throws Exception {
        String newPassword = "newPassword";
        mockMvc.perform(MockMvcRequestBuilders.patch("/patients/john.doe@example.com/password")
                        .content(newPassword)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Hasło zostało zaktualizowane."));
    }
}


