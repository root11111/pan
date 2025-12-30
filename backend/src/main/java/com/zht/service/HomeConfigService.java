package com.zht.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zht.entity.HomeConfig;
import java.util.List;
import java.util.Map;

public interface HomeConfigService extends IService<HomeConfig> {
    Map<String, String> getConfigMap(String lang); // lang: cn 或 en
    HomeConfig getByKey(String configKey);
    void saveConfig(String configKey, String valueCn, String valueEn);
}

