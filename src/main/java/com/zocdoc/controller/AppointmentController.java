package com.zocdoc.controller;

import com.zocdoc.payload.*;
import com.zocdoc.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;


    @GetMapping("/timeslots")
    public ResponseEntity<List<TimeSlotDtoOutput>> getAvailableTimeSlots(
            @RequestBody TimeSlotDTO timeSlotDTO) {
        List<TimeSlotDtoOutput> availableTimeSlots = appointmentService.getAvailableTimeSlots(timeSlotDTO);
        return new ResponseEntity<>(availableTimeSlots, HttpStatus.OK);
    }

    @PostMapping("/book")
    public ResponseEntity<AppointmentDTO> bookAppointment(
            @RequestBody AppointmentDTO appointmentDTO) {

        AppointmentDTO bookedAppointment = appointmentService.bookAppointment(appointmentDTO);
        return ResponseEntity.ok(bookedAppointment);
    }


    @PostMapping("/getAppointment")
    public ResponseEntity<List<AppointmentDtoOutput>> getAppointment(
            @RequestParam(value = "doctorId",required = false, defaultValue = "0")long doctorId,
            @RequestParam(value = "userId")long userId) {

        List<AppointmentDtoOutput> bookedAppointment = appointmentService.getAppointment(doctorId,userId);
        return ResponseEntity.ok(bookedAppointment);
    }


    @GetMapping()
    public ResponseEntity<List<TimeSlotDtoOutput>> getAllTimeSlots(
            @RequestParam("doctorId") Long id) {
        return new ResponseEntity<>(appointmentService.getAllTimeSlots(id),HttpStatus.OK);
    }

}



