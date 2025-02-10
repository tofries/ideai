//package com.forfries.ideai.ai.service;
//
//import com.forfries.ideai.ai.config.AIConfig;
//import com.forfries.ideai.ai.model.request.AIRequest;
//import com.forfries.ideai.ai.model.ImageResponse;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class ImageService {
//    private final RestTemplate restTemplate;
//    private final AIConfig aiConfig;
//
//    public ImageResponse generateImage(AIRequest request) {
//        // 合并所有blocks的内容作为提示词
//        String prompt = request.getBlocks().stream()
//                .map(block -> block.getContent())
//                .collect(Collectors.joining(" "));
//
//        // 添加预设提示词
//        if (request.getPreset() != null && !request.getPreset().isEmpty()) {
//            prompt = request.getPreset() + ", " + prompt;
//        }
//
//        // 创建任务
//        String taskId = createImageTask(prompt);
//
//        // 轮询获取结果
//        return pollTaskResult(taskId);
//    }
//
//    private String createImageTask(String prompt) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("Authorization", "Bearer " + aiConfig.getDashscope().getApiKey());
//        headers.set("X-DashScope-Async", "enable");
//
//        Map<String, Object> requestBody = new HashMap<>();
//        requestBody.put("model", aiConfig.getDashscope().getModel());
//        requestBody.put("input", Map.of("prompt", prompt));
//        requestBody.put("parameters", Map.of(
//            "style", "<auto>",
//            "size", aiConfig.getDashscope().getSize(),
//            "n", 1
//        ));
//
//        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
//
//        String url = aiConfig.getDashscope().getBaseUrl() + "/api/v1/services/aigc/text2image/image-synthesis";
//        Map<String, Object> response = restTemplate.postForObject(url, entity, Map.class);
//
//        Map<String, Object> output = (Map<String, Object>) response.get("output");
//        return (String) output.get("task_id");
//    }
//
//    private ImageResponse pollTaskResult(String taskId) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", "Bearer " + aiConfig.getDashscope().getApiKey());
//
//        String url = aiConfig.getDashscope().getBaseUrl() + "/api/v1/tasks/" + taskId;
//        HttpEntity<?> entity = new HttpEntity<>(headers);
//
//        // 最多尝试30次，每次间隔2秒
//        for (int i = 0; i < 30; i++) {
//            try {
//                Map<String, Object> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class).getBody();
//                Map<String, Object> output = (Map<String, Object>) response.get("output");
//                String status = (String) output.get("task_status");
//
//                if ("SUCCEEDED".equals(status)) {
//                    List<Map<String, Object>> results = (List<Map<String, Object>>) output.get("results");
//                    if (!results.isEmpty()) {
//                        ImageResponse imageResponse = new ImageResponse();
//                        imageResponse.setImageUrl((String) results.get(0).get("url"));
//                        return imageResponse;
//                    }
//                } else if ("FAILED".equals(status)) {
//                    throw new RuntimeException("Image generation failed");
//                }
//
//                Thread.sleep(2000); // 等待2秒后重试
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//                throw new RuntimeException("Task interrupted", e);
//            }
//        }
//
//        throw new RuntimeException("Timeout waiting for image generation");
//    }
//}
