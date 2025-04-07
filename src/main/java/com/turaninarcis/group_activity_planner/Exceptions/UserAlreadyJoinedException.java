package com.turaninarcis.group_activity_planner.Exceptions;

public class UserAlreadyJoinedException extends RuntimeException{
    public UserAlreadyJoinedException(){
        super("User already joined");
    }
}
