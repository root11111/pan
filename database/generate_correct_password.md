# 生成正确的管理员密码哈希值

## 问题说明

从日志可以看到，前端加密后的密码与数据库中的密码不匹配。需要生成正确的密码哈希值。

## 解决方案

### 方法1：使用密码生成接口（推荐）

1. 启动后端服务
2. 访问接口：
   ```
   http://localhost:8082/api/admin/password/generate?password=admin123
   ```
3. 复制返回的哈希值
4. 执行SQL更新：
   ```sql
   UPDATE admin 
   SET password = '复制的哈希值'
   WHERE username = 'admin';
   ```

### 方法2：使用前端控制台生成

1. 打开浏览器控制台（F12）
2. 执行以下代码：
   ```javascript
   async function generatePassword(password) {
     const encoder = new TextEncoder()
     const data = encoder.encode(password)
     const hashBuffer = await crypto.subtle.digest('SHA-256', data)
     const hashArray = new Uint8Array(hashBuffer)
     let binaryString = ''
     for (let i = 0; i < hashArray.length; i++) {
       binaryString += String.fromCharCode(hashArray[i])
     }
     return btoa(binaryString)
   }
   
   generatePassword('admin123').then(hash => console.log('密码哈希值:', hash))
   ```
3. 复制输出的哈希值
4. 更新数据库

### 方法3：临时解决方案

根据日志，前端加密后的密码是：`JAvlGPq9JyTdtvBO6x2llnRI1+gxwIyPqCKAn3THIKk=`

可以临时使用这个值更新数据库：
```sql
UPDATE admin 
SET password = 'JAvlGPq9JyTdtvBO6x2llnRI1+gxwIyPqCKAn3THIKk='
WHERE username = 'admin';
```

但更好的方式是确保前端和后端使用相同的加密方式，然后使用后端生成的正确哈希值。

