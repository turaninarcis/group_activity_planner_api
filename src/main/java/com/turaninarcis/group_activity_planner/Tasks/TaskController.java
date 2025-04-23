package com.turaninarcis.group_activity_planner.Tasks;

import java.util.Map;
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
import com.turaninarcis.group_activity_planner.utility.CreateResponseEntity;

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
    public ResponseEntity<Map<String,String>> createTask(@Valid @RequestBody TaskCreateDTO taskCreateDTO, @PathVariable String activityId, BindingResult result){
        if(result.hasErrors())
            throw new ValidationException(result);

        taskService.createTask(taskCreateDTO, activityId);
        return CreateResponseEntity.okEntity("Task created successfully");
    }
    @PostMapping("/assignment")
    public ResponseEntity<Map<String,String>> createTaskAssignment(@Valid @RequestBody TaskAssigmnentCreateDTO taskAssigmnentCreateDTO, @PathVariable String activityId ,  BindingResult result){
        if(result.hasErrors())
            throw new ValidationException(result);

        taskService.createTaskAssignment(taskAssigmnentCreateDTO, activityId);
        return CreateResponseEntity.okEntity("Task assignment created successfully");
    }

    @PatchMapping("/{taskId}")
    public ResponseEntity<Map<String,String>> updateTask(@Valid @RequestBody TaskUpdateDTO taskUpdateDTO, BindingResult result, @PathVariable String  activityId, @PathVariable UUID taskId){
        if(result.hasErrors()) throw new ValidationException(result);

        taskService.updateTask(taskUpdateDTO, activityId, taskId);

        return CreateResponseEntity.okEntity("Task updated successfully");
    }

    @PatchMapping("/{taskId}/assignment")
    public ResponseEntity<Map<String,String>> updateTaskAssignment(@PathVariable String  activityId, @PathVariable UUID taskId){

        taskService.updateTaskAssignment(activityId, taskId);
        return CreateResponseEntity.okEntity("Task assignment updated successfully");
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Map<String,String>> deleteTask(@PathVariable String  activityId, @PathVariable UUID taskId){

        taskService.deleteTask(activityId, taskId);
        return CreateResponseEntity.okEntity("Task deleted successfully");
    }

    @DeleteMapping("/{taskId}/assignment")
    public ResponseEntity<Map<String,String>> deleteTaskAssignment(@PathVariable String  activityId, @PathVariable UUID taskId){

        taskService.deleteTaskAssignment(activityId, taskId);
        return CreateResponseEntity.okEntity("Task assignment deleted successfully");
    }
}

