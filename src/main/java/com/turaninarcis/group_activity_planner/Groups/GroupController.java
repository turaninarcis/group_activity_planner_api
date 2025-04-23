package com.turaninarcis.group_activity_planner.Groups;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turaninarcis.group_activity_planner.Exceptions.ValidationException;
import com.turaninarcis.group_activity_planner.Groups.Models.GroupCreateDTO;
import com.turaninarcis.group_activity_planner.Groups.Models.GroupMemberUpdateDTO;
import com.turaninarcis.group_activity_planner.Groups.Models.GroupUpdateDTO;
import com.turaninarcis.group_activity_planner.utility.CreateResponseEntity;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/groups")
public class GroupController {
    @Autowired
    GroupService groupService;

    @GetMapping("/{groupId}")
    public ResponseEntity<?> getMethodName(@PathVariable String groupId) {
        return ResponseEntity.ok().body(groupService.getGroupDetails(groupId));
    }
    

    @PostMapping("")
    public ResponseEntity<Map<String,String>> createGroup(@Valid @RequestBody GroupCreateDTO groupCreateDTO, BindingResult result) {
        if(result.hasErrors())
            throw new ValidationException(result);

        groupService.createGroup(groupCreateDTO);
        return CreateResponseEntity.okEntity("Group created successfully");
    }
    @PostMapping("/invite/{inviteToken}")
    public ResponseEntity<Map<String,String>> joinGroup(@PathVariable String inviteToken) {
        groupService.joinGroup(inviteToken);
        return CreateResponseEntity.okEntity("User joined group successfully");
    }
    @PatchMapping("/{groupId}")
    public ResponseEntity<Map<String,String>> updateGroup(@PathVariable String groupId, @Valid @RequestBody GroupUpdateDTO groupUpdateDTO, BindingResult result){
        if(result.hasErrors())
            throw new ValidationException(result);
        groupService.updateGroup(groupUpdateDTO, groupId);
        return CreateResponseEntity.okEntity("Group updated successfully");
    }  
    @PatchMapping("/{groupId}/members")
    public ResponseEntity<Map<String,String>> updateGroupMember(@PathVariable String groupId, @Valid @RequestBody GroupMemberUpdateDTO groupUpdateDTO, BindingResult result){
        if(result.hasErrors())
            throw new ValidationException(result);

        groupService.updateGroupMember(groupUpdateDTO, groupId);
        return CreateResponseEntity.okEntity("Group member role updated successfully");
    }  

    @DeleteMapping("/{groupId}/members/leave")
    public ResponseEntity<Map<String,String>> leaveGroup(@PathVariable String groupId){
        groupService.leaveGroup(groupId);
        return CreateResponseEntity.okEntity("Left the group successfully");
    }  
    @DeleteMapping("/{groupId}/members/{username}/kick")
    public ResponseEntity<Map<String,String>> kickMember(@PathVariable String groupId, @PathVariable String username){
        groupService.kickMember(groupId, username);
        return CreateResponseEntity.okEntity("User kicked from the group successfully");
    }  
    @DeleteMapping("/{groupId}")
    public ResponseEntity<Map<String,String>> deleteGroup(@PathVariable String groupId){
        groupService.deleteGroup(groupId);
        return CreateResponseEntity.okEntity("Group deleted successfully");
    }
}
