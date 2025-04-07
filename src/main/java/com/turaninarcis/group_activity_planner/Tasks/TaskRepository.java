package com.turaninarcis.group_activity_planner.Tasks;

import java.util.Set;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turaninarcis.group_activity_planner.Activities.Models.Activity;
import com.turaninarcis.group_activity_planner.Tasks.Models.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID>{

    public Set<Task> findByActivity(Activity activity);
}
