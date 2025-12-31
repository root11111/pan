# 认证分类数据导入工具使用说明

## 功能说明

这个工具类 `CertificationCategoryImporter` 用于：
1. 从网络下载认证分类相关的图片
2. 生成SQL导入语句，将认证分类数据导入数据库

## 使用方法

### 1. 运行Main方法下载图片并生成SQL

```bash
# 在项目根目录下运行
cd backend
java -cp "target/classes:lib/*" com.zht.util.CertificationCategoryImporter
```

或者在IDE中直接运行 `CertificationCategoryImporter.main()` 方法。

### 2. 执行结果

运行后会生成：
- **图片文件**：保存在 `uploads/icon/certification/` 目录下
- **SQL文件1**：`certification_category_import.sql`（使用LAST_INSERT_ID版本）
- **SQL文件2**：`certification_category_import_fixed_ids.sql`（固定ID版本，**推荐使用**）

### 3. 导入数据库

**推荐使用固定ID版本的SQL文件**，步骤如下：

```sql
-- 1. 备份现有数据（如果有）
CREATE TABLE certification_category_backup AS SELECT * FROM certification_category;

-- 2. 清空表并重置自增ID
TRUNCATE TABLE certification_category;
ALTER TABLE certification_category AUTO_INCREMENT = 1;

-- 3. 执行生成的SQL文件
SOURCE certification_category_import_fixed_ids.sql;
-- 或者直接复制SQL内容执行
```

## 数据结构说明

### 分类层级结构

```
认证服务 (ID=1, parent_id=0)
├── 欧洲认证 (ID=2, parent_id=1)
│   ├── 欧盟 (ID=12, parent_id=2)
│   ├── 匈牙利 (ID=13, parent_id=2)
│   ├── 土耳其 (ID=14, parent_id=2)
│   ├── 爱尔兰 (ID=15, parent_id=2)
│   ├── 斯洛文尼亚 (ID=16, parent_id=2)
│   ├── 乌克兰 (ID=17, parent_id=2)
│   ├── 捷克 (ID=18, parent_id=2)
│   ├── 斯洛伐克 (ID=19, parent_id=2)
│   ├── 希腊 (ID=20, parent_id=2)
│   ├── 德国 (ID=21, parent_id=2)
│   ├── 西班牙 (ID=22, parent_id=2)
│   └── 英国 (ID=23, parent_id=2)
├── 美洲认证 (ID=3, parent_id=1)
│   ├── 美国 (ID=24, parent_id=3)
│   ├── 墨西哥 (ID=25, parent_id=3)
│   ├── 加拿大 (ID=26, parent_id=3)
│   ├── 阿根廷 (ID=27, parent_id=3)
│   └── 巴西 (ID=28, parent_id=3)
├── 亚洲认证 (ID=4, parent_id=1)
│   ├── 中国 (ID=29, parent_id=4)
│   ├── 日本 (ID=30, parent_id=4)
│   ├── 韩国 (ID=31, parent_id=4)
│   ├── 沙特 (ID=32, parent_id=4)
│   ├── 俄罗斯 (ID=33, parent_id=4)
│   ├── 印度 (ID=34, parent_id=4)
│   ├── 新加坡 (ID=35, parent_id=4)
│   ├── 马来西亚 (ID=36, parent_id=4)
│   └── 以色列 (ID=37, parent_id=4)
├── 澳洲认证 (ID=5, parent_id=1)
│   └── 澳洲 (ID=38, parent_id=5)
├── 国际认证 (ID=6, parent_id=1)
│   └── 国际 (ID=39, parent_id=6)
├── 能效认证 (ID=7, parent_id=1)
├── 体系认证 (ID=8, parent_id=1)
├── 可靠性测试 (ID=9, parent_id=1)
├── 电池检测 (ID=10, parent_id=1)
└── 化学检测 (ID=11, parent_id=1)
```

## 注意事项

1. **图片URL**：代码中使用的图片URL来自Wikipedia，如果某些图片下载失败，可以手动替换 `IMAGE_URLS` 中的URL。

2. **图片路径**：下载的图片保存在 `uploads/icon/certification/` 目录，SQL中的路径为 `icon/certification/xxx.png`。

3. **数据库字段**：确保数据库表 `certification_category` 包含以下字段：
   - `id` (主键，自增)
   - `name_cn` (中文名称)
   - `name_en` (英文名称)
   - `icon` (图标路径)
   - `parent_id` (父分类ID)
   - `sort_order` (排序)
   - `create_time` (创建时间)
   - `update_time` (更新时间)

4. **自定义图片**：如果需要使用自定义图片，可以：
   - 修改 `IMAGE_URLS` 中的URL
   - 或者下载后手动替换 `uploads/icon/certification/` 目录下的图片文件

## 扩展说明

如果需要添加新的认证分类或修改现有分类：

1. 修改 `initImageUrls()` 方法添加图片URL
2. 修改 `initDescriptions()` 方法添加描述信息
3. 修改 `generateSQLWithFixedIds()` 方法添加SQL插入语句
4. 重新运行Main方法生成新的SQL文件

## 常见问题

**Q: 图片下载失败怎么办？**
A: 检查网络连接，或者手动下载图片放到 `uploads/icon/certification/` 目录下。

**Q: SQL执行失败怎么办？**
A: 检查数据库表结构是否正确，确保所有字段都存在。如果表中有数据，先备份再清空。

**Q: 如何修改分类名称或顺序？**
A: 直接修改SQL文件中的相应值，或者修改代码中的 `CategoryInfo` 列表。

