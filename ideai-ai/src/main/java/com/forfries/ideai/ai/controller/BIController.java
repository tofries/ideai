package com.forfries.ideai.ai.controller;

import com.forfries.ideai.ai.enums.AIStrategyType;
import com.forfries.ideai.ai.factory.AIStrategyFactory;
import com.forfries.ideai.ai.model.response.StreamAIResponse;
import com.forfries.ideai.ai.operation.chat.AbstractAIStrategy;
import com.forfries.ideai.ai.operation.chat.SummaryAIStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class BIController {

    @Autowired
    AIStrategyFactory strategyFactory;

    @PostMapping(value = "/test", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> test() {
        SummaryAIStrategy aiStrategy = (SummaryAIStrategy) strategyFactory.getAIStrategy(AIStrategyType.SUMMARY);
        StreamAIResponse res = aiStrategy.execute("介绍一下你自己");
        return res.getStream();
    }
}
