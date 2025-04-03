package com.turaninarcis.group_activity_planner.Activities;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turaninarcis.group_activity_planner.Activities.Models.ActivityCreateDTO;
import com.turaninarcis.group_activity_planner.utility.UtilityControllers;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/activities")
public class ActivityController {
    ActivityService activityService;

    @PostMapping("")
    public ResponseEntity<String> postMethodName(@Valid @RequestBody ActivityCreateDTO activityCreateDTO, BindingResult result) {
        if(result.hasErrors())
        {
            String errors = UtilityControllers.getErrors(result);
            return ResponseEntity.badRequest().body(errors);
        }
        return ResponseEntity.ok().body("ok");
        //activityService.createActivity();
    }
    
}
