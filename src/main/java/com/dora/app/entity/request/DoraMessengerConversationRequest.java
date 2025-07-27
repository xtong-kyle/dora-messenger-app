package com.dora.app.entity.request;

import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.List;

@JsonRootName("request")
public class DoraMessengerConversationRequest {

    private String conversationId;
    private String name;
    private String type;
    private List<Long> participants;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Long> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Long> participants) {
        this.participants = participants;
    }
}
