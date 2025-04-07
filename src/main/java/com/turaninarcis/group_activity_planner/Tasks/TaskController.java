package com.turaninarcis.group_activity_planner.Tasks;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turaninarcis.group_activity_planner.Exceptions.ValidationException;
import com.turaninarcis.group_activity_planner.Tasks.Models.TaskAssigmnentCreateDTO;
import com.turaninarcis.group_activity_planner.Tasks.Models.TaskCreateDTO;
import com.turaninarcis.group_activity_planner.Tasks.Models.TaskUpdateDTO;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RequestMapping("/activities/{activityId}/tasks")
@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;



    @PostMapping("")  
    public ResponseEntity<String> createTask(@Valid @RequestBody TaskCreateDTO taskCreateDTO, @PathVariable String activityId, BindingResult result){
        if(result.hasErrors())
            throw new ValidationException(result);

        taskService.createTask(taskCreateDTO, activityId);
        return ResponseEntity.ok().body("Task created successfully");
    }
    @PostMapping("/assignment")
    public ResponseEntity<String> createTaskAssignment(@Valid @RequestBody TaskAssigmnentCreateDTO taskAssigmnentCreateDTO, @PathVariable String activityId ,  BindingResult result){
        if(result.hasErrors())
            throw new ValidationException(result);

        taskService.createTaskAssignment(taskAssigmnentCreateDTO, activityId);
        return ResponseEntity.ok().body("Task assignment created successfully");
    }

    @PatchMapping("/{taskId}")
    public ResponseEntity<String> updateTask(@Valid @RequestBody TaskUpdateDTO taskUpdateDTO, BindingResult result, @PathVariable String  activityId, @PathVariable UUID taskId){
        if(result.hasErrors()) throw new ValidationException(result);

        taskService.updateTask(taskUpdateDTO, activityId, taskId);

        return ResponseEntity.ok().body("Task updated successfully");
    }

    @PatchMapping("/{taskId}/assignment")
    public ResponseEntity<String> updateTaskAssignment(@PathVariable String  activityId, @PathVariable UUID taskId){

        taskService.updateTaskAssignment(activityId, taskId);

        return ResponseEntity.ok().body("Task assignment updated successfully");
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable String  activityId, @PathVariable UUID taskId){

        taskService.deleteTask(activityId, taskId);

        return ResponseEntity.ok().body("Task deleted successfully");
    }

    @DeleteMapping("/{taskId}/assignment")
    public ResponseEntity<String> deleteTaskAssignment(@PathVariable String  activityId, @PathVariable UUID taskId){

        taskService.deleteTaskAssignment(activityId, taskId);

        return ResponseEntity.ok().body("Task assignment deleted successfully");
    }
}

