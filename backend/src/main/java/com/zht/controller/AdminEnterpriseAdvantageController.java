package com.zht.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zht.common.Result;
import com.zht.entity.EnterpriseAdvantage;
import com.zht.service.EnterpriseAdvantageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/advantage")
public class AdminEnterpriseAdvantageController {
    
    @Autowired
    private EnterpriseAdvantageService enterpriseAdvantageService;
    
    /**
     * 获取所有企业优势
     */
    @GetMapping("/list")
    public Result<List<EnterpriseAdvantage>> list() {
        LambdaQueryWrapper<EnterpriseAdvantage> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(EnterpriseAdvantage::getSortOrder);
        List<EnterpriseAdvantage> list = enterpriseAdvantageService.list(wrapper);
        return Result.success(list);
    }
    
    /**
     * 分页查询企业优势
     */
    @GetMapping("/page")
    public Result<Page<EnterpriseAdvantage>> page(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size) {
        Page<EnterpriseAdvantage> page = new Page<>(current, size);
        LambdaQueryWrapper<EnterpriseAdvantage> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(EnterpriseAdvantage::getSortOrder);
        Page<EnterpriseAdvantage> result = enterpriseAdvantageService.page(page, wrapper);
        return Result.success(result);
    }
    
    /**
     * 根据ID获取企业优势
     */
    @GetMapping("/{id}")
    public Result<EnterpriseAdvantage> getById(@PathVariable Long id) {
        EnterpriseAdvantage advantage = enterpriseAdvantageService.getById(id);
        return Result.success(advantage);
    }
    
    /**
     * 保存或更新企业优势
     */
    @PostMapping("/save")
    public Result<String> save(@RequestBody EnterpriseAdvantage advantage) {
        enterpriseAdvantageService.saveOrUpdate(advantage);
        return Result.success("保存成功");
    }
    
    /**
     * 删除企业优势
     */
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Long id) {
        enterpriseAdvantageService.removeById(id);
        return Result.success("删除成功");
    }
    
    /**
     * 批量删除企业优势
     */
    @DeleteMapping("/batch")
    public Result<String> batchDelete(@RequestBody List<Long> ids) {
        enterpriseAdvantageService.removeByIds(ids);
        return Result.success("批量删除成功");
    }
}

