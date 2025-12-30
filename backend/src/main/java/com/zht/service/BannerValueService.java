package com.zht.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zht.entity.BannerValue;
import java.util.List;

public interface BannerValueService extends IService<BannerValue> {
    List<BannerValue> getAllBannerValues();
}

