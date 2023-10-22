package com.zocdoc.payload;

import lombok.Data;
import javax.validation.constraints.NotBlank;


@Data
public class LoginDto {
    @NotBlank(message = "Phone number or email is required!!, Phone number must be a 10-digit number without +91")
    private String phoneNumberOrEmail;

    @NotBlank(message = "Password is required")
    private String password;
}