package com.turaninarcis.group_activity_planner.Activities;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turaninarcis.group_activity_planner.Activities.Models.ActivityCreateDTO;
import com.turaninarcis.group_activity_planner.Activities.Models.ActivityMemberDeleteDTO;
import com.turaninarcis.group_activity_planner.Activities.Models.ActivityMemberUpdateDTO;
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

    @PatchMapping("/{activityId}/members")
    public ResponseEntity<String> updateActivityMemberRole(@PathVariable String activityId, @Valid @RequestBody ActivityMemberUpdateDTO updateDTO, BindingResult result) {
        if(result.hasErrors())
            throw new ValidationException(result);
        activityService.changeMemberRole(updateDTO, activityId);

        return ResponseEntity.ok().body("Activity member role updated successfully");
    }

    @PatchMapping("/{activityId}/confirmation")
    public ResponseEntity<String> updateActivityMemberConfirmation(@PathVariable String activityId) {

        activityService.changeMemberConfirmation(activityId);

        return ResponseEntity.ok().body("Activity confirmation updated successfully");
    }

    @DeleteMapping("/{activityId}/members/kick")
    public ResponseEntity<String> kickActivityMember(@PathVariable String activityId, @Valid @RequestBody ActivityMemberDeleteDTO deleteDTO, BindingResult result) {
        if(result.hasErrors())
            throw new ValidationException(result);
        activityService.kickMember(activityId, deleteDTO);

        return ResponseEntity.ok().body("Activity kicked successfully");
    }

    @DeleteMapping("/{activityId}/members/leave")
    public ResponseEntity<String> leaveActivity(@PathVariable String activityId) {

        activityService.leaveActivity(activityId);

        return ResponseEntity.ok().body("Activity leaved successfully");
    }
}
