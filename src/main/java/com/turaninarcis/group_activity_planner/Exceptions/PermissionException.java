package com.turaninarcis.group_activity_planner.Exceptions;

public class PermissionException extends RuntimeException {
    public PermissionException(){
        super("You don't have persmissions to do this action");
    }
    public PermissionException(String message ){
        super(message);
    }
}
