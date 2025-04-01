package com.turaninarcis.group_activity_planner.Groups;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turaninarcis.group_activity_planner.Groups.Models.GroupCreateDTO;
import com.turaninarcis.group_activity_planner.utility.UtilityControllers;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/groups")
public class GroupController {
    @Autowired
    GroupService groupService;


    @PostMapping("/create")
    public ResponseEntity<String> createGroup(@Valid @RequestBody GroupCreateDTO groupCreateDTO, BindingResult result) {
        if(result.hasErrors())
        {
            return ResponseEntity.badRequest().body(UtilityControllers.getErrors(result));
        }
        try{
            groupService.createGroup(groupCreateDTO);
            return ResponseEntity.ok("Group created successfully");
        }catch(Exception e){
            return ResponseEntity.badRequest().body("Something went wrong");
        }
        
    }
    
}
