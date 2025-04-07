package com.turaninarcis.group_activity_planner.Activities;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turaninarcis.group_activity_planner.Activities.Models.ActivityCreateDTO;
import com.turaninarcis.group_activity_planner.Exceptions.ValidationException;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/activities")
public class ActivityController {
    @Autowired
    ActivityService activityService;

    @PostMapping("")
    public ResponseEntity<String> postMethodName(@Valid @RequestBody ActivityCreateDTO activityCreateDTO, BindingResult result) {
        if(result.hasErrors())
            throw new ValidationException(result);

        activityService.createActivity(activityCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Activity created successfully");
    }
    @GetMapping("/{activityId}")
    public ResponseEntity<?> getActivityDetails(@PathVariable String activityId) {

        return ResponseEntity.ok().body(activityService.getActivityDetails(activityId));
    }
        
}
