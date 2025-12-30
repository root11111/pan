package com.zht.config;

import com.zht.common.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtInterceptor implements HandlerInterceptor {
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 跨域预检请求直接放行
        if ("OPTIONS".equals(request.getMethod())) {
            return true;
        }
        
        // 登录接口放行
        String path = request.getRequestURI();
        if (path.contains("/admin/login") || path.contains("/api/company") || 
            path.contains("/api/laboratory") || path.contains("/api/news") ||
            path.contains("/api/certification") || path.contains("/api/honor") ||
            path.contains("/api/advantage") || path.contains("/api/corporate-culture") ||
            path.contains("/api/home-config") || path.contains("/api/banner-value") ||
            path.contains("/api/message/submit") || path.contains("/api/menu-config") || 
            path.contains("/api/certificate/query") || path.contains("/uploads/") || 
            path.contains("/ws")) {
            return true;
        }
        
        // 获取token
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        
        token = token.substring(7);
        
        try {
            // 验证token
            String username = jwtUtil.getUsernameFromToken(token);
            if (username != null && jwtUtil.validateToken(token, username)) {
                request.setAttribute("username", username);
                return true;
            }
        } catch (Exception e) {
            // token无效
        }
        
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return false;
    }
}

