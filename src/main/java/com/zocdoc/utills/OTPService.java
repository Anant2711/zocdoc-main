package com.zocdoc.utills;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class OTPService {
    private static final int OTP_LENGTH = 6;

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.phone.number}")
    private String phoneNumber;

    @Autowired
    private EmailService emailService;

    @Autowired
    private EmailOtpVerificationService emailOtpVerificationService;

    public void sendOtpOnEmail(String email, String otpCode) {

        emailOtpVerificationService.saveOtp(email, otpCode);

        String subject = "Your OTP Code";
        String text = "Your OTP code is: " + otpCode;
        emailService.sendOtpEmail(email, subject, text);
    }

    public String generateOTP() {
        Random random = new Random();
        StringBuilder otp = new StringBuilder();

        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(random.nextInt(10));
        }

        return otp.toString();
    }

    public void sendOTP(String toPhoneNumber, String otp) {
        try {
            Twilio.init(accountSid, authToken);

            toPhoneNumber="+91"+toPhoneNumber;//for testing in india

            String message = "Your OTP is: " + otp;

            Message.creator(
                    new PhoneNumber(toPhoneNumber),
                    new PhoneNumber(phoneNumber),
                    message
            ).create();
        } catch (Exception e) {
            // Handle any exceptions
            e.printStackTrace();
        }
    }
}

