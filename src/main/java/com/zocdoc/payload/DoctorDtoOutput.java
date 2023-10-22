package com.zocdoc.payload;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class DoctorDtoOutput {

    private String name;

    private String specialization;

    private String permanentLocation;

    private String phoneNumber;

    private String email;

    private String city;

    private String pincode;
}


