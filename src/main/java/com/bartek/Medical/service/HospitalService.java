package com.bartek.Medical.service;

import com.bartek.Medical.exception.InvalidNameHospitalException;
import com.bartek.Medical.hospitalRepository.HospitalRepository;
import com.bartek.Medical.mapper.DoctorMapper;
import com.bartek.Medical.mapper.HospitalMapper;
import com.bartek.Medical.model.Doctor;
import com.bartek.Medical.model.DoctorDTO;
import com.bartek.Medical.model.Hospital;
import com.bartek.Medical.model.HospitalDto;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HospitalService {
    private final HospitalRepository hospitalRepository;
    private final HospitalMapper hospitalMapper;
    private final DoctorMapper doctorMapper;

    public List<HospitalDto> getAllHospitals() {
        List<Hospital> hospitals = hospitalRepository.findAll();
        return hospitalMapper.toDtoList(hospitals);
    }

    public HospitalDto addHospital(HospitalDto hospitalDto) {
        Optional<Hospital> existingHospitalOptional = hospitalRepository.findByName(hospitalDto.getName());

        if (existingHospitalOptional.isPresent()) {
            throw new InvalidNameHospitalException("Taka nazwa szpitala już istnieje");
        }

        Hospital hospital = hospitalMapper.toEntity(hospitalDto);

        Set<Doctor> doctors = hospital.getDoctors();
        if (doctors == null) {
            doctors = new HashSet<>();
            hospital.setDoctors(doctors);
        }

        hospitalRepository.save(hospital);
        return hospitalDto;
    }

    public List<DoctorDTO> getAllDoctorsFromHospital(String hospitalName) {
        Hospital hospital = hospitalRepository.findByName(hospitalName)
                .orElseThrow(() -> new InvalidNameHospitalException("Nie ma Szpitala o takiej nazwie"));
        return hospital.getDoctors().stream().map(doctorMapper::toDto).collect(Collectors.toList());
    }
}
