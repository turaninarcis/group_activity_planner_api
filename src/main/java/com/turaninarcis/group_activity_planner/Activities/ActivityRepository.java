package com.turaninarcis.group_activity_planner.Activities;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turaninarcis.group_activity_planner.Activities.Models.Activity;


@Repository
public interface ActivityRepository extends JpaRepository<Activity, UUID> {
    public Activity findByInviteToken(String inviteToken);
}
