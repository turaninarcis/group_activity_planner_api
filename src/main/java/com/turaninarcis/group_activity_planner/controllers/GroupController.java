package com.turaninarcis.group_activity_planner.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.turaninarcis.group_activity_planner.services.GroupService;

@Controller
public class GroupController {
    @Autowired
    GroupService groupService;
}
