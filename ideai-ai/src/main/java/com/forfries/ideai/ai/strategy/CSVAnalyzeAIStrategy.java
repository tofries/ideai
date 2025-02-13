package com.forfries.ideai.ai.strategy;

import com.forfries.ideai.ai.client.StreamClient;
import com.forfries.ideai.ai.constant.CSVEchartsPrompt;
import com.forfries.ideai.ai.enums.AIStrategyType;
import com.forfries.ideai.ai.enums.ClientType;
import com.forfries.ideai.ai.enums.ResponseFormatType;
import com.forfries.ideai.ai.factory.AIClientFactory;
import com.forfries.ideai.ai.model.input.CSV2EchartsTask;
import com.forfries.ideai.ai.model.output.CSV2EchartsOutput;
import com.forfries.ideai.ai.model.request.AIRequest;
import com.forfries.ideai.ai.model.response.StreamAIResponse;
import org.springframework.stereotype.Component;

@Component
public class CSVAnalyzeAIStrategy extends AbstractAIStrategy<CSV2EchartsTask,CSV2EchartsOutput> {
    public CSVAnalyzeAIStrategy(AIClientFactory AIClientFactory) {
        super(AIClientFactory, AIStrategyType.CSV_ANALYZE, ClientType.Chat);
    }

    @Override
    public CSV2EchartsOutput execute(CSV2EchartsTask input, String clientId) {
        StreamClient client = (StreamClient) this.getClient(clientId);
        AIRequest request = AIRequest.builder()
                .prompt(CSVEchartsPrompt.getPrompt())
                .responseFormatType(ResponseFormatType.JSON)
                .userMessage(CSVEchartsPrompt.getUserMessage(
                        input.getCsvData(),
                        input.getTarget()))
                .build();
        StreamAIResponse generate = client.generate(request);
        return CSV2EchartsOutput.builder()
                .stream(generate.getStream())
                .build();
    }
}
