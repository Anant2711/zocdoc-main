package com.zocdoc.payload;

import lombok.Data;
import java.time.LocalTime;
import java.util.Date;

@Data

public class TimeSlotConverterOutputDto {
    private LocalTime startTime;
    private LocalTime endTime;
    private Date date;
}
