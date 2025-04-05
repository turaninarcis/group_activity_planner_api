package com.turaninarcis.group_activity_planner.Groups.Models;

import java.time.LocalDateTime;

public record GroupMemberDetailsDTO(
    String username,
    GroupRoleEnum role,
    LocalDateTime joinDate
) {}
