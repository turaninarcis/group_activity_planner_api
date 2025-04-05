package com.turaninarcis.group_activity_planner.Groups;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turaninarcis.group_activity_planner.Exceptions.ValidationException;
import com.turaninarcis.group_activity_planner.Groups.Models.GroupCreateDTO;
import com.turaninarcis.group_activity_planner.Groups.Models.GroupMemberUpdateDTO;
import com.turaninarcis.group_activity_planner.Groups.Models.GroupUpdateDTO;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/groups")
public class GroupController {
    @Autowired
    GroupService groupService;


    @PostMapping("")
    public ResponseEntity<String> createGroup(@Valid @RequestBody GroupCreateDTO groupCreateDTO, BindingResult result) {
        if(result.hasErrors())
            throw new ValidationException(result);

        groupService.createGroup(groupCreateDTO);
        return ResponseEntity.ok("Group created successfully");
    }
    @PostMapping("/invite/{inviteToken}")
    public ResponseEntity<String> postMethodName(@PathVariable String inviteToken) {
        groupService.createGroupMember(inviteToken);
        return ResponseEntity.ok().body("User joined group successfully");
    }
    @PatchMapping("/{groupId}")
    public ResponseEntity<String> updateGroup(@PathVariable String groupId, @Valid @RequestBody GroupUpdateDTO groupUpdateDTO, BindingResult result){
        if(result.hasErrors())
            throw new ValidationException(result);
        groupService.updateGroup(groupUpdateDTO, groupId);
        return ResponseEntity.ok().body("Group updated successfully");
    }  
    @PatchMapping("/{groupId}/members")
    public ResponseEntity<String> updateGroupMember(@PathVariable String groupId, @Valid @RequestBody GroupMemberUpdateDTO groupUpdateDTO, BindingResult result){
        if(result.hasErrors())
            throw new ValidationException(result);

        groupService.updateGroupMember(groupUpdateDTO, groupId);
        return ResponseEntity.ok().body("Group member role updated successfully");
    }  
}
