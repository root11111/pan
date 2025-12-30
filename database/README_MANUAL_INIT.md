# 数据库手动初始化说明

## 功能说明

`DatabaseInitializer` 是一个独立的工具类，可以手动执行SQL脚本，不会在应用启动时自动执行。

## 使用方法

### 方式一：在IDE中运行

1. 打开 `backend/src/main/java/com/zht/util/DatabaseInitializer.java`
2. 右键点击 `main` 方法
3. 选择 "Run 'DatabaseInitializer.main()'"

### 方式二：命令行运行

```bash
cd backend
mvn compile exec:java -Dexec.mainClass="com.zht.util.DatabaseInitializer"
```

### 方式三：打包后运行

```bash
cd backend
mvn package
java -cp target/classes:target/lib/* com.zht.util.DatabaseInitializer
```

## 配置说明

在 `DatabaseInitializer.java` 中可以修改以下配置：

```java
// 数据库配置
private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/zht_lab?...";
private static final String DATABASE_USERNAME = "root";
private static final String DATABASE_PASSWORD = "your_password";

// SQL文件基础路径
private static final String BASE_PATH = "D:/workspace/pan/";

// 要执行的SQL文件列表
private static final String[] SQL_FILES = {
    "database/init.sql",
    "database/admin_init.sql"
};
```

## 执行的SQL文件

系统会按顺序执行以下SQL文件：

1. `database/init.sql` - 前端数据初始化
2. `database/admin_init.sql` - 后台管理系统初始化

## 添加新的SQL文件

修改 `SQL_FILES` 数组：

```java
private static final String[] SQL_FILES = {
    "database/init.sql",
    "database/admin_init.sql",
    "database/your_new_file.sql"  // 添加新文件
};
```

## 输出示例

运行后会看到类似输出：

```
========== 开始初始化数据库 ==========
数据库连接: jdbc:mysql://localhost:3306/zht_lab?...
SQL文件基础路径: D:/workspace/pan/

执行SQL文件: D:/workspace/pan/database/init.sql
数据库创建/检查完成: zht_lab
切换数据库: USE zht_lab;
SQL执行统计 - 成功: 50, 跳过: 10, 总计: 60
SQL文件执行完成: D:/workspace/pan/database/init.sql

执行SQL文件: D:/workspace/pan/database/admin_init.sql
SQL执行统计 - 成功: 5, 跳过: 2, 总计: 7
SQL文件执行完成: D:/workspace/pan/database/admin_init.sql

========== 数据库初始化完成！ ==========
```

## 注意事项

1. **数据库连接**：确保数据库服务已启动
2. **文件路径**：确保SQL文件路径正确
3. **文件编码**：SQL文件必须是 UTF-8 编码
4. **重复执行**：已存在的数据会被更新（使用 `ON DUPLICATE KEY UPDATE`）
5. **错误处理**：SQL执行失败会显示错误信息，但不会中断程序

## 与其他方法的区别

- **手动执行**：需要手动运行 `main` 方法
- **不会自动执行**：应用启动时不会自动执行SQL
- **灵活控制**：可以随时执行，不受应用启动影响

