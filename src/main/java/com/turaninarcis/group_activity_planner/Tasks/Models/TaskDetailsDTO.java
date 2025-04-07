package com.turaninarcis.group_activity_planner.Tasks.Models;

import java.util.Set;
import java.util.UUID;

import lombok.Data;

@Data
public class TaskDetailsDTO{
    UUID id;
    String name;
    String description;
    Set<TaskAssignmnentDetailsDTO> assignments;
    public TaskDetailsDTO(UUID id, String name, String description, Set<TaskAssignmnentDetailsDTO> assignmnentDetailsDTOs){
        this.id = id;
        this.name = name;
        this.description = description;
        this.assignments = assignmnentDetailsDTOs;
    }
}

