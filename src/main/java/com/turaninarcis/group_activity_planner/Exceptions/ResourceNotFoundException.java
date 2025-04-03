package com.turaninarcis.group_activity_planner.Exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resourceName){
        super(resourceName + " not found");
    }
}
