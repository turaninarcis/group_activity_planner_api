package com.turaninarcis.group_activity_planner.Users;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.turaninarcis.group_activity_planner.Exceptions.AuthentificationFailedException;
import com.turaninarcis.group_activity_planner.Exceptions.ResourceNotFoundException;
import com.turaninarcis.group_activity_planner.Exceptions.UserAlreadyExistsException;
import com.turaninarcis.group_activity_planner.Users.Models.RoleEnum;
import com.turaninarcis.group_activity_planner.Users.Models.User;
import com.turaninarcis.group_activity_planner.Users.Models.UserCreateDTO;
import com.turaninarcis.group_activity_planner.Users.Models.UserDetailsDTO;
import com.turaninarcis.group_activity_planner.Users.Models.UserLoginDTO;
import com.turaninarcis.group_activity_planner.security.JWTService;

@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private AuthenticationManager authManager;
    private JWTService jwtService;
    private Authentication authentication;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public UserService(UserRepository repo, @Lazy AuthenticationManager authManager, JWTService jwtService){
        this.userRepository = repo;
        this.authManager = authManager;
        this.jwtService = jwtService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }
    

    /**
     * Returns the current user from the SecurityContextHolder and returns it
     * @return current logged in user
     */
    public User getLoggedUser(){
        String username = getLoggedUsername();
        User user = userRepository.findByUsername(username);
        if(user == null)
            throw new ResourceNotFoundException(User.class.getSimpleName());
        return user;
    }

    /**
     * Gets the current users username from the SecurityContextHolder and returns it
     * @return current logged in username
     */
    public String getLoggedUsername() {
        Object principal = authentication.getPrincipal();
        
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }
    public Boolean isUserAdmin(){
        return authentication.getAuthorities().contains(RoleEnum.ROLE_ADMIN);
    }
    public Boolean isAuthenticated(){
        return authentication.isAuthenticated();
    }
    


    public void register(UserCreateDTO userCreateDTO) {
        if(userRepository.findByEmail(userCreateDTO.email()) != null)
            throw new UserAlreadyExistsException("Email is already in use");

        if(userRepository.findByUsername(userCreateDTO.username()) != null)
            throw new UserAlreadyExistsException("Username already in use");


        String password = encoder.encode(userCreateDTO.password());
        User user = new User(userCreateDTO.username(), password, userCreateDTO.email());
        userRepository.save(user);
    }
  


    public UserDetailsDTO getUserDetailsDTO(){
        User user = getLoggedUser();

        return new UserDetailsDTO(user.getUsername(), user.getEmail(), user.isEmailVerified(),
        user.isDeletedAccount(), user.getCreated(),user.getLastTimeUpdated());
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


        authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), userLoginDTO.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtService.generateToken(user.getUsername());

    }

}
