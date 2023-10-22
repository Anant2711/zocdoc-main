package com.zocdoc.utills;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class OtpVerificationService {
    private Map<String, String> otpMap = new HashMap<>(); // Store phone number -> OTP mapping

    public void saveOtp(String phoneNumber, String otpCode) {
        otpMap.put(phoneNumber, otpCode);
    }

    public boolean verifyOtp(String phoneNumber, String userOtp) {
        String storedOtp = otpMap.get(phoneNumber);
        return storedOtp != null && storedOtp.equals(userOtp);
    }

    public void removeOtp(String phoneNumber) {
        otpMap.remove(phoneNumber);
    }
}
