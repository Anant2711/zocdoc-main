package com.zocdoc.service.impl;

import com.zocdoc.entity.Role;
import com.zocdoc.entity.User;
import com.zocdoc.exception.CustomRegistrationException;
import com.zocdoc.mapper.MapperHere;
import com.zocdoc.payload.UserDTO;
import com.zocdoc.repository.DoctorRepository;
import com.zocdoc.repository.RoleRepository;
import com.zocdoc.repository.UserRepository;
import com.zocdoc.service.AuthService;
import com.zocdoc.utills.OTPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private MapperHere mapperHere;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public UserDTO addUser(UserDTO userDTO) {


        if (userRepository.existsByPhoneNumber(userDTO.getPhoneNumber())) {
            throw new CustomRegistrationException("PhoneNumber is already taken! " + userDTO.getPhoneNumber());
        }

        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new CustomRegistrationException("Email is already taken! " + userDTO.getEmail());
        }



        User user = mapperHere.mapToUserEntity(userDTO);

        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));//encode password here..

        Role roles = roleRepository.findByName("ROLE_USER").get();
        user.setRoles(Collections.singleton(roles));

        User save = userRepository.save(user);
        return mapperHere.mapToUserDto(save);
    }

}
