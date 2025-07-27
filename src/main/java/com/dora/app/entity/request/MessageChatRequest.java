package com.dora.app.entity.request;

import com.dora.app.entity.model.MessageModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonRootName("request")
public class MessageChatRequest {
    private long senderId;
    private long receiverId;
    private String conversationId;
    private MessageModel messageModel;

    public long getSenderId() {
        return senderId;
    }

    public void setSenderId(long senderId) {
        this.senderId = senderId;
    }

    public long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(long receiverId) {
        this.receiverId = receiverId;
    }

    public MessageModel getMessageModel() {
        return messageModel;
    }

    public void setMessageModel(MessageModel messageModel) {
        this.messageModel = messageModel;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }
}
