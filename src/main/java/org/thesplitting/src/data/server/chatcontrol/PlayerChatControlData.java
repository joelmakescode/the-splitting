package org.thesplitting.src.data.server.chatcontrol;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record PlayerChatControlData(UUID uuid, String name, List<MessageData> flaggedMessages) {
    public PlayerChatControlData(UUID uuid, String name) {
        this(uuid, name, new ArrayList<>());
    }
}
