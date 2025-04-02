package com.turaninarcis.group_activity_planner.Activities;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turaninarcis.group_activity_planner.Activities.Models.ActivityMember;

@Repository
public interface ActivityMemberRepository extends JpaRepository<ActivityMember, UUID> {
    
}
