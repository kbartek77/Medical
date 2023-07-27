package com.bartek.Medical.controller;

import com.bartek.Medical.model.Doctor;
import com.bartek.Medical.model.DoctorDTO;
import com.bartek.Medical.model.MessageDto;
import com.bartek.Medical.service.DoctorService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctors")
@RequiredArgsConstructor
public class DoctorController {
    private final DoctorService doctorService;

    @GetMapping
    public List<DoctorDTO> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorDTO> getDoctorById(@PathVariable Long id) {
        DoctorDTO doctor = doctorService.getDoctorById(id);
        return ResponseEntity.ok(doctor);
    }

    @PostMapping
    public ResponseEntity<DoctorDTO> addDoctor(@RequestBody DoctorDTO doctorDTO) {
        DoctorDTO savedDoctor = doctorService.addDoctor(doctorDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDoctor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity <MessageDto> deleteDoctorById(@PathVariable Long id) {
        MessageDto messageDto = doctorService.deleteDoctorById(id);
        return ResponseEntity.status(messageDto.getHttpStatus()).body(messageDto);
    }

    @PatchMapping("/{id}/password")
    public ResponseEntity<MessageDto> updatePassword(@PathVariable Long id, @RequestBody String newPassword) {
        MessageDto messageDto = doctorService.updatePassoword(id, newPassword);
        return ResponseEntity.status(messageDto.getHttpStatus()).body(messageDto);
    }

    @PostMapping("/{id}/hospitals")
    public ResponseEntity<DoctorDTO> addHospitalToDoctor(@PathVariable Long id, @RequestParam String hospitalName) {
        DoctorDTO doctorWithHospital = doctorService.addHospitalToDoctor(id, hospitalName);
        return ResponseEntity.ok(doctorWithHospital);
    }
}
