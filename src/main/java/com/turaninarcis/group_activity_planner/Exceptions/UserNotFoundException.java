package com.turaninarcis.group_activity_planner.Exceptions;


public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String username){
        super("User " + username + " doesn't exists");
    }
}
