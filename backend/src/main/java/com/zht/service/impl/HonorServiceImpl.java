package com.zht.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zht.entity.Honor;
import com.zht.mapper.HonorMapper;
import com.zht.service.HonorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HonorServiceImpl extends ServiceImpl<HonorMapper, Honor> implements HonorService {
    @Override
    public List<Honor> getAllHonors() {
        LambdaQueryWrapper<Honor> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(Honor::getSortOrder);
        return this.list(wrapper);
    }
}

