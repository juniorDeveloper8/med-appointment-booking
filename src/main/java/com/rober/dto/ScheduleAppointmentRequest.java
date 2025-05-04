package com.rober.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleAppointmentRequest {

    private Integer patientId;
    private Integer doctorId;
    private LocalDateTime startDate;
    private String typeQuery;
}
