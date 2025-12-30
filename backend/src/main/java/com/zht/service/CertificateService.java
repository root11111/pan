package com.zht.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zht.entity.Certificate;

import java.util.List;

public interface CertificateService extends IService<Certificate> {
    List<Certificate> queryByCertificateNo(String certificateNo);
    List<Certificate> queryByProductName(String productName);
    List<Certificate> queryByCertificateNoAndProductName(String certificateNo, String productName);
}

