package com.zht.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zht.entity.EnterpriseAdvantage;
import com.zht.mapper.EnterpriseAdvantageMapper;
import com.zht.service.EnterpriseAdvantageService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnterpriseAdvantageServiceImpl extends ServiceImpl<EnterpriseAdvantageMapper, EnterpriseAdvantage> 
        implements EnterpriseAdvantageService {
    @Override
    public List<EnterpriseAdvantage> getAllAdvantages() {
        LambdaQueryWrapper<EnterpriseAdvantage> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(EnterpriseAdvantage::getSortOrder);
        List<EnterpriseAdvantage> list = this.list(wrapper);
        // 去重：保留每个title的第一条记录
        return list.stream()
                .collect(java.util.stream.Collectors.toMap(
                        EnterpriseAdvantage::getTitle,
                        item -> item,
                        (existing, replacement) -> existing
                ))
                .values()
                .stream()
                .sorted((a, b) -> Integer.compare(a.getSortOrder(), b.getSortOrder()))
                .collect(java.util.stream.Collectors.toList());
    }
}

