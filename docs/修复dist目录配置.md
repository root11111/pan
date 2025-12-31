# 修复 dist 目录配置

## 问题

Vue 项目构建后的文件在 `/www/wwwroot/tht-lab.com.cn/dist` 目录，但 Nginx 配置的 `root` 指向了 `/www/wwwroot/tht-lab.com.cn`，导致找不到 `index.html`。

## 解决方案

### 方案 1：修改 Nginx 配置（推荐）

在宝塔面板中：

1. 进入 `网站` → `tht-lab.com.cn` → `设置` → `配置文件`
2. 找到 `root` 配置行，修改为：
   ```nginx
   root /www/wwwroot/tht-lab.com.cn/dist;
   ```
3. 保存配置
4. 重启 Nginx

### 方案 2：移动文件（不推荐）

将 `dist` 目录中的文件移动到上一级目录：

```bash
# 在服务器上执行
cd /www/wwwroot/tht-lab.com.cn
mv dist/* .
mv dist/.* . 2>/dev/null || true
rmdir dist
chown -R www:www /www/wwwroot/tht-lab.com.cn
```

---

## 完整的 Nginx 配置

确保你的 Nginx 配置如下：

```nginx
server
{
    listen 80;
    listen 443 ssl http2;
    server_name tht-lab.com.cn;
    
    # 重要：指向 dist 目录
    index index.html index.htm;
    root /www/wwwroot/tht-lab.com.cn/dist;

    # SSL 配置...
    ssl_certificate    /www/server/panel/vhost/cert/tht-lab.com.cn/fullchain.pem;
    ssl_certificate_key    /www/server/panel/vhost/cert/tht-lab.com.cn/privkey.pem;
    # ... 其他 SSL 配置

    # API 代理到后端服务
    location /api/ {
        proxy_pass http://127.0.0.1:8082;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
        
        proxy_http_version 1.1;
        proxy_set_header Upgrade $http_upgrade;
        proxy_set_header Connection "upgrade";
        
        proxy_connect_timeout 60s;
        proxy_send_timeout 60s;
        proxy_read_timeout 60s;
    }

    # 前端路由支持（Vue Router history 模式）
    location / {
        try_files $uri $uri/ /index.html;
    }

    # 静态资源缓存
    location ~ .*\.(gif|jpg|jpeg|png|bmp|swf|ico|svg|webp)$ {
        expires 30d;
        add_header Cache-Control "public, immutable";
    }

    location ~ .*\.(js|css|woff|woff2|ttf|eot)$ {
        expires 1y;
        add_header Cache-Control "public, immutable";
    }

    # Gzip 压缩
    gzip on;
    gzip_vary on;
    gzip_min_length 1024;
    gzip_types text/plain text/css text/xml text/javascript application/x-javascript application/xml+rss application/json application/javascript;

    access_log  /www/wwwlogs/tht-lab.com.cn.log;
    error_log  /www/wwwlogs/tht-lab.com.cn.error.log;
}
```

---

## 验证修复

修改配置后：

1. **检查文件是否存在：**
   ```bash
   ls -la /www/wwwroot/tht-lab.com.cn/dist/index.html
   ```

2. **测试 Nginx 配置：**
   ```bash
   nginx -t
   ```

3. **重启 Nginx：**
   ```bash
   /etc/init.d/nginx reload
   ```

4. **访问网站：**
   在浏览器中访问 `https://tht-lab.com.cn/`，应该能看到网站首页。

---

## 文件结构

修复后的目录结构应该是：

```
/www/wwwroot/tht-lab.com.cn/
├── dist/                    # Vue 构建后的文件
│   ├── index.html          # 必须存在
│   ├── assets/             # JS、CSS 等资源
│   │   ├── index-xxx.js
│   │   └── index-xxx.css
│   └── ...                 # 其他静态文件
└── ...                     # 其他文件（如果有）
```

---

## 注意事项

1. **每次更新前端后：**
   - 本地执行 `npm run build`
   - 上传 `dist` 目录内容到 `/www/wwwroot/tht-lab.com.cn/dist/`
   - 不需要重启 Nginx（静态文件）

2. **文件权限：**
   ```bash
   chown -R www:www /www/wwwroot/tht-lab.com.cn/dist
   chmod -R 755 /www/wwwroot/tht-lab.com.cn/dist
   ```

3. **如果还是 403：**
   - 检查 `dist/index.html` 是否存在
   - 检查文件权限
   - 检查 Nginx 配置中的 `root` 路径是否正确

