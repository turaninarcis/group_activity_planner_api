package com.turaninarcis.group_activity_planner.Groups;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turaninarcis.group_activity_planner.Chat.ChatService;
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
    @Autowired
    ChatService chatService;

    @GetMapping("/{groupId}")
    public ResponseEntity<?> getGroupDetails(@PathVariable String groupId) {
        return ResponseEntity.ok().body(groupService.getGroupDetails(groupId));
    }
    @GetMapping("")
    public ResponseEntity<?> getJoinedGroups() {
        Map<String, Object> response = new HashMap<>();
        response.put("joinedGroups", groupService.getJoinedGroups());
        return ResponseEntity.ok().body(response);
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

    @PatchMapping("/{groupId}/newtoken")
    public ResponseEntity<Map<String,String>> updateGroupInviteToken(@PathVariable String groupId){

        String newInviteToken = groupService.updateGroupInviteToken(groupId);
        Map<String,String> response = new HashMap<>();
        response.put("newToken",newInviteToken);
        return ResponseEntity.ok().body(response);
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
    @DeleteMapping("/{groupId}/members/{memberId}/kick")
    public ResponseEntity<Map<String,String>> kickMember(@PathVariable String groupId, @PathVariable String memberId){
        groupService.kickMember(groupId, memberId);
        return CreateResponseEntity.okEntity("User kicked from the group successfully");
    }  
    @DeleteMapping("/{groupId}")
    public ResponseEntity<Map<String,String>> deleteGroup(@PathVariable String groupId){
        groupService.deleteGroupByMember(groupId);
        return CreateResponseEntity.okEntity("Group deleted successfully");
    }


    @GetMapping("/{groupId}/messages")
    public ResponseEntity<Map<String,Object>> getMessagesForGroup(@PathVariable String groupId) {
        Map<String,Object> reponse = new HashMap<>();
        reponse.put("chatMessages",chatService.getChatMessages(groupId));

        return ResponseEntity.ok().body(reponse);
    }
}
