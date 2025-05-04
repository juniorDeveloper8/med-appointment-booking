package com.rober.service;

import com.rober.dto.*;

import java.util.List;

public interface AppointmentService {

    // Método para agendar una cita
    UserResponse scheduleAppointment(ScheduleAppointmentRequest request);

    // Método para cancelar una cita
    UserResponse cancelAppointment(CancelAppointmentRequest request);

    // Método para confirmar o rechazar una cita
    UserResponse confirmAppointment(ConfirmAppointmentRequest request);

    // Método para obtener todas las citas de un paciente o doctor
    List<AppointmentInfo> getAppointmentsByUserId(Integer userId);

    // Método para obtener una cita por su ID
    AppointmentInfo getAppointmentById(Integer appointmentId);
}
