# 数据库自动初始化说明

## 功能说明

后端应用启动时会自动执行 `database/` 目录下的SQL脚本文件，无需手动执行SQL。

## 配置说明

在 `backend/src/main/resources/application.yml` 中可以配置：

```yaml
database:
  init:
    enabled: true  # 是否启用自动初始化（默认：true）
    base-path: D:/workspace/pan/  # SQL文件基础路径（默认：D:/workspace/pan/）
```

## 执行的SQL文件

系统会按顺序执行以下SQL文件：

1. `database/init.sql` - 前端数据初始化
2. `database/admin_init.sql` - 后台管理系统初始化

## 工作原理

1. 应用启动时，`DatabaseInitializer` 类会自动运行
2. 读取配置的SQL文件路径
3. 解析SQL文件，按分号分割SQL语句
4. 逐个执行SQL语句
5. 自动跳过已存在的表和重复的数据（使用 `ON DUPLICATE KEY UPDATE`）

## 日志输出

启动时会看到类似日志：

```
========== 开始初始化数据库 ==========
数据库连接: jdbc:mysql://localhost:3306/zht_lab?...
SQL文件基础路径: D:/workspace/pan/
执行SQL文件: D:/workspace/pan/database/init.sql
SQL执行统计 - 成功: 50, 跳过: 10, 总计: 60
SQL文件执行完成: D:/workspace/pan/database/init.sql
执行SQL文件: D:/workspace/pan/database/admin_init.sql
SQL执行统计 - 成功: 5, 跳过: 2, 总计: 7
SQL文件执行完成: D:/workspace/pan/database/admin_init.sql
========== 数据库初始化完成！ ==========
```

## 添加新的SQL文件

如果需要添加新的SQL文件，修改 `DatabaseInitializer.java` 中的 `SQL_FILES` 数组：

```java
private static final String[] SQL_FILES = {
    "database/init.sql",
    "database/admin_init.sql",
    "database/your_new_file.sql"  // 添加新文件
};
```

## 禁用自动初始化

如果不想自动执行SQL，可以在 `application.yml` 中设置：

```yaml
database:
  init:
    enabled: false
```

## 注意事项

1. SQL文件路径必须是绝对路径或相对于配置的 `base-path`
2. SQL文件编码必须是 UTF-8
3. 每个SQL语句必须以分号 `;` 结尾
4. 系统会自动处理注释（`--` 和 `/* */`）
5. 重复执行不会报错，已存在的数据会被更新（使用 `ON DUPLICATE KEY UPDATE`）

## 错误处理

- 如果SQL文件不存在，会记录警告日志但不会中断启动
- 如果SQL执行失败，会记录错误日志但不会中断启动
- 已存在的表和重复的数据会被自动跳过

