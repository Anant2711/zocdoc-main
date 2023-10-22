package com.zocdoc.controller;

import com.zocdoc.payload.AdminDTO;
import com.zocdoc.payload.LoginDto;
import com.zocdoc.service.AdminService;
import com.zocdoc.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/admins")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private AuthenticationManager authenticationManager;

   // @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/signup")
    public ResponseEntity<?> registerAdmin(@Valid @RequestBody AdminDTO adminDTO, BindingResult result) {
        if(result.hasErrors()){
                    return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        AdminDTO admin = adminService.addAdmin(adminDTO);
        return new ResponseEntity<>(admin, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<String> authenticateUser(@Valid @RequestBody LoginDto loginDto, BindingResult result){
            if(result.hasErrors()){
                return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
            }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getPhoneNumberOrEmail(), loginDto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("Admin signed-in successfully!.", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/approveDoctor/{doctorId}")
    public String approveDoctor(@PathVariable Long doctorId) {
        adminService.approveDoctor(doctorId);
        return "Approved Successfully";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/disapproveDoctor/{doctorId}")
    public String disapproveDoctor(@PathVariable Long doctorId) {
        adminService.disapproveDoctor(doctorId);
        return "Disapproved Successfully";
    }
}
