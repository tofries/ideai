package com.forfries.ideai.ai.factory;


import com.forfries.ideai.ai.client.AIClient;
import com.forfries.ideai.ai.client.StreamClient;
import com.forfries.ideai.ai.config.AIConfig;
import com.forfries.ideai.ai.constant.ExceptionMessageConstant;
import com.forfries.ideai.ai.enums.ClientType;
import com.forfries.ideai.ai.exception.BusinessException;
import com.forfries.ideai.ai.model.request.AIRequest;
import com.forfries.ideai.ai.model.response.AIResponse;
import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

@Component
public class ClientFactory {
        private EnumMap<ClientType,Map<String, AIClient<? extends AIResponse, ? extends AIRequest>>> clients = new EnumMap<>(ClientType.class);

        private EnumMap<ClientType,String> defaultClients = new EnumMap<>(ClientType.class);

        // 注册方法
        public void registerClient(ClientType clientType,String clientId, AIClient<? extends AIResponse, ? extends AIRequest> client) {
            clients.putIfAbsent(clientType,new HashMap<>());
            defaultClients.putIfAbsent(clientType,clientId);
            clients.get(clientType).put(clientId,client);
        }

        public boolean setDefaultClient(ClientType clientType, String clientId) {
           if( clients.get(clientType).get(clientId) ==null)
               return false;
           defaultClients.replace(clientType, clientId);
           return true;
        }
        // 获取方法
        public AIClient<? extends AIResponse, ? extends AIRequest> getClient(ClientType clientType,String clientId) {
            AIClient<? extends AIResponse, ? extends AIRequest> aiClient = clients.get(clientType).get(clientId);
            BusinessException.throwIf(aiClient==null, ExceptionMessageConstant.AI_CLIENT_NULL);
            return aiClient;
        }

        public AIClient<? extends AIResponse, ? extends AIRequest> getClient(ClientType clientType) {
            AIClient<? extends AIResponse, ? extends AIRequest> aiClient = clients.get(clientType).get(defaultClients.get(clientType));
            BusinessException.throwIf(aiClient==null, ExceptionMessageConstant.AI_CLIENT_NULL);
            return aiClient;
        }

        public String getDefaultClientId(ClientType clientType) {
            String s = defaultClients.get(clientType);
            BusinessException.throwIf(s==null, ExceptionMessageConstant.AI_CLIENT_NULL);
            return s;
        }

}
