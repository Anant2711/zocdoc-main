package com.zocdoc.payload;

import lombok.Data;

@Data
public class AppointmentDtoOutput {

    private DoctorDtoOutput doctor;
    private TimeSlotConverterOutputDto timeSlot;
    private UserDtoOutput user;

}

