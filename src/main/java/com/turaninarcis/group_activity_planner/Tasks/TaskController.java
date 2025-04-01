package com.turaninarcis.group_activity_planner.Tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/tasks")
@RestController
public class TaskController {
    @Autowired
    TaskService taskService;
}
