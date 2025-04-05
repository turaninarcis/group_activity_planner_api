package com.turaninarcis.group_activity_planner.Exceptions;

public class IncorrectPasswordException extends RuntimeException{
    public IncorrectPasswordException(){
        super("Incorrect password");
    }
}
