package com.dora.app.service;

import com.dora.app.entity.model.RedisTokenModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

import static com.dora.app.constant.DoraAuthConstants.KEY_PREFIX_;

@Service
public class DoraMessengerRedisAuthService {

    private final RedisTemplate<String, Object> redisTemplate;

    public DoraMessengerRedisAuthService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    private String redisKey(String userId) {
        return KEY_PREFIX_ + userId;
    }

    public void saveToken(RedisTokenModel redisTokenModel) {
        // Store the whole object, will be serialized automatically
        redisTemplate.opsForValue().set(redisKey(redisTokenModel.getUserId()), redisTokenModel, Duration.ofHours(1));
    }

    public RedisTokenModel getToken(String userId) {
        Object obj = redisTemplate.opsForValue().get(redisKey(userId));
        if (obj == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(obj, RedisTokenModel.class);
    }

    public void deleteToken(String userId) {
        redisTemplate.delete(redisKey(userId));
    }
}
