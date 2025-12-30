package com.zht.controller;

import com.zht.common.JwtUtil;
import com.zht.common.PasswordUtil;
import com.zht.common.Result;
import com.zht.dto.LoginRequest;
import com.zht.dto.LoginResponse;
import com.zht.entity.Admin;
import com.zht.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
    private AdminService adminService;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @PostMapping("/login")
    public Result<LoginResponse> login(@RequestBody LoginRequest request) {
        Admin admin = adminService.getByUsername(request.getUsername());
        if (admin == null) {
            return Result.error("用户名或密码错误");
        }
        
        if (admin.getStatus() != null && admin.getStatus() == 0) {
            return Result.error("账号已被禁用");
        }
        
        // 前端已经对密码进行了 SHA-256 + Base64 加密
        // 后端直接比较加密后的密码与数据库中的密码
        String encryptedPassword = request.getPassword();
        
        // 调试信息（生产环境可删除）
        System.out.println("登录请求 - 用户名: " + request.getUsername());
        System.out.println("数据库密码: " + admin.getPassword());
        System.out.println("请求密码(已加密): " + encryptedPassword);
        System.out.println("密码匹配: " + encryptedPassword.equals(admin.getPassword()));
        
        if (!encryptedPassword.equals(admin.getPassword())) {
            return Result.error("用户名或密码错误");
        }
        
        String token = jwtUtil.generateToken(admin.getUsername());
        
        LoginResponse response = new LoginResponse();
        response.setToken(token);
        response.setUsername(admin.getUsername());
        response.setNickname(admin.getNickname());
        
        return Result.success(response);
    }
    
    @GetMapping("/info")
    public Result<Admin> getInfo(@RequestHeader("Authorization") String token) {
        String username = jwtUtil.getUsernameFromToken(token.replace("Bearer ", ""));
        Admin admin = adminService.getByUsername(username);
        admin.setPassword(null); // 不返回密码
        return Result.success(admin);
    }
}

