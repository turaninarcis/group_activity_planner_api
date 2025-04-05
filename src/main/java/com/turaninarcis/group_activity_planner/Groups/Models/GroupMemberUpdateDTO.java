package com.turaninarcis.group_activity_planner.Groups.Models;

import jakarta.validation.constraints.NotNull;

public record GroupMemberUpdateDTO(
    @NotNull(message = "Username must be provided")
    String username,
    @NotNull(message = "Role must be provided")
    GroupRoleEnum role
) {}
