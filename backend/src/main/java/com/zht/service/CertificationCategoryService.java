package com.zht.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zht.entity.CertificationCategory;

import java.util.List;

public interface CertificationCategoryService extends IService<CertificationCategory> {
    /**
     * 获取所有分类（树形结构）
     */
    List<CertificationCategory> getCategoryTree();
    
    /**
     * 根据父ID获取子分类
     */
    List<CertificationCategory> getByParentId(Long parentId);
    
    /**
     * 获取所有顶级分类
     */
    List<CertificationCategory> getTopCategories();
    
    /**
     * 级联删除分类（删除分类及其所有子分类）
     */
    void deleteCategoryCascade(Long id);
}

