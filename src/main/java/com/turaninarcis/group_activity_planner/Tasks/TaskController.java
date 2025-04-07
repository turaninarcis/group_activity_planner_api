package com.turaninarcis.group_activity_planner.Tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turaninarcis.group_activity_planner.Exceptions.ValidationException;
import com.turaninarcis.group_activity_planner.Tasks.Models.TaskAssigmnentCreateDTO;
import com.turaninarcis.group_activity_planner.Tasks.Models.TaskCreateDTO;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RequestMapping("/activities/tasks")
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
    @PostMapping("/assignment")
    public ResponseEntity<String> createTaskAssignment(@Valid @RequestBody TaskAssigmnentCreateDTO taskAssigmnentCreateDTO, BindingResult result){
        if(result.hasErrors())
            throw new ValidationException(result);

        taskService.createTaskAssignment(taskAssigmnentCreateDTO);
        return ResponseEntity.ok().body("Task assignment created successfully");
    }
}

