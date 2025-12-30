package com.zht.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zht.common.Result;
import com.zht.entity.HomeConfig;
import com.zht.service.HomeConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/home-config")
public class AdminHomeConfigController {
    
    @Autowired
    private HomeConfigService homeConfigService;
    
    @GetMapping("/list")
    public Result<List<HomeConfig>> getAllConfigs() {
        LambdaQueryWrapper<HomeConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(HomeConfig::getSortOrder);
        List<HomeConfig> list = homeConfigService.list(wrapper);
        return Result.success(list);
    }
    
    @GetMapping("/{id}")
    public Result<HomeConfig> getById(@PathVariable Long id) {
        HomeConfig config = homeConfigService.getById(id);
        return Result.success(config);
    }
    
    @PostMapping("/save")
    public Result<String> save(@RequestBody HomeConfig config) {
        // 根据 configKey 查询是否已存在
        if (config.getId() == null && config.getConfigKey() != null) {
            HomeConfig existing = homeConfigService.getByKey(config.getConfigKey());
            if (existing != null) {
                // 如果存在，设置ID并更新
                config.setId(existing.getId());
            }
        }
        homeConfigService.saveOrUpdate(config);
        return Result.success("保存成功");
    }
    
    @PostMapping("/batch-save")
    public Result<String> batchSave(@RequestBody List<HomeConfig> configs) {
        for (HomeConfig config : configs) {
            // 根据 configKey 查询是否已存在
            HomeConfig existing = homeConfigService.getByKey(config.getConfigKey());
            if (existing != null) {
                // 如果存在，设置ID并更新
                config.setId(existing.getId());
                homeConfigService.updateById(config);
            } else {
                // 如果不存在，插入新记录
                homeConfigService.save(config);
            }
        }
        return Result.success("批量保存成功");
    }
    
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Long id) {
        homeConfigService.removeById(id);
        return Result.success("删除成功");
    }
}

