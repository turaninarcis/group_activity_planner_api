package com.turaninarcis.group_activity_planner.Activities.Models;

import java.time.LocalDateTime;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;

public record ActivityUpdateDTO(
    @Length(min = 3,max = 50, message = "The activity name needs to be between 3 and 50 characters")
    String name,
    @Length(max = 254, message = "The activity description should not exceed 254 characters")
    String description,
    
    @FutureOrPresent
    LocalDateTime startDate,
    @Future
    LocalDateTime endDate
) {}
