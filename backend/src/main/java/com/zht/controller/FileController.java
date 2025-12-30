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
    
    /**
     * 获取图标列表
     */
    @GetMapping("/icons")
    public Result<List<String>> getIcons() {
        List<String> iconUrls = new ArrayList<>();
        File iconDir = new File(UPLOAD_DIR + "icon/");
        
        if (iconDir.exists() && iconDir.isDirectory()) {
            File[] files = iconDir.listFiles((dir, name) -> {
                String lowerName = name.toLowerCase();
                return lowerName.endsWith(".png") || 
                       lowerName.endsWith(".jpg") || 
                       lowerName.endsWith(".jpeg") || 
                       lowerName.endsWith(".gif") || 
                       lowerName.endsWith(".svg") || 
                       lowerName.endsWith(".webp");
            });
            
            if (files != null) {
                for (File file : files) {
                    // 排除特定图片，这个图片会显示在Header中
                    if (!file.getName().equals("v2-374cca7798b851b355142ce05a7fd301_1440w.png")) {
                        iconUrls.add("/api/uploads/icon/" + file.getName());
                    }
                }
            }
        }
        
        return Result.success(iconUrls);
    }
}

