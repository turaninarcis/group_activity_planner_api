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
    public ResponseEntity<String> createGroup(@Valid @RequestBody GroupCreateDTO groupCreateDTO, BindingResult result) {
        if(result.hasErrors())
            throw new ValidationException(result);

        groupService.createGroup(groupCreateDTO);
        return ResponseEntity.ok("Group created successfully");
    }
    @PostMapping("/invite/{inviteToken}")
    public ResponseEntity<String> joinGroup(@PathVariable String inviteToken) {
        groupService.joinGroup(inviteToken);
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

    @DeleteMapping("/{groupId}/members/leave")
    public ResponseEntity<String> leaveGroup(@PathVariable String groupId){
        groupService.leaveGroup(groupId);
        return ResponseEntity.ok().body("Leaved the group successfully");
    }  
    @DeleteMapping("/{groupId}/members/{username}/kick")
    public ResponseEntity<String> kickMember(@PathVariable String groupId, @PathVariable String username){
        groupService.kickMember(groupId, username);
        return ResponseEntity.ok().body("User kicked from the group successfully");
    }  
    @DeleteMapping("/{groupId}")
    public ResponseEntity<String> deleteGroup(@PathVariable String groupId){
        groupService.deleteGroup(groupId);
        return ResponseEntity.ok().body("Group successfully deleted");
    }
}
