package com.zht.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zht.entity.HomeConfig;
import com.zht.mapper.HomeConfigMapper;
import com.zht.service.HomeConfigService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HomeConfigServiceImpl extends ServiceImpl<HomeConfigMapper, HomeConfig> implements HomeConfigService {
    
    @Override
    public Map<String, String> getConfigMap(String lang) {
        List<HomeConfig> list = this.list();
        Map<String, String> configMap = new HashMap<>();
        for (HomeConfig config : list) {
            if ("en".equalsIgnoreCase(lang)) {
                configMap.put(config.getConfigKey(), config.getConfigValueEn());
            } else {
                configMap.put(config.getConfigKey(), config.getConfigValueCn());
            }
        }
        return configMap;
    }
    
    @Override
    public HomeConfig getByKey(String configKey) {
        LambdaQueryWrapper<HomeConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(HomeConfig::getConfigKey, configKey);
        return this.getOne(wrapper);
    }
    
    @Override
    public void saveConfig(String configKey, String valueCn, String valueEn) {
        HomeConfig config = getByKey(configKey);
        if (config == null) {
            config = new HomeConfig();
            config.setConfigKey(configKey);
            config.setConfigType("text");
        }
        if (StringUtils.hasText(valueCn)) {
            config.setConfigValueCn(valueCn);
        }
        if (StringUtils.hasText(valueEn)) {
            config.setConfigValueEn(valueEn);
        }
        this.saveOrUpdate(config);
    }
}

