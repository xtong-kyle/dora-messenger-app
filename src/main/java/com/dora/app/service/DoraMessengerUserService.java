package com.dora.app.service;

import com.dora.app.repository.UserDetailRepository;
import com.dora.app.repository.entity.UserEntity;
import com.dora.app.entity.response.MessengerUserInfoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DoraMessengerUserService {

    @Autowired
    private UserDetailRepository userDetailRepository;

    public MessengerUserInfoResponse getUserChatInfoByName(String username) {
        Optional<UserEntity> userEntity = userDetailRepository.getUserBaseInfoByName(username);
        MessengerUserInfoResponse userInfoResponse = null;
        if (userEntity.isPresent()) {
            userInfoResponse = new MessengerUserInfoResponse();
            userInfoResponse.setId(userEntity.get().getId());
            userInfoResponse.setUsername(userEntity.get().getUsername());
        }
        return userInfoResponse;
    }

}
