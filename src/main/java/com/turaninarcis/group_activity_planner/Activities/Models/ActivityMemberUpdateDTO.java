package com.turaninarcis.group_activity_planner.Activities.Models;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record ActivityMemberUpdateDTO(
    @NotNull(message = "Member id needs to be provided")
    UUID id,
    @NotNull(message = "Member role must be provided")
    ActivityMemberRoleEnum role
) {}
