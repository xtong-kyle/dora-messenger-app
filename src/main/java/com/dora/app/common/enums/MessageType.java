package com.dora.app.common.enums;

public enum MessageType {
    TEXT("TEXT"), MEDIA("MEDIA");

    private String type;

    MessageType(String type) {
        this.type = type;
    }

}
