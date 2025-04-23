package com.turaninarcis.group_activity_planner.Activities;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turaninarcis.group_activity_planner.Activities.Models.ActivityCreateDTO;
import com.turaninarcis.group_activity_planner.Activities.Models.ActivityMemberUpdateDTO;
import com.turaninarcis.group_activity_planner.Activities.Models.ActivityShortDetailsDTO;
import com.turaninarcis.group_activity_planner.Activities.Models.ActivityUpdateDTO;
import com.turaninarcis.group_activity_planner.Exceptions.ValidationException;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.turaninarcis.group_activity_planner.utility.CreateResponseEntity;



@RestController
@RequestMapping("/activities")
public class ActivityController {
    @Autowired
    ActivityService activityService;

    @PostMapping("")
    public ResponseEntity<Map<String,String>> createActivity(@Valid @RequestBody ActivityCreateDTO activityCreateDTO, BindingResult result) {
        if(result.hasErrors())
            throw new ValidationException(result);

        activityService.createActivity(activityCreateDTO);
        return CreateResponseEntity.createdEntity("Activity created successfully");
    }

    @GetMapping("")
    public ResponseEntity<?> getJoinedActivities() {
        Set<ActivityShortDetailsDTO> joinedActivities = activityService.getAllJoinedActivities();
        Map<String, Object> response = new HashMap<>();
        response.put("activitiesCount", joinedActivities.size());
        response.put("activities",joinedActivities);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{activityId}")
    public ResponseEntity<?> getActivityDetails(@PathVariable String activityId) {

        Map<String, Object> response = new HashMap<>();
        response.put("activityDetails",activityService.getActivityDetails(activityId));
        return ResponseEntity.ok().body(response);
    }
    @PatchMapping("/{activityId}")
    public ResponseEntity<Map<String,String>>  updateActivity(@PathVariable String activityId, @Valid @RequestBody ActivityUpdateDTO updateDTO, BindingResult result) {
        if(result.hasErrors())
            throw new ValidationException(result);
        activityService.updateActivity(updateDTO, activityId);
        return CreateResponseEntity.okEntity("Activity updated successfully");
    }
    @DeleteMapping("/{activityId}")
    public ResponseEntity<Map<String,String>>  deleteActivity(@PathVariable String activityId) {
        activityService.deleteActivity(activityId);
        return CreateResponseEntity.okEntity("Activity deleted successfully");
    }

    @PostMapping("/join/{activityJoinToken}")
    public ResponseEntity<Map<String,String>>  joinActivity(@PathVariable String activityJoinToken) {

        activityService.joinActivity(activityJoinToken);
        return CreateResponseEntity.okEntity("Joined the activity successfully");
    }

    @PatchMapping("/{activityId}/members")
    public ResponseEntity<Map<String,String>> updateActivityMemberRole(@PathVariable String activityId, @Valid @RequestBody ActivityMemberUpdateDTO updateDTO, BindingResult result) {
        if(result.hasErrors())
            throw new ValidationException(result);
        activityService.changeMemberRole(updateDTO, activityId);
        return CreateResponseEntity.okEntity("Activity member role updated successfully");
    }

    @PatchMapping("/{activityId}/confirmation")
    public ResponseEntity<Map<String,String>>  updateActivityMemberConfirmation(@PathVariable String activityId) {

        activityService.changeMemberConfirmation(activityId);
        return CreateResponseEntity.okEntity("Activiy confirmation updated successfully");
    }

    @DeleteMapping("/{activityId}/members/kick")
    public ResponseEntity<Map<String,String>>  kickActivityMember(@PathVariable String activityId, @RequestBody Map<String, Object> requestBody) {
        UUID memberId = UUID.fromString((String) requestBody.get("id"));
        activityService.kickMember(activityId, memberId);
        return CreateResponseEntity.okEntity("Activity kicked successfully");
    }

    @DeleteMapping("/{activityId}/members/leave")
    public ResponseEntity<Map<String,String>>  leaveActivity(@PathVariable String activityId) {

        activityService.leaveActivity(activityId);
        return CreateResponseEntity.okEntity("Activity left successfully");
    }

    @GetMapping("/{activityId}/joined")
    public ResponseEntity<?> isUserJoined(@PathVariable String activityId) {

        Map<String, Boolean> response = new HashMap<>();
        response.put("joined", activityService.isUserMemberBoolean(activityId));

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/{activityId}/newtoken")
    public ResponseEntity<Map<String,String>>  generateNewActivityToken(@PathVariable String activityId) {
     
        Map<String, String> response = new HashMap<>();
        response.put("newToken", activityService.updateInviteToken(activityId));

        return ResponseEntity.ok().body(response);
    }
}
