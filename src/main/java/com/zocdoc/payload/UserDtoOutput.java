package com.zocdoc.payload;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class UserDtoOutput {

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String email;
}
