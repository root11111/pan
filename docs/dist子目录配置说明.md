# dist 子目录配置说明

## 目录结构

前端构建后的文件在：
```
/www/wwwroot/www.tht-lab.com.cn/
└── dist/                    # Vue 构建后的文件
    ├── index.html          # 必须存在
    ├── assets/             # JS、CSS 等资源
    │   ├── index-xxx.js
    │   └── index-xxx.css
    └── ...                 # 其他静态文件
```

## Nginx 配置

### root 配置

由于 `dist` 目录在 `/www/wwwroot/www.tht-lab.com.cn/dist`，Nginx 配置中的 `root` 需要指向 `dist` 目录：

```nginx
root /www/wwwroot/www.tht-lab.com.cn/dist;
```

### 完整配置示例

参考 `docs/宝塔Nginx配置示例.conf` 或 `docs/宝塔Nginx配置示例-修复版.conf`

## 验证步骤

### 1. 检查文件是否存在

```bash
# 检查 index.html
ls -la /www/wwwroot/www.tht-lab.com.cn/dist/index.html

# 检查目录内容
ls -la /www/wwwroot/www.tht-lab.com.cn/dist/
```

### 2. 检查文件权限

```bash
chown -R www:www /www/wwwroot/www.tht-lab.com.cn/dist
chmod -R 755 /www/wwwroot/www.tht-lab.com.cn/dist
chmod 644 /www/wwwroot/www.tht-lab.com.cn/dist/index.html
```

### 3. 更新 Nginx 配置

1. 登录宝塔面板
2. 进入 `网站` → `www.tht-lab.com.cn` → `设置` → `配置文件`
3. 找到 `root` 配置行，修改为：
   ```nginx
   root /www/wwwroot/www.tht-lab.com.cn/dist;
   ```
4. 保存配置

### 4. 测试配置

```bash
# 测试 Nginx 配置语法
nginx -t
```

### 5. 重载 Nginx

在宝塔面板中：
- 点击 `网站` → `www.tht-lab.com.cn` → `设置` → `重载配置`

或者使用命令：
```bash
/etc/init.d/nginx reload
```

## 常见问题

### 问题 1：仍然显示 403 Forbidden

**检查**：
- [ ] `index.html` 文件是否存在：`ls -la /www/wwwroot/www.tht-lab.com.cn/dist/index.html`
- [ ] 文件权限是否正确：`chmod 644 /www/wwwroot/www.tht-lab.com.cn/dist/index.html`
- [ ] 目录权限是否正确：`chmod 755 /www/wwwroot/www.tht-lab.com.cn/dist`
- [ ] Nginx 配置中的 `root` 是否指向 `dist` 目录

### 问题 2：静态资源 404

**检查**：
- [ ] `assets` 目录是否存在
- [ ] JS、CSS 文件是否存在
- [ ] 文件权限是否正确

### 问题 3：API 请求失败

**检查**：
- [ ] Nginx 配置中的 `/api/` location 是否正确
- [ ] 后端服务是否在 8082 端口运行
- [ ] 查看错误日志：`tail -f /www/wwwlogs/www.tht-lab.com.cn.error.log`

## 部署流程

### 每次更新前端后：

1. **本地构建**：
   ```bash
   cd frontend
   npm run build
   ```

2. **上传文件**：
   - 将 `frontend/dist` 目录中的所有文件上传到 `/www/wwwroot/www.tht-lab.com.cn/dist/`
   - 或者直接覆盖整个 `dist` 目录

3. **检查权限**：
   ```bash
   chown -R www:www /www/wwwroot/www.tht-lab.com.cn/dist
   chmod -R 755 /www/wwwroot/www.tht-lab.com.cn/dist
   ```

4. **验证访问**：
   - 访问 `https://www.tht-lab.com.cn/` 应该能看到网站首页
   - 不需要重启 Nginx（静态文件）

## 配置对比

### 如果 dist 在根目录下
```nginx
root /www/wwwroot/www.tht-lab.com.cn;
```

### 如果 dist 在子目录下（当前情况）
```nginx
root /www/wwwroot/www.tht-lab.com.cn/dist;
```

## 注意事项

1. **路径一致性**：确保 Nginx 配置中的 `root` 路径与实际文件路径一致
2. **文件权限**：确保 Nginx 用户（通常是 `www`）有读取权限
3. **目录权限**：确保目录有执行权限（755）
4. **文件权限**：确保文件有读取权限（644）

