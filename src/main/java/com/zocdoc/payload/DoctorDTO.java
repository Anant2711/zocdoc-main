package com.zocdoc.payload;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.*;

@Data
public class DoctorDTO {

    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Specialization is required")
    private String specialization;

    @NotBlank(message = "Permanent location is required")
    private String permanentLocation;

    @Column(length = 10)
    @Pattern(regexp = "^(?!\\+91)[0-9]{10}$", message = "Phone number must be a 10-digit number without +91")
    private String phoneNumber;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    @NotBlank(message = "Experience is required")
    private String experience;

    private Boolean approved;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "Pincode is required")
    @Size(min = 6, max = 6, message = "Pincode must be 6 digits long")
    private String pincode;
}


