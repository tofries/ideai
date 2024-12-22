package com.forfries.ideaiai.service.impl;

import com.forfries.ideaiai.model.response.OpenAIResponse;
import com.forfries.ideaiai.service.AIService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OpenAIServiceImpl implements AIService {
    private final WebClient openaiWebClient;
    
    @Value("${openai.model}")
    private String model;
    
    @Override
    public Flux<String> generateSummary(String content) {
        Map<String, Object> requestBody = Map.of(
            "model", model,
            "messages", List.of(
                Map.of(
                    "role", "system",
                    "content", "你是一个专业的文本总结助手，请简明扼要地总结用户输入的内容。"
                ),
                Map.of(
                    "role", "user",
                    "content", content
                )
            ),
            "stream", true
        );
        
        return openaiWebClient.post()
                .uri("/chat/completions")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToFlux(OpenAIResponse.class)
                .map(response -> {
                    if (response.getChoices() != null && !response.getChoices().isEmpty()) {
                        OpenAIResponse.Delta delta = response.getChoices().get(0).getDelta();
                        return delta != null && delta.getContent() != null ? delta.getContent() : "";
                    }
                    return "";
                })
                .filter(content2 -> !content2.isEmpty());
    }
} 