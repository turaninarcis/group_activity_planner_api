package com.turaninarcis.group_activity_planner.Exceptions;


public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message){
        super(message);
    }
}
