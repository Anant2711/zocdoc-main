package com.zocdoc.service.impl;

import com.zocdoc.entity.Doctor;
import com.zocdoc.entity.Role;
import com.zocdoc.exception.CustomRegistrationException;
import com.zocdoc.exception.UserNotFoundException;
import com.zocdoc.mapper.MapperHere;
import com.zocdoc.payload.DoctorDTO;
import com.zocdoc.repository.DoctorRepository;
import com.zocdoc.repository.RoleRepository;
import com.zocdoc.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private MapperHere mapperHere;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public DoctorDTO addDoctor(DoctorDTO doctorDTO) {

        if(doctorRepository.existsByPhoneNumber(doctorDTO.getPhoneNumber())){
            throw new CustomRegistrationException("PhoneNumber is already taken! "+doctorDTO.getPhoneNumber());
        }

        if(doctorRepository.existsByEmail(doctorDTO.getEmail())){
            throw new CustomRegistrationException("Email is already taken! "+doctorDTO.getEmail());
        }

        Doctor doctor = mapperHere.mapToDoctorEntity(doctorDTO);
        doctor.setApproved(false);
        doctor.setPassword(passwordEncoder.encode(doctorDTO.getPassword()));

        Role roles = roleRepository.findByName("ROLE_DOCTOR").get();
        doctor.setRoles(Collections.singleton(roles));

        Doctor save = doctorRepository.save(doctor);
        return mapperHere.mapToDoctorDto(save);
    }


    public List<DoctorDTO> searchDoctors(String place, String specialization, String pincode, String city) {
        return doctorRepository.findByPermanentLocationContainingIgnoreCaseOrSpecializationContainingIgnoreCaseAndPincodeOrCityAndApproved(
                place, specialization, pincode, city, true
        ).stream().map(mapperHere::mapToDoctorDto).collect(Collectors.toList());

    }

    public List<DoctorDTO> getAllDoctors() {
        List<Doctor> doctors = doctorRepository.findAll();
        return doctors.stream()
                .map(doc->mapperHere.mapToDoctorDto(doc))
                .collect(Collectors.toList());
    }



}

