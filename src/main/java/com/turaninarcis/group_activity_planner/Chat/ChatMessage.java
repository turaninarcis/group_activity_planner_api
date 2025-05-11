package com.turaninarcis.group_activity_planner.Chat;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.turaninarcis.group_activity_planner.Groups.Models.Group;
import com.turaninarcis.group_activity_planner.Groups.Models.GroupMember;

import jakarta.persistence.Column;
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

@Getter
@Setter
@Entity
@Table(name="messages")
@NoArgsConstructor
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @ManyToOne
    @JoinColumn(name="sender_id",nullable = true)
    private GroupMember sender;

    @ManyToOne
    @JoinColumn(name="group_id", nullable = false)
    private Group group;

    @Column(nullable = false, columnDefinition = "VARCHAR(1000)")
    private String message;
    
    @Column(columnDefinition = "VARCHAR(400)")
    private String image;

    @CreationTimestamp
    private LocalDateTime timestamp;

    public ChatMessage(GroupMember sender, Group group, String message, String image){
        this.sender = sender;
        this.message = message;
        this.group = group;
        this.image=image;
    }
    public ChatMessage(GroupMember sender, Group group, String message){
        this.sender = sender;
        this.message = message;
        this.group = group;
    }
}
