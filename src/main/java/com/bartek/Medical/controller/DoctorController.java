package com.bartek.Medical.controller;

import com.bartek.Medical.model.Doctor;
import com.bartek.Medical.model.DoctorDTO;
import com.bartek.Medical.model.Hospital;
import com.bartek.Medical.model.MessageDto;
import com.bartek.Medical.service.DoctorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
@Slf4j
@RestController
@RequestMapping("/doctors")
@RequiredArgsConstructor
public class DoctorController {
    private final DoctorService doctorService;
    @Operation(summary = "Get All Doctors", tags = "Doctors")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = DoctorDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid request data", content = @Content)
    })
    @GetMapping
    public List<DoctorDTO> getAllDoctors() {
        log.info("Getting all doctors... {}");
        List<DoctorDTO> doctorDTOList = doctorService.getAllDoctors();
        log.info("returned doctors {}", Arrays.toString(doctorDTOList.toArray()));
        return doctorDTOList;
    }
    @Operation(summary = "Get Doctors by id", tags = "Doctors")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = DoctorDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid request data", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<DoctorDTO> getDoctorById(@PathVariable Long id) {
        log.info("Getting doctor by id: {}", id);
        DoctorDTO doctor = doctorService.getDoctorById(id);
        return ResponseEntity.ok(doctor);
    }
    @Operation(summary = "Add dodctor to data base", tags = "Doctors")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = DoctorDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid request data", content = @Content)
    })
    @PostMapping
    public ResponseEntity<DoctorDTO> addDoctor(@RequestBody DoctorDTO doctorDTO) {
        log.info("Adding a new doctor...{}", doctorDTO.toString());
        DoctorDTO savedDoctor = doctorService.addDoctor(doctorDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDoctor);
    }
    @Operation(summary = "Delete doctor by id", tags = "Doctors")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = DoctorDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid request data", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity <MessageDto> deleteDoctorById(@PathVariable Long id) {
        log.info("Deleting doctor by id: {}", id);
        MessageDto messageDto = doctorService.deleteDoctorById(id);
        return ResponseEntity.status(messageDto.getHttpStatus()).body(messageDto);
    }
    @Operation(summary = "Update doctor password by id", tags = "Doctors")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = MessageDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid request data", content = @Content)
    })
    @PatchMapping("/{id}/password")
    public ResponseEntity<MessageDto> updatePassword(@PathVariable Long id, @RequestBody String newPassword) {
        log.info("Updating doctor password by id: {}", id);
        MessageDto messageDto = doctorService.updatePassoword(id, newPassword);
        return ResponseEntity.status(messageDto.getHttpStatus()).body(messageDto);
    }
    @Operation(summary = "Add hospital to doctor", tags = "Doctors")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = DoctorDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid request data", content = @Content)
    })
    @PostMapping("/{id}/hospitals")
    public ResponseEntity<DoctorDTO> addHospitalToDoctor(@PathVariable Long id, @RequestBody String hospitalName) {
        log.info("Added hospital do doctor by id: {}", id);
        DoctorDTO doctorWithHospital = doctorService.addHospitalToDoctor(id, hospitalName);
        return ResponseEntity.ok(doctorWithHospital);
    }
}
