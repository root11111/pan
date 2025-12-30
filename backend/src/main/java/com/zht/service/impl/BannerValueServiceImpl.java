package com.zht.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zht.entity.BannerValue;
import com.zht.mapper.BannerValueMapper;
import com.zht.service.BannerValueService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BannerValueServiceImpl extends ServiceImpl<BannerValueMapper, BannerValue> implements BannerValueService {
    
    @Override
    public List<BannerValue> getAllBannerValues() {
        LambdaQueryWrapper<BannerValue> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(BannerValue::getSortOrder);
        return this.list(wrapper);
    }
}

