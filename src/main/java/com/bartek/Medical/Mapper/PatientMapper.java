package com.bartek.Medical.Mapper;

import com.bartek.Medical.Model.Patient;
import com.bartek.Medical.Model.PatientDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PatientMapper  {
    PatientDto toDto(Patient patient);
    Patient toEntity(PatientDto patientDto);
    List<PatientDto> toDtoList(List<Patient> patients);
    void updateEntityFromDto(PatientDto patientDto, @MappingTarget Patient patient);
}
