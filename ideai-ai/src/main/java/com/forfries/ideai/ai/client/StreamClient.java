package com.forfries.ideai.ai.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.forfries.ideai.ai.config.AIConfig;
import com.forfries.ideai.ai.enums.ResponseFormatType;
import com.forfries.ideai.ai.model.request.AIRequest;
import com.forfries.ideai.ai.model.request.ChatMessage;
import com.forfries.ideai.ai.model.request.ChatRequest;
import com.forfries.ideai.ai.model.response.AIResponse;
import com.forfries.ideai.ai.model.response.StreamAIResponse;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public class StreamClient extends AIClient<StreamAIResponse, AIRequest> {
    private final AIConfig.ChatClientProperties properties;

    private final WebClient webClient;

    public StreamClient(AIConfig.ChatClientProperties properties) {
        this.properties = properties;

        this.webClient = WebClient.builder()
                .baseUrl(properties.getBaseUrl())
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + properties.getApiKey())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Override
    public StreamAIResponse generate(AIRequest request) {
        ChatRequest chatRequest = new ChatRequest(properties.getModel(),
                request.getPrompt(),
                request.getUserMessage(),
                request.getResponseFormatType());
        String requestBody;
        try {
            requestBody = mapper.writeValueAsString(chatRequest);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON转换失败", e);
        }
        StreamAIResponse response = new StreamAIResponse();
        Flux<String> responseFlux = webClient.post()
                .uri("/v1/chat/completions")
                .bodyValue(requestBody)
                .accept(MediaType.TEXT_EVENT_STREAM) // 关键：声明接受流式响应
                .retrieve()
                .bodyToFlux(String.class)
                .takeUntil(data -> data.contains("[DONE]"))
                .flatMap(this::parseSSEData)
                .onErrorResume(e -> {
                    response.setSuccess(false);
                    response.setErrorMessage(e.getMessage());
                    return Flux.just("");
                });// 遇到结束标记停止
        response.setStream(responseFlux);
        return response;
    }

    private static final ObjectMapper mapper = new ObjectMapper();

    private Mono<String> parseSSEData(String jsonData) {
        return Mono.fromCallable(() -> {
            try {
                if (jsonData.isEmpty() || "[DONE]".equals(jsonData)) return "";

                JsonNode node = mapper.readTree(jsonData);
                return node.path("choices").get(0)
                        .path("delta").path("content").asText("");
            } catch (JsonProcessingException e) {
                return ""; // 无效 JSON 返回空值
            }
        }).filter(content -> !content.isEmpty());
    }

}