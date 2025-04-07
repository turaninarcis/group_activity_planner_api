package com.turaninarcis.group_activity_planner.Tasks;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turaninarcis.group_activity_planner.Tasks.Models.Task;
import com.turaninarcis.group_activity_planner.Tasks.Models.TaskAssignment;
import com.turaninarcis.group_activity_planner.Activities.Models.ActivityMember;


@Repository
public interface TaskAssignmentRepository extends JpaRepository<TaskAssignment, UUID> {
    public TaskAssignment findByUserAndTask(ActivityMember user, Task task);
}
