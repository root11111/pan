package com.zht.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zht.entity.Admin;

public interface AdminService extends IService<Admin> {
    Admin getByUsername(String username);
    boolean updatePassword(Long id, String oldPassword, String newPassword);
}

