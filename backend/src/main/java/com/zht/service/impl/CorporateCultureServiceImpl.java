package com.zht.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zht.entity.CorporateCulture;
import com.zht.mapper.CorporateCultureMapper;
import com.zht.service.CorporateCultureService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CorporateCultureServiceImpl extends ServiceImpl<CorporateCultureMapper, CorporateCulture> 
        implements CorporateCultureService {
    
    @Override
    public List<CorporateCulture> getAllCultures() {
        LambdaQueryWrapper<CorporateCulture> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(CorporateCulture::getSortOrder);
        return this.list(wrapper);
    }
}

