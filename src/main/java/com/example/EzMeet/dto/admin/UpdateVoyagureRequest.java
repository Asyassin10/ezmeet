package com.example.EzMeet.dto.admin;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UpdateVoyagureRequest (
    @NotBlank(message = "Name is required") String name,
    @NotBlank(message = "First name is required") String firstName,
    @Email(message = "Email must be valid") 
    @NotBlank(message = "Email is required") String email
 ){}
