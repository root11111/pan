# uploads 目录配置说明

## 📁 目录位置

**服务器路径**：`/www/wwwroot/www.tht-lab.com.cn/uploads/`

**目录结构**：
```
/www/wwwroot/www.tht-lab.com.cn/
├── dist/                    # 前端文件
│   ├── index.html
│   └── assets/
└── uploads/                 # 图片文件（与 dist 同级）
    ├── *.jpg, *.png         # 根目录图片
    ├── icon/                # 图标目录
    │   ├── certification/
    │   └── certification_service/
    ├── images/
    ├── img/
    └── certificates/        # 证书文件（如果有）
```

## ✅ 已修改的后端代码

以下文件已自动适配开发和生产环境：

1. **`WebMvcConfig.java`** - 图片资源映射
2. **`FileController.java`** - 文件上传目录
3. **`AdminCertificateController.java`** - 证书上传目录

代码会自动判断环境：
- **开发环境**：使用 `D:/workspace/pan/uploads/`
- **生产环境**：使用 `/www/wwwroot/www.tht-lab.com.cn/uploads/`

## 🔧 文件权限设置

上传后，确保设置正确的权限：

```bash
# 设置目录权限
chown -R www:www /www/wwwroot/www.tht-lab.com.cn/uploads
chmod -R 755 /www/wwwroot/www.tht-lab.com.cn/uploads

# 设置文件权限
find /www/wwwroot/www.tht-lab.com.cn/uploads -type f -exec chmod 644 {} \;
find /www/wwwroot/www.tht-lab.com.cn/uploads -type d -exec chmod 755 {} \;
```

## 📝 重新编译和部署

修改代码后需要重新编译：

```bash
cd backend
mvn clean package
```

然后上传新的 JAR 文件到服务器并重启服务。

## 🌐 图片访问方式

图片通过后端 API 访问：

- **访问路径**：`https://www.tht-lab.com.cn/api/uploads/xxx.jpg`
- **后端映射**：`/api/uploads/**` → `/www/wwwroot/www.tht-lab.com.cn/uploads/`

## ⚠️ 注意事项

1. **目录结构**：保持与本地相同的目录结构
2. **文件权限**：确保后端应用有读写权限
3. **Nginx 配置**：不需要额外配置，图片通过后端 API 访问
4. **环境判断**：代码会自动判断开发/生产环境

## 🔍 验证步骤

1. **检查文件是否存在**：
   ```bash
   ls -la /www/wwwroot/www.tht-lab.com.cn/uploads/
   ```

2. **检查权限**：
   ```bash
   stat /www/wwwroot/www.tht-lab.com.cn/uploads/
   ```

3. **访问测试**：
   - 访问 `https://www.tht-lab.com.cn/api/uploads/xxx.jpg`
   - 应该能正常显示图片

4. **上传测试**：
   - 登录后台管理系统
   - 尝试上传一张图片
   - 检查是否保存到正确位置

## 💡 优化建议（可选）

如果需要通过 Nginx 直接提供静态文件服务（更快），可以在 Nginx 配置中添加：

```nginx
# 在 location /api/ 之前添加
location /uploads/ {
    alias /www/wwwroot/www.tht-lab.com.cn/uploads/;
    expires 30d;
    add_header Cache-Control "public, immutable";
    access_log off;
}
```

这样图片可以直接通过 Nginx 访问，不经过后端，速度更快。

