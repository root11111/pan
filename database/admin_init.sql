-- ============================================
-- 后台管理系统数据库初始化脚本
-- ============================================

USE zht_lab;

-- 管理员表
CREATE TABLE IF NOT EXISTS admin (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(255) NOT NULL COMMENT '密码（SHA-256加密后Base64编码）',
    nickname VARCHAR(100) COMMENT '昵称',
    status INT DEFAULT 1 COMMENT '状态：0-禁用 1-启用',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='管理员表';

-- 聊天消息表
CREATE TABLE IF NOT EXISTS chat_message (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    from_user VARCHAR(100) NOT NULL COMMENT '发送者',
    to_user VARCHAR(100) NOT NULL COMMENT '接收者',
    content TEXT NOT NULL COMMENT '消息内容',
    type INT DEFAULT 0 COMMENT '类型：0-用户消息 1-管理员消息',
    status INT DEFAULT 0 COMMENT '状态：0-未读 1-已读',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_to_user (to_user),
    INDEX idx_from_user (from_user),
    INDEX idx_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='聊天消息表';

-- 插入默认管理员账号
-- 用户名：admin
-- 密码：admin123
-- 密码加密方式：SHA-256 + Base64
-- 注意：如果密码不正确，可以通过后端 PasswordUtil.encrypt("admin123") 重新生成
INSERT INTO admin (username, password, nickname, status) VALUES
('admin', 'jGl25bVBBBW96Qi9Te4V37Fnqchz/Eu4qB9vKrRIqRg=', '管理员', 1)
ON DUPLICATE KEY UPDATE 
    password = VALUES(password),
    nickname = VALUES(nickname);
