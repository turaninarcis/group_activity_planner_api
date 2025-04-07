package com.turaninarcis.group_activity_planner.Activities.Models;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.turaninarcis.group_activity_planner.Tasks.Models.TaskAssignment;
import com.turaninarcis.group_activity_planner.Users.Models.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "activity_member")
public class ActivityMember {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "activity_id", nullable = false)
    private Activity activity;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<TaskAssignment> assignments;

    @Enumerated(EnumType.STRING)
    private ActivityMemberRoleEnum role;

    @CreationTimestamp
    private LocalDateTime joinDate;

    private boolean confirmed = false;

    public ActivityMember(User user, Activity activity, ActivityMemberRoleEnum role){
        this.user = user;
        this.activity = activity;
        this.role=role;
    }
    public static ActivityMember MakeCreator(User user, Activity activity){
        ActivityMember creator = new ActivityMember(user, activity, ActivityMemberRoleEnum.CREATOR);
        creator.setConfirmed(true);
        return creator;
    }
}
