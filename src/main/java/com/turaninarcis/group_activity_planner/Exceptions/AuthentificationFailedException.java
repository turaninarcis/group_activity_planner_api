package com.turaninarcis.group_activity_planner.Exceptions;

public class AuthentificationFailedException extends RuntimeException {
    public AuthentificationFailedException(){
        super("Incorrect login details. Please try again");
    }
}
