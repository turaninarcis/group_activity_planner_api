package com.turaninarcis.group_activity_planner.Exceptions;

public class PageNotFoundException extends RuntimeException {
    public PageNotFoundException(){
        super("Page not found");
    }
}
