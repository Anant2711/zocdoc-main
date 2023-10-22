package com.zocdoc.payload;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.*;
@Data
public class AdminDTO {

    private long id;

    @NotBlank(message = "Name is required")
    private String name;

    @Column(length = 10)
    @Pattern(regexp = "^(?!\\+91)[0-9]{10}$", message = "Phone number must be a 10-digit number without +91")
    private String phoneNumber;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;
}
