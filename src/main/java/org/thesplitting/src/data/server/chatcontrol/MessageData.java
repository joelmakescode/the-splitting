package org.thesplitting.src.data.server.chatcontrol;

import java.time.LocalDateTime;

public record MessageData(LocalDateTime dateTime, String message, ChatControlLevel level) {
    public static MessageData create(String message, ChatControlLevel level) {
        return new MessageData(LocalDateTime.now(), message, level);
    }
}
