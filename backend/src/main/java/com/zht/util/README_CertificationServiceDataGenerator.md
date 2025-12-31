# 认证服务数据生成工具使用说明

## 功能说明

这个工具类 `CertificationServiceDataGenerator` 用于：
1. 为每个国家/地区生成具体的认证服务项目
2. 从网络下载认证服务标志图片
3. 生成SQL导入语句，将认证服务数据导入数据库

## 数据结构

### 认证服务层级

```
认证服务 (certification_service表)
├── 按国家/地区分类
│   ├── 欧盟
│   │   ├── CE认证
│   │   ├── RoHS认证
│   │   ├── REACH认证
│   │   ├── WEEE认证
│   │   └── ErP指令
│   ├── 德国
│   │   ├── GS认证
│   │   └── VDE认证
│   ├── 美国
│   │   ├── FCC认证
│   │   ├── UL认证
│   │   ├── FDA认证
│   │   ├── Energy Star
│   │   └── CPSC认证
│   ├── 中国
│   │   ├── CCC认证
│   │   ├── CQC认证
│   │   ├── SRRC认证
│   │   └── 能效标识
│   └── ... 其他国家
├── 能效认证
│   ├── ErP能效认证
│   ├── Energy Star
│   └── 中国能效标识
├── 体系认证
│   ├── ISO 9001质量管理体系
│   ├── ISO 14001环境管理体系
│   └── ISO 45001职业健康安全管理体系
├── 可靠性测试
│   ├── 环境测试
│   ├── 寿命测试
│   └── 机械测试
├── 电池检测
│   ├── 电池安全测试
│   └── 电池性能测试
└── 化学检测
    ├── RoHS检测
    ├── REACH检测
    └── 有害物质检测
```

## 使用方法

### 1. 运行Main方法

```bash
# 在项目根目录下运行
cd backend
java -cp "target/classes:lib/*" com.zht.util.CertificationServiceDataGenerator
```

或者在IDE中直接运行 `CertificationServiceDataGenerator.main()` 方法。

### 2. 执行结果

运行后会生成：
- **图片文件**：保存在 `uploads/icon/certification_service/` 目录下
- **SQL文件**：`certification_service_import.sql`

### 3. 导入数据库

**重要：请先导入certification_category数据！**

```sql
-- 1. 备份现有数据（如果有）
CREATE TABLE certification_service_backup AS SELECT * FROM certification_service;

-- 2. 清空表并重置自增ID
TRUNCATE TABLE certification_service;
ALTER TABLE certification_service AUTO_INCREMENT = 1;

-- 3. 执行生成的SQL文件
SOURCE certification_service_import.sql;
-- 或者直接复制SQL内容执行
```

## 完整导入流程

### 步骤1：导入认证分类数据

```bash
# 运行认证分类导入工具
java -cp "target/classes:lib/*" com.zht.util.CertificationCategoryImporter
```

执行生成的SQL：
```sql
TRUNCATE TABLE certification_category;
ALTER TABLE certification_category AUTO_INCREMENT = 1;
SOURCE certification_category_import_fixed_ids.sql;
```

### 步骤2：导入认证服务数据

```bash
# 运行认证服务数据生成工具
java -cp "target/classes:lib/*" com.zht.util.CertificationServiceDataGenerator
```

执行生成的SQL：
```sql
TRUNCATE TABLE certification_service;
ALTER TABLE certification_service AUTO_INCREMENT = 1;
SOURCE certification_service_import.sql;
```

## 数据说明

### 已包含的认证服务

#### 欧洲认证
- **欧盟**：CE、RoHS、REACH、WEEE、ErP
- **德国**：GS、VDE
- **英国**：UKCA、BSI
- **其他欧洲国家**：CE认证

#### 美洲认证
- **美国**：FCC、UL、FDA、Energy Star、CPSC
- **加拿大**：IC、CSA
- **墨西哥**：NOM
- **巴西**：INMETRO
- **阿根廷**：IRAM

#### 亚洲认证
- **中国**：CCC、CQC、SRRC、能效标识
- **日本**：PSE、VCCI、JIS
- **韩国**：KC、KCC
- **印度**：BIS
- **新加坡**：IMDA
- **马来西亚**：SIRIM
- **俄罗斯**：EAC
- **沙特**：SASO
- **以色列**：SII

#### 其他分类
- **澳洲**：RCM
- **国际**：ISO
- **能效认证**：ErP、Energy Star、中国能效标识
- **体系认证**：ISO 9001、ISO 14001、ISO 45001
- **可靠性测试**：环境测试、寿命测试、机械测试
- **电池检测**：电池安全测试、电池性能测试
- **化学检测**：RoHS检测、REACH检测、有害物质检测

## 字段说明

### certification_service表字段

- `id`：主键，自增
- `name_cn`：中文名称
- `name_en`：英文名称
- `category`：分类名称（欧洲认证、美洲认证等）
- `category_id`：关联certification_category表的ID
- `parent_id`：父服务ID（0表示顶级服务）
- `region`：国家/地区（如"中国"、"美国"等）
- `summary_cn`：中文简介
- `summary_en`：英文简介
- `description_cn`：中文详细描述
- `description_en`：英文详细描述
- `image`：图片路径
- `sort_order`：排序
- `create_time`：创建时间
- `update_time`：更新时间

## 注意事项

1. **依赖关系**：必须先导入 `certification_category` 数据，因为 `certification_service` 的 `category_id` 字段关联分类表。

2. **图片URL**：代码中使用的图片URL来自Wikipedia，如果某些图片下载失败，可以：
   - 手动替换 `SERVICE_IMAGE_URLS` 中的URL
   - 或手动下载图片放到 `uploads/icon/certification_service/` 目录

3. **图片路径**：下载的图片保存在 `uploads/icon/certification_service/` 目录，SQL中的路径为 `icon/certification_service/xxx.png`。

4. **数据完整性**：当前代码包含了主要国家和地区的认证服务，如果需要添加更多：
   - 修改 `initCertificationServices()` 方法添加新的认证服务
   - 修改 `initServiceImageUrls()` 方法添加图片URL
   - 重新运行Main方法生成新的SQL文件

## 扩展说明

如果需要添加新的认证服务：

1. 在 `initCertificationServices()` 方法中添加新的 `CertificationServiceInfo` 对象
2. 在 `initServiceImageUrls()` 方法中添加图片URL（如果有）
3. 重新运行Main方法生成新的SQL文件

## 常见问题

**Q: 图片下载失败怎么办？**
A: 检查网络连接，或者手动下载图片放到 `uploads/icon/certification_service/` 目录下。

**Q: SQL执行失败，提示category_id不存在？**
A: 请先执行 `certification_category_import_fixed_ids.sql` 导入分类数据。

**Q: 如何修改认证服务的描述信息？**
A: 直接修改代码中 `CertificationServiceInfo` 对象的描述字段，然后重新生成SQL。

**Q: 如何添加新的国家/地区的认证服务？**
A: 在 `initCertificationServices()` 方法中添加新的国家/地区及其认证服务列表。

