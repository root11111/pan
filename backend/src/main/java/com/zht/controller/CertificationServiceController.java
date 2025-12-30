package com.zht.controller;

import com.zht.common.Result;
import com.zht.entity.CertificationService;
import com.zht.service.CertificationServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/certification")
public class CertificationServiceController {
    @Autowired
    private CertificationServiceService certificationServiceService;

    @GetMapping("/list")
    public Result<List<CertificationService>> getAll() {
        List<CertificationService> list = certificationServiceService.getAll();
        return Result.success(list);
    }

    @GetMapping("/category")
    public Result<List<CertificationService>> getByCategory(@RequestParam String category) {
        List<CertificationService> list = certificationServiceService.getByCategory(category);
        return Result.success(list);
    }

    @GetMapping("/category-id")
    public Result<List<CertificationService>> getByCategoryId(@RequestParam Long categoryId) {
        List<CertificationService> list = certificationServiceService.getByCategoryId(categoryId);
        return Result.success(list);
    }

    @GetMapping("/{id}")
    public Result<CertificationService> getById(@PathVariable Long id) {
        CertificationService service = certificationServiceService.getById(id);
        if (service == null) {
            return Result.error("认证服务不存在");
        }
        return Result.success(service);
    }
}

