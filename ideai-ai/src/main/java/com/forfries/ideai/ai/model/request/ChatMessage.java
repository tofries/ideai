package com.forfries.ideai.ai.model.request;

import lombok.Getter;

@Getter
public class ChatMessage {
    private String role;
    private String content;

    public ChatMessage(String role, String content) {
        this.role = role;
        this.content = content;
    }
}
