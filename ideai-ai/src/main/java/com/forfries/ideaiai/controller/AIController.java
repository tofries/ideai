package com.forfries.ideaiai.controller;

import com.forfries.ideaiai.model.AIRequest;
import com.forfries.ideaiai.model.AIResponse;
import com.forfries.ideaiai.model.CompletionResponse;
import com.forfries.ideaiai.model.SummaryResponse;
import com.forfries.ideaiai.service.AIService;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class AIController {
    private final AIService aiService;

    @PostMapping("/summarize")
    public AIResponse<SummaryResponse> summarize(@RequestBody AIRequest request) {
        try {
            return AIResponse.success(aiService.summarize(request));
        } catch (Exception e) {
            return AIResponse.error(e.getMessage());
        }
    }

    @PostMapping("/completion")
    public AIResponse<CompletionResponse> complete(
            @RequestBody AIRequest request,
            @RequestParam(required = false) String type) {
        try {
            String content = request.getBlocks().stream()
                    .map(block -> block.getContent())
                    .reduce("", (a, b) -> a + "\n" + b).trim();
            return AIResponse.success(aiService.complete(content, type));
        } catch (Exception e) {
            return AIResponse.error(e.getMessage());
        }
    }
}
