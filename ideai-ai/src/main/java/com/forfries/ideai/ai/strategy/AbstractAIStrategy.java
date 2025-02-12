package com.forfries.ideai.ai.strategy;

import com.forfries.ideai.ai.client.AIClient;
import com.forfries.ideai.ai.enums.AIStrategyType;
import com.forfries.ideai.ai.enums.ClientType;
import com.forfries.ideai.ai.factory.ClientFactory;
import lombok.Data;

@Data
public abstract class AbstractAIStrategy<T, R> {
    protected final ClientFactory clientFactory;
    protected final AIStrategyType strategyType;
    protected final ClientType clientType;

    protected AbstractAIStrategy(ClientFactory clientFactory, AIStrategyType strategyType, ClientType clientType) {
        this.clientFactory = clientFactory;
        this.strategyType = strategyType;
        this.clientType = clientType;
    }

    public abstract R execute(T input,String clientId);

    public R execute(T input){
        return execute(input ,clientFactory.getDefaultClientId(clientType));
    }

    public AIClient<?,?> getClient(String clientId){
        return clientFactory.getClient(clientType,clientId);
    }
}
