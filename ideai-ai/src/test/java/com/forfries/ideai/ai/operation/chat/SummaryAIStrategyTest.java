package com.forfries.ideai.ai.operation.chat;

import com.forfries.ideai.ai.enums.AIStrategyType;
import com.forfries.ideai.ai.factory.AIStrategyFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class SummaryAIStrategyTest {
    @Autowired
    private AIStrategyFactory aiStrategyFactory;

    @Test
    void test() {
        SummaryAIStrategy aiStrategy = (SummaryAIStrategy) aiStrategyFactory.getAIStrategy(AIStrategyType.SUMMARY);
        System.out.println(aiStrategy.execute("介绍一下你自己"));
    }
}