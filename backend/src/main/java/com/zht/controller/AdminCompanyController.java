package com.zht.controller;

import com.zht.common.Result;
import com.zht.entity.CompanyInfo;
import com.zht.service.CompanyInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/company")
public class AdminCompanyController {
    
    @Autowired
    private CompanyInfoService companyInfoService;
    
    @GetMapping("/info")
    public Result<CompanyInfo> getInfo() {
        CompanyInfo info = companyInfoService.getCompanyInfo();
        return Result.success(info);
    }
    
    @PostMapping("/info")
    public Result<String> updateInfo(@RequestBody CompanyInfo companyInfo) {
        CompanyInfo existing = companyInfoService.getCompanyInfo();
        if (existing != null) {
            companyInfo.setId(existing.getId());
        }
        companyInfoService.saveOrUpdate(companyInfo);
        return Result.success("更新成功");
    }
}

