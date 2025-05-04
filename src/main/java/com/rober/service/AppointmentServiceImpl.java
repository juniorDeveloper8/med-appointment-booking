package com.rober.service;

import com.rober.dto.*;
import com.rober.entity.Appointment;
import com.rober.entity.User;
import com.rober.entity.enums.Status;
import com.rober.repositories.AppointmentRepository;
import com.rober.repositories.UserRepository;
import com.rober.utils.AppoitmentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentServiceImpl implements AppointmentService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    AppointmentRepository appointmentRepository;

    @Override
    public UserResponse scheduleAppointment(ScheduleAppointmentRequest request) {

        User doctor = userRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        User patient = userRepository.findById(request.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        Optional<Appointment> existingAppointment = appointmentRepository
                .findByDoctorIdAndStartDate(request.getDoctorId(), request.getStartDate());

        if (existingAppointment.isPresent()) {
            return new UserResponse(AppoitmentUtils.HORARIO_NO_DISPONIBLE_CODE, AppoitmentUtils.HORARIO_NO_DISPONIBLE_MSG);
        }

        Appointment appointment = Appointment.builder()
                .doctorId(request.getDoctorId())
                .patientId(request.getPatientId())
                .startDate(request.getStartDate())
                .endDate(request.getStartDate().plusMinutes(30))
                .status(Status.PENDIENTE)
                .type(request.getTypeQuery())
                .build();

        appointmentRepository.save(appointment);

        return new UserResponse(AppoitmentUtils.CITA_CONFIRMADA_CODE, AppoitmentUtils.CITA_CONFIRMADA_MSG);

    }

    @Override
    public UserResponse cancelAppointment(CancelAppointmentRequest request) {
        return null;
    }

    @Override
    public UserResponse confirmAppointment(ConfirmAppointmentRequest request) {
        return null;
    }

    @Override
    public List<AppointmentInfo> getAppointmentsByUserId(Integer userId) {
        return List.of();
    }

    @Override
    public AppointmentInfo getAppointmentById(Integer appointmentId) {
        return null;
    }
}
