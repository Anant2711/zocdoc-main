package com.zocdoc.payload;

import com.zocdoc.entity.Doctor;
import lombok.Data;

import java.time.LocalTime;
import java.util.Date;

@Data
public class TimeSlotDtoOutput {
    private Long doctorId;
    private LocalTime startTime;
    private LocalTime endTime;
    private Date date;
    private Boolean available;
}
