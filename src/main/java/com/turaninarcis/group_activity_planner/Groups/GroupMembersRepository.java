package com.turaninarcis.group_activity_planner.Groups;

import java.util.Set;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.turaninarcis.group_activity_planner.Groups.Models.Group;
import com.turaninarcis.group_activity_planner.Groups.Models.GroupMember;
import com.turaninarcis.group_activity_planner.Groups.Models.GroupMemberDetailsDTO;
import com.turaninarcis.group_activity_planner.Users.Models.User;

import jakarta.transaction.Transactional;

@Repository
public interface GroupMembersRepository extends JpaRepository<GroupMember,UUID>{
    public GroupMember findByUserAndGroup(User user, Group group);

    @Query("SELECT new com.turaninarcis.group_activity_planner.Groups.Models.GroupMemberDetailsDTO(gm.id, u.username , gm.role, gm.joinDate) FROM GroupMember gm JOIN gm.user u WHERE gm.group.id = ?1 AND gm.deleted = false")
    Set<GroupMemberDetailsDTO>findGroupMembersDetails(UUID groupId);

    GroupMember findByUserUsernameAndGroupId(String username, UUID groupId);

    @Modifying
    @Transactional
    @Query("DELETE FROM GroupMember gm WHERE gm.group.id = :groupId")
    void deleteByGroupId(@Param("groupId") UUID groupId);

}
