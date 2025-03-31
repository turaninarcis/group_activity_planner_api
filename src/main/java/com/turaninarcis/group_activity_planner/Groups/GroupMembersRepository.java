package com.turaninarcis.group_activity_planner.Groups;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turaninarcis.group_activity_planner.Groups.Models.GroupMember;

@Repository
public interface GroupMembersRepository extends JpaRepository<GroupMember,UUID>{
    
}
