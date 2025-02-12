package com.forfries.ideai.ai.model.request;

import com.forfries.ideai.ai.enums.ResponseFormatType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AIRequest {
    private String prompt;
    private String userMessage;
    private ResponseFormatType responseFormatType;
}
