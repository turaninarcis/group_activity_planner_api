package com.turaninarcis.group_activity_planner.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/register")
    public String register(@RequestBody String username, @RequestBody String password, @RequestBody String email) {
        userService.register(username,password,email);
        return "ok";
    }
    
}
