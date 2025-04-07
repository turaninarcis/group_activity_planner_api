package com.turaninarcis.group_activity_planner.Activities.Models;

import java.time.LocalDateTime;
import java.util.UUID;

public record ActivityMemberDetailsDTO(
    UUID id,
    String username,
    ActivityMemberRoleEnum role,
    boolean confirmed,
    LocalDateTime joinDateTime
) {
    
}
