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
import com.bartek.Medical.model.MessageDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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

    public DoctorDTO getDoctorById(Long id) {
        Optional<Doctor> doctorOptional = doctorRepository.findById(id);
        Doctor doctor = doctorOptional.orElseThrow(() -> new DoctorNotFoundException("Doktor o podanym Id nie został odnazleziony. "));
        return doctorMapper.toDto(doctor);
    }

    public DoctorDTO addDoctor(DoctorDTO doctorDTO) {

        Doctor doctor = doctorMapper.toEntity(doctorDTO);

        doctorRepository.save(doctor);

        return doctorDTO;
    }

    public MessageDto deleteDoctorById(Long id) {
        Optional<Doctor> doctor = doctorRepository.findById(id);

        Doctor foundDoctor = doctor.orElseThrow(() -> new DoctorNotFoundException("Doktor o podanym Id nie został odnaleziony."));

        doctorRepository.delete(foundDoctor);
        return new MessageDto("Doktor został usunięty", HttpStatus.OK, "DELETE_DOCTOR");
    }

    public MessageDto updatePassoword(Long id, String newPassword) {
        Optional<Doctor> doctorOptional = doctorRepository.findById(id);
        Doctor doctor = doctorOptional.orElseThrow(() -> new DoctorNotFoundException("Doktor o podanym Id nie został odnaleziony."));
        doctor.setPassword(newPassword);
        doctorRepository.save(doctor);
        return new MessageDto("Twoje hasło zostało zmienione", HttpStatus.OK, "CHANGE_PASSWORD");
    }

    public DoctorDTO addHospitalToDoctor(Long id, String hospitalName) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new DoctorNotFoundException("Doktor o podanym Id nie został odnaleziony"));
        Hospital hospital = hospitalRepository.findByName(hospitalName)
                .orElseThrow(() -> new InvalidNameHospitalException("Szpital o takiej nazwie nie został odnaleziony"));

        doctor.setHospital(hospital);

        return doctorMapper.toDto(doctorRepository.save(doctor));
    }
}
