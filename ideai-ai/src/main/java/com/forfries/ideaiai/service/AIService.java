package com.forfries.ideaiai.service;

import com.forfries.ideaiai.config.AIConfig;
import com.forfries.ideaiai.model.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import lombok.RequiredArgsConstructor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AIService {
    private final RestTemplate restTemplate;
    private final AIConfig aiConfig;

    public SummaryResponse summarize(AIRequest request) {
        // 将所有blocks的内容合并，保持原有顺序
        String combinedContent = request.getBlocks().stream()
                .map(TextBlock::getContent)
                .collect(Collectors.joining("\n"));

        Map<String, Object> aiRequest = new HashMap<>();
        aiRequest.put("model", aiConfig.getDeepseek().getModel());
        aiRequest.put("messages", new Object[]{
            Map.of(
                "role", "system",
                "content", String.format("你是一个帮助总结文本的AI助手。请按照以下预设方案进行总结：%s", request.getPreset())
            ),
            Map.of(
                "role", "user",
                "content", combinedContent
            )
        });

        var response = makeRequest("/v1/chat/completions", aiRequest);
        SummaryResponse summaryResponse = new SummaryResponse();
        summaryResponse.setSummary(extractContentFromResponse(response));
        return summaryResponse;
    }

    public CompletionResponse complete(String content, String type) {
        Map<String, Object> request = new HashMap<>();
        request.put("model", aiConfig.getDeepseek().getModel());
        request.put("messages", new Object[]{
            Map.of(
                "role", "system",
                "content", "You are a helpful assistant that corrects text. " +
                          "Focus on " + (type != null ? type : "grammar and spelling") + " errors."
            ),
            Map.of(
                "role", "user",
                "content", content
            )
        });

        var response = makeRequest("/v1/chat/completions", request);
        return processCompletionResponse(content, extractContentFromResponse(response));
    }

    private Map<String, Object> makeRequest(String endpoint, Map<String, Object> requestBody) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + aiConfig.getDeepseek().getApiKey());

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
        return restTemplate.postForObject(aiConfig.getDeepseek().getBaseUrl() + endpoint, entity, Map.class);
    }

    private String extractContentFromResponse(Map<String, Object> response) {
        try {
            var choices = (List<Map<String, Object>>) response.get("choices");
            var message = (Map<String, Object>) choices.get(0).get("message");
            return (String) message.get("content");
        } catch (Exception e) {
            throw new RuntimeException("Failed to process AI response", e);
        }
    }

    private CompletionResponse processCompletionResponse(String originalContent, String aiResponse) {
        CompletionResponse response = new CompletionResponse();
        response.setPageId(String.valueOf(System.currentTimeMillis()));
        
        CompletionResponse.TextBlock block = new CompletionResponse.TextBlock();
        block.setBlockId("block1");
        block.setCorrectedText(aiResponse);
        
        response.setBlocks(List.of(block));
        return response;
    }
}
