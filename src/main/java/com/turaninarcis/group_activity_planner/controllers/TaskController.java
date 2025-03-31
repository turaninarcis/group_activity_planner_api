package com.turaninarcis.group_activity_planner.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.turaninarcis.group_activity_planner.services.TaskService;

@Controller
public class TaskController {
    @Autowired
    TaskService taskService;
}
