package com.dora.app.util;

import java.util.UUID;

public class IdGenerateUtil {

    public static String generate32DigitsUUID() {
        return UUID.randomUUID().toString();
    }
}
