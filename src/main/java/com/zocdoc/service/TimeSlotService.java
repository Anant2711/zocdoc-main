package com.zocdoc.service;

import com.zocdoc.payload.TimeSlotDTO;

import java.util.List;

public interface TimeSlotService {
    List<TimeSlotDTO> generateTimeSlotsForDay();
}
