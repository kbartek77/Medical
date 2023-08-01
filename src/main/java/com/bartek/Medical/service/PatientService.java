package com.bartek.Medical.service;


import com.bartek.Medical.exception.InvalidEmailException;
import com.bartek.Medical.exception.PatientNotFoundException;
import com.bartek.Medical.mapper.PatientMapper;
import com.bartek.Medical.model.Patient;
import com.bartek.Medical.model.PatientDto;
import com.bartek.Medical.patientRepositoryImpl.PatientRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    public List<PatientDto> getAllPatients() {
        List<Patient> patients = patientRepository.findAll();
        return patientMapper.toDtoList(patients);
    }

    public PatientDto getPatientByEmail(String email) {
        Optional<Patient> patientOptional = patientRepository.findByEmail(email);
        Patient patient = patientOptional.orElseThrow(() -> new PatientNotFoundException("Pacjent o podanym adresie e-mail nie został odnaleziony."));
        return patientMapper.toDto(patient);
    }

    public PatientDto addPatient(PatientDto patientDto) {
        Optional<Patient> existingPatient = patientRepository.findByEmail(patientDto.getEmail());
        if (existingPatient.isPresent()) {
            throw new InvalidEmailException("Pacjent o podanym adresie e-mail już istnieje.");
        }

        Patient patient = patientMapper.toEntity(patientDto);

        patientRepository.save(patient);

        return patientDto;
    }

    public boolean deletePatientByEmail(String email) {
        Optional<Patient> patient = patientRepository.findByEmail(email);
        if (patient.isEmpty()) {
            throw new PatientNotFoundException("Pacjent o podanym adresie e-mail nie został odnaleziony.");
        }
        patientRepository.delete(patient.get());
        return true;
    }

    public PatientDto updatePatient(String email, PatientDto updatedPatientDto) {
        Optional<Patient> patientOptional = patientRepository.findByEmail(email);
        Patient patient = patientOptional.orElseThrow(() -> new PatientNotFoundException("Pacjent o podanym adresie e-mail nie został odnaleziony."));

        patientMapper.updateEntityFromDto(updatedPatientDto, patient);

        Patient updatedPatient = patientRepository.save(patient);

        return patientMapper.toDto(updatedPatient);
    }

    public boolean updatePassword(String email, String newPassword) {
        Optional<Patient> patientOptional = patientRepository.findByEmail(email);
        Patient patient = patientOptional.orElseThrow(() -> new PatientNotFoundException("Pacjent o podanym adresie e-mail nie został odnaleziony."));
        patient.setPassword(newPassword);

        patientRepository.save(patient);

        return true;
    }
}
