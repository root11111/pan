# 认证服务数据爬虫使用说明

## 功能说明

这个爬虫程序可以从指定网页抓取认证服务数据（包括名称、图片、描述等），并自动导入到数据库中。

## 使用方法

### 1. 准备工作

- 确保数据库连接配置正确（`application.yml` 或 `application.properties`）
- 确保上传目录存在：`D:/workspace/pan/uploads/`
- 确保项目依赖已安装（Jsoup等）

### 2. 运行爬虫

有两种方式运行：

#### 方式一：直接运行Main方法

1. 打开 `ScrapeCertificationServicesMain.java`
2. 右键点击 `main` 方法
3. 选择 "Run 'ScrapeCertificationServicesMain.main()'"

#### 方式二：使用Maven命令

```bash
cd backend
mvn spring-boot:run -Dspring-boot.run.main-class=com.zht.util.ScrapeCertificationServicesMain
```

### 3. 配置说明

在 `ScrapeCertificationServicesMain.java` 中可以修改以下参数：

- `url`: 要抓取的网页URL（默认：`http://www.zht-lab.cn/News.aspx?ClassID=37`）
- `categoryId`: 分类ID（欧洲认证为2）

### 4. 数据说明

程序会抓取以下信息：
- **服务名称**：从网页中提取的认证服务名称（如：CE认证、MEEI认证等）
- **描述内容**：服务的详细描述
- **图片**：如果网页中有图片，会自动下载到 `uploads` 目录

### 5. 去重机制

程序会自动检查数据库中是否已存在相同的服务（根据名称和分类ID），如果已存在则跳过，不会重复导入。

## 注意事项

1. **网络连接**：确保能够访问目标网站
2. **网页结构**：如果网页结构改变，可能需要调整解析逻辑
3. **图片下载**：图片会下载到本地，确保有足够的磁盘空间
4. **数据库权限**：确保数据库用户有插入数据的权限

## 故障排查

### 问题1：抓取不到数据

**可能原因**：
- 网页结构已改变
- 网络连接问题
- URL不正确

**解决方法**：
- 检查网页是否能正常访问
- 查看控制台输出的错误信息
- 可能需要调整 `WebScraper.java` 中的解析逻辑

### 问题2：图片下载失败

**可能原因**：
- 图片URL不正确
- 网络问题
- 磁盘空间不足

**解决方法**：
- 检查 `uploads` 目录是否存在且有写权限
- 检查网络连接
- 查看控制台错误信息

### 问题3：数据库保存失败

**可能原因**：
- 数据库连接配置错误
- 表结构不匹配
- 数据格式问题

**解决方法**：
- 检查数据库连接配置
- 检查 `certification_service` 表结构
- 查看控制台错误信息

## 扩展说明

如果需要抓取其他页面的数据，可以：

1. 修改 `ScrapeCertificationServicesMain.java` 中的 `url` 和 `categoryId`
2. 如果网页结构不同，可能需要调整 `WebScraper.java` 中的解析逻辑
3. 可以添加多个URL的批量抓取功能

## 代码结构

- `WebScraper.java`: 核心爬虫逻辑
- `ScrapeCertificationServicesMain.java`: 主程序入口

