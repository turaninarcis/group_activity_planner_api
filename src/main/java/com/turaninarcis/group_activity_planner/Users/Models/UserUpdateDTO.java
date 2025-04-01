package com.turaninarcis.group_activity_planner.Users.Models;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserUpdateDTO(
    UUID id,

    String oldPassword,

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 10, message = "Password must be at least 10 characters long")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$",
         message = "Password must contain at least one uppercase letter, one lowercase letter, and one number")
    String newPassword,

    @NotBlank(message = "Email cannot be empty")
    @Pattern(regexp = "^([a-zA-Z0-9._-]+)@([a-zA-Z0-9.-]+\\.[a-zA-Z]{2,})$", message = "Invalid email format")
    String email,

    @NotBlank(message = "Username cannot be empty")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    String username
) {}
