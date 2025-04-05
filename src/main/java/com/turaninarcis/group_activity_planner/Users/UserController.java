package com.turaninarcis.group_activity_planner.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.turaninarcis.group_activity_planner.Exceptions.AuthentificationFailedException;
import com.turaninarcis.group_activity_planner.Exceptions.ValidationException;
import com.turaninarcis.group_activity_planner.Users.Models.UserCreateDTO;
import com.turaninarcis.group_activity_planner.Users.Models.UserDetailsDTO;
import com.turaninarcis.group_activity_planner.Users.Models.UserLoginDTO;
import com.turaninarcis.group_activity_planner.Users.Models.UserUpdateDTO;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestBody;
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
            throw new ValidationException(result);

        userService.register(createDTO);
        return ResponseEntity.ok("User created successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody UserLoginDTO loginDTO) {
        String jwtToken = userService.getJwtToken(loginDTO);

        if(jwtToken==null)
            throw new AuthentificationFailedException();
        return ResponseEntity.ok(jwtToken);
    }

    @PatchMapping("")
    public ResponseEntity<String> changeAccountDetails(@Valid @RequestBody UserUpdateDTO updateDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            throw new ValidationException(bindingResult);
        
        userService.updateUser(updateDTO);
        return ResponseEntity.ok().body("Updated successfully");
    }
    @GetMapping("")
    public ResponseEntity<?> getUserInfo() {
        UserDetailsDTO userDetailsDTO = userService.getUserDetailsDTO();
        return ResponseEntity.ok(userDetailsDTO);
    }
    @DeleteMapping("")
    public ResponseEntity<String> deleteLoggedUser(){
        userService.deleteUser();
        return ResponseEntity.ok().body("User deleted successfully");
    }
    

}
