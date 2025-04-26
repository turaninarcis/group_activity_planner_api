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
                .map(message -> new MessageDTO(message.getSender().getUser().getUsername(), message.getGroup().getId().toString(), message.getContent(), message.getTimestamp().toString()))
                .collect(Collectors.toList());
    }
}
