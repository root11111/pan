package com.zht.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zht.entity.Laboratory;
import com.zht.mapper.LaboratoryMapper;
import com.zht.service.LaboratoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LaboratoryServiceImpl extends ServiceImpl<LaboratoryMapper, Laboratory> implements LaboratoryService {
    @Override
    public List<Laboratory> getAllLaboratories() {
        LambdaQueryWrapper<Laboratory> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(Laboratory::getSortOrder);
        List<Laboratory> list = this.list(wrapper);
        // 去重：保留每个name_cn的第一条记录
        return list.stream()
                .collect(java.util.stream.Collectors.toMap(
                        Laboratory::getNameCn,
                        item -> item,
                        (existing, replacement) -> existing
                ))
                .values()
                .stream()
                .sorted((a, b) -> Integer.compare(a.getSortOrder(), b.getSortOrder()))
                .collect(java.util.stream.Collectors.toList());
    }
}

