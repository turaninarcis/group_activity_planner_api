package com.turaninarcis.group_activity_planner.Activities;

import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.turaninarcis.group_activity_planner.Activities.Models.Activity;
import com.turaninarcis.group_activity_planner.Activities.Models.ActivityCreateDTO;
import com.turaninarcis.group_activity_planner.Activities.Models.ActivityDetailsDTO;
import com.turaninarcis.group_activity_planner.Activities.Models.ActivityMember;
import com.turaninarcis.group_activity_planner.Activities.Models.ActivityMemberDetailsDTO;
import com.turaninarcis.group_activity_planner.Exceptions.PermissionException;
import com.turaninarcis.group_activity_planner.Exceptions.ResourceNotFoundException;
import com.turaninarcis.group_activity_planner.Tasks.TaskService;
import com.turaninarcis.group_activity_planner.Tasks.Models.TaskDetailsDTO;
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

    @Lazy
    @Autowired
    private TaskService taskService;

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

    public Activity getActivityById(String activityId){
        UUID id = UUID.fromString(activityId);
        Activity activity = activityRepository.findById(id).orElse(null);
        if(activity == null)
            throw new ResourceNotFoundException(Activity.class.getSimpleName());
        return activity;
    }
    public boolean isMemberModerator(User user, Activity activity){
        ActivityMember activityMember = activityMemberRepository.findByActivityAndUser(activity, user);
        if(activityMember == null)
            return false;

        return activityMember.getRole().isModerator();
    }
    public boolean isUserMember(Activity activity){
        User user = userService.getLoggedUser();
        ActivityMember activityMember = activityMemberRepository.findByActivityAndUser(activity, user);
        if(activityMember == null)
            return false;
        return true;
    }
    public ActivityDetailsDTO getActivityDetails(String activityId){

        
        Activity activity = getActivityById(activityId);

        if(!isUserMember(activity))
            throw new PermissionException("You are not a member of the activity!");

        Set<ActivityMemberDetailsDTO> members = activityMemberRepository.getAllActivityMembersDetails(activity);
        Set<TaskDetailsDTO> tasks = taskService.getAllTasksDetails(activity);
        ActivityDetailsDTO detailsDTO = ActivityDetailsDTO.builder()
                                        .name(activity.getName())
                                        .description(activity.getDescription())
                                        .inviteToken(activity.getInviteToken())
                                        .dateOfCreation(activity.getCreated())
                                        .lastModify(activity.getModified())
                                        .startDate(activity.getStartDate())
                                        .endDate(activity.getEndDate())
                                        .members(members)
                                        .tasks(tasks)
                                        .build();
        return detailsDTO;
    }
}
