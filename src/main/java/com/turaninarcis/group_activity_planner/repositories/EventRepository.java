package com.turaninarcis.group_activity_planner.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turaninarcis.group_activity_planner.models.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {
    
}
