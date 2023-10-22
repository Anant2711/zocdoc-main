package com.zocdoc.service;

import com.zocdoc.payload.DoctorDTO;
import com.zocdoc.payload.UserDTO;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    UserDTO addUser(UserDTO userDTO);

}
