package com.zht.controller;

import com.zht.common.Result;
import com.zht.entity.CertificationCategory;
import com.zht.service.CertificationCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/certification-category")
public class CertificationCategoryController {
    
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
}

