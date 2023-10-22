package com.zocdoc.repository;

import com.zocdoc.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByDoctorIdOrUserId(long doctorId, long userId);
}
