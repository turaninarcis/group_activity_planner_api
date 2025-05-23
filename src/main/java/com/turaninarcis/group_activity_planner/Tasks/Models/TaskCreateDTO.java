package com.turaninarcis.group_activity_planner.Tasks.Models;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;

public record TaskCreateDTO(

    @NotBlank(message = "The task needs a name")
    @Length(min=2, max = 50, message = "Task name must contain between 2 and 50 characters")
    String name,
    @Length(max = 254, message = "Task description cannot contain more than 254 characters")
    String description
) {}
