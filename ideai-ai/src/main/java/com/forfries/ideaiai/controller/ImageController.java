package com.forfries.ideaiai.controller;

import com.forfries.ideaiai.model.AIRequest;
import com.forfries.ideaiai.model.AIResponse;
import com.forfries.ideaiai.model.ImageResponse;
import com.forfries.ideaiai.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @PostMapping("/image")
    public AIResponse<ImageResponse> generateImage(@RequestBody AIRequest request) {
        try {
            return AIResponse.success(imageService.generateImage(request));
        } catch (Exception e) {
            return AIResponse.error(e.getMessage());
        }
    }
}
