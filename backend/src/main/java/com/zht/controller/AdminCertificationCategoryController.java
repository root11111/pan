package com.zht.controller;

import com.zht.common.Result;
import com.zht.entity.CertificationCategory;
import com.zht.service.CertificationCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/certification-category")
public class AdminCertificationCategoryController {
    
    @Autowired
    private CertificationCategoryService categoryService;
    
    /**
     * 获取所有分类（树形结构）
     */
    @GetMapping("/tree")
    public Result<List<CertificationCategory>> getCategoryTree() {
        List<CertificationCategory> tree = categoryService.getCategoryTree();
        return Result.success(tree);
    }
    
    /**
     * 获取所有分类（扁平列表）
     */
    @GetMapping("/list")
    public Result<List<CertificationCategory>> list() {
        List<CertificationCategory> list = categoryService.list();
        return Result.success(list);
    }
    
    /**
     * 根据父ID获取子分类
     */
    @GetMapping("/parent/{parentId}")
    public Result<List<CertificationCategory>> getByParentId(@PathVariable Long parentId) {
        List<CertificationCategory> list = categoryService.getByParentId(parentId);
        return Result.success(list);
    }
    
    /**
     * 获取顶级分类
     */
    @GetMapping("/top")
    public Result<List<CertificationCategory>> getTopCategories() {
        List<CertificationCategory> list = categoryService.getTopCategories();
        return Result.success(list);
    }
    
    /**
     * 根据ID获取分类
     */
    @GetMapping("/{id}")
    public Result<CertificationCategory> getById(@PathVariable Long id) {
        CertificationCategory category = categoryService.getById(id);
        return Result.success(category);
    }
    
    /**
     * 保存或更新分类
     */
    @PostMapping("/save")
    public Result<String> save(@RequestBody CertificationCategory category) {
        categoryService.saveOrUpdate(category);
        return Result.success("保存成功");
    }
    
    /**
     * 删除分类（级联删除所有子分类）
     */
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Long id) {
        try {
            // 级联删除分类及其所有子分类
            categoryService.deleteCategoryCascade(id);
            return Result.success("删除成功");
        } catch (Exception e) {
            return Result.error("删除失败：" + e.getMessage());
        }
    }
    
    /**
     * 批量删除分类（级联删除所有子分类）
     */
    @DeleteMapping("/batch")
    public Result<String> batchDelete(@RequestBody List<Long> ids) {
        try {
            // 级联删除每个分类及其所有子分类
            for (Long id : ids) {
                categoryService.deleteCategoryCascade(id);
            }
            return Result.success("批量删除成功");
        } catch (Exception e) {
            return Result.error("批量删除失败：" + e.getMessage());
        }
    }
}

