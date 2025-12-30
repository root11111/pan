package com.zht.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zht.common.Result;
import com.zht.entity.Certificate;
import com.zht.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin/certificate")
public class AdminCertificateController {
    
    @Autowired
    private CertificateService certificateService;
    
    private static final String UPLOAD_DIR = "D:/workspace/pan/uploads/certificates/";
    
    /**
     * 获取所有证书
     */
    @GetMapping("/list")
    public Result<List<Certificate>> list() {
        LambdaQueryWrapper<Certificate> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Certificate::getIssueDate);
        List<Certificate> list = certificateService.list(wrapper);
        return Result.success(list);
    }
    
    /**
     * 分页查询证书
     */
    @GetMapping("/page")
    public Result<Page<Certificate>> page(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String certificateNo,
            @RequestParam(required = false) String productName) {
        Page<Certificate> page = new Page<>(current, size);
        LambdaQueryWrapper<Certificate> wrapper = new LambdaQueryWrapper<>();
        
        if (certificateNo != null && !certificateNo.isEmpty()) {
            wrapper.like(Certificate::getCertificateNo, certificateNo);
        }
        if (productName != null && !productName.isEmpty()) {
            wrapper.like(Certificate::getProductName, productName);
        }
        
        wrapper.orderByDesc(Certificate::getIssueDate);
        Page<Certificate> result = certificateService.page(page, wrapper);
        return Result.success(result);
    }
    
    /**
     * 根据ID获取证书
     */
    @GetMapping("/{id}")
    public Result<Certificate> getById(@PathVariable Long id) {
        Certificate certificate = certificateService.getById(id);
        return Result.success(certificate);
    }
    
    /**
     * 保存或更新证书
     */
    @PostMapping("/save")
    public Result<String> save(@RequestBody Certificate certificate) {
        // 自动计算状态
        if (certificate.getExpiryDate() != null) {
            LocalDate now = LocalDate.now();
            if (certificate.getExpiryDate().isBefore(now)) {
                certificate.setStatus("已过期");
            } else {
                certificate.setStatus("有效");
            }
        }
        certificateService.saveOrUpdate(certificate);
        return Result.success("保存成功");
    }
    
    /**
     * 上传证书文件
     */
    @PostMapping("/upload")
    public Result<String> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return Result.error("文件不能为空");
        }
        
        try {
            // 创建上传目录
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            
            // 生成唯一文件名
            String originalFilename = file.getOriginalFilename();
            String extension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String filename = UUID.randomUUID().toString() + extension;
            
            // 保存文件
            Path filePath = uploadPath.resolve(filename);
            Files.write(filePath, file.getBytes());
            
            // 返回文件路径（相对于uploads目录）
            String relativePath = "certificates/" + filename;
            return Result.success(relativePath);
        } catch (IOException e) {
            return Result.error("文件上传失败: " + e.getMessage());
        }
    }
    
    /**
     * 删除证书
     */
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Long id) {
        Certificate certificate = certificateService.getById(id);
        if (certificate != null && certificate.getCertificateFile() != null) {
            // 删除文件
            try {
                Path filePath = Paths.get(UPLOAD_DIR + certificate.getCertificateFile().replace("certificates/", ""));
                if (Files.exists(filePath)) {
                    Files.delete(filePath);
                }
            } catch (IOException e) {
                // 忽略文件删除错误
            }
        }
        certificateService.removeById(id);
        return Result.success("删除成功");
    }
    
    /**
     * 批量删除证书
     */
    @DeleteMapping("/batch")
    public Result<String> batchDelete(@RequestBody List<Long> ids) {
        // 删除关联的文件
        for (Long id : ids) {
            Certificate certificate = certificateService.getById(id);
            if (certificate != null && certificate.getCertificateFile() != null) {
                try {
                    Path filePath = Paths.get(UPLOAD_DIR + certificate.getCertificateFile().replace("certificates/", ""));
                    if (Files.exists(filePath)) {
                        Files.delete(filePath);
                    }
                } catch (IOException e) {
                    // 忽略文件删除错误
                }
            }
        }
        certificateService.removeByIds(ids);
        return Result.success("批量删除成功");
    }
}

