-- 认证分类数据导入SQL（固定ID版本）
-- 生成时间: 2025-12-30 20:14:20
-- 注意：请先清空表或使用TRUNCATE TABLE certification_category;
-- 然后设置AUTO_INCREMENT起始值：ALTER TABLE certification_category AUTO_INCREMENT = 1;

-- ============================================
-- 一级分类：认证服务 (ID=1)
-- ============================================
INSERT INTO certification_category (id, name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
(1, '认证服务', 'Certification Services', NULL, 0, 1, '2025-12-30 20:14:20', '2025-12-30 20:14:20');

-- ============================================
-- 二级分类 (ID=2-11)
-- ============================================
INSERT INTO certification_category (id, name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
(2, '欧洲认证', 'European Certification', 'icon/certification/欧洲认证.png', 1, 1, '2025-12-30 20:14:20', '2025-12-30 20:14:20');
INSERT INTO certification_category (id, name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
(3, '美洲认证', 'American Certification', 'icon/certification/美洲认证.png', 1, 2, '2025-12-30 20:14:20', '2025-12-30 20:14:20');
INSERT INTO certification_category (id, name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
(4, '亚洲认证', 'Asian Certification', 'icon/certification/亚洲认证.png', 1, 3, '2025-12-30 20:14:20', '2025-12-30 20:14:20');
INSERT INTO certification_category (id, name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
(5, '澳洲认证', 'Australian Certification', 'icon/certification/澳洲认证.png', 1, 4, '2025-12-30 20:14:20', '2025-12-30 20:14:20');
INSERT INTO certification_category (id, name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
(6, '国际认证', 'International Certification', 'icon/certification/国际认证.png', 1, 5, '2025-12-30 20:14:20', '2025-12-30 20:14:20');
INSERT INTO certification_category (id, name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
(7, '能效认证', 'Energy Efficiency Certification', 'icon/certification/能效认证.png', 1, 6, '2025-12-30 20:14:20', '2025-12-30 20:14:20');
INSERT INTO certification_category (id, name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
(8, '体系认证', 'System Certification', 'icon/certification/体系认证.png', 1, 7, '2025-12-30 20:14:20', '2025-12-30 20:14:20');
INSERT INTO certification_category (id, name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
(9, '可靠性测试', 'Reliability Testing', 'icon/certification/可靠性测试.png', 1, 8, '2025-12-30 20:14:20', '2025-12-30 20:14:20');
INSERT INTO certification_category (id, name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
(10, '电池检测', 'Battery Testing', 'icon/certification/电池检测.png', 1, 9, '2025-12-30 20:14:20', '2025-12-30 20:14:20');
INSERT INTO certification_category (id, name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
(11, '化学检测', 'Chemical Testing', 'icon/certification/化学检测.png', 1, 10, '2025-12-30 20:14:20', '2025-12-30 20:14:20');

-- ============================================
-- 三级分类：欧洲认证 (ID=12-23, parent_id=2)
-- ============================================
INSERT INTO certification_category (id, name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
(12, '欧盟', 'European Union', 'icon/certification/欧盟.png', 2, 1, '2025-12-30 20:14:20', '2025-12-30 20:14:20');
INSERT INTO certification_category (id, name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
(13, '匈牙利', 'Hungary', 'icon/certification/匈牙利.png', 2, 2, '2025-12-30 20:14:20', '2025-12-30 20:14:20');
INSERT INTO certification_category (id, name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
(14, '土耳其', 'Turkey', 'icon/certification/土耳其.png', 2, 3, '2025-12-30 20:14:20', '2025-12-30 20:14:20');
INSERT INTO certification_category (id, name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
(15, '爱尔兰', 'Ireland', 'icon/certification/爱尔兰.png', 2, 4, '2025-12-30 20:14:20', '2025-12-30 20:14:20');
INSERT INTO certification_category (id, name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
(16, '斯洛文尼亚', 'Slovenia', 'icon/certification/斯洛文尼亚.png', 2, 5, '2025-12-30 20:14:20', '2025-12-30 20:14:20');
INSERT INTO certification_category (id, name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
(17, '乌克兰', 'Ukraine', 'icon/certification/乌克兰.png', 2, 6, '2025-12-30 20:14:20', '2025-12-30 20:14:20');
INSERT INTO certification_category (id, name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
(18, '捷克', 'Czech Republic', 'icon/certification/捷克.png', 2, 7, '2025-12-30 20:14:20', '2025-12-30 20:14:20');
INSERT INTO certification_category (id, name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
(19, '斯洛伐克', 'Slovakia', 'icon/certification/斯洛伐克.png', 2, 8, '2025-12-30 20:14:20', '2025-12-30 20:14:20');
INSERT INTO certification_category (id, name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
(20, '希腊', 'Greece', 'icon/certification/希腊.png', 2, 9, '2025-12-30 20:14:20', '2025-12-30 20:14:20');
INSERT INTO certification_category (id, name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
(21, '德国', 'Germany', 'icon/certification/德国.png', 2, 10, '2025-12-30 20:14:20', '2025-12-30 20:14:20');
INSERT INTO certification_category (id, name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
(22, '西班牙', 'Spain', 'icon/certification/西班牙.png', 2, 11, '2025-12-30 20:14:20', '2025-12-30 20:14:20');
INSERT INTO certification_category (id, name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
(23, '英国', 'United Kingdom', 'icon/certification/英国.png', 2, 12, '2025-12-30 20:14:20', '2025-12-30 20:14:20');

-- ============================================
-- 三级分类：美洲认证 (ID=24-28, parent_id=3)
-- ============================================
INSERT INTO certification_category (id, name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
(24, '美国', 'United States', 'icon/certification/美国.png', 3, 1, '2025-12-30 20:14:20', '2025-12-30 20:14:20');
INSERT INTO certification_category (id, name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
(25, '墨西哥', 'Mexico', 'icon/certification/墨西哥.png', 3, 2, '2025-12-30 20:14:20', '2025-12-30 20:14:20');
INSERT INTO certification_category (id, name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
(26, '加拿大', 'Canada', 'icon/certification/加拿大.png', 3, 3, '2025-12-30 20:14:20', '2025-12-30 20:14:20');
INSERT INTO certification_category (id, name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
(27, '阿根廷', 'Argentina', 'icon/certification/阿根廷.png', 3, 4, '2025-12-30 20:14:20', '2025-12-30 20:14:20');
INSERT INTO certification_category (id, name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
(28, '巴西', 'Brazil', 'icon/certification/巴西.png', 3, 5, '2025-12-30 20:14:20', '2025-12-30 20:14:20');

-- ============================================
-- 三级分类：亚洲认证 (ID=29-37, parent_id=4)
-- ============================================
INSERT INTO certification_category (id, name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
(29, '中国', 'China', 'icon/certification/中国.png', 4, 1, '2025-12-30 20:14:20', '2025-12-30 20:14:20');
INSERT INTO certification_category (id, name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
(30, '日本', 'Japan', 'icon/certification/日本.png', 4, 2, '2025-12-30 20:14:20', '2025-12-30 20:14:20');
INSERT INTO certification_category (id, name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
(31, '韩国', 'South Korea', 'icon/certification/韩国.png', 4, 3, '2025-12-30 20:14:20', '2025-12-30 20:14:20');
INSERT INTO certification_category (id, name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
(32, '沙特', 'Saudi Arabia', 'icon/certification/沙特.png', 4, 4, '2025-12-30 20:14:20', '2025-12-30 20:14:20');
INSERT INTO certification_category (id, name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
(33, '俄罗斯', 'Russia', 'icon/certification/俄罗斯.png', 4, 5, '2025-12-30 20:14:20', '2025-12-30 20:14:20');
INSERT INTO certification_category (id, name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
(34, '印度', 'India', 'icon/certification/印度.png', 4, 6, '2025-12-30 20:14:20', '2025-12-30 20:14:20');
INSERT INTO certification_category (id, name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
(35, '新加坡', 'Singapore', 'icon/certification/新加坡.png', 4, 7, '2025-12-30 20:14:20', '2025-12-30 20:14:20');
INSERT INTO certification_category (id, name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
(36, '马来西亚', 'Malaysia', 'icon/certification/马来西亚.png', 4, 8, '2025-12-30 20:14:20', '2025-12-30 20:14:20');
INSERT INTO certification_category (id, name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
(37, '以色列', 'Israel', 'icon/certification/以色列.png', 4, 9, '2025-12-30 20:14:20', '2025-12-30 20:14:20');

-- ============================================
-- 三级分类：澳洲认证 (ID=38, parent_id=5)
-- ============================================
INSERT INTO certification_category (id, name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
(38, '澳洲', 'Australia', 'icon/certification/澳洲.png', 5, 1, '2025-12-30 20:14:20', '2025-12-30 20:14:20');

-- ============================================
-- 三级分类：国际认证 (ID=39, parent_id=6)
-- ============================================
INSERT INTO certification_category (id, name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
(39, '国际', 'International', 'icon/certification/国际.png', 6, 1, '2025-12-30 20:14:20', '2025-12-30 20:14:20');
