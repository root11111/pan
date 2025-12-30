package com.zht.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zht.common.Result;
import com.zht.entity.MenuConfig;
import com.zht.service.MenuConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/menu-config")
public class AdminMenuConfigController {
    
    @Autowired
    private MenuConfigService menuConfigService;
    
    /**
     * 获取所有菜单配置
     */
    @GetMapping("/list")
    public Result<List<MenuConfig>> list() {
        LambdaQueryWrapper<MenuConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(MenuConfig::getSortOrder);
        List<MenuConfig> list = menuConfigService.list(wrapper);
        return Result.success(list);
    }
    
    /**
     * 分页查询菜单配置
     */
    @GetMapping("/page")
    public Result<Page<MenuConfig>> page(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size) {
        Page<MenuConfig> page = new Page<>(current, size);
        LambdaQueryWrapper<MenuConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(MenuConfig::getSortOrder);
        Page<MenuConfig> result = menuConfigService.page(page, wrapper);
        return Result.success(result);
    }
    
    /**
     * 根据ID获取菜单配置
     */
    @GetMapping("/{id}")
    public Result<MenuConfig> getById(@PathVariable Long id) {
        MenuConfig menuConfig = menuConfigService.getById(id);
        return Result.success(menuConfig);
    }
    
    /**
     * 保存或更新菜单配置
     */
    @PostMapping("/save")
    public Result<String> save(@RequestBody MenuConfig menuConfig) {
        menuConfigService.saveOrUpdate(menuConfig);
        return Result.success("保存成功");
    }
    
    /**
     * 删除菜单配置
     */
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Long id) {
        menuConfigService.removeById(id);
        return Result.success("删除成功");
    }
    
    /**
     * 批量删除菜单配置
     */
    @DeleteMapping("/batch")
    public Result<String> batchDelete(@RequestBody List<Long> ids) {
        menuConfigService.removeByIds(ids);
        return Result.success("批量删除成功");
    }
}

