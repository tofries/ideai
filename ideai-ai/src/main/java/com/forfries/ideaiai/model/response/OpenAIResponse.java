package com.forfries.ideaiai.model.response;

import lombok.Data;
import java.util.List;

@Data
public class OpenAIResponse {
    private String id;
    private String object;
    private List<Choice> choices;
    
    @Data
    public static class Choice {
        private int index;
        private Delta delta;
        private String finishReason;
    }
    
    @Data
    public static class Delta {
        private String content;
    }
} 