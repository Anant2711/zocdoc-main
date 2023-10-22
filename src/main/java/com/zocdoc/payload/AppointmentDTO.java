package com.zocdoc.payload;

import lombok.Data;

@Data
public class AppointmentDTO {

    private Long doctorId;
    private TimeSlotConverterOutputDto timeSlot;
    private Long userId;

}

