package com.turaninarcis.group_activity_planner.Activities.Models;

import java.time.LocalDateTime;

public record ActivityMemberDetailsDTO(
    String username,
    ActivityMemberRoleEnum role,
    boolean confirmed,
    LocalDateTime joinDateTime
) {
    
}
