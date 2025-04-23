package com.turaninarcis.group_activity_planner.Activities;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.turaninarcis.group_activity_planner.Activities.Models.Activity;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, UUID> {
    public Activity findByInviteToken(String inviteToken);


    @Query("SELECT am.activity FROM ActivityMember am WHERE am.user.id = :userId")
    List<Activity> findAllByUserId(@Param("userId") UUID userId);
}
