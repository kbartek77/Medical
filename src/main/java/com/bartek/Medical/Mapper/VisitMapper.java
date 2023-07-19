package com.bartek.Medical.Mapper;

import com.bartek.Medical.Model.Visit;
import com.bartek.Medical.Model.VisitDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VisitMapper {
    VisitDto toDto(Visit visit);
    Visit toEntity(VisitDto visitDto);
    List<VisitDto> toDtos(List<Visit> visits);
    List<Visit> toEntities(List<VisitDto> visitDtos);
}
