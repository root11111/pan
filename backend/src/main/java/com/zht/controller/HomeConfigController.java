package com.zht.controller;

import com.zht.common.Result;
import com.zht.entity.HomeConfig;
import com.zht.service.HomeConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/home-config")
public class HomeConfigController {
    
    @Autowired
    private HomeConfigService homeConfigService;
    
    @GetMapping("/map")
    public Result<Map<String, String>> getConfigMap(@RequestParam(defaultValue = "cn") String lang) {
        Map<String, String> configMap = homeConfigService.getConfigMap(lang);
        return Result.success(configMap);
    }
    
    @GetMapping("/list")
    public Result<List<HomeConfig>> getAllConfigs() {
        List<HomeConfig> list = homeConfigService.list();
        return Result.success(list);
    }
}

