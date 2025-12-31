# Nginx 配置文件使用说明

## 📁 使用的配置文件

**唯一配置文件**：`docs/宝塔Nginx配置示例.conf`

这是最终版本，包含所有修复：
- ✅ API 代理配置（支持 `/api` 和 `/api/`）
- ✅ WebSocket 代理配置
- ✅ Vue Router history 模式支持
- ✅ 静态资源缓存优化
- ✅ Gzip 压缩
- ✅ dist 目录配置（`root /www/wwwroot/www.tht-lab.com.cn/dist`）

## 🚀 使用方法

### 1. 复制配置到宝塔面板

1. 打开 `docs/宝塔Nginx配置示例.conf`
2. 复制全部内容
3. 登录宝塔面板
4. 进入 `网站` → `www.tht-lab.com.cn` → `设置` → `配置文件`
5. 粘贴并替换现有配置
6. 保存

### 2. 测试配置

```bash
nginx -t
```

### 3. 重载 Nginx

在宝塔面板中点击"重载配置"，或执行：
```bash
/etc/init.d/nginx reload
```

## ⚙️ 配置要点

### 目录配置
```nginx
root /www/wwwroot/www.tht-lab.com.cn/dist;
index index.html index.htm;
```

### API 代理
```nginx
location ~ ^/api(/|$) {
    proxy_pass http://127.0.0.1:8082;
    ...
}
```

### Vue Router 支持
```nginx
location / {
    try_files $uri $uri/ /index.html;
}
```

## 📝 注意事项

1. **文件权限**：确保 dist 目录权限正确
   ```bash
   chown -R www:www /www/wwwroot/www.tht-lab.com.cn/dist
   chmod -R 755 /www/wwwroot/www.tht-lab.com.cn/dist
   ```

2. **后端服务**：确保后端在 8082 端口运行

3. **SSL 证书路径**：如果证书路径不同，需要修改配置中的证书路径

## 🔍 验证

部署后访问：
- 首页：`https://www.tht-lab.com.cn/`
- 后台：`https://www.tht-lab.com.cn/admin/login`

打开浏览器开发者工具（F12），检查：
- API 请求应该是 `/api/...`（相对路径）
- 不应该出现 `localhost:8082` 的错误

