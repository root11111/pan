# 网页内容抓取和数据库初始化工具使用说明

## 工具说明

本项目提供了三个主要工具来抓取参考网站的内容并初始化数据库：

1. **WebContentScraper.java** - 网页内容抓取工具
2. **ImageDownloader.java** - 图片下载工具
3. **DatabaseInitializer.java** - 数据库初始化工具
4. **ImagePathFixer.java** - 图片路径修复工具

## 使用步骤

### 第一步：抓取网页内容

运行 `WebContentScraper.java` 的 `main` 方法，它会：
- 访问所有配置的页面（首页、关于我们、实验室、荣誉资质、新闻、联系我们等）
- 提取页面内容和图片URL
- 生成SQL脚本文件到 `database/` 目录
- 生成图片列表文件 `database/images_to_download.txt`

**执行命令：**
```bash
cd backend/src/main/java
javac -cp ".;path/to/jsoup-1.17.2.jar" com/zht/util/WebContentScraper.java
java -cp ".;path/to/jsoup-1.17.2.jar" com.zht.util.WebContentScraper
```

或者在IDE中直接运行 `WebContentScraper.main()`。

**生成的文件：**
- `database/home_data.sql` - 首页数据
- `database/about_data.sql` - 关于我们数据
- `database/laboratory_data.sql` - 实验室数据
- `database/honor_data.sql` - 荣誉资质数据
- `database/news_data.sql` - 新闻数据
- `database/contact_data.sql` - 联系我们数据
- `database/images_to_download.txt` - 图片下载列表
- `database/images_list.sql` - 图片列表SQL注释

### 第二步：修复图片路径（可选）

如果之前有图片下载到了错误的位置，运行 `ImagePathFixer.java`：

```bash
java com.zht.util.ImagePathFixer
```

### 第三步：下载图片

运行 `ImageDownloader.java` 的 `main` 方法，它会：
- 自动执行 `ImagePathFixer` 修复图片路径
- 从 `database/images_to_download.txt` 读取图片URL列表
- 删除旧的图片文件
- 下载所有新图片到 `D:/workspace/pan/uploads/` 目录

**执行命令：**
```bash
java com.zht.util.ImageDownloader
```

### 第四步：初始化数据库

运行 `DatabaseInitializer.java` 的 `main` 方法，它会：
- 清除旧数据（保留admin用户）
- 执行所有SQL脚本文件（包括WebContentScraper生成的）
- 初始化数据库结构和数据

**执行命令：**
```bash
java com.zht.util.DatabaseInitializer
```

## 配置说明

### WebContentScraper 配置

在 `WebContentScraper.java` 中配置要抓取的页面：

```java
private static final Map<String, PageConfig> PAGE_CONFIGS = new LinkedHashMap<>();
```

### ImageDownloader 配置

在 `ImageDownloader.java` 中配置：
- `SAVE_DIR` - 图片保存目录（默认：`D:/workspace/pan/uploads/`）
- `IMAGE_LIST_FILE` - 图片列表文件路径（默认：`D:/workspace/pan/database/images_to_download.txt`）

### DatabaseInitializer 配置

在 `DatabaseInitializer.java` 中配置：
- `DATABASE_URL` - 数据库连接URL
- `DATABASE_USERNAME` - 数据库用户名
- `DATABASE_PASSWORD` - 数据库密码
- `BASE_PATH` - SQL文件基础路径（默认：`D:/workspace/pan/`）

## 注意事项

1. **执行顺序**：建议按照上述步骤顺序执行
2. **数据备份**：执行数据库初始化前，建议备份现有数据
3. **网络连接**：抓取网页内容需要网络连接
4. **图片路径**：确保图片路径配置正确，前端和后端都需要正确映射
5. **SQL文件**：如果某些SQL文件不存在，DatabaseInitializer会自动跳过

## 参考网站

- 首页：http://www.ltt-lab.com.cn/
- 关于我们：http://www.zht-lab.cn/Aboutus.aspx?ClassID=2
- 实验室展示：http://www.zht-lab.cn/Product.aspx
- 荣誉资质：http://www.zht-lab.cn/PicList.aspx?ClassID=27
- 证书查询：http://www.zht-lab.cn/query.aspx?ClassID=36
- 新闻资讯：http://www.zht-lab.cn/News.aspx?ClassID=14
- 在线留言：http://www.zht-lab.cn/Message.aspx?ClassID=22
- 联系我们：http://www.zht-lab.cn/Contact.aspx?ClassID=23

## 后台管理

所有抓取的内容都可以通过后台管理系统进行编辑：
- 公司信息管理
- 企业优势管理
- 实验室管理
- 认证服务管理
- 新闻管理
- 荣誉资质管理
- 留言管理

