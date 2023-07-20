package com.bartek.Medical.mapper;

import com.bartek.Medical.model.Hospital;
import com.bartek.Medical.model.HospitalDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HospitalMapper {
    HospitalDto toDto(Hospital hospital);
    Hospital toEntitiy(HospitalDto hospitalDto);
    List<HospitalDto> toDtoList (List<Hospital> hospitals);
}
