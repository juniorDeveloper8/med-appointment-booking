package com.rober.repositories;

import com.rober.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    Optional<Appointment> findByDoctorIdAndStartDate(Integer doctorId, LocalDateTime startDate);

    List<Appointment> findByDoctorIdOrPatientId(Integer doctorId, Integer patientId);

}
