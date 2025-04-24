package com.turaninarcis.group_activity_planner.Activities.Models;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.turaninarcis.group_activity_planner.Tasks.Models.TaskDetailsDTO;

import lombok.Builder;
@Builder
public record ActivityDetailsDTO(
    UUID id,
    String name,
    String description,
    String inviteToken,
    LocalDateTime dateOfCreation,
    LocalDateTime lastModify,
    LocalDateTime startDate,
    LocalDateTime endDate,
    Set<ActivityMemberDetailsDTO> members,
    List<TaskDetailsDTO> tasks
) {
    
}
