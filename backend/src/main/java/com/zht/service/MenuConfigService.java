package com.zht.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zht.entity.MenuConfig;

import java.util.List;

public interface MenuConfigService extends IService<MenuConfig> {
    List<MenuConfig> getAllEnabledMenus();
}

