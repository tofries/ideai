package com.forfries.ideai.ai.factory;

import com.forfries.ideai.ai.enums.AIStrategyType;
import com.forfries.ideai.ai.operation.chat.AbstractAIStrategy;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;

// LLMStrategyFactory.java
@Component
public class AIStrategyFactory {
    private final EnumMap<AIStrategyType, AbstractAIStrategy<?,?>> aiStrategyMap = new EnumMap<>(AIStrategyType.class);


    public AIStrategyFactory(List<AbstractAIStrategy<?,?>> aiStrategyList) {
        aiStrategyList.forEach(aiStrategy -> {
            aiStrategyMap.put(aiStrategy.getStrategyType(), aiStrategy);
            });
    }

    public AbstractAIStrategy<?,?> getAIStrategy(AIStrategyType strategyType) {
        return aiStrategyMap.get(strategyType);
    }
}


