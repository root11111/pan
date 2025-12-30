package com.zht.controller;

import com.zht.common.Result;
import com.zht.entity.Certificate;
import com.zht.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/certificate")
public class CertificateController {
    
    @Autowired
    private CertificateService certificateService;
    
    /**
     * 根据证书编号查询
     */
    @GetMapping("/query")
    public Result<List<Certificate>> query(
            @RequestParam(required = false) String certificateNo,
            @RequestParam(required = false) String productName) {
        List<Certificate> certificates;
        
        if (certificateNo != null && !certificateNo.trim().isEmpty() && 
            productName != null && !productName.trim().isEmpty()) {
            // 同时查询证书编号和产品名称
            certificates = certificateService.queryByCertificateNoAndProductName(
                certificateNo.trim(), productName.trim());
        } else if (certificateNo != null && !certificateNo.trim().isEmpty()) {
            // 只查询证书编号
            certificates = certificateService.queryByCertificateNo(certificateNo.trim());
        } else if (productName != null && !productName.trim().isEmpty()) {
            // 只查询产品名称
            certificates = certificateService.queryByProductName(productName.trim());
        } else {
            return Result.error("请输入证书编号或产品名称");
        }
        
        return Result.success(certificates);
    }
    
    /**
     * 根据ID获取证书详情
     */
    @GetMapping("/{id}")
    public Result<Certificate> getById(@PathVariable Long id) {
        Certificate certificate = certificateService.getById(id);
        if (certificate == null) {
            return Result.error("证书不存在");
        }
        return Result.success(certificate);
    }
}

