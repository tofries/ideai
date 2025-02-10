package com.forfries.ideai.ai.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import reactor.core.publisher.Flux;

@EqualsAndHashCode(callSuper = true)
@Data
public class StreamAIResponse extends AIResponse {
    Flux<String> stream;
}
