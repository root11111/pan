package com.zht.controller;

import com.zht.common.Result;
import com.zht.entity.EnterpriseAdvantage;
import com.zht.service.EnterpriseAdvantageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/advantage")
public class EnterpriseAdvantageController {
    @Autowired
    private EnterpriseAdvantageService enterpriseAdvantageService;

    @GetMapping("/list")
    public Result<List<EnterpriseAdvantage>> getAllAdvantages() {
        List<EnterpriseAdvantage> list = enterpriseAdvantageService.getAllAdvantages();
        return Result.success(list);
    }
}

