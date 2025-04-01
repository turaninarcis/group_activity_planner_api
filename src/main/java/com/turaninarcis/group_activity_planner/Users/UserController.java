package com.turaninarcis.group_activity_planner.Users;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.turaninarcis.group_activity_planner.Exceptions.UserAlreadyExistsException;
import com.turaninarcis.group_activity_planner.Exceptions.UserNotFoundException;
import com.turaninarcis.group_activity_planner.Users.Models.UserCreateDTO;
import com.turaninarcis.group_activity_planner.Users.Models.UserDetailsDTO;
import com.turaninarcis.group_activity_planner.Users.Models.UserLoginDTO;
import com.turaninarcis.group_activity_planner.utility.UtilityControllers;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/users")
@RestController
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody UserCreateDTO createDTO, BindingResult result) {
        if(result.hasErrors())
        {
            return ResponseEntity.badRequest().body(UtilityControllers.getErrors(result));
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

    @GetMapping("/info")
    public ResponseEntity<?> getUserInfo() {
        try{
            UserDetailsDTO userDetailsDTO = userService.getUserDetailsDTO();
            return ResponseEntity.ok(userDetailsDTO);

        }catch(UserNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }
    

}
