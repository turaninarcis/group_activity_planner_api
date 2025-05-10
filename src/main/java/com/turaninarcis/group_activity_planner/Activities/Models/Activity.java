package com.turaninarcis.group_activity_planner.Activities.Models;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.turaninarcis.group_activity_planner.Tasks.Models.Task;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "activities")
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @NotBlank
    private String name;

    private String description;

    @OneToMany(mappedBy = "activity", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<ActivityMember> members;

    @OneToMany(mappedBy = "activity", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Task> tasks;

    @NotBlank
    @NotNull
    private String inviteToken;

    @CreationTimestamp
    private LocalDateTime created;

    private String imagePath;

    @UpdateTimestamp
    private LocalDateTime modified;

    private LocalDateTime startDate;
    private LocalDateTime endDate;


    public Activity(String name, String description, String inviteToken, LocalDateTime startDate, LocalDateTime endDate){
        this.name = name;
        this.description = description;
        this.inviteToken = inviteToken;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Activity(String name, String description, String inviteToken, String imagePath, LocalDateTime startDate, LocalDateTime endDate){
        this.name = name;
        this.description = description;
        this.inviteToken = inviteToken;
        this.startDate = startDate;
        this.endDate = endDate;
        this.imagePath = imagePath;
    }
}
