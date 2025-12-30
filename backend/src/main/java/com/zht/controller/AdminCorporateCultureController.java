package com.zht.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zht.common.Result;
import com.zht.entity.CorporateCulture;
import com.zht.service.CorporateCultureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/corporate-culture")
public class AdminCorporateCultureController {
    
    @Autowired
    private CorporateCultureService corporateCultureService;
    
    /**
     * 获取所有企业文化
     */
    @GetMapping("/list")
    public Result<List<CorporateCulture>> list() {
        LambdaQueryWrapper<CorporateCulture> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(CorporateCulture::getSortOrder);
        List<CorporateCulture> list = corporateCultureService.list(wrapper);
        return Result.success(list);
    }
    
    /**
     * 分页查询企业文化
     */
    @GetMapping("/page")
    public Result<Page<CorporateCulture>> page(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size) {
        Page<CorporateCulture> page = new Page<>(current, size);
        LambdaQueryWrapper<CorporateCulture> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(CorporateCulture::getSortOrder);
        Page<CorporateCulture> result = corporateCultureService.page(page, wrapper);
        return Result.success(result);
    }
    
    /**
     * 根据ID获取企业文化
     */
    @GetMapping("/{id}")
    public Result<CorporateCulture> getById(@PathVariable Long id) {
        CorporateCulture culture = corporateCultureService.getById(id);
        return Result.success(culture);
    }
    
    /**
     * 保存或更新企业文化
     */
    @PostMapping("/save")
    public Result<String> save(@RequestBody CorporateCulture culture) {
        corporateCultureService.saveOrUpdate(culture);
        return Result.success("保存成功");
    }
    
    /**
     * 删除企业文化
     */
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Long id) {
        corporateCultureService.removeById(id);
        return Result.success("删除成功");
    }
    
    /**
     * 批量删除企业文化
     */
    @DeleteMapping("/batch")
    public Result<String> batchDelete(@RequestBody List<Long> ids) {
        corporateCultureService.removeByIds(ids);
        return Result.success("批量删除成功");
    }
}

