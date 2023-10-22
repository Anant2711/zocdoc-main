package com.zocdoc.payload;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Data
public class TimeSlotDTO {
    private Long doctorId;
    private LocalTime morningStartTime;
    private LocalTime morningEndTime;
    private LocalTime noonStartTime;
    private LocalTime noonEndTime;
    private LocalTime eveningStartTime;
    private LocalTime eveningEndTime;
    private int breakDuration;
    private Date date;

}

