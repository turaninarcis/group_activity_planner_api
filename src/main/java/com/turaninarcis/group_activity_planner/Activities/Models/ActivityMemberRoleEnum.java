package com.turaninarcis.group_activity_planner.Activities.Models;

public enum ActivityMemberRoleEnum {
    CREATOR, ADMINISTRATOR, PARTICIPANT;

    public boolean isAdmin(){
        return this == CREATOR || this == ADMINISTRATOR;
    }
}
