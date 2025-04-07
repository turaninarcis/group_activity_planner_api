package com.turaninarcis.group_activity_planner.Tasks;



import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turaninarcis.group_activity_planner.Activities.ActivityService;
import com.turaninarcis.group_activity_planner.Activities.Models.Activity;
import com.turaninarcis.group_activity_planner.Activities.Models.ActivityMember;
import com.turaninarcis.group_activity_planner.Exceptions.ResourceNotFoundException;
import com.turaninarcis.group_activity_planner.Tasks.Models.Task;
import com.turaninarcis.group_activity_planner.Tasks.Models.TaskAssigmnentCreateDTO;
import com.turaninarcis.group_activity_planner.Tasks.Models.TaskAssignment;
import com.turaninarcis.group_activity_planner.Tasks.Models.TaskAssignmnentDetailsDTO;
import com.turaninarcis.group_activity_planner.Tasks.Models.TaskCreateDTO;
import com.turaninarcis.group_activity_planner.Tasks.Models.TaskDetailsDTO;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TaskAssignmentRepository taskAssignmentRepository;
    @Autowired
    private ActivityService activityService;


    public void createTask(TaskCreateDTO taskCreateDTO, String activityId){
        Activity activity = activityService.getActivityById(activityId);
   
        activityService.isUserModerator(activity);
        
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
        TaskDetailsDTO taskDetailsDTO = new TaskDetailsDTO(task.getId(), task.getName(), task.getDescription(), getAllTaskAssignmentDetails(task));
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
        return new TaskAssignmnentDetailsDTO(taskAssignment.getId() ,taskAssignment.getUser().getUser().getUsername() ,taskAssignment.isCompleted());
    }


    public void createTaskAssignment(TaskAssigmnentCreateDTO taskAssigmnentCreateDTO, String activityId){
        Activity activitty = activityService.getActivityById(activityId);
        ActivityMember member = activityService.isUserMember(activitty);

        Task task = findTaskById(taskAssigmnentCreateDTO.taskId());

        TaskAssignment taskAssignment = new TaskAssignment(member, task);
        taskAssignmentRepository.save(taskAssignment);
    }
}
