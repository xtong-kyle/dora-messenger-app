package com.dora.app.entity.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("userInfo")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessengerUserInfoResponse {

    private Long id;
    private String username;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
