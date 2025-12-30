package com.zht.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zht.entity.CertificationService;
import com.zht.mapper.CertificationServiceMapper;
import com.zht.service.CertificationServiceService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CertificationServiceServiceImpl extends ServiceImpl<CertificationServiceMapper, CertificationService> 
        implements CertificationServiceService {
    @Override
    public List<CertificationService> getByCategory(String category) {
        LambdaQueryWrapper<CertificationService> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CertificationService::getCategory, category);
        wrapper.orderByAsc(CertificationService::getSortOrder);
        return this.list(wrapper);
    }

    @Override
    public List<CertificationService> getByCategoryId(Long categoryId) {
        LambdaQueryWrapper<CertificationService> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CertificationService::getCategoryId, categoryId);
        wrapper.orderByAsc(CertificationService::getSortOrder);
        return this.list(wrapper);
    }

    @Override
    public List<CertificationService> getAll() {
        LambdaQueryWrapper<CertificationService> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(CertificationService::getSortOrder);
        return this.list(wrapper);
    }
}

