package com.forfries.ideai.ai.model.response;

import lombok.Data;

@Data
public class AIResponse {
    private boolean success;
    private boolean isDone;
    private String errorMessage;
}