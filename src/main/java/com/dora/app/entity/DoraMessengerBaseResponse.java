package com.dora.app.entity;

import com.fasterxml.jackson.annotation.JsonRootName;
import org.springframework.http.HttpStatus;

@JsonRootName("response")
public class DoraMessengerBaseResponse<T> {

    private int statusCode;
    private HttpStatus status;
    private String message;
    private T payload;

    public DoraMessengerBaseResponse(int statusCode, HttpStatus status, String message, T payload) {
        this.statusCode = statusCode;
        this.status = status;
        this.message = message;
        this.payload = payload;
    }

    public DoraMessengerBaseResponse(int statusCode, HttpStatus status,String message) {
        this.statusCode = statusCode;
        this.status = status;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }
}
