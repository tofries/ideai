package com.forfries.ideai.ai.client;

import com.forfries.ideai.ai.model.request.AIRequest;
import com.forfries.ideai.ai.model.response.AIResponse;

public abstract class AIClient<T extends AIResponse,E extends AIRequest> {

    abstract T generate(E request);
}
