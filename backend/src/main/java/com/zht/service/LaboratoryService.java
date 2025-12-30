package com.zht.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zht.entity.Laboratory;

import java.util.List;

public interface LaboratoryService extends IService<Laboratory> {
    List<Laboratory> getAllLaboratories();
}

