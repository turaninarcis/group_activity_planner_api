package com.turaninarcis.group_activity_planner.Exceptions;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;



@RestController
public class PageNotFoundController implements ErrorController{
    
    @RequestMapping(value = "/error", method={RequestMethod.GET , RequestMethod.POST, RequestMethod.PATCH, RequestMethod.PUT, RequestMethod.DELETE})
    public void pageNotFound(HttpServletRequest request, HttpServletResponse response) {
        if(response.getStatus() == 404)
            throw new PageNotFoundException();
        else if(response.getStatus() == 401)
            throw new UserNotLoggedInException();
    }   
}
