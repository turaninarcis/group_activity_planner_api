package com.turaninarcis.group_activity_planner.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turaninarcis.group_activity_planner.repositories.TaskAssignmentRepository;
import com.turaninarcis.group_activity_planner.repositories.TaskRepository;

@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    TaskAssignmentRepository taskAssignmentRepository;
}
