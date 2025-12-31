# 修复线上API地址问题

## 问题描述

线上环境访问后台管理页面时，出现 CORS 错误：
```
Access to XMLHttpRequest at 'http://localhost:8082/api/admin/honor/list' from origin 'https://tht-lab.com.cn' has been blocked by CORS policy
```

## 问题原因

前端代码中多处使用了硬编码的 `http://localhost:8082/api` 作为 API 基础URL的默认值。在构建生产版本时，如果没有设置环境变量，这些硬编码地址会被打包进代码，导致线上环境仍然请求 `localhost:8082`。

## 解决方案

已修改所有相关文件，使用环境判断：
- **开发环境** (`import.meta.env.DEV === true`)：使用 `http://localhost:8082/api`
- **生产环境** (`import.meta.env.DEV === false`)：使用相对路径 `/api`（通过 Nginx 代理）

### 修改的文件列表

1. `frontend/src/utils/image.js` - 图片URL处理工具
2. `frontend/src/views/admin/Honor.vue` - 荣誉资质管理
3. `frontend/src/views/admin/CertificationCategory.vue` - 认证分类管理
4. `frontend/src/views/admin/Certificate.vue` - 证书管理
5. `frontend/src/views/admin/EnterpriseAdvantage.vue` - 企业优势管理
6. `frontend/src/views/admin/HomeConfig.vue` - 首页配置管理
7. `frontend/src/views/admin/CorporateCulture.vue` - 企业文化管理
8. `frontend/src/views/admin/MenuConfig.vue` - 菜单配置管理
9. `frontend/src/views/admin/Certification.vue` - 认证服务管理

## 部署步骤

### 1. 重新构建前端项目

在本地开发环境执行：

```bash
cd frontend
npm run build
```

### 2. 上传构建文件

将 `frontend/dist` 目录中的所有文件上传到服务器的 `/www/wwwroot/tht-lab.com.cn/dist/` 目录。

### 3. 验证部署

1. 访问 `https://tht-lab.com.cn/admin/login` 登录后台
2. 点击"荣誉资质"菜单，应该能正常加载数据
3. 检查浏览器控制台，确认 API 请求地址为 `/api/...` 而不是 `http://localhost:8082/api/...`

## 技术说明

### 修改前
```javascript
const api = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || 'http://localhost:8082/api',
})
```

### 修改后
```javascript
const api = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || (import.meta.env.DEV ? 'http://localhost:8082/api' : '/api'),
})
```

这样修改后：
- **开发环境**：`npm run dev` 时，`import.meta.env.DEV` 为 `true`，使用 `http://localhost:8082/api`
- **生产环境**：`npm run build` 时，`import.meta.env.DEV` 为 `false`，使用 `/api`（相对路径）

## 注意事项

1. **Nginx 配置**：确保 Nginx 已正确配置 `/api/` 路径代理到后端服务（`http://127.0.0.1:8082`）
2. **后端服务**：确保后端服务在 `8082` 端口正常运行
3. **缓存问题**：如果修改后仍有问题，尝试清除浏览器缓存或使用无痕模式访问

## 验证 Nginx 配置

确保 Nginx 配置中包含以下内容：

```nginx
location /api/ {
    proxy_pass http://127.0.0.1:8082;
    proxy_set_header Host $host;
    proxy_set_header X-Real-IP $remote_addr;
    proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
    proxy_set_header X-Forwarded-Proto $scheme;
}
```

