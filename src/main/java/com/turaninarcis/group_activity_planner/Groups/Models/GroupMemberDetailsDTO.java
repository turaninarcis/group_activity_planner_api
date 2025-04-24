package com.turaninarcis.group_activity_planner.Groups.Models;

import java.time.LocalDateTime;
import java.util.UUID;

public record GroupMemberDetailsDTO(
    UUID id,
    String username,
    GroupRoleEnum role,
    LocalDateTime joinDate
) {}
