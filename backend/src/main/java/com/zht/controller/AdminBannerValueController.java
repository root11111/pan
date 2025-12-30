package com.zht.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zht.common.Result;
import com.zht.entity.BannerValue;
import com.zht.service.BannerValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/banner-value")
public class AdminBannerValueController {
    
    @Autowired
    private BannerValueService bannerValueService;
    
    @GetMapping("/list")
    public Result<List<BannerValue>> list() {
        LambdaQueryWrapper<BannerValue> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(BannerValue::getSortOrder);
        List<BannerValue> list = bannerValueService.list(wrapper);
        return Result.success(list);
    }
    
    @GetMapping("/page")
    public Result<Page<BannerValue>> page(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size) {
        Page<BannerValue> page = new Page<>(current, size);
        LambdaQueryWrapper<BannerValue> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(BannerValue::getSortOrder);
        Page<BannerValue> result = bannerValueService.page(page, wrapper);
        return Result.success(result);
    }
    
    @GetMapping("/{id}")
    public Result<BannerValue> getById(@PathVariable Long id) {
        BannerValue bannerValue = bannerValueService.getById(id);
        return Result.success(bannerValue);
    }
    
    @PostMapping("/save")
    public Result<String> save(@RequestBody BannerValue bannerValue) {
        bannerValueService.saveOrUpdate(bannerValue);
        return Result.success("保存成功");
    }
    
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Long id) {
        bannerValueService.removeById(id);
        return Result.success("删除成功");
    }
    
    @DeleteMapping("/batch")
    public Result<String> batchDelete(@RequestBody List<Long> ids) {
        bannerValueService.removeByIds(ids);
        return Result.success("批量删除成功");
    }
}

