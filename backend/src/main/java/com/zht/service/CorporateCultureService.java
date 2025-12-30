package com.zht.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zht.entity.CorporateCulture;

import java.util.List;

public interface CorporateCultureService extends IService<CorporateCulture> {
    List<CorporateCulture> getAllCultures();
}

