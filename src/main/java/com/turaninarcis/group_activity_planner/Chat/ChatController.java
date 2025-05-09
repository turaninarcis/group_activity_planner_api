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
        GroupMember member = groupService.getGroupMemberByUsernameAndGroupId(messageDTO.getSenderName(), messageDTO.getGroupId());
        System.out.println("Sender name" + messageDTO.getSenderName());
        // Fetch the group entity from the database using the group ID
        Group group = groupService.getGroupById(messageDTO.getGroupId());

        // Create a new message entity
        ChatMessage message;
        if(messageDTO.getImage()==null)
             message = new ChatMessage(member,group,messageDTO.getMessage());
        else message = new ChatMessage(member,group,messageDTO.getMessage(), messageDTO.getImage());

        System.out.println("Image" + messageDTO.getImage());

        // Save the message in the database
        chatMessageRepository.save(message);
        MessageDTO newDTO = MessageDTO.builder()
                                .groupId(message.getGroup().getId().toString())
                                .message(message.getMessage())
                                .image(message.getImage())
                                .senderName(message.getSender().getUser().getUsername())
                                .sendDateTime(message.getTimestamp().toString())
                                .build();
        // Send the message to the specific group chat via WebSocket

        messagingTemplate.convertAndSend("/topic/group/" + messageDTO.getGroupId(), newDTO);
    }



}
