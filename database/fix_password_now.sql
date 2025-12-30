-- ============================================
-- 立即修复管理员密码
-- ============================================
-- 根据前端加密后的密码值更新数据库

USE zht_lab;

-- 使用前端加密后的密码哈希值更新
UPDATE admin 
SET password = 'JAvlGPq9JyTdtvBO6x2llnRI1+gxwIyPqCKAn3THIKk='
WHERE username = 'admin';

-- 验证
SELECT username, password, nickname FROM admin WHERE username = 'admin';

