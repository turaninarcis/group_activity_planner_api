package com.turaninarcis.group_activity_planner.Tasks;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turaninarcis.group_activity_planner.Activities.ActivityService;
import com.turaninarcis.group_activity_planner.Activities.Models.Activity;
import com.turaninarcis.group_activity_planner.Exceptions.PermissionException;
import com.turaninarcis.group_activity_planner.Exceptions.ResourceNotFoundException;
import com.turaninarcis.group_activity_planner.Tasks.Models.Task;
import com.turaninarcis.group_activity_planner.Tasks.Models.TaskAssigmnentCreateDTO;
import com.turaninarcis.group_activity_planner.Tasks.Models.TaskCreateDTO;
import com.turaninarcis.group_activity_planner.Users.UserService;
import com.turaninarcis.group_activity_planner.Users.Models.User;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TaskAssignmentRepository taskAssignmentRepository;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private UserService userService;

    public void createTask(TaskCreateDTO taskCreateDTO){
        Activity activity = activityService.getActivityById(taskCreateDTO.activityId());
        if(activity == null)
            throw new ResourceNotFoundException(Activity.class.getSimpleName());

        User user = userService.getLoggedUser();
   
        if(!activityService.isMemberModerator(user, activity))
            throw new PermissionException();
        
        Task task = new Task(taskCreateDTO.name(),taskCreateDTO.description(),activity);
        taskRepository.save(task);
    }
    public void createTaskAssignment(TaskAssigmnentCreateDTO taskAssigmnentCreateDTO){
        User user = userService.getLoggedUser();
        Task task = taskRepository.findById(taskAssigmnentCreateDTO.taskId()).orElse(null);

    }
}
