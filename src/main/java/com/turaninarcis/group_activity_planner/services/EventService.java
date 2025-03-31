package com.turaninarcis.group_activity_planner.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turaninarcis.group_activity_planner.repositories.EventMemberRepository;
import com.turaninarcis.group_activity_planner.repositories.EventRepository;

@Service
public class EventService {
    @Autowired
    EventRepository eventRepository;
    @Autowired
    EventMemberRepository eventMemberRepository;
}
