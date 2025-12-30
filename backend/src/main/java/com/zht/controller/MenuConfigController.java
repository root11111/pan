package com.zht.controller;

import com.zht.common.Result;
import com.zht.entity.MenuConfig;
import com.zht.service.MenuConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/menu-config")
public class MenuConfigController {
    
    @Autowired
    private MenuConfigService menuConfigService;
    
    /**
     * 获取所有启用的菜单配置
     */
    @GetMapping("/enabled")
    public Result<List<MenuConfig>> getEnabledMenus() {
        List<MenuConfig> menus = menuConfigService.getAllEnabledMenus();
        return Result.success(menus);
    }
}

