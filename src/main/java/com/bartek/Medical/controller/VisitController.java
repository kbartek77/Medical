package com.bartek.Medical.controller;

import com.bartek.Medical.model.VisitDto;
import com.bartek.Medical.service.VisitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/visits")
@AllArgsConstructor
public class VisitController {
    private final VisitService visitService;
    @Operation(summary = "Create new visit in database", tags = "Visit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = VisitDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid or taken date", content = @Content)
    })
    @PostMapping
    public ResponseEntity<VisitDto> createVisit(@RequestBody VisitDto visitDto) {
        log.info("Creating visit");
        VisitDto createdVisit = visitService.createVisit(visitDto);
        return ResponseEntity.ok(createdVisit);
    }
    @Operation(summary = "Assign patient to visit", tags = "Visit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = VisitDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid or taken date", content = @Content),
            @ApiResponse(responseCode = "404", description = "Visit not found", content = @Content)
    })
    @PatchMapping("/{visitId}/assign")
    public ResponseEntity<VisitDto> assignPatientToVisit(@PathVariable Long visitId, @RequestBody Long patientId) {
        log.info("Assign patient to visit");
        VisitDto assignedVisit = visitService.assignPatientToVisit(visitId, patientId);
        return ResponseEntity.ok(assignedVisit);
    }
    @Operation(summary = "Get all visits from one patient by id", tags = "Visit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = VisitDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid request data", content = @Content)
    })
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<VisitDto>> getPatientVisits(@PathVariable Long patientId) {
        log.info("get all visits from patient");
        List<VisitDto> patientVisits = visitService.getPatientVisits(patientId);
        return ResponseEntity.ok(patientVisits);
    }
}
