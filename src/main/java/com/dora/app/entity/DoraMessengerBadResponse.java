package com.dora.app.entity;

import org.springframework.http.HttpStatus;

public class DoraMessengerBadResponse extends DoraMessengerBaseResponse{

    public DoraMessengerBadResponse() {
        super(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, "BAD_REQUEST");
    }

    public DoraMessengerBadResponse(String message) {
        super(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, message);
    }
}
