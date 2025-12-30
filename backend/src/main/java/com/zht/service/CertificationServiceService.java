package com.zht.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zht.entity.CertificationService;

import java.util.List;

public interface CertificationServiceService extends IService<CertificationService> {
    List<CertificationService> getByCategory(String category);
    List<CertificationService> getByCategoryId(Long categoryId);
    List<CertificationService> getAll();
}

