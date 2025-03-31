package com.turaninarcis.group_activity_planner.Events;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turaninarcis.group_activity_planner.Events.Models.EventMember;

@Repository
public interface EventMemberRepository extends JpaRepository<EventMember, UUID> {
    
}
