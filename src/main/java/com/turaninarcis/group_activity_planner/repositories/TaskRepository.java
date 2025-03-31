package com.turaninarcis.group_activity_planner.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turaninarcis.group_activity_planner.models.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID>{
    
}
