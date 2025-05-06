package com.turaninarcis.group_activity_planner.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.turaninarcis.group_activity_planner.Users.UserService;
import com.turaninarcis.group_activity_planner.Users.Models.User;

import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTFilter extends OncePerRequestFilter{
    @Autowired
    JWTService jwtService;

    @Autowired 
    ApplicationContext context;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,@NonNull HttpServletResponse response,@NonNull FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String id = null;
        token = jwtService.getToken(authHeader);
        if(token != null)
            id = jwtService.getUserIdFromToken(token);
            System.out.println(id);
        if(id != null && SecurityContextHolder.getContext().getAuthentication() == null){
            User user = context.getBean(UserService.class).loadUserById(id);

            if(!jwtService.isTokenInvalid(token)){
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()); 
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
