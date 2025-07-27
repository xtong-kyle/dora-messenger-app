package com.dora.app.interceptor;

import com.dora.app.entity.model.RedisTokenModel;
import com.dora.app.service.DoraMessengerRedisAuthService;
import com.dora.app.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class WebSocketChannelInterceptor implements ChannelInterceptor {

    @Autowired
    private DoraMessengerRedisAuthService redisAuthService;
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor =
                MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String userId = accessor.getFirstNativeHeader("userId");
            RedisTokenModel tokenModel = redisAuthService.getToken(userId);
            if (tokenModel != null && jwtUtil.validateToken(tokenModel.getToken())) {
                Authentication auth = new UsernamePasswordAuthenticationToken(userId, null, List.of());
                accessor.setUser(auth); // attached to WebSocket session
                SecurityContextHolder.getContext().setAuthentication(auth);
            } else {
                throw new MessageDeliveryException("Invalid token");
            }
        }

        return message;
    }
}
