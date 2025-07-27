package com.dora.app.entity;

import org.springframework.http.HttpStatus;

public class DoraMessengerExceptionResponse extends DoraMessengerBaseResponse{

    public DoraMessengerExceptionResponse() {
        super(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR");
    }
}
