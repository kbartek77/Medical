package com.bartek.Medical.controller;


import com.bartek.Medical.model.PatientDto;
import com.bartek.Medical.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;
    @Operation(summary = "Get all patients in database", tags = "Patient")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PatientDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid request data", content = @Content)
    })
    @GetMapping
    public List<PatientDto> showPatient() {
        log.info("Getting all patients...");
        return patientService.getAllPatients();
    }
    @Operation(summary = "Get patient by email", tags = "Patient")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PatientDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid email data", content = @Content)
    })
    @GetMapping("/{email}")
    public PatientDto getPatient(@PathVariable String email) {
        log.info("Getting one patient by email: {}", email);
        return patientService.getPatientByEmail(email);
    }
    @Operation(summary = "Add patient with correct data", tags = "Patient")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PatientDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid request data", content = @Content)
    })
    @PostMapping
    public PatientDto addPatient(@RequestBody PatientDto patientDto) {
        log.info("Added one patient...{}", patientDto.toString());
        return patientService.addPatient(patientDto);
    }
    @Operation(summary = "Delete Patient using email", tags = "Patient")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PatientDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid email data", content = @Content)
    })
    @DeleteMapping("/{email}")
    public boolean deletePatientbyEmail(@PathVariable String email) {
        log.info("Deleting patient by email: {}", email);
        return patientService.deletePatientByEmail(email);
    }
    @Operation(summary = "Update patient data by email", tags = "Patient")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PatientDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid email data", content = @Content)
    })
    @PutMapping("/{email}")
    public PatientDto updatePatient(@PathVariable String email, @RequestBody PatientDto updatedPatientDto) {
        log.info("Updating patient detail by email: {}", email);
        return patientService.updatePatient(email, updatedPatientDto);
    }
    @Operation(summary = "Change patient password by email", tags = "Patient")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PatientDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid Email", content = @Content)
    })
    @PatchMapping("/{email}/password")
    public String editPassword(@PathVariable String email, @RequestBody String newPassword) {
        log.info("Edited patient password by email: {}", email);
        boolean passwordUpdated = patientService.updatePassword(email, newPassword);

        if (passwordUpdated) {
            return "Hasło zostało zaktualizowane.";
        } else {
            log.warn("Patient dont find by email while edit password");
            return "Pacjent o podanym adresie e-mail nie został odnaleziony.";
        }
    }
}