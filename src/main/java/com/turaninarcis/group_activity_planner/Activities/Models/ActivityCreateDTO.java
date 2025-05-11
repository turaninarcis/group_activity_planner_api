package com.turaninarcis.group_activity_planner.Activities.Models;

import java.time.LocalDateTime;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;

public record ActivityCreateDTO(
    @NotBlank(message = "The activity needs a name")
    @Length(min = 3,max = 50, message = "The activity name needs to be between 3 and 50 characters")
    String name,
    @Length(max = 254, message = "The activity description should not exceed 254 characters")
    String description,

    @FutureOrPresent(message = "The activity can't start in the past")
    LocalDateTime startDate,
    @Future(message = "The activity can't end in the past")
    LocalDateTime endDate
){} 
