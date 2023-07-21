package com.bartek.Medical.controller;

import com.bartek.Medical.model.DoctorDTO;
import com.bartek.Medical.model.HospitalDto;
import com.bartek.Medical.service.HospitalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/hospitals")
public class HospitalController {
    private final HospitalService hospitalService;

    @PostMapping
    public ResponseEntity<HospitalDto> addHospital(@RequestBody HospitalDto hospitalDto){
        HospitalDto addHospital = hospitalService.addHospital(hospitalDto);
        return ResponseEntity.ok(addHospital);
    }
    @GetMapping
    public List<HospitalDto> getAllHospitlas(){
        return hospitalService.getAllHospitals();
    }

    @GetMapping("/{hospitalName}")
    public List<DoctorDTO> getAllDoctorsFromHospital(@PathVariable String hospitalName){
        return hospitalService.getAllDoctorsFromHospital(hospitalName);
    }
}
