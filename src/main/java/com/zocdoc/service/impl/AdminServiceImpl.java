package com.zocdoc.service.impl;


import com.zocdoc.entity.Admin;
import com.zocdoc.entity.Doctor;
import com.zocdoc.entity.Role;
import com.zocdoc.exception.CustomRegistrationException;
import com.zocdoc.exception.UserNotFoundException;
import com.zocdoc.mapper.MapperHere;
import com.zocdoc.payload.AdminDTO;
import com.zocdoc.repository.AdminRepository;
import com.zocdoc.repository.DoctorRepository;
import com.zocdoc.repository.RoleRepository;
import com.zocdoc.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MapperHere mapperHere;
    @Override
    public AdminDTO addAdmin(AdminDTO adminDTO) {

        if(adminRepository.existsByPhoneNumber(adminDTO.getPhoneNumber())){
            throw new CustomRegistrationException("PhoneNumber is already taken! "+adminDTO.getPhoneNumber());
        }

        if(adminRepository.existsByEmail(adminDTO.getEmail())){
            throw new CustomRegistrationException("Email is already taken! "+adminDTO.getEmail());
        }
        Admin admin = mapperHere.mapToAdminEntity(adminDTO);

        admin.setEnabled(true);
        admin.setPassword(passwordEncoder.encode(adminDTO.getPassword()));

        Role roles = roleRepository.findByName("ROLE_ADMIN").get();
        admin.setRoles(Collections.singleton(roles));

        Admin save = adminRepository.save(admin);

        return mapperHere.mapToAdminDto(save);

    }


    @Override
    public String approveDoctor(Long doctorId) {
        Optional<Doctor> optionalDoctor = doctorRepository.findById(doctorId);

        if (optionalDoctor.isPresent()) {
            Doctor doctor = optionalDoctor.get();
            doctor.setApproved(true);
            doctorRepository.save(doctor);
        } else {
            throw new UserNotFoundException("Doctor not found with ID: " + doctorId);
        }
        return "Doctor Approved!";
    }

    @Override
    public String disapproveDoctor(Long doctorId) {
        Optional<Doctor> optionalDoctor = doctorRepository.findById(doctorId);

        if (optionalDoctor.isPresent()) {
            Doctor doctor = optionalDoctor.get();
            doctor.setApproved(false);
            doctorRepository.save(doctor);
        } else {
            throw new UserNotFoundException("Doctor not found with ID: " + doctorId);
        }
        return "Doctor disapproved!";
    }

}

