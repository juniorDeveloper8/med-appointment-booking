package com.rober.service;

import com.rober.dto.*;
import com.rober.entity.Appointment;
import com.rober.entity.User;
import com.rober.entity.enums.Status;
import com.rober.repositories.AppointmentRepository;
import com.rober.repositories.UserRepository;
import com.rober.utils.AppointmentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AppointmentServiceImpl implements AppointmentService {

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
            return UserResponse.builder()
                    .responseCode(AppointmentUtils.HORARIO_NO_DISPONIBLE_CODE)
                    .responseMessage(AppointmentUtils.HORARIO_NO_DISPONIBLE_MSG)
                    .build();
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

        return UserResponse.builder()
                .responseCode(AppointmentUtils.CITA_CONFIRMADA_CODE)
                .responseMessage(AppointmentUtils.CITA_CONFIRMADA_MSG)
                .build();
    }

    @Override
    public UserResponse cancelAppointment(CancelAppointmentRequest request) {

        Optional<Appointment> optionalAppointment = appointmentRepository.findById(request.getAppointmentId());

        if (optionalAppointment.isEmpty()) {
            return UserResponse.builder()
                    .responseCode(AppointmentUtils.CITA_NO_ENCONTRADA_CODE)
                    .responseMessage(AppointmentUtils.CITA_NO_ENCONTRADA_MSG)
                    .build();
        }

        Appointment appointment = optionalAppointment.get();

        if (appointment.getStatus() == Status.CANCELADA || appointment.getStatus() == Status.CONFIRMADA) {
            return UserResponse.builder()
                    .responseCode(AppointmentUtils.CITA_ESTADO_INVALIDO_CODE)
                    .responseMessage(AppointmentUtils.CITA_ESTADO_INVALIDO_MSG)
                    .build();
        }

        // Solo cambiamos el estado a CANCELADA
        appointment.setStatus(Status.CANCELADA);
        appointmentRepository.save(appointment);

        return UserResponse.builder()
                .responseCode(AppointmentUtils.CITA_CANCELADA_CODE)
                .responseMessage(AppointmentUtils.CITA_CANCELADA_MSG)
                .build();
    }

    @Override
    public UserResponse confirmAppointment(ConfirmAppointmentRequest request) {

        Optional<Appointment> optionalAppointment = appointmentRepository.findById(request.getAppointmentId());

        if (optionalAppointment.isEmpty()) {
            return UserResponse.builder()
                    .responseCode(AppointmentUtils.CITA_NO_ENCONTRADA_CODE)
                    .responseMessage(AppointmentUtils.CITA_NO_ENCONTRADA_MSG)
                    .build();
        }

        Appointment appointment = optionalAppointment.get();

        if (appointment.getStatus() != Status.PENDIENTE) {
            return UserResponse.builder()
                    .responseCode(AppointmentUtils.CITA_ESTADO_INVALIDO_CODE)
                    .responseMessage(AppointmentUtils.CITA_ESTADO_INVALIDO_MSG)
                    .build();
        }

        appointment.setStatus(request.getStatus());
        appointmentRepository.save(appointment);

        String code = request.getStatus() == Status.CONFIRMADA
                ? AppointmentUtils.CITA_CONFIRMADA_CODE
                : AppointmentUtils.CITA_CANCELADA_CODE;

        String msg = request.getStatus() == Status.CONFIRMADA
                ? AppointmentUtils.CITA_CONFIRMADA_MSG
                : AppointmentUtils.CITA_CANCELADA_MSG;

        return UserResponse.builder()
                .responseCode(code)
                .responseMessage(msg)
                .build();
    }

    @Override
    public List<AppointmentInfo> getAppointmentsByUserId(Integer userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Obtenemos las citas donde el usuario es doctor o paciente
        List<Appointment> appointments = appointmentRepository.findByDoctorIdOrPatientId(userId, userId);

        // Convertimos cada cita en un DTO AppointmentInfo
        return appointments.stream().map(appointment -> {

            User doctor = userRepository.findById(appointment.getDoctorId())
                    .orElse(null);

            return AppointmentInfo.builder()
                    .nameDoctor(doctor != null ? doctor.getName() : "Desconocido")
                    .TypeQuery(appointment.getType())
                    .startDate(appointment.getStartDate())
                    .status(appointment.getStatus().name())
                    .build();
        }).collect(Collectors.toList());
    }

    @Override
    public UserResponse getAppointmentById(Integer userId, Integer appointmentId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));


        Optional<Appointment> optionalAppointment = appointmentRepository.findById(appointmentId);

        // Si no se encuentra la cita
        if (optionalAppointment.isEmpty()) {
            return UserResponse.builder()
                    .responseCode(AppointmentUtils.CITA_NO_ENCONTRADA_CODE)
                    .responseMessage(AppointmentUtils.CITA_NO_ENCONTRADA_MSG)
                    .build();
        }

        Appointment appointment = optionalAppointment.get();

        // Verificamos que el usuario tenga acceso a esta cita (que sea doctor o paciente asociado)
        if (!appointment.getDoctorId().equals(userId) && !appointment.getPatientId().equals(userId)) {
            return UserResponse.builder()
                    .responseCode(AppointmentUtils.CITA_NO_ACCESO_CODE)
                    .responseMessage("El usuario no tiene acceso a esta cita.")
                    .build();
        }

        User doctor = userRepository.findById(appointment.getDoctorId())
                .orElse(null);

        AppointmentInfo appointmentInfo = AppointmentInfo.builder()
                .nameDoctor(doctor != null ? doctor.getName() : "Desconocido")
                .TypeQuery(appointment.getType())
                .startDate(appointment.getStartDate())
                .status(appointment.getStatus().name())
                .build();

        return UserResponse.builder()
                .responseCode(AppointmentUtils.CITA_LISTADA_CODE)
                .responseMessage(AppointmentUtils.CITA_LISTADA_MSG)
                .appointmentInfo(appointmentInfo)
                .build();
    }


}
