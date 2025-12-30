# 认证服务数据导入工具使用说明

## 功能说明

`CertificationServiceImporter.java` 是一个用于从 Excel 文件导入认证服务数据到数据库的工具。

## 主要功能

1. **读取 Excel 文件**：从 `D:/workspace/pan/docs/检测技术有限公司.xlsx` 读取数据
2. **分类匹配**：根据第一列的分类名称，自动匹配到对应的分类ID（参考 `init_certification_category.sql`）
3. **图片下载**：自动识别图片URL列，下载图片到 `uploads/` 目录
4. **生成 SQL**：生成 INSERT SQL 语句，保存到 `database/import_certification_service_data.sql`
5. **直接导入**：可选择直接导入到数据库

## 使用方法

### 1. 确保依赖已添加

工具需要 Apache POI 库来读取 Excel 文件。已在 `pom.xml` 中添加：

```xml
<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi</artifactId>
    <version>5.2.5</version>
</dependency>
<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi-ooxml</artifactId>
    <version>5.2.5</version>
</dependency>
```

### 2. Excel 文件格式要求

Excel 文件应包含以下列（列名支持多种变体，工具会自动识别）：

- **第一列**：分类名称（必填）- 用于匹配分类ID
- **中文名称**：服务的中文名称（必填）
- **英文名称**：服务的英文名称（可选）
- **地区**：国家或地区（可选）
- **封面图片**：封面图片URL或路径（可选，如果是URL会自动下载）
- **中文简介**：中文简介（可选）
- **英文简介**：英文简介（可选）
- **中文描述**：中文描述内容（可选，如果是图片URL会自动下载）
- **英文描述**：英文描述内容（可选）
- **排序**：排序号（可选，默认为行号）

### 3. 运行工具

在 IDE 中直接运行 `CertificationServiceImporter.main()` 方法，或者使用命令行：

```bash
cd backend
mvn compile exec:java -Dexec.mainClass="com.zht.util.CertificationServiceImporter"
```

### 4. 执行流程

1. 工具会读取 Excel 文件的第一行作为表头
2. 从第二行开始读取数据
3. 根据第一列的分类名称，查找对应的分类ID
4. 如果列值是图片URL（以 http:// 或 https:// 开头，或以图片扩展名结尾），会自动下载到 `uploads/` 目录
5. 生成 SQL 语句并保存到 `database/import_certification_service_data.sql`
6. 询问是否直接导入数据库：
   - 输入 `y`：直接执行 SQL 导入数据库
   - 输入 `n`：只生成 SQL 文件，需要手动执行

## 分类映射

工具会根据 Excel 第一列的分类名称，自动匹配到以下分类ID：

### 二级分类
- 欧洲认证 → 2
- 美洲认证 → 3
- 亚洲认证 → 4
- 澳洲认证 → 5
- 国际认证 → 6
- 能效认证 → 7
- 体系认证 → 8
- 可靠性测试 → 9
- 电池检测 → 10
- 化学检测 → 11

### 三级分类（欧洲认证）
- 欧盟 → 12
- 匈牙利 → 13
- 土耳其 → 14
- 爱尔兰 → 15
- 斯洛文尼亚 → 16
- 乌克兰 → 17
- 捷克 → 18
- 斯洛伐克 → 19
- 希腊 → 20
- 德国 → 21
- 西班牙 → 22
- 英国 → 23

### 三级分类（美洲认证）
- 美国 → 24
- 墨西哥 → 25
- 加拿大 → 26
- 阿根廷 → 27
- 巴西 → 28

### 三级分类（亚洲认证）
- 中国 → 29
- 日本 → 30
- 韩国 → 31
- 沙特 → 32
- 俄罗斯 → 33
- 印度 → 34
- 新加坡 → 35
- 马来西亚 → 36
- 以色列 → 37

### 三级分类（其他）
- 澳洲 → 38
- 国际 → 39

## 注意事项

1. **Excel 文件路径**：确保文件位于 `D:/workspace/pan/docs/检测技术有限公司.xlsx`
2. **图片下载**：如果图片URL无法访问，会跳过下载，但会记录错误信息
3. **分类匹配**：如果分类名称无法匹配，该行数据会被跳过，并输出警告信息
4. **数据库配置**：如果需要直接导入，请确保数据库配置正确（在代码中修改 `DB_URL`、`DB_USER`、`DB_PASSWORD`）
5. **数据验证**：工具会跳过没有中文名称的行

## 输出文件

- **SQL 文件**：`database/import_certification_service_data.sql`
- **下载的图片**：`uploads/` 目录

## 示例 SQL 输出

```sql
INSERT INTO certification_service 
(name_cn, name_en, category_id, region, summary_cn, summary_en, image, description_cn, description_en, sort_order, parent_id, create_time, update_time) 
VALUES 
('CE认证', 'CE Certification', 12, '欧盟', 'CE认证简介', 'CE Certification Summary', '/uploads/xxx.jpg', '详细描述', 'Detailed Description', 1, 0, NOW(), NOW());
```

## 故障排除

1. **找不到分类**：检查 Excel 第一列的分类名称是否与分类映射表中的名称匹配
2. **图片下载失败**：检查网络连接和图片URL是否有效
3. **数据库连接失败**：检查数据库配置和连接信息
4. **Excel 读取失败**：确保文件格式为 `.xlsx`，且文件未被其他程序占用

