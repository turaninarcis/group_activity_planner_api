package com.turaninarcis.group_activity_planner.Groups.Models;

import java.time.LocalDateTime;
import java.util.Set;

public record GroupDetailsDTO(
    String id,
    String name,
    String description,
    String inviteToken,
    LocalDateTime creationDate,
    LocalDateTime lastUpdate,
    Set<GroupMemberDetailsDTO> groupMembers
) {}
