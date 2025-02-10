package com.forfries.ideai.ai.operation.chat;

import com.forfries.ideai.ai.client.AIClient;
import com.forfries.ideai.ai.client.StreamClient;
import com.forfries.ideai.ai.enums.AIStrategyType;
import com.forfries.ideai.ai.enums.ClientType;
import com.forfries.ideai.ai.factory.ClientFactory;
import com.forfries.ideai.ai.model.SummaryResponse;
import com.forfries.ideai.ai.model.request.AIRequest;
import com.forfries.ideai.ai.model.response.AIResponse;
import com.forfries.ideai.ai.model.response.StreamAIResponse;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class SummaryAIStrategy extends AbstractAIStrategy<String, StreamAIResponse> {

    public SummaryAIStrategy(ClientFactory clientFactory) {
        super(clientFactory,AIStrategyType.SUMMARY,ClientType.Chat);
    }

    @Override
    public StreamAIResponse execute(String input, String clientId) {

        StreamClient client = (StreamClient) clientFactory.getClient(clientType, clientId);
        return client.generate(new AIRequest(input));
    }
}
