package com.zht.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zht.entity.Honor;

import java.util.List;

public interface HonorService extends IService<Honor> {
    List<Honor> getAllHonors();
}

