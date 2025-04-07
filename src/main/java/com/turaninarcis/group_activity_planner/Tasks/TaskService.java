package com.turaninarcis.group_activity_planner.Tasks;



import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turaninarcis.group_activity_planner.Activities.ActivityService;
import com.turaninarcis.group_activity_planner.Activities.Models.Activity;
import com.turaninarcis.group_activity_planner.Exceptions.PermissionException;
import com.turaninarcis.group_activity_planner.Exceptions.ResourceNotFoundException;
import com.turaninarcis.group_activity_planner.Tasks.Models.Task;
import com.turaninarcis.group_activity_planner.Tasks.Models.TaskAssigmnentCreateDTO;
import com.turaninarcis.group_activity_planner.Tasks.Models.TaskAssignment;
import com.turaninarcis.group_activity_planner.Tasks.Models.TaskAssignmnentDetailsDTO;
import com.turaninarcis.group_activity_planner.Tasks.Models.TaskCreateDTO;
import com.turaninarcis.group_activity_planner.Tasks.Models.TaskDetailsDTO;
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

        User user = userService.getLoggedUser();
   
        if(!activityService.isMemberModerator(user, activity))
            throw new PermissionException();
        
        Task task = new Task(taskCreateDTO.name(),taskCreateDTO.description(),activity);
        taskRepository.save(task);
    }
    public Task findTaskById(UUID id){
        Task task = taskRepository.findById(id).orElse(null);
        if(task == null)
            throw new ResourceNotFoundException(Task.class.getSimpleName());
        return task;
    }

    public Set<TaskDetailsDTO> getAllTasksDetails(Activity activity){
        Set<Task> tasks = activity.getTasks();
        Set<TaskDetailsDTO> taskDetails = new HashSet<>();
        for (Task task : tasks) {
            taskDetails.add(getTaskDetails(task));
        }
        return taskDetails;
    }

    public TaskDetailsDTO getTaskDetails(Task task){
        TaskDetailsDTO taskDetailsDTO = new TaskDetailsDTO(task.getName(), task.getDescription(), getAllTaskAssignmentDetails(task));
        return taskDetailsDTO;
    }

    private Set<TaskAssignmnentDetailsDTO> getAllTaskAssignmentDetails(Task task){
        Set<TaskAssignment> taskAssignments = task.getAssignments();
        Set<TaskAssignmnentDetailsDTO> assignmentsDetails = new HashSet<>();
        for (TaskAssignment assignment : taskAssignments) {
            assignmentsDetails.add(getTaskAssignmentDetailsDTO(assignment));
        }
        return assignmentsDetails;
    }

    private TaskAssignmnentDetailsDTO getTaskAssignmentDetailsDTO(TaskAssignment taskAssignment){
        return new TaskAssignmnentDetailsDTO(taskAssignment.getUser().getUsername(), taskAssignment.isCompleted());
    }


    public void createTaskAssignment(TaskAssigmnentCreateDTO taskAssigmnentCreateDTO){
        User user = userService.getLoggedUser();
        Task task = findTaskById(taskAssigmnentCreateDTO.taskId());

        TaskAssignment taskAssignment = new TaskAssignment(user, task);
        taskAssignmentRepository.save(taskAssignment);
    }
}
