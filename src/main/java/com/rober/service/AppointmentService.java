package com.rober.service;

import com.rober.dto.*;

import java.util.List;

public interface AppointmentService {

    // Metodo para agendar una cita
    UserResponse scheduleAppointment(ScheduleAppointmentRequest request);

    // Metodo para cancelar una cita
    UserResponse cancelAppointment(CancelAppointmentRequest request);

    // Metodo para confirmar o rechazar una cita
    UserResponse confirmAppointment(ConfirmAppointmentRequest request);

    // Metodo para obtener todas las citas de un paciente o doctor
    List<AppointmentInfo> getAppointmentsByUserId(Integer userId);

    // Metodo para obtener una cita por su ID
    UserResponse getAppointmentById(Integer appointmentId,Integer userId);
}
