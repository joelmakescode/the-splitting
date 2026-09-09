package org.thesplitting.src.data.server.chatcontrol;

public record ChatFilterResult(ChatControlLevel level, String matchedWord, String message) {}
