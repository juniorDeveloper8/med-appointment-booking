package com.rober.dto;

import com.rober.entity.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmAppointmentRequest {

    private Integer appointmentId;
    private Integer doctorId;
    private Status status;
}
