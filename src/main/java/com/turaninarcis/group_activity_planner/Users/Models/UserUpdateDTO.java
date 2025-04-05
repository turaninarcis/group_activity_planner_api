package com.turaninarcis.group_activity_planner.Users.Models;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserUpdateDTO(
    UUID id,

    @NotNull(message = "You must provide your account password to make changes")
    String password,

    @Size(min = 10, message = "Password must be at least 10 characters long")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$",
         message = "Password must contain at least one uppercase letter, one lowercase letter, and one number")
    String newPassword,

    @Pattern(regexp = "^([a-zA-Z0-9._-]+)@([a-zA-Z0-9.-]+\\.[a-zA-Z]{2,})$", message = "Invalid email format")
    String email,

    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    String username
) {}
