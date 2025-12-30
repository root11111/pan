package com.zht.controller;

import com.zht.common.PasswordUtil;
import com.zht.common.Result;
import org.springframework.web.bind.annotation.*;

/**
 * 管理员密码工具控制器
 * 用于生成密码哈希值，方便初始化数据库
 */
@RestController
@RequestMapping("/admin/password")
public class AdminPasswordController {
    
    @GetMapping("/generate")
    public Result<String> generatePasswordHash(@RequestParam String password) {
        String hash = PasswordUtil.encrypt(password);
        return Result.success("密码: " + password + "\n哈希值: " + hash);
    }
}

