package com.zht.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zht.entity.EnterpriseAdvantage;

import java.util.List;

public interface EnterpriseAdvantageService extends IService<EnterpriseAdvantage> {
    List<EnterpriseAdvantage> getAllAdvantages();
}

