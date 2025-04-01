package com.turaninarcis.group_activity_planner.Groups.Models;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;

public record GroupCreateDTO(
    @NotBlank(message = "Name field must not be blank")
    @Length(min = 3, max = 40, message = "Name field must be between 3 and 40 characters")
    String name,
    @Length(max = 250, message = "Descripton cannot be longer than 250 characters")
    String description
) {}
