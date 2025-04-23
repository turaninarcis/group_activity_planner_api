package com.turaninarcis.group_activity_planner.Activities.Models;

import java.time.LocalDateTime;
import java.util.UUID;

public record ActivityShortDetailsDTO(
    UUID id,
    String name,
    LocalDateTime startDate
) {} 

