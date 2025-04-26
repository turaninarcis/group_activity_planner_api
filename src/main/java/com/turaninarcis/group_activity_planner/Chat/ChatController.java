package com.turaninarcis.group_activity_planner.Chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;


import com.turaninarcis.group_activity_planner.Groups.GroupService;
import com.turaninarcis.group_activity_planner.Groups.Models.Group;
import com.turaninarcis.group_activity_planner.Groups.Models.GroupMember;

@Controller
public class ChatController {
    @Autowired
    private GroupService groupService;
    @Autowired ChatMessageRepository chatMessageRepository;

    private final SimpMessagingTemplate messagingTemplate;
    public ChatController(SimpMessagingTemplate messagingTemplate){
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/sendMessage")
    public void sendMessage(@Payload MessageDTO messageDTO) {
        // Fetch the sender user from the database using the user ID from JWT or session
        GroupMember member = groupService.getGroupMemberByUsernameAndGroupId(messageDTO.senderName(), messageDTO.groupId());

        // Fetch the group entity from the database using the group ID
        Group group = groupService.getGroupById(messageDTO.groupId());

        // Create a new message entity
        ChatMessage message = new ChatMessage(member,group,messageDTO.content());


        // Save the message in the database
        chatMessageRepository.save(message);
        MessageDTO newDto = new MessageDTO(message.getSender().getUser().getUsername(), message.getGroup().getId().toString(), message.getContent(), message.getTimestamp().toString());
        // Send the message to the specific group chat via WebSocket

        messagingTemplate.convertAndSend("/topic/group/" + messageDTO.groupId(), newDto);
    }



}
