package com.bartek.Medical.mapper;

import com.bartek.Medical.model.Doctor;
import com.bartek.Medical.model.DoctorDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spiring")
public interface DoctorMapper {
    DoctorDTO toDto(Doctor doctor);
    Doctor toEntity(DoctorDTO doctorDTO);
}
