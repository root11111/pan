package com.zht.controller;

import com.zht.common.Result;
import com.zht.entity.CompanyInfo;
import com.zht.service.CompanyInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/company")
public class CompanyInfoController {
    @Autowired
    private CompanyInfoService companyInfoService;

    @GetMapping("/info")
    public Result<CompanyInfo> getCompanyInfo() {
        CompanyInfo info = companyInfoService.getCompanyInfo();
        return Result.success(info);
    }
}

