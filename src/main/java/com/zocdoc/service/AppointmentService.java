package com.zocdoc.service;

import com.zocdoc.entity.Appointment;
import com.zocdoc.entity.Doctor;
import com.zocdoc.entity.TimeSlot;
import com.zocdoc.payload.*;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public interface AppointmentService {

    public List<TimeSlotDtoOutput> getAvailableTimeSlots(TimeSlotDTO timeSlotDTO);
    AppointmentDTO bookAppointment(AppointmentDTO appointmentDTO);

    List<TimeSlotDtoOutput> getAllTimeSlots(Long id);

    List<AppointmentDtoOutput> getAppointment(long doctorId, long userId);
}

