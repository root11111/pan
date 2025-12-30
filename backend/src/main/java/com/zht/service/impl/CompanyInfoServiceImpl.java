package com.zht.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zht.entity.CompanyInfo;
import com.zht.mapper.CompanyInfoMapper;
import com.zht.service.CompanyInfoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyInfoServiceImpl extends ServiceImpl<CompanyInfoMapper, CompanyInfo> implements CompanyInfoService {
    @Override
    public CompanyInfo getCompanyInfo() {
        List<CompanyInfo> list = this.list();
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }
}

