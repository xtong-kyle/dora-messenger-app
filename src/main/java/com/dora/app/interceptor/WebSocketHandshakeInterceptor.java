package com.dora.app.interceptor;

import com.dora.app.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.List;
import java.util.Map;

@Component
public class WebSocketHandshakeInterceptor implements HandshakeInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        // 1. Extract token from Authorization header
        if (request instanceof ServletServerHttpRequest servletRequest) {
            HttpServletRequest httpServletRequest = servletRequest.getServletRequest();

            // Extract JWT from headers
            String authHeader = httpServletRequest.getHeader("Authorization");

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                if (jwtUtil.validateToken(token)) {
                    String username = jwtUtil.extractUsername(token);
                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(username, null, List.of());
                    attributes.put("principal", auth);
                }
            }
        }

        // 2. If no header, try token from query param (e.g., ws://host/chat?token=xxx)
        if (request instanceof ServletServerHttpRequest servletRequest) {
            String token = servletRequest.getServletRequest().getParameter("token");
            if (token != null && jwtUtil.validateToken(token)) {
                String username = jwtUtil.extractUsername(token);
                attributes.put("user", username);
                return true;
            }
        }

        // Reject handshake if no valid token
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}
