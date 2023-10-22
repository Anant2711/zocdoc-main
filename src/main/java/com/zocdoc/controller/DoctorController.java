package com.zocdoc.controller;

import com.zocdoc.payload.DoctorDTO;
import com.zocdoc.payload.LoginDto;
import com.zocdoc.payload.UserDTO;
import com.zocdoc.service.DoctorService;
import com.zocdoc.utills.EmailOtpVerificationService;
import com.zocdoc.utills.OTPService;
import com.zocdoc.utills.OtpVerificationService;
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
import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    String phoneNumber;
    DoctorDTO doctorDTO;
    String email;
//    String otp;

    @Autowired
    private EmailOtpVerificationService emailOtpVerificationService;

    @Autowired
    private OtpVerificationService otpVerificationService;

    @Autowired
    private OTPService otpService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public ResponseEntity<?> registerDoctor(@Valid @RequestBody DoctorDTO doctorDTO, BindingResult result) {
            if(result.hasErrors()){
                return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
            }
        phoneNumber = doctorDTO.getPhoneNumber();
        email=doctorDTO.getEmail();
        String otp = otpService.generateOTP();

        emailOtpVerificationService.saveOtp(email, otp);//email
        otpService.sendOtpOnEmail(email,otp);

        otpVerificationService.saveOtp(phoneNumber, otp);
        otpService.sendOTP(phoneNumber, otp);

        this.doctorDTO=doctorDTO;
        return new ResponseEntity<>("OTP Send successfully!",HttpStatus.OK);
    }

    @PostMapping("/signup/otp")
    public ResponseEntity<?> verifyOtp( @RequestParam int otp) {
        String userOtp=String.valueOf(otp);

        if (otpVerificationService.verifyOtp(phoneNumber, userOtp)) {
            otpVerificationService.removeOtp(phoneNumber);
            DoctorDTO doctor = doctorService.addDoctor(doctorDTO);
            return new ResponseEntity<>(doctor,HttpStatus.CREATED);

        } else if (emailOtpVerificationService.verifyOtp(email, userOtp)) {
            emailOtpVerificationService.removeOtp(email);
            DoctorDTO doctor = doctorService.addDoctor(doctorDTO);
            return new ResponseEntity<>(doctor,HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>( "Invalid OTP",HttpStatus.BAD_REQUEST);
        }
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
        return new ResponseEntity<>("Doctor signed-in successfully!.", HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<DoctorDTO>> searchDoctors(
            @RequestParam(required = false) String place,
            @RequestParam(required = false) String specialization,
            @RequestParam(required = false) String pincode,
            @RequestParam(required = false) String city
    ) {
        List<DoctorDTO> doctors = doctorService.searchDoctors(place, specialization, pincode, city);
        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }

    @GetMapping("/allDoctors")//for testing purpose only....
    public ResponseEntity<List<DoctorDTO>> getAllDoctors() {
        List<DoctorDTO> doctors = doctorService.getAllDoctors();
        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }


}

