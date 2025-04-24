package com.turaninarcis.group_activity_planner.Groups.Models;

import jakarta.validation.constraints.NotNull;

public record GroupMemberUpdateDTO(
    @NotNull(message = "Member id must be provided")
    String memberId,
    @NotNull(message = "Role must be provided")
    GroupRoleEnum role
) {}
