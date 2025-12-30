-- 初始化认证分类数据
-- 先创建表（如果不存在）

-- 创建认证分类表
CREATE TABLE IF NOT EXISTS certification_category (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name_cn VARCHAR(200) NOT NULL COMMENT '中文名称',
    name_en VARCHAR(200) COMMENT '英文名称',
    parent_id BIGINT DEFAULT 0 COMMENT '父分类ID，0表示顶级分类',
    sort_order INT DEFAULT 0 COMMENT '排序',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_parent_id (parent_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='认证分类表';

-- 清空现有数据（可选，如果需要重新导入）
-- TRUNCATE TABLE certification_category;

-- 一级分类：认证服务
INSERT INTO certification_category (id, name_cn, name_en, parent_id, sort_order) VALUES
(1, '认证服务', 'Certification Services', 0, 1);

-- 二级分类
INSERT INTO certification_category (id, name_cn, name_en, parent_id, sort_order) VALUES
(2, '欧洲认证', 'European Certification', 1, 1),
(3, '美洲认证', 'American Certification', 1, 2),
(4, '亚洲认证', 'Asian Certification', 1, 3),
(5, '澳洲认证', 'Australian Certification', 1, 4),
(6, '国际认证', 'International Certification', 1, 5),
(7, '能效认证', 'Energy Efficiency Certification', 1, 6),
(8, '体系认证', 'System Certification', 1, 7),
(9, '可靠性测试', 'Reliability Testing', 1, 8),
(10, '电池检测', 'Battery Testing', 1, 9),
(11, '化学检测', 'Chemical Testing', 1, 10);

-- 三级分类：欧洲认证
INSERT INTO certification_category (id, name_cn, name_en, parent_id, sort_order) VALUES
(12, '欧盟', 'European Union', 2, 1),
(13, '匈牙利', 'Hungary', 2, 2),
(14, '土耳其', 'Turkey', 2, 3),
(15, '爱尔兰', 'Ireland', 2, 4),
(16, '斯洛文尼亚', 'Slovenia', 2, 5),
(17, '乌克兰', 'Ukraine', 2, 6),
(18, '捷克', 'Czech Republic', 2, 7),
(19, '斯洛伐克', 'Slovakia', 2, 8),
(20, '希腊', 'Greece', 2, 9),
(21, '德国', 'Germany', 2, 10),
(22, '西班牙', 'Spain', 2, 11),
(23, '英国', 'United Kingdom', 2, 12);

-- 三级分类：美洲认证
INSERT INTO certification_category (id, name_cn, name_en, parent_id, sort_order) VALUES
(24, '美国', 'United States', 3, 1),
(25, '墨西哥', 'Mexico', 3, 2),
(26, '加拿大', 'Canada', 3, 3),
(27, '阿根廷', 'Argentina', 3, 4),
(28, '巴西', 'Brazil', 3, 5);

-- 三级分类：亚洲认证
INSERT INTO certification_category (id, name_cn, name_en, parent_id, sort_order) VALUES
(29, '中国', 'China', 4, 1),
(30, '日本', 'Japan', 4, 2),
(31, '韩国', 'South Korea', 4, 3),
(32, '沙特', 'Saudi Arabia', 4, 4),
(33, '俄罗斯', 'Russia', 4, 5),
(34, '印度', 'India', 4, 6),
(35, '新加坡', 'Singapore', 4, 7),
(36, '马来西亚', 'Malaysia', 4, 8),
(37, '以色列', 'Israel', 4, 9);

-- 三级分类：澳洲认证
INSERT INTO certification_category (id, name_cn, name_en, parent_id, sort_order) VALUES
(38, '澳洲', 'Australia', 5, 1);

-- 三级分类：国际认证
INSERT INTO certification_category (id, name_cn, name_en, parent_id, sort_order) VALUES
(39, '国际', 'International', 6, 1);
