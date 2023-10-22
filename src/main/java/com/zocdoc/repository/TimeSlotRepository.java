package com.zocdoc.repository;

import com.zocdoc.entity.Doctor;
import com.zocdoc.entity.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {
    TimeSlot findByStartTimeAndDateAndDoctorId(LocalTime startTime,  Date date, Long doctorId);

    List<TimeSlot> findByDoctorId(Long id);

    boolean existsByDateAndDoctorId(Date date, Long doctorId);

    TimeSlot findByDoctorIdAndDate(Long doctorId, Date date);
}
