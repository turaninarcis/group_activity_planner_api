package com.turaninarcis.group_activity_planner.Exceptions;

public class UserNotLoggedInException extends RuntimeException {
    public UserNotLoggedInException(){
        super("You are not logged in!");
    } 
}
