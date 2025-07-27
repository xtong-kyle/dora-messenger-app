package com.dora.app.entity.response;

import com.dora.app.entity.model.ChatUserModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.List;

@JsonRootName("chatUsers")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DoraMessengerChatUsersResponse {

    private Long userId;
    private List<ChatUserModel> chatUserModels;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<ChatUserModel> getChatUserModels() {
        return chatUserModels;
    }

    public void setChatUserModels(List<ChatUserModel> chatUserModels) {
        this.chatUserModels = chatUserModels;
    }
}
