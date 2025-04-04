package com.turaninarcis.group_activity_planner.Groups;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.turaninarcis.group_activity_planner.Groups.Models.Group;
import com.turaninarcis.group_activity_planner.Groups.Models.GroupMember;
import com.turaninarcis.group_activity_planner.Users.Models.User;

@Repository
public interface GroupMembersRepository extends JpaRepository<GroupMember,UUID>{
    //@Query("SELECT u from GroupMember u JOIN FETCH u.user JOIN FETCH u.group WHERE u.user = ?1 AND u.group = ?2")
    public GroupMember findByUserAndGroup(User user, Group group);
}
