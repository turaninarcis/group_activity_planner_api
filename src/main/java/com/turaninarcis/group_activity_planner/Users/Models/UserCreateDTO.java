package com.turaninarcis.group_activity_planner.Users.Models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserCreateDTO(
    @NotBlank(message = "Username cannot be empty")
    @Size(min = 3, max = 24, message = "Username must be between 3 and 24 characters")
    String username,

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 10, max = 30, message = "Password must contain between 10 and 30 characters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$",
         message = "Password must contain at least one uppercase letter, one lowercase letter, and one number")
    String password,

    @Pattern(regexp = "^([a-zA-Z0-9._-]+)@([a-zA-Z0-9.-]+\\.[a-zA-Z]{2,})$", message = "Invalid email format")
    @NotBlank(message = "Email cannot be empty")
    String email

){}
