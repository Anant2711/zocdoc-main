package com.zocdoc.controller;

import com.zocdoc.payload.LoginDto;
import com.zocdoc.payload.UserDTO;
import com.zocdoc.service.AuthService;
import com.zocdoc.utills.EmailOtpVerificationService;
import com.zocdoc.utills.OtpVerificationService;
import com.zocdoc.utills.OTPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    String phoneNumber;
    String email;
    UserDTO userDTO;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private OtpVerificationService otpVerificationService;
    @Autowired
    private EmailOtpVerificationService emailOtpVerificationService;

    @Autowired
    private OTPService otpService;


    @Autowired
    private AuthService authService;


    @PostMapping("/signin")
    public ResponseEntity<String> authenticateUser(@Valid @RequestBody LoginDto loginDto, BindingResult result){
            if(result.hasErrors()){
                return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
            }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getPhoneNumberOrEmail(), loginDto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("User signed-in successfully!.", HttpStatus.OK);
    }


    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDTO userDTO, BindingResult result) {
            if(result.hasErrors()){
                return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
            }
//        if(userDTO==null){  //for future use only.
//            userDTO=this.userDTO;
//        }
        phoneNumber = userDTO.getPhoneNumber();
        email=userDTO.getEmail();
        String otp = otpService.generateOTP();

        emailOtpVerificationService.saveOtp(email, otp);//email
        otpService.sendOtpOnEmail(email,otp);

        otpVerificationService.saveOtp(phoneNumber, otp);//phone
        otpService.sendOTP(phoneNumber, otp);

        this.userDTO=userDTO;
        return new ResponseEntity<>("OTP Send successfully!",HttpStatus.OK);
    }

    @PostMapping("/signup/otp")
    public ResponseEntity<?> verifyOtp( @RequestParam int otp) {
        String userOtp=String.valueOf(otp);

        if (otpVerificationService.verifyOtp(phoneNumber, userOtp)) {
            otpVerificationService.removeOtp(phoneNumber);
            UserDTO user = authService.addUser(userDTO);
            return new ResponseEntity<>(user,HttpStatus.CREATED);

        } else if (emailOtpVerificationService.verifyOtp(email, userOtp)) {
            emailOtpVerificationService.removeOtp(email);
            UserDTO user = authService.addUser(userDTO);
            return new ResponseEntity<>(user,HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>( "Invalid OTP",HttpStatus.BAD_REQUEST);
        }
    }


}
