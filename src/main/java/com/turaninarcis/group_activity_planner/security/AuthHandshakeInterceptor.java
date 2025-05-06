package com.turaninarcis.group_activity_planner.security;

import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class AuthHandshakeInterceptor implements HandshakeInterceptor {
    private final JWTService jwtService;
    public AuthHandshakeInterceptor(JWTService jwtService)
    {
        this.jwtService = jwtService;
    }
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
            Map<String, Object> attributes) throws Exception {

        String token = UriComponentsBuilder.fromUri(request.getURI()).build().getQueryParams().getFirst("token");

        System.out.println(token);

        if(jwtService.isTokenInvalid(token))return false;

        String username = jwtService.getUserNameFromToken(token);
        String userId = jwtService.getUserIdFromToken(token);
        attributes.put("username", username);
        attributes.put("userId", userId);
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
            Exception exception) {
        System.console().printf("Handshake done");
    }
    
}
