package com.bartek.Medical.Model.controller;

import com.bartek.Medical.Model.Visit;
import com.bartek.Medical.Model.VisitDto;
import com.bartek.Medical.VisitRepostioryImpl.VisitRespository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class VisitControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    VisitRespository visitRespository;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    DataSource dataSource;
    public static boolean dataLoaded = false;
//    @BeforeEach
//    void setUp() throws SQLException {
//        if (dataLoaded == false) {
//            try (Connection connection = dataSource.getConnection()) {
//                ScriptUtils.executeSqlScript(connection, new ClassPathResource("schema.sql"));
//            }
//            dataLoaded = true;
//        }
//    }

    @Rollback
    @Test
    void createVisit_ValidData_VisitCreated() throws Exception {
        VisitDto visitDto = new VisitDto();
        visitDto.setDateTime(LocalDateTime.of(2024, 10, 10, 15, 30));
        visitDto.setEndDateTime(LocalDateTime.of(2024, 10, 10, 16, 30));


        mockMvc.perform(MockMvcRequestBuilders.post("/visits")
                        .content(objectMapper.writeValueAsString(visitDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateTime").value(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss").format(LocalDateTime.of(2024, 10, 10, 15, 30))));
    }

    @Rollback
    @Test
    void assignPatientToVisit_ValidData_VisitAssigned() throws Exception {
        Long visitId = 3L;
        Long patientId = 1L;
        VisitDto visit1 = new VisitDto();
        visit1.setId(visitId);

        mockMvc.perform(MockMvcRequestBuilders.patch("/visits/" + visitId + "/assign")
                        .content(objectMapper.writeValueAsString(patientId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(3));
    }

    @Rollback
    @Test
    void getPatientVisits_ValidData_ReturnedVisits() throws Exception {
        Long patientId = 1L;
        VisitDto visit1 = new VisitDto();
        visit1.setId(1L);

        mockMvc.perform(MockMvcRequestBuilders.get("/visits/patient/" + patientId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1));
    }
}
