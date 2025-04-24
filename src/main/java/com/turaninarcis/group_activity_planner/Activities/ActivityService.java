package com.turaninarcis.group_activity_planner.Activities;

import java.util.HashSet;
import java.util.List;
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
import com.turaninarcis.group_activity_planner.Activities.Models.ActivityMemberRoleEnum;
import com.turaninarcis.group_activity_planner.Activities.Models.ActivityMemberUpdateDTO;
import com.turaninarcis.group_activity_planner.Activities.Models.ActivityShortDetailsDTO;
import com.turaninarcis.group_activity_planner.Activities.Models.ActivityUpdateDTO;
import com.turaninarcis.group_activity_planner.Exceptions.PermissionException;
import com.turaninarcis.group_activity_planner.Exceptions.ResourceNotFoundException;
import com.turaninarcis.group_activity_planner.Exceptions.UserAlreadyJoinedException;
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
        UUID id;
        try{
            id = UUID.fromString(activityId);
        }catch(RuntimeException e){
            throw new ResourceNotFoundException("Activity");
        }
        Activity activity = activityRepository.findById(id).orElse(null);
        if(activity == null)
            throw new ResourceNotFoundException(Activity.class.getSimpleName());
        return activity;
    }


    public Set<ActivityShortDetailsDTO> getAllJoinedActivities(){
        User user = userService.getLoggedUser();
        List<Activity> activities = activityRepository.findAllByUserId(user.getId());
        Set<ActivityShortDetailsDTO> joinedActivities = new HashSet<ActivityShortDetailsDTO>();
        for (Activity activity : activities) {
            joinedActivities.add(new ActivityShortDetailsDTO(activity.getId(), activity.getName(), activity.getStartDate()));
        }
        return joinedActivities;
    }

    public ActivityMember isUserModerator(Activity activity){
        ActivityMember activityMember = isUserMember(activity);
        if(activityMember.getRole().isModerator() == false)
            throw new PermissionException();

        return activityMember;
    }
    public ActivityMember isUserAdministrator(Activity activity){
        ActivityMember activityMember = isUserMember(activity);
        if(activityMember.getRole().isAdmin() == false)
            throw new PermissionException();
        return activityMember;
    }

    public ActivityMember isUserMember(Activity activity){
        User user = userService.getLoggedUser();
        ActivityMember activityMember = activityMemberRepository.findByActivityAndUser(activity, user);

        if(activityMember == null)
            throw new PermissionException("You are not a member of the activity!");

        return activityMember;
    }

    public Boolean isUserMemberBoolean(String activityId){
        User user = userService.getLoggedUser();
        Activity activity = getActivityById(activityId);
        ActivityMember activityMember = activityMemberRepository.findByActivityAndUser(activity, user);

        if(activityMember == null)
            return false;

        return true;
    }

    public ActivityMember getMemberById(UUID id){
        ActivityMember member = activityMemberRepository.findById(id).orElse(null);
        if(member == null)
            throw new ResourceNotFoundException("Member");
        return member;
    }

    public ActivityDetailsDTO getActivityDetails(String activityId){

        
        Activity activity = getActivityById(activityId);

        isUserMember(activity);

        Set<ActivityMemberDetailsDTO> members = activityMemberRepository.getAllActivityMembersDetails(activity);
        List<TaskDetailsDTO> tasks = taskService.getAllTasksDetails(activity);
        ActivityDetailsDTO detailsDTO = ActivityDetailsDTO.builder()
                                        .id(activity.getId())
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

    public void updateActivity(ActivityUpdateDTO updateDTO, String activityId){
        Activity activity = getActivityById(activityId);

        isUserAdministrator(activity);

        if(updateDTO.name() != null) activity.setName(updateDTO.name());
        if(updateDTO.description() != null) activity.setDescription(updateDTO.description());
        if(updateDTO.startDate() != null) activity.setStartDate(updateDTO.startDate());
        if(updateDTO.endDate() != null) activity.setEndDate(updateDTO.endDate());

        activityRepository.save(activity);
    }
    public String updateInviteToken(String activityId){
        Activity activity = getActivityById(activityId);
        isUserAdministrator(activity);
        activity.setInviteToken(UUID.randomUUID().toString());
        activityRepository.save(activity);
        return activity.getInviteToken();
    }
    public void deleteActivity(String activityId){
        Activity activity = getActivityById(activityId);

        isUserAdministrator(activity);

        activityRepository.delete(activity);
    }
    public void joinActivity(String joinActivityToken){
        Activity activity = activityRepository.findByInviteToken(joinActivityToken);
        if(activity == null)
            throw new ResourceNotFoundException(Activity.class.getSimpleName());
        
        User user = userService.getLoggedUser();
        ActivityMember activityMember = activityMemberRepository.findByActivityAndUser(activity, user);
        if(activityMember != null)
            throw new UserAlreadyJoinedException();
        
        ActivityMember newMember = new ActivityMember(user, activity, ActivityMemberRoleEnum.PARTICIPANT);
        activityMemberRepository.save(newMember);
    }

    public void changeMemberRole(ActivityMemberUpdateDTO activityMemberUpdateDTO, String activityId){
        Activity activity = getActivityById(activityId);
        isUserAdministrator(activity);

        if(activityMemberUpdateDTO.role() == ActivityMemberRoleEnum.CREATOR)
            throw new PermissionException("You cannot assign a new creator!");

        ActivityMember member = getMemberById(activityMemberUpdateDTO.id());
        member.setRole(activityMemberUpdateDTO.role());
        
        activityMemberRepository.save(member);
    }

    public void changeMemberConfirmation(String activityId){
        Activity activity = getActivityById(activityId);
        ActivityMember member = isUserMember(activity);

        member.setConfirmed(!member.isConfirmed());

        activityMemberRepository.save(member);
    }

    public void kickMember(String activityId, UUID memberId){
        if(memberId==null) throw new ResourceNotFoundException("Activity member");

        Activity activity = getActivityById(activityId);
        ActivityMember member = getMemberById(memberId);

        if(member.getRole() == ActivityMemberRoleEnum.CREATOR) throw new PermissionException("You cannot kick the creator of this activity!");

        isUserAdministrator(activity);

        activityMemberRepository.delete(member);
    }
    public void leaveActivity(String activityId){
        Activity activity = getActivityById(activityId);
        ActivityMember member = isUserMember(activity);

        activityMemberRepository.delete(member);
    }
}
