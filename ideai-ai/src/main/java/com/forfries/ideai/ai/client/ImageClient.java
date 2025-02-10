package com.forfries.ideai.ai.client;

import com.forfries.ideai.ai.config.AIConfig;
import com.forfries.ideai.ai.model.request.AIRequest;
import com.forfries.ideai.ai.model.response.AIResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ImageClient extends AIClient<AIResponse,AIRequest>{

    private final AIConfig.ImageClientProperties properties;

    @Override
    AIResponse generate(AIRequest request) {
        properties.getApiKey();
        return null;
    }
}
