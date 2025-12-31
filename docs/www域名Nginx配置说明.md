# www.tht-lab.com.cn Nginx 配置说明

## 配置文件位置
`docs/宝塔Nginx配置示例.conf`

## 配置要点

### 1. 目录配置
```nginx
root /www/wwwroot/www.tht-lab.com.cn;
index index.html index.htm;
```
- 前端 `dist` 目录的内容应该直接放在 `/www/wwwroot/www.tht-lab.com.cn/` 目录下
- 确保 `index.html` 文件在此目录下

### 2. API 代理配置
```nginx
location /api/ {
    proxy_pass http://127.0.0.1:8082;
    ...
}
```
- 所有 `/api/` 开头的请求会被代理到后端服务（8082端口）
- 支持跨域和WebSocket升级

### 3. WebSocket 代理配置
```nginx
location /ws {
    proxy_pass http://127.0.0.1:8082;
    ...
}
```
- 用于后台管理系统的实时聊天功能
- 支持 WebSocket 连接升级

### 4. Vue Router History 模式支持
```nginx
location / {
    try_files $uri $uri/ /index.html;
}
```
- 支持 Vue Router 的 history 模式
- 所有路由请求都会返回 `index.html`，由前端路由处理

### 5. 静态资源缓存
- 图片文件：缓存30天
- JS/CSS/字体文件：缓存1年
- 提升网站加载速度

### 6. Gzip 压缩
- 启用 Gzip 压缩，减少传输大小
- 提升页面加载速度

## 部署步骤

### 1. 上传前端文件
将 `frontend/dist` 目录中的所有文件上传到：
```
/www/wwwroot/www.tht-lab.com.cn/
```

确保目录结构如下：
```
/www/wwwroot/www.tht-lab.com.cn/
├── index.html          # 必须存在
├── assets/            # JS、CSS等资源
│   ├── index-xxx.js
│   └── index-xxx.css
└── ...                 # 其他静态文件
```

### 2. 修改 Nginx 配置
1. 登录宝塔面板
2. 进入 `网站` → `www.tht-lab.com.cn` → `设置` → `配置文件`
3. 将 `docs/宝塔Nginx配置示例.conf` 的内容复制到配置文件中
4. 保存配置

### 3. 测试配置
```bash
# 测试 Nginx 配置语法
nginx -t
```

### 4. 重启 Nginx
在宝塔面板中：
- 点击 `网站` → `www.tht-lab.com.cn` → `设置` → `重载配置`

或者使用命令：
```bash
/etc/init.d/nginx reload
```

### 5. 验证部署
1. 访问 `https://www.tht-lab.com.cn/` 应该能看到网站首页
2. 访问 `https://www.tht-lab.com.cn/admin/login` 应该能看到后台登录页
3. 打开浏览器开发者工具（F12），检查 Network 标签：
   - API 请求应该显示为 `/api/...`（相对路径）
   - 不应该出现 `http://localhost:8082` 的错误

## 注意事项

### 1. PHP 配置
配置中已注释掉 PHP 配置：
```nginx
#include enable-php-82.conf;
```
因为 Vue 项目是纯前端，不需要 PHP。如果后续需要 PHP，可以取消注释。

### 2. 伪静态规则
配置中已注释掉宝塔的伪静态规则：
```nginx
#include /www/server/panel/vhost/rewrite/www.tht-lab.com.cn.conf;
```
因为 Vue Router 需要自己的路由处理。如果取消注释，可能会影响前端路由。

### 3. 后端服务
确保后端服务在 `127.0.0.1:8082` 正常运行，否则 API 请求会失败。

### 4. 文件权限
确保文件权限正确：
```bash
chown -R www:www /www/wwwroot/www.tht-lab.com.cn
chmod -R 755 /www/wwwroot/www.tht-lab.com.cn
```

## 常见问题

### 1. 403 Forbidden
- 检查 `index.html` 是否存在
- 检查文件权限
- 检查 Nginx 配置中的 `root` 路径是否正确

### 2. API 请求失败
- 检查后端服务是否在 8082 端口运行
- 检查 Nginx 配置中的 `/api/` location 是否正确
- 查看 Nginx 错误日志：`/www/wwwlogs/www.tht-lab.com.cn.error.log`

### 3. 前端路由404
- 确认 `location /` 中有 `try_files $uri $uri/ /index.html;`
- 确认没有其他 location 规则覆盖了根路径

### 4. WebSocket 连接失败
- 检查 `/ws` location 配置是否正确
- 检查后端是否支持 WebSocket
- 查看浏览器控制台的错误信息

## 配置对比

### 原配置（宝塔默认）
- ❌ 没有 API 代理
- ❌ 没有 WebSocket 支持
- ❌ 没有 Vue Router 支持
- ❌ 静态资源缓存不够优化

### 新配置（优化后）
- ✅ API 代理到后端
- ✅ WebSocket 支持
- ✅ Vue Router history 模式支持
- ✅ 优化的静态资源缓存
- ✅ Gzip 压缩

## 更新前端后

每次更新前端代码后：
1. 本地执行 `npm run build`
2. 上传 `dist` 目录内容到 `/www/wwwroot/www.tht-lab.com.cn/`
3. 不需要重启 Nginx（静态文件）

