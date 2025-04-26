package com.turaninarcis.group_activity_planner.Chat;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage,UUID>{
    List<ChatMessage> findByGroupIdOrderByTimestamp(UUID groupId);
}
