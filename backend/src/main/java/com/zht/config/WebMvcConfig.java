package com.zht.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    
    @Autowired
    private JwtInterceptor jwtInterceptor;
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 开发环境：D:/workspace/pan/uploads/
        // 生产环境：/www/wwwroot/www.tht-lab.com.cn/uploads/
        String uploadPath = System.getProperty("user.dir").contains("workspace") 
            ? "file:D:/workspace/pan/uploads/" 
            : "file:/www/wwwroot/www.tht-lab.com.cn/uploads/";
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(uploadPath);
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/api/admin/**")
                .excludePathPatterns("/api/admin/login", "/api/admin/password/**");
    }
}

