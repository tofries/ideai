package com.forfries.ideaiai.controller;

import com.forfries.ideaiai.model.request.SummaryRequest;
import com.forfries.ideaiai.service.AIService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class AISummaryController {
    private final AIService aiService;
    
    @PostMapping(value = "/summary", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> generateSummary(@RequestBody SummaryRequest request) {
        return aiService.generateSummary(request.getContent());
    }
} 