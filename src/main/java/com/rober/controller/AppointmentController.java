package com.rober.controller;

import com.rober.dto.*;
import com.rober.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/appoitment")
public class AppointmentController {

    @Autowired
    AppointmentService service;

    @PostMapping
    public UserResponse scheduleAppointment(@RequestBody ScheduleAppointmentRequest request){
        return service.scheduleAppointment(request);
    }

    @PostMapping
    public UserResponse confirmAppointment(@RequestBody ConfirmAppointmentRequest request) {
        return service.confirmAppointment(request);
    }

    @PostMapping
    public UserResponse cancelAppointment(@RequestBody CancelAppointmentRequest request){
        return service.cancelAppointment(request);
    }

    @GetMapping("userId")
    public AppointmentInfo getAppointmentsByUserId(@PathVariable Integer userId){
        return (AppointmentInfo) service.getAppointmentsByUserId(userId);
    }

    @GetMapping
    public UserResponse getAppointmesById(@PathVariable Integer appointmentId, @PathVariable Integer userId ){
        return service.getAppointmentById(appointmentId, userId);
    }
}
