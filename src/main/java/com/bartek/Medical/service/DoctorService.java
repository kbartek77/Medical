package com.bartek.Medical.service;

import com.bartek.Medical.doctorRepository.DoctorRepository;
import com.bartek.Medical.exception.DoctorNotFoundException;
import com.bartek.Medical.exception.InvalidEmailException;
import com.bartek.Medical.exception.InvalidNameHospitalException;
import com.bartek.Medical.hospitalRepository.HospitalRepository;
import com.bartek.Medical.mapper.DoctorMapper;
import com.bartek.Medical.model.Doctor;
import com.bartek.Medical.model.DoctorDTO;
import com.bartek.Medical.model.Hospital;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;
    private HospitalRepository hospitalRepository;

    public List<DoctorDTO> getAllDoctors() {
        List<Doctor> doctors = doctorRepository.findAll();
        return doctorMapper.toDtosList(doctors);
    }

    public DoctorDTO getDoctorById(Long Id) {
        Optional<Doctor> doctorOptional = doctorRepository.findById(Id);
        Doctor doctor = doctorOptional.orElseThrow(() -> new DoctorNotFoundException("Doktor o podanym Id nie został odnazleziony. "));
        return doctorMapper.toDto(doctor);
    }

    public DoctorDTO addDoctor(DoctorDTO doctorDTO) {
        Doctor doctor = doctorMapper.toEntity(doctorDTO);

        doctorRepository.save(doctor);

        return doctorDTO;
    }

    public boolean deleteDoctorById(Long Id) {
        Optional<Doctor> doctor = doctorRepository.findById(Id);
        if (doctor.isEmpty()) {
            throw new DoctorNotFoundException("Doktor o podanym Id nie został odnaleziony.");
        }
        doctorRepository.delete(doctor.get());
        return true;
    }

    public boolean updatePassowrd(Long Id, String newPassword) {
        Optional<Doctor> doctorOptional = doctorRepository.findById(Id);
        Doctor doctor = doctorOptional.orElseThrow(() -> new DoctorNotFoundException("Doktor o podanym Id nie został odnaleziony."));
        doctor.setPassword(newPassword);
        doctorRepository.save(doctor);
        return true;
    }

    public DoctorDTO addHospitalToDoctor(Long Id, String hospitalName) {
        Doctor doctor = doctorRepository.findById(Id)
                .orElseThrow(() -> new DoctorNotFoundException("Doktor o podanym Id nie został odnaleziony"));
        Hospital hospital = hospitalRepository.findByName(hospitalName)
                .orElseThrow(() -> new InvalidNameHospitalException("Szpital o takiej nazwie nie został odnaleziony"));

        doctor.getHospitals().add(hospital);

        doctorRepository.save(doctor);

        return doctorMapper.toDto(doctor);
    }
}
