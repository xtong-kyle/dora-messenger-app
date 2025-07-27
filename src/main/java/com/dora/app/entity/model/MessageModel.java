package com.dora.app.entity.model;

import com.dora.app.common.enums.MessageType;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.sql.Timestamp;

@JsonRootName("messageModel")
public class MessageModel {

    private String id;
    private Long userId;
    private String text;
    private MessageType type;
    private Timestamp createTime;

    public MessageModel() {

    }

    public MessageModel(String id, Long userId, MessageType type, String text, Timestamp createTime) {
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.text = text;
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}
