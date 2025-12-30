-- ============================================
-- 重置管理员密码为 admin123
-- ============================================
-- 执行此脚本前，请先运行后端接口生成正确的密码哈希值
-- GET http://localhost:8082/api/admin/password/generate?password=admin123
-- 然后将返回的哈希值更新到下面的SQL中

USE zht_lab;

-- 方法1：直接更新密码（推荐）
-- 注意：请将下面的哈希值替换为通过接口生成的最新值
UPDATE admin 
SET password = 'jGl25bVBBBW96Qi9Te4V37Fnqchz/Eu4qB9vKrRIqRg='
WHERE username = 'admin';

-- 方法2：如果方法1不行，删除后重新插入
-- DELETE FROM admin WHERE username = 'admin';
-- INSERT INTO admin (username, password, nickname, status) VALUES
-- ('admin', '请替换为正确的哈希值', '管理员', 1);

-- 验证：查询当前密码哈希值
SELECT username, password, nickname FROM admin WHERE username = 'admin';

