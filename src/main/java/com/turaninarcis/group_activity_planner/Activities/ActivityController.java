package com.turaninarcis.group_activity_planner.Activities;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turaninarcis.group_activity_planner.Activities.Models.ActivityCreateDTO;
import com.turaninarcis.group_activity_planner.Activities.Models.ActivityUpdateDTO;
import com.turaninarcis.group_activity_planner.Exceptions.ValidationException;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/activities")
public class ActivityController {
    @Autowired
    ActivityService activityService;

    @PostMapping("")
    public ResponseEntity<String> createActivity(@Valid @RequestBody ActivityCreateDTO activityCreateDTO, BindingResult result) {
        if(result.hasErrors())
            throw new ValidationException(result);

        activityService.createActivity(activityCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Activity created successfully");
    }
    @GetMapping("/{activityId}")
    public ResponseEntity<?> getActivityDetails(@PathVariable String activityId) {
        return ResponseEntity.ok().body(activityService.getActivityDetails(activityId));
    }
    @PatchMapping("/{activityId}")
    public ResponseEntity<String> updateActivity(@PathVariable String activityId, @Valid @RequestBody ActivityUpdateDTO updateDTO, BindingResult result) {
        if(result.hasErrors())
            throw new ValidationException(result);
        activityService.updateActivity(updateDTO, activityId);

        return ResponseEntity.ok().body("Activity updated successfully");
    }
    @DeleteMapping("/{activityId}")
    public ResponseEntity<String> deleteActivity(@PathVariable String activityId) {
        activityService.deleteActivity(activityId);

        return ResponseEntity.ok().body("Activity deleted successfully");
    }

    @PostMapping("/join/{activityJoinToken}")
    public ResponseEntity<String> joinActivity(@PathVariable String activityJoinToken) {

        activityService.joinActivity(activityJoinToken);
        return ResponseEntity.ok().body("Joined the activity successfully");
    }
}
