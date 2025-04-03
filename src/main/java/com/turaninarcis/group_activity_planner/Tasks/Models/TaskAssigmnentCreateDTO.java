package com.turaninarcis.group_activity_planner.Tasks.Models;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record TaskAssigmnentCreateDTO(
    @NotNull(message = "You need to pass the taskId")
    UUID taskId
) 
{}
