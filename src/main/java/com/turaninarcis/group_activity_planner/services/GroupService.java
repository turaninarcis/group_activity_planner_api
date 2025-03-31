package com.turaninarcis.group_activity_planner.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turaninarcis.group_activity_planner.repositories.GroupMembersRepository;
import com.turaninarcis.group_activity_planner.repositories.GroupRepository;

@Service
public class GroupService {
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    GroupMembersRepository groupMembersRepository;
}
