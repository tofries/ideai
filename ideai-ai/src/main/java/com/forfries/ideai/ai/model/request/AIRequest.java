package com.forfries.ideai.ai.model.request;

import com.forfries.ideai.ai.model.TextBlock;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@AllArgsConstructor
public class AIRequest {
    private String userMessage;
}
