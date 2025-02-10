//package com.forfries.ideai.ai.controller;
//
//import com.forfries.ideai.ai.model.request.AIRequest;
//import com.forfries.ideai.ai.model.GeneralResponse;
//import com.forfries.ideai.ai.model.CompletionResponse;
//import com.forfries.ideai.ai.model.SummaryResponse;
//import com.forfries.ideai.ai.service.AIService;
//import org.springframework.web.bind.annotation.*;
//import lombok.RequiredArgsConstructor;
//
//@RestController
//@RequestMapping("/ai")
//@RequiredArgsConstructor
//public class AIController {
//    private final AIService aiService;
//
//    @PostMapping("/summarize")
//    public GeneralResponse<SummaryResponse> summarize(@RequestBody AIRequest request) {
//        try {
//            return GeneralResponse.success(aiService.summarize(request));
//        } catch (Exception e) {
//            return GeneralResponse.error(e.getMessage());
//        }
//    }
//
//    @PostMapping("/completion")
//    public GeneralResponse<CompletionResponse> complete(
//            @RequestBody AIRequest request,
//            @RequestParam(required = false) String type) {
//        try {
////            String content = request.getBlocks().stream()
////                    .map(block -> block.getContent())
////                    .reduce("", (a, b) -> a + "\n" + b).trim();
////            return GeneralResponse.success(aiService.complete(content, type));
//        } catch (Exception e) {
//            return GeneralResponse.error(e.getMessage());
//        }
//    }
//}
