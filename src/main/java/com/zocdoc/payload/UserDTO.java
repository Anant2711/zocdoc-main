package com.zocdoc.payload;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.*;

@Data
public class UserDTO {
    private Long id;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @Column(length = 10)
    @Pattern(regexp = "^(?!\\+91)[0-9]{10}$", message = "Phone number must be a 10-digit number without +91")
    private String phoneNumber;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;
}
