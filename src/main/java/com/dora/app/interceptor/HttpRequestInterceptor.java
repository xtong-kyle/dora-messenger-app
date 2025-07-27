package com.dora.app.interceptor;

import com.dora.app.entity.DoraMessengerBadResponse;
import com.dora.app.entity.DoraMessengerExceptionResponse;
import com.dora.app.service.DoraMessengerRedisAuthService;
import com.dora.app.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class HttpRequestInterceptor implements HandlerInterceptor {

    @Autowired
    private DoraMessengerRedisAuthService redisAuthService;
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            return true; // Let CORS preflight pass through
        }
        String userId = request.getHeader("userId");
        try {
            if (null == userId) {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.setContentType("application/json");
                DoraMessengerBadResponse badResponse = new DoraMessengerBadResponse("Missing identity in header");
                response.getWriter().write(new ObjectMapper().writeValueAsString(badResponse));
                return false;
            }
        } catch (Exception e) {
            response.getWriter().write(new ObjectMapper().writeValueAsString(new DoraMessengerExceptionResponse()));
        }
        return true;
    }
}
