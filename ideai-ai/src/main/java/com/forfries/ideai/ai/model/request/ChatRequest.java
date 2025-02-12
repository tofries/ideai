package com.forfries.ideai.ai.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.forfries.ideai.ai.enums.ResponseFormatType;
import lombok.Data;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Getter
public class ChatRequest {
    // Jackson需要getter方法
    private final String model;
    private final List<ChatMessage> messages;
    private final boolean stream;
    private final ResponseFormat response_format;

    public ChatRequest(String model, String systemContent, String userContent, ResponseFormatType outType) {
        this.model = model;
        response_format = new ResponseFormat(outType);
        this.messages = Arrays.asList(
            new ChatMessage("system", systemContent),
            new ChatMessage("user", userContent)
        );
        this.stream = true;
    }
}
