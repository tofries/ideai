package com.forfries.ideaiai.service;

import reactor.core.publisher.Flux;

public interface AIService {
    Flux<String> generateSummary(String content);
} 