package com.dora.app.entity.model;

import com.dora.app.common.enums.MessageType;


public class ChatUserModel {

    private Long userId;
    private String username;
    private String lastActivity;
    private String latestMessage;
    private MessageType lastestMessageType;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLastActivity() {
        return lastActivity;
    }

    public void setLastActivity(String lastActivity) {
        this.lastActivity = lastActivity;
    }

    public String getLatestMessage() {
        return latestMessage;
    }

    public void setLatestMessage(String latestMessage) {
        this.latestMessage = latestMessage;
    }

    public MessageType getLastestMessageType() {
        return lastestMessageType;
    }

    public void setLastestMessageType(MessageType lastestMessageType) {
        this.lastestMessageType = lastestMessageType;
    }

}
