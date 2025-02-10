//package com.forfries.ideai.ai.controller;
//
//import com.forfries.ideai.ai.model.request.AIRequest;
//import com.forfries.ideai.ai.model.GeneralResponse;
//import com.forfries.ideai.ai.model.ImageResponse;
//import com.forfries.ideai.ai.service.ImageService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/ai")
//@RequiredArgsConstructor
//public class ImageController {
//    private final ImageService imageService;
//
//    @PostMapping("/image")
//    public GeneralResponse<ImageResponse> generateImage(@RequestBody AIRequest request) {
//        try {
//            return GeneralResponse.success(imageService.generateImage(request));
//        } catch (Exception e) {
//            return GeneralResponse.error(e.getMessage());
//        }
//    }
//}
