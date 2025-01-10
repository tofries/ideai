package com.forfries.ideaiai.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "ai")
@Data
public class AIConfig {
    private DeepSeek deepseek;
    private DashScope dashscope;
    
    @Data
    public static class DeepSeek {
        private String apiKey;
        private String baseUrl;
        private String model;
    }
    
    @Data
    public static class DashScope {
        private String apiKey;
        private String baseUrl = "https://dashscope.aliyuncs.com";
        private String model = "wanx-v1";
        private String size = "1024*1024";
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
