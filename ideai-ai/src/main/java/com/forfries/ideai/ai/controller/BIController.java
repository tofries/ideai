package com.forfries.ideai.ai.controller;

import com.forfries.ideai.ai.factory.AIStrategyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class BIController {

    @Autowired
    AIStrategyFactory strategyFactory;

//    @PostMapping(value = "/stream-chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    public Flux<String> streamChat(@RequestBody String userMessage) {
//
//    }
}
