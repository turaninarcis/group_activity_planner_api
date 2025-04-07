package com.turaninarcis.group_activity_planner.Tasks.Models;

import java.util.Set;

import lombok.Data;

@Data
public class TaskDetailsDTO{
    String name;
    String description;
    Set<TaskAssignmnentDetailsDTO> assignments;
    public TaskDetailsDTO(String name, String description, Set<TaskAssignmnentDetailsDTO> assignmnentDetailsDTOs){
        this.name = name;
        this.description = description;
        this.assignments = assignmnentDetailsDTOs;
    }
}

