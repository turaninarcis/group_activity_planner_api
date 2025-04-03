package com.turaninarcis.group_activity_planner.Activities;

import java.util.UUID;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turaninarcis.group_activity_planner.Activities.Models.Activity;
import com.turaninarcis.group_activity_planner.Activities.Models.ActivityCreateDTO;
import com.turaninarcis.group_activity_planner.Activities.Models.ActivityMember;
import com.turaninarcis.group_activity_planner.Users.UserService;
import com.turaninarcis.group_activity_planner.Users.Models.User;

@Service
public class ActivityService {
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private ActivityMemberRepository activityMemberRepository;
    @Autowired
    private UserService userService;


    public void createActivity(ActivityCreateDTO activityCreateDTO) {
        String inviteToken = UUID.randomUUID().toString();

        Activity activity = Activity.builder()
                                .name(activityCreateDTO.name())
                                .description(activityCreateDTO.description())
                                .inviteToken(inviteToken)
                                .startDate(activityCreateDTO.startDate())
                                .endDate(activityCreateDTO.endDate())
                                .build();                         
        Activity savedActivity = activityRepository.save(activity);

        User user = userService.getLoggedUser();
        ActivityMember activityCreator = ActivityMember.MakeCreator(user,savedActivity);

        activityMemberRepository.save(activityCreator);
    }

    public Activity getActivityById(UUID id){
        return activityRepository.findById(id).orElse(null);
    }
    public boolean isMemberModerator(User user, Activity activity){
        ActivityMember activityMember = activityMemberRepository.findByActivityAndUser(activity, user);
        if(activityMember == null)
            return false;

        return activityMember.getRole().isModerator();
    }
}
