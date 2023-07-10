package com.bartek.Medical.Controller;

import com.bartek.Medical.Model.VisitDto;
import com.bartek.Medical.Service.VisitService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/visits")
@AllArgsConstructor
public class VisitController {
    VisitService visitService;

    @PostMapping
    public ResponseEntity<VisitDto> createVisit(@RequestBody VisitDto visitDto) {
        VisitDto createdVisit = visitService.createVisit(visitDto.getDateTime());
        return ResponseEntity.ok(createdVisit);
    }

    @PatchMapping("/{visitId}/assign")
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
