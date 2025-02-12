package com.forfries.ideai.ai.strategy;

import com.forfries.ideai.ai.client.AIClient;
import com.forfries.ideai.ai.client.StreamClient;
import com.forfries.ideai.ai.constant.PromptConstant;
import com.forfries.ideai.ai.enums.AIStrategyType;
import com.forfries.ideai.ai.enums.ClientType;
import com.forfries.ideai.ai.enums.ResponseFormatType;
import com.forfries.ideai.ai.factory.ClientFactory;
import com.forfries.ideai.ai.model.input.CSV2EchartsTask;
import com.forfries.ideai.ai.model.output.CSV2EchartsOutput;
import com.forfries.ideai.ai.model.request.AIRequest;
import com.forfries.ideai.ai.model.response.StreamAIResponse;
import org.springframework.stereotype.Component;

@Component
public class CSVAnalyzeAIStrategy extends AbstractAIStrategy<CSV2EchartsTask,CSV2EchartsOutput> {
    public CSVAnalyzeAIStrategy(ClientFactory clientFactory) {
        super(clientFactory, AIStrategyType.CSV_ANALYZE, ClientType.Chat);
    }

    @Override
    public CSV2EchartsOutput execute(CSV2EchartsTask input, String clientId) {
        StreamClient client = (StreamClient) this.getClient(clientId);
        AIRequest request = AIRequest.builder()
                .prompt(PromptConstant.SCV_ECHARTS_PROMPT)
                .responseFormatType(ResponseFormatType.JSON)
                .userMessage(String.format("""
                        #DATA_START#%s#DATA_END#  #PROMPT_START#%s#PROMPT_END#""",
                        input.getCsvData(),input.getTarget()))
                .build();
        StreamAIResponse generate = client.generate(request);
        return CSV2EchartsOutput.builder()
                .stream(generate.getStream())
                .build();
    }
}
