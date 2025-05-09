package com.turaninarcis.group_activity_planner.Chat;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class ChatService {
    @Autowired
    ChatMessageRepository chatMessageRepository;

    public List<MessageDTO> getChatMessages(String groupId){
        List<ChatMessage> messages = chatMessageRepository.findByGroupIdOrderByTimestamp(UUID.fromString(groupId));
        return messages.stream()
                .map(message -> MessageDTO.builder()
                    .groupId(message.getGroup().getId().toString())
                    .message(message.getMessage())
                    .image(message.getImage())
                    .senderName(message.getSender().getUser().getUsername())
                    .sendDateTime(message.getTimestamp().toString())
                    .build())
                .collect(Collectors.toList());
    }
}
