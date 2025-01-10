package com.forfries.ideaifile.controller;

import com.aliyuncs.exceptions.ClientException;
import com.forfries.ideaifile.model.File;
import com.forfries.ideaifile.model.Result;
import com.forfries.ideaifile.service.FileUploadService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@AllArgsConstructor
@Slf4j
@RequestMapping("/file")
@RestController
public class FileUploadController {
    private final FileUploadService fileUploadService;

    @PostMapping("/upload")
    public Result uploadFile(@RequestParam("file") MultipartFile file) throws IOException, ClientException {
        File newFile = new File();
        newFile=fileUploadService.uploadFile(file.getInputStream(),file.getOriginalFilename(),file.getSize());
        return Result.success(newFile.getFileUrl());
    }
}
