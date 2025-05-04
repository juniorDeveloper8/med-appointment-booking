package com.rober.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CancelAppointmentRequest {
    private Integer appointmentId;
    private Integer userId;
    private String cancelReason;
}
