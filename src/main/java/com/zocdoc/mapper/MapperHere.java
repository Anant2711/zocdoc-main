package com.zocdoc.mapper;

import com.zocdoc.entity.*;
import com.zocdoc.payload.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MapperHere {

    @Autowired
    private ModelMapper modelMapper;

    public Doctor mapToDoctorEntity(DoctorDTO doctorDTO){
        return modelMapper.map(doctorDTO, Doctor.class);
    }
    public DoctorDTO mapToDoctorDto(Doctor doctor){
        return  modelMapper.map(doctor, DoctorDTO.class);
    }
    public Admin mapToAdminEntity(AdminDTO adminDTO){
        return modelMapper.map(adminDTO, Admin.class);
    }
    public AdminDTO mapToAdminDto(Admin admin){
        return  modelMapper.map(admin, AdminDTO.class);
    }
    public User mapToUserEntity(UserDTO user){
        return modelMapper.map(user, User.class);
    }
    public UserDTO mapToUserDto(User user){
        return modelMapper.map(user,UserDTO.class);
    }
    public TimeSlot mapToTimeSlotEntity(TimeSlotDTO timeSlotDTO){
        return modelMapper.map(timeSlotDTO, TimeSlot.class);
    }
    public TimeSlotDTO mapToTimeSlotDto(TimeSlot timeSlot){
        return  modelMapper.map(timeSlot, TimeSlotDTO.class);
    }
    public TimeSlot mapTOTimeSlotConverterOutputEntity(TimeSlotConverterOutputDto timeSlot){
        return  modelMapper.map(timeSlot, TimeSlot.class);
    }
    public TimeSlotDtoOutput mapToTimeSlotDtoOutput(TimeSlot timeSlot){
        return modelMapper.map(timeSlot, TimeSlotDtoOutput.class);
    }

    public Appointment mapToAppointmentEntity(AppointmentDTO appointment){
        return modelMapper.map(appointment,Appointment.class);
    }
    public AppointmentDTO mapToAppointmentDto(Appointment appointment){
        return  modelMapper.map(appointment, AppointmentDTO.class);
    }

    public AppointmentDtoOutput mapToAppointmentDtoOutput(Appointment appointment) {
        return modelMapper.map(appointment,AppointmentDtoOutput.class);
    }
}
