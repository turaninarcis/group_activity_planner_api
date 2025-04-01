package com.turaninarcis.group_activity_planner.Users;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;

import com.turaninarcis.group_activity_planner.Exceptions.UserAlreadyExistsException;
import com.turaninarcis.group_activity_planner.Users.Models.UserCreateDTO;
import com.turaninarcis.group_activity_planner.Users.Models.UserLoginDTO;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("/users")
@Controller
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody UserCreateDTO createDTO, BindingResult result) {
        if(result.hasErrors())
        {
            return ResponseEntity.badRequest().body(getErrors(result));
        }
        try{
            userService.register(createDTO);
            return ResponseEntity.ok("User created successfully");
        }catch(UserAlreadyExistsException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch(Exception e){
            return ResponseEntity.badRequest().body("Something went wrong");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody UserLoginDTO loginDTO) {
        
        String jwtToken = userService.getJwtToken(loginDTO);
        if(jwtToken==null)
            return ResponseEntity.badRequest().body("User credentials are not correct");
        return ResponseEntity.ok(jwtToken);
    }

    public String getErrors(BindingResult result){
        List<String> errorList = result.getAllErrors()
                                    .stream()
                                    .map(error -> error.getDefaultMessage()) // Get the default validation message
                                    .collect(Collectors.toList());
        return String.join("\n", errorList);
    }
}
