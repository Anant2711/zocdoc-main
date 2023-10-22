package com.zocdoc.service.impl;

import com.zocdoc.entity.Appointment;
import com.zocdoc.entity.Doctor;
import com.zocdoc.entity.TimeSlot;
import com.zocdoc.entity.User;
import com.zocdoc.exception.CustomAppointmentException;
import com.zocdoc.payload.*;
import com.zocdoc.repository.AppointmentRepository;
import com.zocdoc.repository.DoctorRepository;
import com.zocdoc.repository.TimeSlotRepository;
import com.zocdoc.repository.UserRepository;
import com.zocdoc.service.AppointmentService;
import com.zocdoc.mapper.MapperHere;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    Date date;

    String strDate;
    @Autowired
    private TimeSlotRepository timeSlotRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MapperHere mapperHere;

    @Override
    public List<TimeSlotDtoOutput> getAvailableTimeSlots(
            TimeSlotDTO timeSlotDTO) {
        Long doctorId = timeSlotDTO.getDoctorId();
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new CustomAppointmentException("Doctor not found"));
        List<TimeSlot> timeSlots = calculateAvailableTimeSlots(timeSlotDTO, doctor);
        timeSlotRepository.saveAll(timeSlots);
        return timeSlots.stream().map(x -> mapperHere.mapToTimeSlotDtoOutput(x)).collect(Collectors.toList());
    }

    private List<TimeSlot> calculateAvailableTimeSlots(TimeSlotDTO timeSlotDTO, Doctor doctor) {
        LocalTime morningStartTime = timeSlotDTO.getMorningStartTime();
        LocalTime morningEndTime = timeSlotDTO.getMorningEndTime();
        LocalTime noonStartTime = timeSlotDTO.getNoonStartTime();
        LocalTime noonEndTime = timeSlotDTO.getNoonEndTime();
        LocalTime eveningStartTime = timeSlotDTO.getEveningStartTime();
        LocalTime eveningEndTime = timeSlotDTO.getEveningEndTime();
        int breakDuration = timeSlotDTO.getBreakDuration();

        Date currentDate = new Date();
        Date givenDate = timeSlotDTO.getDate();

        if (givenDate.before(currentDate)) {
            date = currentDate; // Use the current date if the given date is before it
        } else {
            date = givenDate; // Use the given date if it's not before the current date
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateOnly=dateFormat.format(date);
        try {
            date = dateFormat.parse(dateOnly.substring(0, 10));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        System.out.println(date);


        if (timeSlotRepository.existsByDateAndDoctorId(date, doctor.getId())) {
            throw new CustomAppointmentException("Slots are already Allotted for this Date!! " + date);
        }


        List<TimeSlot> availableTimeSlots = new ArrayList<>();

        List<LocalTime> timeRanges = new ArrayList<>();
        timeRanges.add(morningStartTime);
        timeRanges.add(morningEndTime);
        timeRanges.add(noonStartTime);
        timeRanges.add(noonEndTime);
        timeRanges.add(eveningStartTime);
        timeRanges.add(eveningEndTime);

        for (int i = 0; i < timeRanges.size() - 1; i+=2) {
            LocalTime startTime = timeRanges.get(i);
            LocalTime endTime = timeRanges.get(i + 1);

            // Use morningEndTime and noonEndTime in calculations
            if (endTime.equals(morningEndTime) || endTime.equals(noonEndTime)) {
                endTime = endTime.minusMinutes(breakDuration);
            }

            while (startTime.isBefore(endTime)) {
                int updatedBreakDuration = getBreakDurationForTime(startTime, doctor, date, breakDuration);

                LocalTime endTimeSlot = startTime.plusMinutes(updatedBreakDuration);

                LocalDateTime currentDateTime = LocalDateTime.of(LocalDate.now(), startTime);
                LocalDateTime endDateTime = LocalDateTime.of(LocalDate.now(), endTimeSlot);

                if (currentDateTime.isAfter(LocalDateTime.now()) && currentDateTime.isBefore(endDateTime)) {

                    // Doctor doctor1 = doctorRepository.findById(doctor.getId()).get();

                    TimeSlot updatedTimeSlotDto = new TimeSlot();

                    updatedTimeSlotDto.setDoctorId(doctor.getId());
                    updatedTimeSlotDto.setStartTime(currentDateTime.toLocalTime());
                    updatedTimeSlotDto.setEndTime(endDateTime.toLocalTime());
                    updatedTimeSlotDto.setDate(date);
                    updatedTimeSlotDto.setAvailable(true);

                    availableTimeSlots.add(updatedTimeSlotDto);
                }

                startTime = endTimeSlot;
            }
        }

        return availableTimeSlots;
    }

    private int getBreakDurationForTime(LocalTime startTime, Doctor doctor, Date date, int defaultBreakDuration) {
        return defaultBreakDuration;
    }



    @Override
    @Transactional
    public AppointmentDTO bookAppointment(AppointmentDTO appointmentDTO) {
        Long doctorId = appointmentDTO.getDoctorId();
        Long userId = appointmentDTO.getUserId();

        System.out.println(doctorId);
        System.out.println(userId);

        TimeSlotConverterOutputDto timeSlotDTO = appointmentDTO.getTimeSlot();

        Date currentDate = new Date();
        Date givenDate = timeSlotDTO.getDate();

        if (givenDate.before(currentDate)) {
            date = currentDate; // Use the current date if the given date is before it
        } else {
            date = givenDate; // Use the given date if it's not before the current date
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateOnly=dateFormat.format(date);
        try {
            date = dateFormat.parse(dateOnly.substring(0, 10));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        System.out.println(date);

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new CustomAppointmentException("Doctor not found!!"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomAppointmentException("User not found!!"));

        TimeSlot byDoctorIdAndDate = timeSlotRepository.findByDoctorIdAndDate(doctorId, date);
        if(byDoctorIdAndDate==null){
            throw new CustomAppointmentException("TimeSlot Not generated for this up-coming Date"+date);
        }

        // Create a new TimeSlot entity from the TimeSlotDTO
        TimeSlot timeSlotHere = mapperHere.mapTOTimeSlotConverterOutputEntity(timeSlotDTO);
        System.out.println(timeSlotHere.getStartTime());
        System.out.println(timeSlotHere.getDate());
        TimeSlot timeSlot = timeSlotRepository.findByStartTimeAndDateAndDoctorId(
                timeSlotHere.getStartTime(),
                date,
                doctorId
        );
        System.out.println(timeSlot.getId()+timeSlot.getDoctorId());

        if (!timeSlot.isAvailable()) {
            throw new CustomAppointmentException("Selected slot is Already Taken!!");
        }

        timeSlot.setAvailable(false);
        timeSlotRepository.save(timeSlot);


        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setTimeSlot(timeSlot);
        appointment.setUser(user);

        Appointment savedAppointment = appointmentRepository.save(appointment);

        // Create and return the AppointmentDTO for the saved appointment
        return mapperHere.mapToAppointmentDto(savedAppointment);
    }

    @Override
    public List<TimeSlotDtoOutput> getAllTimeSlots(Long id) {
        List<TimeSlot> timeSlot = timeSlotRepository.findByDoctorId(id);
        return timeSlot.stream().map(x->mapperHere.mapToTimeSlotDtoOutput(x)).collect(Collectors.toList());
    }

    @Override
    public List<AppointmentDtoOutput> getAppointment(long doctorId, long userId) {
        List<Appointment> allAppointments= appointmentRepository.findByDoctorIdOrUserId(doctorId,userId);
        return allAppointments.stream().map(x->mapperHere.mapToAppointmentDtoOutput(x)).collect(Collectors.toList());
    }

}