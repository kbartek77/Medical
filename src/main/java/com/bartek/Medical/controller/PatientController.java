package com.bartek.Medical.controller;


import com.bartek.Medical.model.PatientDto;
import com.bartek.Medical.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;

    @GetMapping
    public List<PatientDto> showPatient() {
        return patientService.getAllPatients();
    }

    @GetMapping("/{email}")
    public PatientDto getPatient(@PathVariable String email) {
        return patientService.getPatientByEmail(email);
    }

    @PostMapping
    public PatientDto addPatient(@RequestBody PatientDto patientDto) {
        return patientService.addPatient(patientDto);
    }

    @DeleteMapping("/{email}")
    public boolean deletePatientbyEmail(@PathVariable String email) {
        return patientService.deletePatientByEmail(email);
    }

    @PutMapping("/{email}")
    public PatientDto updatePatient(@PathVariable String email, @RequestBody PatientDto updatedPatientDto) {
        return patientService.updatePatient(email, updatedPatientDto);
    }

    @PatchMapping("/{email}/password")
    public String editPassword(@PathVariable String email, @RequestBody String newPassword) {
        boolean passwordUpdated = patientService.updatePassword(email, newPassword);

        if (passwordUpdated) {
            return "Hasło zostało zaktualizowane.";
        } else {
            return "Pacjent o podanym adresie e-mail nie został odnaleziony.";
        }
    }
}