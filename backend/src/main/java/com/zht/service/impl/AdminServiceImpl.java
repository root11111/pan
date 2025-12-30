package com.zht.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zht.common.PasswordUtil;
import com.zht.entity.Admin;
import com.zht.mapper.AdminMapper;
import com.zht.service.AdminService;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
    
    @Override
    public Admin getByUsername(String username) {
        LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Admin::getUsername, username);
        return this.getOne(wrapper);
    }
    
    @Override
    public boolean updatePassword(Long id, String oldPassword, String newPassword) {
        Admin admin = this.getById(id);
        if (admin == null) {
            return false;
        }
        
        if (!PasswordUtil.verify(oldPassword, admin.getPassword())) {
            return false;
        }
        
        admin.setPassword(PasswordUtil.encrypt(newPassword));
        return this.updateById(admin);
    }
}

