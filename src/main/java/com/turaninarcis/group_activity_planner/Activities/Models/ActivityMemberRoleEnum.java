package com.turaninarcis.group_activity_planner.Activities.Models;

public enum ActivityMemberRoleEnum {
    CREATOR, ADMINISTRATOR, MODERATOR ,PARTICIPANT;

    public boolean isAdmin(){
        return this == CREATOR || this == ADMINISTRATOR;
    }
    public boolean isModerator(){
        return this == MODERATOR || isAdmin();
    }
}
