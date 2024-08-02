package com.cloud6.chat.Utils;

import java.util.UUID;

public class MessageUtils {
    public static String generateMessageId() { return UUID.randomUUID().toString(); }
}
