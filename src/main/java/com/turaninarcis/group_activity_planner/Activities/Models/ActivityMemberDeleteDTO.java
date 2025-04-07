package com.turaninarcis.group_activity_planner.Activities.Models;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record ActivityMemberDeleteDTO(
    @NotNull(message = "Member id is required")
    UUID id
) {}
