package com.zht.controller;

import com.zht.common.Result;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/file")
public class FileController {
    private static final String UPLOAD_DIR = "D:/workspace/pan/uploads/";

    @PostMapping("/upload")
    public Result<String> uploadFile(MultipartFile file) {
        if (file.isEmpty()) {
            return Result.error("文件不能为空");
        }

        try {
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFilename = UUID.randomUUID().toString() + extension;
            
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            File destFile = new File(UPLOAD_DIR + newFilename);
            file.transferTo(destFile);
            
            return Result.success("/uploads/" + newFilename);
        } catch (IOException e) {
            return Result.error("文件上传失败: " + e.getMessage());
        }
    }
    
    @PostMapping("/upload/multiple")
    public Result<List<String>> uploadFiles(@RequestParam("files") MultipartFile[] files) {
        List<String> urls = new ArrayList<>();
        
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                try {
                    String originalFilename = file.getOriginalFilename();
                    String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
                    String newFilename = UUID.randomUUID().toString() + extension;
                    
                    File uploadDir = new File(UPLOAD_DIR);
                    if (!uploadDir.exists()) {
                        uploadDir.mkdirs();
                    }

                    File destFile = new File(UPLOAD_DIR + newFilename);
                    file.transferTo(destFile);
                    
                    urls.add("/uploads/" + newFilename);
                } catch (IOException e) {
                    return Result.error("文件上传失败: " + e.getMessage());
                }
            }
        }
        
        return Result.success(urls);
    }
}

