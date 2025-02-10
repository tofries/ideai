package com.tofries.ideaitest.demos.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.fasterxml.jackson.databind.JsonNode;

@RestController
public class OpenAIController {

    // 初始化 WebClient（推荐在配置类中创建 Bean）
    private final WebClient webClient = WebClient.builder()
            .baseUrl("https://api.tofries.com/v1")
            .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer sk-5HiuQCIsaYvmg2004c55855e92074cDb98DfC22a5144D344")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();

    @PostMapping(value = "/stream-chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamChat(@RequestBody String userMessage) {
        // 构建 OpenAI 请求体
        String requestBody = String.format("""
            {
                "model": "DeepSeek-R1",
                "messages": [{"role": "user", "content": "%s"}],
                "stream": true
            }
            """, userMessage.replace("\"", "\\\""));

            Flux<String> serverSentEventFlux = webClient.post()
                .uri("/chat/completions")
                .bodyValue(requestBody)
                .accept(MediaType.TEXT_EVENT_STREAM) // 关键：声明接受流式响应
                .retrieve()
                .bodyToFlux(String.class)
                .takeUntil(data -> data.contains("[DONE]")) // 遇到结束标记停止
                .flatMap(this::parseSSEData);
        Response response = new Response(true, serverSentEventFlux);
        return response.getStream();
    }

    private static final ObjectMapper mapper = new ObjectMapper();

    private Mono<String> parseSSEData(String jsonData) {
        return Mono.fromCallable(() -> {
            try {
                if (jsonData.isEmpty() || "[DONE]".equals(jsonData)) return "";

                JsonNode node = mapper.readTree(jsonData);
                return node.path("choices").get(0)
                        .path("delta").path("content").asText("");
            } catch (Exception e) {
                return ""; // 无效 JSON 返回空值
            }
        }).filter(content -> !content.isEmpty());
    }

}
