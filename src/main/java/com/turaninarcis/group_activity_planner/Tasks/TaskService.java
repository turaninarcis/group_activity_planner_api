package com.turaninarcis.group_activity_planner.Tasks;



import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turaninarcis.group_activity_planner.Activities.ActivityService;
import com.turaninarcis.group_activity_planner.Activities.Models.Activity;
import com.turaninarcis.group_activity_planner.Activities.Models.ActivityMember;
import com.turaninarcis.group_activity_planner.Exceptions.PermissionException;
import com.turaninarcis.group_activity_planner.Exceptions.ResourceNotFoundException;
import com.turaninarcis.group_activity_planner.Tasks.Models.Task;
import com.turaninarcis.group_activity_planner.Tasks.Models.TaskAssignment;
import com.turaninarcis.group_activity_planner.Tasks.Models.TaskAssignmnentDetailsDTO;
import com.turaninarcis.group_activity_planner.Tasks.Models.TaskCreateDTO;
import com.turaninarcis.group_activity_planner.Tasks.Models.TaskDetailsDTO;
import com.turaninarcis.group_activity_planner.Tasks.Models.TaskUpdateDTO;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TaskAssignmentRepository taskAssignmentRepository;
    @Autowired
    private ActivityService activityService;


    public UUID createTask(TaskCreateDTO taskCreateDTO, String activityId){
        Activity activity = activityService.getActivityById(activityId);
   
        activityService.isUserAdministrator(activity);
        
        Task task = new Task(taskCreateDTO.name(),taskCreateDTO.description(),activity);
        taskRepository.save(task);

        return task.getId();
    }
    public Task findTaskById(UUID id){
        Task task = taskRepository.findById(id).orElse(null);
        if(task == null)
            throw new ResourceNotFoundException(Task.class.getSimpleName());
        return task;
    }

    public List<TaskDetailsDTO> getAllTasksDetails(Activity activity){
        Set<Task> tasks = activity.getTasks();
        List<Task> sortedTasks = new ArrayList<>(tasks);

        sortedTasks.sort(Comparator.comparing(Task::getName));

        List<TaskDetailsDTO> taskDetails = new ArrayList<TaskDetailsDTO>();

        for (Task task : sortedTasks) {
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


    public void createTaskAssignment(UUID taskId, String activityId){
        Activity activitty = activityService.getActivityById(activityId);
        ActivityMember member = activityService.isUserMember(activitty);

        Task task = findTaskById(taskId);

        TaskAssignment taskAssignment = new TaskAssignment(member, task);
        taskAssignmentRepository.save(taskAssignment);

    }

    public void updateTask(TaskUpdateDTO updateDTO, String activityId, UUID taskId){
        Task task = findTaskById(taskId);

        Activity activity = activityService.getActivityById(activityId);
        activityService.isUserAdministrator(activity);

        if(updateDTO.name()!= null) task.setName(updateDTO.name());
        if(updateDTO.description()!= null) task.setDescription(updateDTO.description());

        taskRepository.save(task);
    }

    public void deleteTask(String activityId, UUID taskId){
        if(taskId == null) throw new ResourceNotFoundException("Task");

        Activity activity = activityService.getActivityById(activityId);
        activityService.isUserAdministrator(activity);

        Task task = findTaskById(taskId);
        taskRepository.delete(task);
    }

    public TaskAssignment isUserTaskAssignmentOwner(String activityId, UUID taskId){
        Activity activity = activityService.getActivityById(activityId);
        ActivityMember member = activityService.isUserMember(activity);
        Task task = findTaskById(taskId);

        TaskAssignment taskAssignment = taskAssignmentRepository.findByUserAndTask(member, task);
        if(taskAssignment==null)
            throw new PermissionException("You do not have the permission to change other people assignments!");
        return taskAssignment;
    }

    public void updateTaskAssignment(String activityId,UUID taskId){
        TaskAssignment taskAssignment = isUserTaskAssignmentOwner(activityId, taskId);

        taskAssignment.setCompleted(!taskAssignment.isCompleted());
        taskAssignmentRepository.save(taskAssignment);
    }
    public void deleteTaskAssignment(String activityId, UUID taskId){
        TaskAssignment taskAssignment = isUserTaskAssignmentOwner(activityId, taskId);

        taskAssignmentRepository.delete(taskAssignment);
    }
}
