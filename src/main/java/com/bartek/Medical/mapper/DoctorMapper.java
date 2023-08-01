package com.bartek.Medical.mapper;

import com.bartek.Medical.model.Doctor;
import com.bartek.Medical.model.DoctorDTO;
import com.bartek.Medical.model.Patient;
import com.bartek.Medical.model.PatientDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DoctorMapper {
    DoctorDTO toDto(Doctor doctor);
    Doctor toEntity(DoctorDTO doctorDTO);
    List<DoctorDTO> toDtosList(List<Doctor> doctors);
}
