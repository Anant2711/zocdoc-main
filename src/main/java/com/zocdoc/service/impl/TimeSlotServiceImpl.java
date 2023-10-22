//package com.zocdoc.service.impl;
//
//import com.zocdoc.entity.TimeSlot;
//import com.zocdoc.mapper.MapperHere;
//import com.zocdoc.payload.TimeSlotDTO;
//import com.zocdoc.repository.TimeSlotRepository;
//import com.zocdoc.service.TimeSlotService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//import java.time.LocalTime;
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class TimeSlotServiceImpl implements TimeSlotService {
//
//    @Autowired
//    private TimeSlotRepository timeSlotRepository;
//
//    @Autowired
//    private MapperHere mapperHere;
//
//    @Override
//    public List<TimeSlotDTO> generateTimeSlotsForDay() {
//        List<TimeSlotDTO> generatedSlots = new ArrayList<>();
//        LocalTime startTime = LocalTime.of(9, 0); // Start time for morning slots
//        LocalTime endTimeMorning = LocalTime.of(12, 0);
//        LocalTime endTimeDay = LocalTime.of(18, 0);
//        LocalTime endTimeEvening = LocalTime.of(21, 0); // End time for evening slots
//
//        while (startTime.isBefore(endTimeMorning)) {
//            generatedSlots.add(createTimeSlotDTO(startTime));
//            startTime = startTime.plusMinutes(15);
//        }
//
//        startTime = LocalTime.of(12, 0);
//        while (startTime.isBefore(endTimeDay)) {
//            generatedSlots.add(createTimeSlotDTO(startTime));
//            startTime = startTime.plusMinutes(15);
//        }
//
//        startTime = LocalTime.of(18, 0);
//        while (startTime.isBefore(endTimeEvening)) {
//            generatedSlots.add(createTimeSlotDTO(startTime));
//            startTime = startTime.plusMinutes(15);
//        }
//
//        // Save generated slots to the repository
//        List<TimeSlot> timeSlotEntities = new ArrayList<>();
//        for (TimeSlotDTO timeSlotDTO : generatedSlots) {
//            timeSlotEntities.add(mapperHere.mapToTimeSlotEntity(timeSlotDTO));
//        }
//        timeSlotRepository.saveAll(timeSlotEntities);
//
//        return generatedSlots;
//    }
//
//    // Other methods
//
//    private TimeSlotDTO createTimeSlotDTO(LocalTime time) {
//        TimeSlotDTO timeSlotDTO = new TimeSlotDTO();
//        timeSlotDTO.setStartTime(time.atDate(LocalDate.now()));
//        timeSlotDTO.setEndTime(time.plusMinutes(15).atDate(LocalDate.now()));
//        timeSlotDTO.setAvailable(true);
//        return timeSlotDTO;
//    }
//}
