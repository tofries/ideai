package com.forfries.ideai.ai.strategy;

import com.forfries.ideai.ai.client.AIClient;
import com.forfries.ideai.ai.enums.AIStrategyType;
import com.forfries.ideai.ai.enums.ClientType;
import com.forfries.ideai.ai.factory.AIClientFactory;
import lombok.Data;

@Data
public abstract class AbstractAIStrategy<T, R> {
    protected final AIClientFactory AIClientFactory;
    protected final AIStrategyType strategyType;
    protected final ClientType clientType;

    protected AbstractAIStrategy(AIClientFactory AIClientFactory, AIStrategyType strategyType, ClientType clientType) {
        this.AIClientFactory = AIClientFactory;
        this.strategyType = strategyType;
        this.clientType = clientType;
    }

    public abstract R execute(T input,String clientId);

    public R execute(T input){
        return execute(input , AIClientFactory.getDefaultClientId(clientType));
    }

    public AIClient<?,?> getClient(String clientId){
        return AIClientFactory.getClient(clientType,clientId);
    }
}
