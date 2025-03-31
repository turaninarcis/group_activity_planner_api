package com.turaninarcis.group_activity_planner.Tasks.Models;

import java.util.UUID;

import com.turaninarcis.group_activity_planner.Users.Models.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "task_assignments")
public class TaskAssignment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    private boolean completed = false;

    public TaskAssignment(User user, Task task){
        this.user = user;
        this.task = task;
    }
}
