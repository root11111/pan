package com.zht.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zht.common.Result;
import com.zht.entity.Honor;
import com.zht.service.HonorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/honor")
public class AdminHonorController {
    
    @Autowired
    private HonorService honorService;
    
    /**
     * 获取所有荣誉资质
     */
    @GetMapping("/list")
    public Result<List<Honor>> list() {
        LambdaQueryWrapper<Honor> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(Honor::getSortOrder);
        List<Honor> list = honorService.list(wrapper);
        return Result.success(list);
    }
    
    /**
     * 分页查询荣誉资质
     */
    @GetMapping("/page")
    public Result<Page<Honor>> page(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size) {
        Page<Honor> page = new Page<>(current, size);
        LambdaQueryWrapper<Honor> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(Honor::getSortOrder);
        Page<Honor> result = honorService.page(page, wrapper);
        return Result.success(result);
    }
    
    /**
     * 根据ID获取荣誉资质
     */
    @GetMapping("/{id}")
    public Result<Honor> getById(@PathVariable Long id) {
        Honor honor = honorService.getById(id);
        return Result.success(honor);
    }
    
    /**
     * 保存或更新荣誉资质
     */
    @PostMapping("/save")
    public Result<String> save(@RequestBody Honor honor) {
        honorService.saveOrUpdate(honor);
        return Result.success("保存成功");
    }
    
    /**
     * 删除荣誉资质
     */
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Long id) {
        honorService.removeById(id);
        return Result.success("删除成功");
    }
    
    /**
     * 批量删除荣誉资质
     */
    @DeleteMapping("/batch")
    public Result<String> batchDelete(@RequestBody List<Long> ids) {
        honorService.removeByIds(ids);
        return Result.success("批量删除成功");
    }
}

