package com.rober.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private String responseCode;
    private String responseMessage;
    private AppointmentInfo appointmentInfo;
    private String status;


    public UserResponse(String responseCode, String responseMessage) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
        this.appointmentInfo = null;
        this.status = "PENDIENTE";
    }
}
