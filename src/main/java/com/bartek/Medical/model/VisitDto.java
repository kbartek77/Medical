package com.bartek.Medical.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisitDto {
    private Long id;
    private LocalDateTime dateTime;
    private LocalDateTime endDateTime;
    private Long patientId;
}