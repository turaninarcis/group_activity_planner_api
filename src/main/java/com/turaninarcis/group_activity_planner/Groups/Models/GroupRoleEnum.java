package com.turaninarcis.group_activity_planner.Groups.Models;

public enum GroupRoleEnum {
    MEMBER,ADMINISTRATOR,CREATOR;

    public boolean isAdmin(){
        return this == ADMINISTRATOR || this == CREATOR;
    }
}
