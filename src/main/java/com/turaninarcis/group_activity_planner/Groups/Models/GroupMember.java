package com.turaninarcis.group_activity_planner.Groups.Models;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.turaninarcis.group_activity_planner.Users.Models.User;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Getter
@Setter
@Table(name="group_members")
@NoArgsConstructor
public class GroupMember {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name="group_id", nullable = false)
    private Group group;

    @Enumerated(EnumType.STRING)
    private GroupRoleEnum role = GroupRoleEnum.MEMBER;

    @CreationTimestamp
    private LocalDateTime joinDate;


    public GroupMember(User user, Group group){
        this.user=user;
        this.group=group;
    }
}
