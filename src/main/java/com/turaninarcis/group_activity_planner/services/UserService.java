package com.turaninarcis.group_activity_planner.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turaninarcis.group_activity_planner.repositories.UserRepository;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
}
