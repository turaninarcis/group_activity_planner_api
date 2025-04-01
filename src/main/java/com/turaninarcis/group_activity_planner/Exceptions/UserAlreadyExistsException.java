package com.turaninarcis.group_activity_planner.Exceptions;

public class UserAlreadyExistsException extends RuntimeException{
    public UserAlreadyExistsException(String username){
        super("User " + username + " already exists");
    }
}
