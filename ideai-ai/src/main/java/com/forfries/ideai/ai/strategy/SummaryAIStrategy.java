package com.forfries.ideai.ai.strategy;

import com.forfries.ideai.ai.client.StreamClient;
import com.forfries.ideai.ai.constant.SummaryPrompt;
import com.forfries.ideai.ai.enums.AIStrategyType;
import com.forfries.ideai.ai.enums.ClientType;
import com.forfries.ideai.ai.factory.AIClientFactory;
import com.forfries.ideai.ai.model.request.AIRequest;
import com.forfries.ideai.ai.model.response.StreamAIResponse;
import com.tofries.ideai.common.model.SummaryTask;
import org.springframework.stereotype.Component;

@Component
public class SummaryAIStrategy extends AbstractAIStrategy<SummaryTask, StreamAIResponse> {

    public SummaryAIStrategy(AIClientFactory AIClientFactory) {
        super(AIClientFactory,AIStrategyType.SUMMARY,ClientType.Chat);
    }

    @Override
    public StreamAIResponse execute(SummaryTask input, String clientId) {

        StreamClient client = (StreamClient) AIClientFactory.getClient(clientType, clientId);
        return client.generate(AIRequest.builder()
                .prompt(SummaryPrompt.getPrompt(
                        input.getMaxLength(),
                        input.getSummaryStyle(),
                        input.getUserSuggestion()))
                .userMessage(SummaryPrompt.getUserMessage(
                        input.getOriginalText()
                ))
                .build());
    }
}
