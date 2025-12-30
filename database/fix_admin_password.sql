-- ============================================
-- 修复管理员密码脚本
-- ============================================
-- 如果登录时提示密码错误，执行此脚本更新密码

USE zht_lab;

-- 更新管理员密码为 admin123
-- 密码加密方式：SHA-256 + Base64
-- 注意：这个哈希值是通过后端 PasswordUtil.encrypt("admin123") 生成的
-- 如果仍然不正确，请运行后端的 PasswordTest.java 生成新的哈希值

UPDATE admin 
SET password = 'jGl25bVBBBW96Qi9Te4V37Fnqchz/Eu4qB9vKrRIqRg='
WHERE username = 'admin';

-- 如果上面的密码仍然不正确，可以尝试重新插入
-- 先删除再插入
DELETE FROM admin WHERE username = 'admin';

INSERT INTO admin (username, password, nickname, status) VALUES
('admin', 'jGl25bVBBBW96Qi9Te4V37Fnqchz/Eu4qB9vKrRIqRg=', '管理员', 1);

