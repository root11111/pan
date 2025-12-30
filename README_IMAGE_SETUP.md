# 图片配置说明

## 问题说明

数据库中的图片路径是相对路径（如 `upload/20231130161735.png`），前端需要将这些路径转换为完整的URL才能正确显示图片。

## 解决方案

### 1. 图片URL处理工具

已创建 `frontend/src/utils/image.js` 工具文件，提供以下功能：

- `getImageUrl(imagePath)`: 将数据库中的相对路径转换为完整URL
- `processImageUrls(data, fields)`: 批量处理数据中的图片字段

### 2. 前端组件更新

已更新以下组件使用图片URL处理工具：

- ✅ `Home.vue` - 首页（企业优势、实验室、荣誉资质、新闻）
- ✅ `Laboratory.vue` - 实验室展示页
- ✅ `News.vue` - 新闻列表页
- ✅ `NewsDetail.vue` - 新闻详情页
- ✅ `Honor.vue` - 荣誉资质页
- ✅ `Header.vue` - 头部组件（Logo）

### 3. 后端静态资源映射

后端已配置静态资源映射（`WebMvcConfig.java`）：

```java
registry.addResourceHandler("/uploads/**")
        .addResourceLocations("file:D:/workspace/pan/uploads/");
```

这意味着：
- 访问 `/api/uploads/20231130161735.png` 
- 实际读取文件 `D:/workspace/pan/uploads/20231130161735.png`

### 4. 图片路径转换逻辑

数据库路径格式：`upload/20231130161735.png`
转换后URL：`/api/uploads/20231130161735.png`

转换规则：
1. 如果路径以 `upload/` 开头，移除 `upload/` 前缀
2. 添加 `/api/uploads/` 前缀
3. 如果已经是完整URL（http/https），直接返回

## 下载图片

### 方式一：使用Python脚本（推荐）

```bash
cd scripts
python download_images.py
```

脚本会自动从原网站下载所有图片到 `D:/workspace/pan/uploads/` 目录。

### 方式二：手动下载

1. 访问原网站：http://www.zht-lab.cn/
2. 右键点击图片 → 另存为
3. 保存到 `D:/workspace/pan/uploads/` 目录
4. 保持文件名与数据库中的路径一致

### 方式三：通过后台管理上传

1. 登录后台管理系统
2. 进入相应的管理页面（公司信息、实验室、新闻等）
3. 使用图片上传功能上传图片
4. 系统会自动保存到 `uploads` 目录并更新数据库

## 图片列表

### 企业优势图标（4张）
- upload/20231130161735.png
- upload/20240119150129.png
- upload/20240119150054.png
- upload/20240119150032.png

### 实验室图片（8张）
- upload/20241217133436948_381613.jpg
- upload/20241217133436946_528812.jpg
- upload/20241217133436944_528811.jpg
- upload/20241217133436940_528810.jpg
- upload/20241217133436938_52889.jpg
- upload/20241217133436936_52888.jpg
- upload/20241217133436934_52887.jpg
- upload/20241217133436932_52886.jpg

### 新闻图片（8张）
- upload/20250610144522.jpg
- upload/20250609143908.png
- upload/20250606151148.jpg
- upload/20250605152329.png
- upload/20250604152354.png
- upload/20250603151544.jpg
- upload/20250530150503.png
- upload/20250529160958.png

### 荣誉资质图片（1张）
- upload/img/20241217105815.png

### 其他图片（1张）
- upload/20241224111919.jpg

**总计：22张图片**

## 验证图片配置

1. 启动后端服务（端口8082）
2. 启动前端服务（端口3001）
3. 访问首页，检查图片是否正常显示
4. 如果图片不显示，检查：
   - 图片文件是否存在于 `D:/workspace/pan/uploads/` 目录
   - 浏览器控制台是否有404错误
   - 后端日志是否有资源访问错误

## 注意事项

1. **文件路径**：确保图片文件路径与数据库中的路径一致
2. **文件权限**：确保后端有读取文件的权限
3. **文件大小**：建议图片大小不超过2MB
4. **文件格式**：支持 jpg, jpeg, png, gif 格式
5. **路径大小写**：Windows系统不区分大小写，但建议保持一致

