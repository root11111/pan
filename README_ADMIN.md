# 后台管理系统使用说明

## 功能特性

### 1. 登录认证
- ✅ JWT Token 认证
- ✅ 密码 SHA-256 加密传输
- ✅ 路由守卫保护

### 2. 内容管理
- ✅ 公司信息管理（名称、描述、联系方式、Logo等）
- ✅ 实验室管理（增删改查）
- ✅ 新闻管理（增删改查、图片上传）
- ✅ 留言管理（查看、处理状态、删除）

### 3. 实时聊天
- ✅ WebSocket 实时通信
- ✅ 在线用户列表
- ✅ 聊天历史记录

### 4. 文件上传
- ✅ 单文件上传
- ✅ 多文件上传
- ✅ 图片预览

## 默认账号

- **用户名**: `admin`
- **密码**: `admin123`

## 数据库初始化

执行以下SQL脚本创建管理员表和聊天消息表：

```bash
# 执行管理后台相关表
mysql -u root -p zht_lab < database/admin_init.sql
```

或者手动执行 `database/admin_init.sql` 文件中的SQL语句。

## 启动说明

### 后端
```bash
cd backend
mvn clean install
mvn spring-boot:run
```

### 前端
```bash
cd frontend
npm install
npm run dev
```

### 访问管理后台
- 前端地址: http://localhost:3001
- 管理后台登录: http://localhost:3001/admin/login

## API 接口说明

### 认证接口
- `POST /api/admin/login` - 管理员登录
- `GET /api/admin/info` - 获取管理员信息

### 内容管理接口
- `GET /api/admin/company/info` - 获取公司信息
- `POST /api/admin/company/info` - 更新公司信息
- `GET /api/admin/laboratory/list` - 获取实验室列表
- `POST /api/admin/laboratory/save` - 保存实验室
- `DELETE /api/admin/laboratory/{id}` - 删除实验室
- `GET /api/admin/news/page` - 分页获取新闻
- `POST /api/admin/news/save` - 保存新闻
- `DELETE /api/admin/news/{id}` - 删除新闻
- `GET /api/admin/message/page` - 分页获取留言
- `PUT /api/admin/message/{id}/status` - 更新留言状态
- `DELETE /api/admin/message/{id}` - 删除留言

### 文件上传接口
- `POST /api/file/upload` - 单文件上传
- `POST /api/file/upload/multiple` - 多文件上传

### WebSocket 接口
- WebSocket 地址: `ws://localhost:8082/ws`
- 发送消息: `/app/send`
- 订阅消息: `/queue/messages/{userId}`

## 注意事项

1. **密码加密**: 前端使用 SHA-256 + Base64 加密密码后传输，后端使用相同方式验证
2. **Token 存储**: Token 存储在 localStorage 中，key 为 `admin_token`
3. **文件上传路径**: 默认上传到 `D:/workspace/pan/uploads/`，可在 `FileController` 中修改
4. **WebSocket**: 需要确保 WebSocket 连接正常，聊天功能才能使用

## 安全建议

1. 修改默认管理员密码
2. 定期更新 JWT Secret
3. 限制文件上传类型和大小
4. 添加操作日志记录
5. 实现 IP 白名单（可选）

