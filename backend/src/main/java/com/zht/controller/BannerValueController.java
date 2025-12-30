package com.zht.controller;

import com.zht.common.Result;
import com.zht.entity.BannerValue;
import com.zht.service.BannerValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/banner-value")
public class BannerValueController {
    
    @Autowired
    private BannerValueService bannerValueService;
    
    @GetMapping("/list")
    public Result<List<BannerValue>> getAllBannerValues() {
        List<BannerValue> list = bannerValueService.getAllBannerValues();
        return Result.success(list);
    }
}

