package com.zht.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zht.entity.MenuConfig;
import com.zht.mapper.MenuConfigMapper;
import com.zht.service.MenuConfigService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuConfigServiceImpl extends ServiceImpl<MenuConfigMapper, MenuConfig> 
        implements MenuConfigService {
    
    @Override
    public List<MenuConfig> getAllEnabledMenus() {
        LambdaQueryWrapper<MenuConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MenuConfig::getEnabled, true);
        wrapper.orderByAsc(MenuConfig::getSortOrder);
        return this.list(wrapper);
    }
}

