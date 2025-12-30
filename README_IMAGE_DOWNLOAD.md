# 图片下载说明（Java版本）

## 功能说明

`ImageDownloader` 是一个Java工具类，可以从原网站下载所有需要的图片到本地 `uploads` 目录。

## 使用方法

### 方式一：在IDE中运行（推荐）

1. 打开 `backend/src/main/java/com/zht/util/ImageDownloader.java`
2. 右键点击 `main` 方法
3. 选择 "Run 'ImageDownloader.main()'"

### 方式二：命令行运行

```bash
cd backend
mvn compile exec:java -Dexec.mainClass="com.zht.util.ImageDownloader"
```

### 方式三：打包后运行

```bash
cd backend
mvn package
java -cp target/classes:target/lib/* com.zht.util.ImageDownloader
```

## 配置说明

在 `ImageDownloader.java` 中可以修改以下配置：

```java
// 原网站基础URL
private static final String BASE_URL = "http://www.zht-lab.cn/";

// 本地保存目录（根据实际情况修改）
private static final String SAVE_DIR = "D:/workspace/pan/uploads/";
```

## 下载的图片列表

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

## 输出示例

运行后会看到类似输出：

```
========== 开始下载图片 ==========
原网站: http://www.zht-lab.cn/
保存目录: D:/workspace/pan/uploads/
图片总数: 22

下载: upload/20231130161735.png ... ✓ 成功
下载: upload/20240119150129.png ... ✓ 成功
下载: upload/20240119150054.png ... ✓ 成功
...
========== 下载完成 ==========
成功: 22
失败: 0
```

## 功能特性

1. **自动创建目录**：如果保存目录不存在，会自动创建
2. **错误处理**：下载失败会显示错误信息，但不会中断程序
3. **进度显示**：实时显示下载进度和结果
4. **统计信息**：显示成功和失败的图片数量
5. **失败列表**：列出所有下载失败的图片

## 在其他地方调用

如果需要在其他地方调用下载功能：

```java
// 下载单个图片
boolean success = ImageDownloader.download(
    "http://www.zht-lab.cn/upload/xxx.jpg",
    "D:/workspace/pan/uploads/upload/xxx.jpg"
);

// 批量下载
ImageDownloader.DownloadResult result = ImageDownloader.downloadAll(
    "http://www.zht-lab.cn/",
    new String[]{"upload/xxx.jpg", "upload/yyy.png"},
    "D:/workspace/pan/uploads/"
);
System.out.println("成功: " + result.getSuccessCount());
System.out.println("失败: " + result.getFailCount());
```

## 注意事项

1. **网络连接**：确保可以访问原网站
2. **文件权限**：确保有写入文件的权限
3. **磁盘空间**：确保有足够的磁盘空间
4. **超时设置**：默认连接和读取超时都是10秒，可以根据需要修改
5. **文件覆盖**：如果文件已存在，会被覆盖

## 故障排除

### 问题1：下载失败，显示连接超时
- 检查网络连接
- 检查原网站是否可访问
- 增加超时时间

### 问题2：下载失败，显示权限错误
- 检查保存目录的写入权限
- 确保目录路径正确

### 问题3：部分图片下载失败
- 检查原网站是否还有这些图片
- 检查图片URL是否正确
- 查看错误信息了解具体原因

