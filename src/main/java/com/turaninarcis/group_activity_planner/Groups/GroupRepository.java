package com.turaninarcis.group_activity_planner.Groups;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.turaninarcis.group_activity_planner.Groups.Models.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group,UUID> {
   Group findByInviteToken(String groupToken);

   @Query("SELECT gm.group FROM GroupMember gm WHERE gm.user.id = :userId")
    List<Group> findAllByUserId(@Param("userId") UUID userId);
}
