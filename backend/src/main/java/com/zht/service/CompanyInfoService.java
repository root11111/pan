package com.zht.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zht.entity.CompanyInfo;

public interface CompanyInfoService extends IService<CompanyInfo> {
    CompanyInfo getCompanyInfo();
}

