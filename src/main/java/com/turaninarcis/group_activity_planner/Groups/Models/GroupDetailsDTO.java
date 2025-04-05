package com.turaninarcis.group_activity_planner.Groups.Models;

import java.time.LocalDateTime;
import java.util.Set;

public record GroupDetailsDTO(
    String name,
    String description,
    LocalDateTime creationDate,
    LocalDateTime lastUpdate,
    Set<GroupMemberDetailsDTO> groupMembers
) {}
