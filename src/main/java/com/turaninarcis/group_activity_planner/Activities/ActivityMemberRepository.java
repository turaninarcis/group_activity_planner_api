package com.turaninarcis.group_activity_planner.Activities;

import java.util.Set;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.turaninarcis.group_activity_planner.Activities.Models.Activity;
import com.turaninarcis.group_activity_planner.Activities.Models.ActivityMember;
import com.turaninarcis.group_activity_planner.Activities.Models.ActivityMemberDetailsDTO;
import com.turaninarcis.group_activity_planner.Users.Models.User;

@Repository
public interface ActivityMemberRepository extends JpaRepository<ActivityMember, UUID> {
    @Query("SELECT u from ActivityMember u WHERE u.activity = ?1 AND u.user = ?2")
    public ActivityMember findByActivityAndUser(Activity activity, User user);

    @Query("SELECT new com.turaninarcis.group_activity_planner.Activities.Models.ActivityMemberDetailsDTO(am.id,u.username, am.role, am.confirmed, am.joinDate)" +
           "FROM ActivityMember am JOIN am.user u WHERE am.activity = ?1")
    public Set<ActivityMemberDetailsDTO> getAllActivityMembersDetails(Activity activity);
}
