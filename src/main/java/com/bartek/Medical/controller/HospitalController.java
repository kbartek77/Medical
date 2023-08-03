package com.bartek.Medical.controller;

import com.bartek.Medical.model.DoctorDTO;
import com.bartek.Medical.model.HospitalDto;
import com.bartek.Medical.service.HospitalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/hospitals")
public class HospitalController {
    private final HospitalService hospitalService;

    @Operation(summary = "Add hospital to database", tags = "Hospital")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = HospitalDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid request data", content = @Content)
    })
    @PostMapping
    public ResponseEntity<HospitalDto> addHospital(@RequestBody HospitalDto hospitalDto) {
        log.info("added Hospitals {}",hospitalDto.toString());
        HospitalDto addHospital = hospitalService.addHospital(hospitalDto);
        return ResponseEntity.ok(addHospital);
    }
    @Operation(summary = "Get all hospitals from database", tags = "Hospital")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = HospitalDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid request data", content = @Content)
    })
    @GetMapping
    public List<HospitalDto> getAllHospitlas() {
        log.info("Getting all hospitals...");
        return hospitalService.getAllHospitals();
    }
    @Operation(summary = "Get all doctors from one hospital", tags = "Hospital")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = HospitalDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid request data", content = @Content)
    })
    @GetMapping("/{hospitalName}")
    public List<DoctorDTO> getAllDoctorsFromHospital(@PathVariable String hospitalName) {
        log.info("Getting all doctors from one hospital..");
        return hospitalService.getAllDoctorsFromHospital(hospitalName);
    }
}

