package com.turaninarcis.group_activity_planner.Activities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turaninarcis.group_activity_planner.Activities.Models.ActivityCreateDTO;

@Service
public class ActivityService {
    @Autowired
    ActivityRepository activityRepository;
    @Autowired
    ActivityMemberRepository activityMemberRepository;
    public void createActivity(ActivityCreateDTO activityCreateDTO) {
        
    }
}
