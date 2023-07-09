package com.bartek.Medical.Controller;

import com.bartek.Medical.Model.VisitDto;
import com.bartek.Medical.Service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/visits")

public class VisitController {
    private final VisitService visitService;

    public VisitController(VisitService visitService) {
        this.visitService = visitService;
    }

    @PostMapping
    public ResponseEntity<VisitDto> createVisit(@RequestParam LocalDateTime dateTime) {
        VisitDto createdVisit = visitService.createVisit(dateTime);
        return ResponseEntity.ok(createdVisit);
    }

    @PostMapping("/{visitId}/assign")
    public ResponseEntity<VisitDto> assignPatientToVisit(@PathVariable Long visitId, @RequestParam Long patientId) {
        VisitDto assignedVisit = visitService.assignPatientToVisit(visitId, patientId);
        return ResponseEntity.ok(assignedVisit);
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<VisitDto>> getPatientVisits(@PathVariable Long patientId) {
        List<VisitDto> patientVisits = visitService.getPatientVisits(patientId);
        return ResponseEntity.ok(patientVisits);
    }
}
