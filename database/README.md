# 数据库初始化说明

## 文件说明

### 1. init.sql
前台网站的基础数据表，包括：
- company_info - 公司信息表
- laboratory - 实验室表
- certification_service - 认证服务表
- news - 新闻表
- honor - 荣誉资质表
- message - 留言表
- enterprise_advantage - 企业优势表

### 2. admin_init.sql
后台管理系统的数据表，包括：
- admin - 管理员表
- chat_message - 聊天消息表

### 3. update_company_info.sql
更新公司信息的SQL脚本（用于已存在的数据库）

## 执行顺序

### 首次安装
1. 先执行 `init.sql` 创建前台数据表
2. 再执行 `admin_init.sql` 创建后台管理表

### 执行方式

#### 方式一：命令行执行
```bash
# Windows
mysql -u root -p zht_lab < database/init.sql
mysql -u root -p zht_lab < database/admin_init.sql

# Linux/Mac
mysql -u root -p zht_lab < database/init.sql
mysql -u root -p zht_lab < database/admin_init.sql
```

#### 方式二：MySQL客户端执行
1. 打开 MySQL 客户端（如 Navicat、MySQL Workbench）
2. 选择数据库 `zht_lab`
3. 依次执行 `init.sql` 和 `admin_init.sql` 文件中的SQL语句

## 默认账号

### 管理员账号
- **用户名**: `admin`
- **密码**: `admin123`
- **密码加密**: SHA-256 + Base64

### 密码修改

如果需要修改管理员密码，可以使用以下方式：

1. **通过后端工具类生成**：
   - 运行 `PasswordGenerator.java` 生成新密码的哈希值
   - 更新数据库中的 password 字段

2. **直接SQL更新**：
   ```sql
   UPDATE admin 
   SET password = '新密码的SHA-256+Base64哈希值'
   WHERE username = 'admin';
   ```

## 注意事项

1. 确保数据库 `zht_lab` 已创建
2. 确保 MySQL 版本 >= 5.7（支持 utf8mb4）
3. 执行前请备份现有数据（如果数据库已存在）
4. 默认管理员密码建议首次登录后立即修改

