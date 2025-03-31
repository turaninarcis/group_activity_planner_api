package com.turaninarcis.group_activity_planner.Tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class TaskController {
    @Autowired
    TaskService taskService;
}
