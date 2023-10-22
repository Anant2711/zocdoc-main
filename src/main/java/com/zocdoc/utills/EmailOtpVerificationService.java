package com.zocdoc.utills;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmailOtpVerificationService {
    private Map<String, String> otpMap = new HashMap<>(); // Store email -> OTP mapping

    public void saveOtp(String email, String otpCode) {
        otpMap.put(email, otpCode);
    }

    public boolean verifyOtp(String email, String userOtp) {
        String storedOtp = otpMap.get(email);
        return storedOtp != null && storedOtp.equals(userOtp);
    }

    public void removeOtp(String email) {
        otpMap.remove(email);
    }
}

