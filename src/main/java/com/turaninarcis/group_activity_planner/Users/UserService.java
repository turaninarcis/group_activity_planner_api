package com.turaninarcis.group_activity_planner.Users;

import java.util.UUID;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.turaninarcis.group_activity_planner.Exceptions.AuthentificationFailedException;
import com.turaninarcis.group_activity_planner.Exceptions.IncorrectPasswordException;
import com.turaninarcis.group_activity_planner.Exceptions.UserAlreadyExistsException;
import com.turaninarcis.group_activity_planner.Users.Models.RoleEnum;
import com.turaninarcis.group_activity_planner.Users.Models.User;
import com.turaninarcis.group_activity_planner.Users.Models.UserCreateDTO;
import com.turaninarcis.group_activity_planner.Users.Models.UserDetailsDTO;
import com.turaninarcis.group_activity_planner.Users.Models.UserLoginDTO;
import com.turaninarcis.group_activity_planner.Users.Models.UserUpdateDTO;
import com.turaninarcis.group_activity_planner.security.JWTService;

@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private AuthenticationManager authManager;
    private JWTService jwtService;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public UserService(UserRepository repo, @Lazy AuthenticationManager authManager, JWTService jwtService){
        this.userRepository = repo;
        this.authManager = authManager;
        this.jwtService = jwtService;
    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        return userRepository.findById(UUID.fromString(id)).orElse(null);
    }

    public User loadUserById(String id) throws UsernameNotFoundException {
        return userRepository.findById(UUID.fromString(id)).orElse(null);
    }

    public User findUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    /**
     * Returns the current user from the SecurityContextHolder and returns it
     * @return current logged in user
     */
    public User getLoggedUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    /**
     * Gets the current users username from the SecurityContextHolder and returns it
     * @return current logged in username
     */

    public Boolean isUserAdmin(){
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(RoleEnum.ROLE_ADMIN);
    }
    public Boolean isAuthenticated(){
        return SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
    }
    


    public String register(UserCreateDTO userCreateDTO) {
        if(userRepository.findByEmail(userCreateDTO.email()) != null)
            throw new UserAlreadyExistsException("Email is already in use");

        if(userRepository.findByUsername(userCreateDTO.username()) != null)
            throw new UserAlreadyExistsException("Username already in use");


        String password = encoder.encode(userCreateDTO.password());
        User user = new User(userCreateDTO.username(), password, userCreateDTO.email());
        userRepository.save(user);
        authManager.authenticate(new UsernamePasswordAuthenticationToken(userCreateDTO.username(), userCreateDTO.password()));
        return jwtService.generateToken(userCreateDTO.username(), user.getId());
    }
  

    public UserDetailsDTO updateUser(UserUpdateDTO updateDTO){
        User user = getLoggedUser();

        if(!encoder.matches(updateDTO.password(), user.getPassword()))
        throw new IncorrectPasswordException();
        
        if(updateDTO.email()!= null){
            if(userRepository.existsByEmailAndIdNot(updateDTO.email(), user.getId()))
                throw new UserAlreadyExistsException("Someone else already uses this email");
            user.setEmail(updateDTO.email());
        }

        if(updateDTO.newPassword() != null)
            user.setPassword(encoder.encode(updateDTO.newPassword()));

        if(updateDTO.username()!= null){
            if(userRepository.existsByUsernameAndIdNot(updateDTO.username(), user.getId()))
                throw new UserAlreadyExistsException("Someone else already uses this username");
            user.setUsername(updateDTO.username());
        }

        userRepository.save(user);

        return CreateUserDetailsDTO(user);
    }
    public UserDetailsDTO getUserDetailsDTO(){
        User user = getLoggedUser();

        return CreateUserDetailsDTO(user);
    }

    private UserDetailsDTO CreateUserDetailsDTO(User user){
        return new UserDetailsDTO(user.getUsername(), user.getEmail(), user.isEmailVerified(),
        user.isDeletedAccount(), user.getCreated(),user.getLastTimeUpdated());
    }

    public void deleteUser(){
        User user = getLoggedUser();
        userRepository.delete(user);
    }

    /**Returns jwt token if login credentials are correct or throws exception if not
     * 
     * @param userLoginDTO
     * @return JWT Token/null
     */
    public String getJwtToken(UserLoginDTO userLoginDTO){
        User user = userRepository.findByUsername(userLoginDTO.username());
        if(user == null)
            user = userRepository.findByEmail(userLoginDTO.username());
        if(user == null)
            throw new AuthentificationFailedException();

        authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getId(), userLoginDTO.password()));
        
        return jwtService.generateToken(user.getUsername(), user.getId());
    }

}
