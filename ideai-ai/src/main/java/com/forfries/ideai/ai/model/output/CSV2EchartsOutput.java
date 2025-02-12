package com.forfries.ideai.ai.model.output;

import lombok.Builder;
import lombok.Data;
import reactor.core.publisher.Flux;

@Data
@Builder
public class CSV2EchartsOutput {
    Flux<String> stream;
}
