package com.zht.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zht.entity.CertificationCategory;
import com.zht.mapper.CertificationCategoryMapper;
import com.zht.service.CertificationCategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CertificationCategoryServiceImpl extends ServiceImpl<CertificationCategoryMapper, CertificationCategory> 
        implements CertificationCategoryService {
    
    @Override
    public List<CertificationCategory> getCategoryTree() {
        List<CertificationCategory> all = this.list();
        // 获取所有顶级分类
        List<CertificationCategory> topCategories = all.stream()
                .filter(cat -> cat.getParentId() == null || cat.getParentId() == 0)
                .sorted((a, b) -> {
                    if (a.getSortOrder() == null) return 1;
                    if (b.getSortOrder() == null) return -1;
                    return a.getSortOrder().compareTo(b.getSortOrder());
                })
                .collect(Collectors.toList());
        
        // 递归构建树形结构，为每个分类添加所有层级的子分类
        for (CertificationCategory topCategory : topCategories) {
            buildChildrenRecursively(topCategory, all);
        }
        
        return topCategories;
    }
    
    /**
     * 递归构建分类的子分类树
     */
    private void buildChildrenRecursively(CertificationCategory category, List<CertificationCategory> all) {
        List<CertificationCategory> children = all.stream()
                .filter(cat -> category.getId().equals(cat.getParentId()))
                .sorted((a, b) -> {
                    if (a.getSortOrder() == null) return 1;
                    if (b.getSortOrder() == null) return -1;
                    return a.getSortOrder().compareTo(b.getSortOrder());
                })
                .collect(Collectors.toList());
        
        category.setChildren(children);
        
        // 递归为每个子分类构建其子分类
        for (CertificationCategory child : children) {
            buildChildrenRecursively(child, all);
        }
    }
    
    @Override
    public List<CertificationCategory> getByParentId(Long parentId) {
        LambdaQueryWrapper<CertificationCategory> wrapper = new LambdaQueryWrapper<>();
        if (parentId == null || parentId == 0) {
            wrapper.isNull(CertificationCategory::getParentId)
                   .or()
                   .eq(CertificationCategory::getParentId, 0);
        } else {
            wrapper.eq(CertificationCategory::getParentId, parentId);
        }
        wrapper.orderByAsc(CertificationCategory::getSortOrder);
        return this.list(wrapper);
    }
    
    @Override
    public List<CertificationCategory> getTopCategories() {
        return getByParentId(0L);
    }
    
    /**
     * 级联删除分类（递归删除所有子分类）
     */
    @Override
    public void deleteCategoryCascade(Long id) {
        // 获取所有子分类
        List<CertificationCategory> children = getByParentId(id);
        
        // 递归删除所有子分类
        for (CertificationCategory child : children) {
            deleteCategoryCascade(child.getId());
        }
        
        // 删除当前分类
        this.removeById(id);
    }
}

