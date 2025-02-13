package com.forfries.ideai.ai.config;

import com.forfries.ideai.ai.client.ImageClient;
import com.forfries.ideai.ai.client.StreamClient;
import com.forfries.ideai.ai.enums.ClientType;
import com.forfries.ideai.ai.factory.AIClientFactory;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Data
@Configuration
@ConfigurationProperties(prefix = "ai")
public class AIConfig {
    private Map<String, ChatClientProperties> chat = new HashMap<>();
    private Map<String, ImageClientProperties> image = new HashMap<>();


    @Data
    public abstract static class BaseClientProperties {
        private String baseUrl;
        private String apiKey;
        private String model;
    }

    @Data
    public static class ChatClientProperties extends BaseClientProperties {
        private String maxToken;
    }

    @Data
    public static class ImageClientProperties extends BaseClientProperties {
        private String size;
    }

    @Bean
    public AIClientFactory aiClientFactory() {
        AIClientFactory factory = new AIClientFactory();
        this.getChat().forEach((clientId, properties) -> {
            factory.registerClient(ClientType.Chat,clientId,new StreamClient(properties));
        });
        this.getImage().forEach((clientId, properties) -> {
            factory.registerClient(ClientType.Image,clientId,new ImageClient(properties));
        });
        return factory;
    }

}