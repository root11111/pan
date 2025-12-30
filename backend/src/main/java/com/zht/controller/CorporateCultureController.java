package com.zht.controller;

import com.zht.common.Result;
import com.zht.entity.CorporateCulture;
import com.zht.service.CorporateCultureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/corporate-culture")
public class CorporateCultureController {
    
    @Autowired
    private CorporateCultureService corporateCultureService;
    
    @GetMapping("/list")
    public Result<List<CorporateCulture>> getAllCultures() {
        List<CorporateCulture> list = corporateCultureService.getAllCultures();
        return Result.success(list);
    }
}

