package com.turaninarcis.group_activity_planner.Tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turaninarcis.group_activity_planner.Exceptions.ValidationException;
import com.turaninarcis.group_activity_planner.Tasks.Models.TaskCreateDTO;
import com.turaninarcis.group_activity_planner.utility.UtilityControllers;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RequestMapping("/tasks")
@RestController
public class TaskController {
    @Autowired
    private TaskService taskService;

    @PostMapping("")  
    public ResponseEntity<String> createTask(@Valid @RequestBody TaskCreateDTO taskCreateDTO, BindingResult result){
        if(result.hasErrors())
            throw new ValidationException(result);

        taskService.createTask(taskCreateDTO);
        return ResponseEntity.ok().body("Task created successfully");
    }
}

