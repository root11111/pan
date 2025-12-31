-- 认证分类数据导入SQL
-- 生成时间: 2025-12-30 20:14:20
-- 注意：请先清空表或确保ID从1开始，然后按顺序执行以下SQL

-- ============================================
-- 一级分类：认证服务
-- ============================================
INSERT INTO certification_category (name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
('认证服务', 'Certification Services', NULL, 0, 1, '2025-12-30 20:14:20', '2025-12-30 20:14:20');
SET @parent_id_1 = LAST_INSERT_ID();

-- ============================================
-- 二级分类
-- ============================================
INSERT INTO certification_category (name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
('欧洲认证', 'European Certification', 'icon/certification/欧洲认证.png', @parent_id_1, 1, '2025-12-30 20:14:20', '2025-12-30 20:14:20');
SET @parent_id_2 = LAST_INSERT_ID();
INSERT INTO certification_category (name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
('美洲认证', 'American Certification', 'icon/certification/美洲认证.png', @parent_id_1, 2, '2025-12-30 20:14:20', '2025-12-30 20:14:20');
SET @parent_id_3 = LAST_INSERT_ID();
INSERT INTO certification_category (name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
('亚洲认证', 'Asian Certification', 'icon/certification/亚洲认证.png', @parent_id_1, 3, '2025-12-30 20:14:20', '2025-12-30 20:14:20');
SET @parent_id_4 = LAST_INSERT_ID();
INSERT INTO certification_category (name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
('澳洲认证', 'Australian Certification', 'icon/certification/澳洲认证.png', @parent_id_1, 4, '2025-12-30 20:14:20', '2025-12-30 20:14:20');
SET @parent_id_5 = LAST_INSERT_ID();
INSERT INTO certification_category (name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
('国际认证', 'International Certification', 'icon/certification/国际认证.png', @parent_id_1, 5, '2025-12-30 20:14:20', '2025-12-30 20:14:20');
SET @parent_id_6 = LAST_INSERT_ID();
INSERT INTO certification_category (name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
('能效认证', 'Energy Efficiency Certification', 'icon/certification/能效认证.png', @parent_id_1, 6, '2025-12-30 20:14:20', '2025-12-30 20:14:20');
SET @parent_id_7 = LAST_INSERT_ID();
INSERT INTO certification_category (name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
('体系认证', 'System Certification', 'icon/certification/体系认证.png', @parent_id_1, 7, '2025-12-30 20:14:20', '2025-12-30 20:14:20');
SET @parent_id_8 = LAST_INSERT_ID();
INSERT INTO certification_category (name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
('可靠性测试', 'Reliability Testing', 'icon/certification/可靠性测试.png', @parent_id_1, 8, '2025-12-30 20:14:20', '2025-12-30 20:14:20');
SET @parent_id_9 = LAST_INSERT_ID();
INSERT INTO certification_category (name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
('电池检测', 'Battery Testing', 'icon/certification/电池检测.png', @parent_id_1, 9, '2025-12-30 20:14:20', '2025-12-30 20:14:20');
SET @parent_id_10 = LAST_INSERT_ID();
INSERT INTO certification_category (name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
('化学检测', 'Chemical Testing', 'icon/certification/化学检测.png', @parent_id_1, 10, '2025-12-30 20:14:20', '2025-12-30 20:14:20');
SET @parent_id_11 = LAST_INSERT_ID();

-- ============================================
-- 三级分类：欧洲认证 (@parent_id_2)
-- ============================================
INSERT INTO certification_category (name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
('欧盟', 'European Union', 'icon/certification/欧盟.png', @parent_id_2, 1, '2025-12-30 20:14:20', '2025-12-30 20:14:20');
INSERT INTO certification_category (name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
('匈牙利', 'Hungary', 'icon/certification/匈牙利.png', @parent_id_2, 2, '2025-12-30 20:14:20', '2025-12-30 20:14:20');
INSERT INTO certification_category (name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
('土耳其', 'Turkey', 'icon/certification/土耳其.png', @parent_id_2, 3, '2025-12-30 20:14:20', '2025-12-30 20:14:20');
INSERT INTO certification_category (name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
('爱尔兰', 'Ireland', 'icon/certification/爱尔兰.png', @parent_id_2, 4, '2025-12-30 20:14:20', '2025-12-30 20:14:20');
INSERT INTO certification_category (name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
('斯洛文尼亚', 'Slovenia', 'icon/certification/斯洛文尼亚.png', @parent_id_2, 5, '2025-12-30 20:14:20', '2025-12-30 20:14:20');
INSERT INTO certification_category (name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
('乌克兰', 'Ukraine', 'icon/certification/乌克兰.png', @parent_id_2, 6, '2025-12-30 20:14:20', '2025-12-30 20:14:20');
INSERT INTO certification_category (name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
('捷克', 'Czech Republic', 'icon/certification/捷克.png', @parent_id_2, 7, '2025-12-30 20:14:20', '2025-12-30 20:14:20');
INSERT INTO certification_category (name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
('斯洛伐克', 'Slovakia', 'icon/certification/斯洛伐克.png', @parent_id_2, 8, '2025-12-30 20:14:20', '2025-12-30 20:14:20');
INSERT INTO certification_category (name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
('希腊', 'Greece', 'icon/certification/希腊.png', @parent_id_2, 9, '2025-12-30 20:14:20', '2025-12-30 20:14:20');
INSERT INTO certification_category (name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
('德国', 'Germany', 'icon/certification/德国.png', @parent_id_2, 10, '2025-12-30 20:14:20', '2025-12-30 20:14:20');
INSERT INTO certification_category (name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
('西班牙', 'Spain', 'icon/certification/西班牙.png', @parent_id_2, 11, '2025-12-30 20:14:20', '2025-12-30 20:14:20');
INSERT INTO certification_category (name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
('英国', 'United Kingdom', 'icon/certification/英国.png', @parent_id_2, 12, '2025-12-30 20:14:20', '2025-12-30 20:14:20');

-- ============================================
-- 三级分类：美洲认证 (@parent_id_3)
-- ============================================
INSERT INTO certification_category (name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
('美国', 'United States', 'icon/certification/美国.png', @parent_id_3, 1, '2025-12-30 20:14:20', '2025-12-30 20:14:20');
INSERT INTO certification_category (name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
('墨西哥', 'Mexico', 'icon/certification/墨西哥.png', @parent_id_3, 2, '2025-12-30 20:14:20', '2025-12-30 20:14:20');
INSERT INTO certification_category (name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
('加拿大', 'Canada', 'icon/certification/加拿大.png', @parent_id_3, 3, '2025-12-30 20:14:20', '2025-12-30 20:14:20');
INSERT INTO certification_category (name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
('阿根廷', 'Argentina', 'icon/certification/阿根廷.png', @parent_id_3, 4, '2025-12-30 20:14:20', '2025-12-30 20:14:20');
INSERT INTO certification_category (name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
('巴西', 'Brazil', 'icon/certification/巴西.png', @parent_id_3, 5, '2025-12-30 20:14:20', '2025-12-30 20:14:20');

-- ============================================
-- 三级分类：亚洲认证 (@parent_id_4)
-- ============================================
INSERT INTO certification_category (name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
('中国', 'China', 'icon/certification/中国.png', @parent_id_4, 1, '2025-12-30 20:14:20', '2025-12-30 20:14:20');
INSERT INTO certification_category (name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
('日本', 'Japan', 'icon/certification/日本.png', @parent_id_4, 2, '2025-12-30 20:14:20', '2025-12-30 20:14:20');
INSERT INTO certification_category (name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
('韩国', 'South Korea', 'icon/certification/韩国.png', @parent_id_4, 3, '2025-12-30 20:14:20', '2025-12-30 20:14:20');
INSERT INTO certification_category (name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
('沙特', 'Saudi Arabia', 'icon/certification/沙特.png', @parent_id_4, 4, '2025-12-30 20:14:20', '2025-12-30 20:14:20');
INSERT INTO certification_category (name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
('俄罗斯', 'Russia', 'icon/certification/俄罗斯.png', @parent_id_4, 5, '2025-12-30 20:14:20', '2025-12-30 20:14:20');
INSERT INTO certification_category (name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
('印度', 'India', 'icon/certification/印度.png', @parent_id_4, 6, '2025-12-30 20:14:20', '2025-12-30 20:14:20');
INSERT INTO certification_category (name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
('新加坡', 'Singapore', 'icon/certification/新加坡.png', @parent_id_4, 7, '2025-12-30 20:14:20', '2025-12-30 20:14:20');
INSERT INTO certification_category (name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
('马来西亚', 'Malaysia', 'icon/certification/马来西亚.png', @parent_id_4, 8, '2025-12-30 20:14:20', '2025-12-30 20:14:20');
INSERT INTO certification_category (name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
('以色列', 'Israel', 'icon/certification/以色列.png', @parent_id_4, 9, '2025-12-30 20:14:20', '2025-12-30 20:14:20');

-- ============================================
-- 三级分类：澳洲认证 (@parent_id_5)
-- ============================================
INSERT INTO certification_category (name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
('澳洲', 'Australia', 'icon/certification/澳洲.png', @parent_id_5, 1, '2025-12-30 20:14:20', '2025-12-30 20:14:20');

-- ============================================
-- 三级分类：国际认证 (@parent_id_6)
-- ============================================
INSERT INTO certification_category (name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES
('国际', 'International', 'icon/certification/国际.png', @parent_id_6, 1, '2025-12-30 20:14:20', '2025-12-30 20:14:20');
