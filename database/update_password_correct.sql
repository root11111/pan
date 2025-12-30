-- ============================================
-- 更新管理员密码为正确的哈希值
-- ============================================
-- 根据后端日志，正确的密码哈希值应该是后端重新加密后的值
-- 或者使用密码生成接口获取

USE zht_lab;

-- 方法1：使用后端生成的正确哈希值
-- 访问 http://localhost:8082/api/admin/password/generate?password=admin123
-- 复制返回的哈希值，然后执行下面的SQL

-- 临时方案：先使用后端重新加密后的值（从日志中看到的）
-- 注意：这个值可能不正确，最好通过接口生成
UPDATE admin 
SET password = '9qklFVmv0h6L7jpNM/f6nQmeda1KIUa4PrZ5n5UWWGs='
WHERE username = 'admin';

-- 验证
SELECT username, password FROM admin WHERE username = 'admin';

-- 如果上面的值不对，请执行以下步骤：
-- 1. 访问 http://localhost:8082/api/admin/password/generate?password=admin123
-- 2. 复制返回的哈希值
-- 3. 执行：UPDATE admin SET password = '复制的哈希值' WHERE username = 'admin';

