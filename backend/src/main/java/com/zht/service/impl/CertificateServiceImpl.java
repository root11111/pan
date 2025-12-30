package com.zht.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zht.entity.Certificate;
import com.zht.mapper.CertificateMapper;
import com.zht.service.CertificateService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class CertificateServiceImpl extends ServiceImpl<CertificateMapper, Certificate> 
        implements CertificateService {
    
    @Override
    public List<Certificate> queryByCertificateNo(String certificateNo) {
        LambdaQueryWrapper<Certificate> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(Certificate::getCertificateNo, certificateNo);
        wrapper.orderByDesc(Certificate::getIssueDate);
        return this.list(wrapper);
    }
    
    @Override
    public List<Certificate> queryByProductName(String productName) {
        LambdaQueryWrapper<Certificate> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(Certificate::getProductName, productName);
        wrapper.orderByDesc(Certificate::getIssueDate);
        return this.list(wrapper);
    }
    
    @Override
    public List<Certificate> queryByCertificateNoAndProductName(String certificateNo, String productName) {
        LambdaQueryWrapper<Certificate> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(certificateNo)) {
            wrapper.like(Certificate::getCertificateNo, certificateNo);
        }
        if (StringUtils.hasText(productName)) {
            wrapper.like(Certificate::getProductName, productName);
        }
        wrapper.orderByDesc(Certificate::getIssueDate);
        return this.list(wrapper);
    }
}

