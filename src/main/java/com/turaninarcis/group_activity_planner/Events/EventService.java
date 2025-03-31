package com.turaninarcis.group_activity_planner.Events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {
    @Autowired
    EventRepository eventRepository;
    @Autowired
    EventMemberRepository eventMemberRepository;
}
