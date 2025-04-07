package com.turaninarcis.group_activity_planner.Tasks.Models;

import java.util.UUID;

public record TaskAssignmnentDetailsDTO(
    UUID id,
    String username,
    boolean completed
) {
} 
    

