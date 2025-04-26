package com.turaninarcis.group_activity_planner.Chat;


public record MessageDTO(
    String senderName,
    String groupId,
    String content,
    String sendDateTime
) {
} 
