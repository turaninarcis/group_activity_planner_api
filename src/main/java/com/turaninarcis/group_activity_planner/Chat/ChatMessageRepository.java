package com.turaninarcis.group_activity_planner.Chat;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;

public interface ChatMessageRepository extends JpaRepository<ChatMessage,UUID>{
    List<ChatMessage> findByGroupIdOrderByTimestamp(UUID groupId);

    @Modifying
    @Transactional
    @Query("DELETE FROM ChatMessage m WHERE m.group.id = :groupId")
    void deleteByGroupId(@Param("groupId") UUID groupId);

}
