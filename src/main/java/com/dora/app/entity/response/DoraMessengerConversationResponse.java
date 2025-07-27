package com.dora.app.entity.response;

import com.dora.app.entity.model.ChatUserModel;
import com.dora.app.entity.model.MessageModel;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.ArrayList;
import java.util.List;


@JsonRootName("response")
public class DoraMessengerConversationResponse {

    private String conversationId;
    private String name;
    private List<ChatUserModel> participants;
    private List<MessageModel> messageModels;

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ChatUserModel> getParticipants() {
        return participants;
    }

    public void setParticipants(List<ChatUserModel> participants) {
        this.participants = participants;
    }

    public List<MessageModel> getMessageModels() {
        if (messageModels == null) {
            messageModels = new ArrayList<>();
        }
        return messageModels;
    }

    public void setMessageModels(List<MessageModel> messageModels) {
        this.messageModels = messageModels;
    }
}
