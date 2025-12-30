-- 初始化认证分类数据（简化版本）
-- 根据HTML结构导入分类层级

-- 清空现有数据（可选，如果需要重新导入）
-- DELETE FROM certification_category;

-- 插入一级分类：认证服务
INSERT INTO certification_category (name_cn, name_en, parent_id, sort_order) VALUES
('认证服务', 'Certification Services', 0, 1);

-- 插入二级分类（有三级导航的）
INSERT INTO certification_category (name_cn, name_en, parent_id, sort_order) VALUES
('欧洲认证', 'European Certification', (SELECT id FROM certification_category WHERE name_cn = '认证服务' LIMIT 1), 1),
('美洲认证', 'American Certification', (SELECT id FROM certification_category WHERE name_cn = '认证服务' LIMIT 1), 2),
('亚洲认证', 'Asian Certification', (SELECT id FROM certification_category WHERE name_cn = '认证服务' LIMIT 1), 3),
('澳洲认证', 'Australian Certification', (SELECT id FROM certification_category WHERE name_cn = '认证服务' LIMIT 1), 4),
('国际认证', 'International Certification', (SELECT id FROM certification_category WHERE name_cn = '认证服务' LIMIT 1), 5),
('能效认证', 'Energy Efficiency Certification', (SELECT id FROM certification_category WHERE name_cn = '认证服务' LIMIT 1), 6),
('体系认证', 'System Certification', (SELECT id FROM certification_category WHERE name_cn = '认证服务' LIMIT 1), 7),
('可靠性测试', 'Reliability Testing', (SELECT id FROM certification_category WHERE name_cn = '认证服务' LIMIT 1), 8),
('电池检测', 'Battery Testing', (SELECT id FROM certification_category WHERE name_cn = '认证服务' LIMIT 1), 9),
('化学检测', 'Chemical Testing', (SELECT id FROM certification_category WHERE name_cn = '认证服务' LIMIT 1), 10);

-- 插入三级分类：欧洲认证的子分类
INSERT INTO certification_category (name_cn, name_en, parent_id, sort_order) VALUES
('欧盟', 'European Union', (SELECT id FROM certification_category WHERE name_cn = '欧洲认证' LIMIT 1), 1),
('匈牙利', 'Hungary', (SELECT id FROM certification_category WHERE name_cn = '欧洲认证' LIMIT 1), 2),
('土耳其', 'Turkey', (SELECT id FROM certification_category WHERE name_cn = '欧洲认证' LIMIT 1), 3),
('爱尔兰', 'Ireland', (SELECT id FROM certification_category WHERE name_cn = '欧洲认证' LIMIT 1), 4),
('斯洛文尼亚', 'Slovenia', (SELECT id FROM certification_category WHERE name_cn = '欧洲认证' LIMIT 1), 5),
('乌克兰', 'Ukraine', (SELECT id FROM certification_category WHERE name_cn = '欧洲认证' LIMIT 1), 6),
('捷克', 'Czech Republic', (SELECT id FROM certification_category WHERE name_cn = '欧洲认证' LIMIT 1), 7),
('斯洛伐克', 'Slovakia', (SELECT id FROM certification_category WHERE name_cn = '欧洲认证' LIMIT 1), 8),
('希腊', 'Greece', (SELECT id FROM certification_category WHERE name_cn = '欧洲认证' LIMIT 1), 9),
('德国', 'Germany', (SELECT id FROM certification_category WHERE name_cn = '欧洲认证' LIMIT 1), 10),
('西班牙', 'Spain', (SELECT id FROM certification_category WHERE name_cn = '欧洲认证' LIMIT 1), 11),
('英国', 'United Kingdom', (SELECT id FROM certification_category WHERE name_cn = '欧洲认证' LIMIT 1), 12);

-- 插入三级分类：美洲认证的子分类
INSERT INTO certification_category (name_cn, name_en, parent_id, sort_order) VALUES
('美国', 'United States', (SELECT id FROM certification_category WHERE name_cn = '美洲认证' LIMIT 1), 1),
('墨西哥', 'Mexico', (SELECT id FROM certification_category WHERE name_cn = '美洲认证' LIMIT 1), 2),
('加拿大', 'Canada', (SELECT id FROM certification_category WHERE name_cn = '美洲认证' LIMIT 1), 3),
('阿根廷', 'Argentina', (SELECT id FROM certification_category WHERE name_cn = '美洲认证' LIMIT 1), 4),
('巴西', 'Brazil', (SELECT id FROM certification_category WHERE name_cn = '美洲认证' LIMIT 1), 5);

-- 插入三级分类：亚洲认证的子分类
INSERT INTO certification_category (name_cn, name_en, parent_id, sort_order) VALUES
('中国', 'China', (SELECT id FROM certification_category WHERE name_cn = '亚洲认证' LIMIT 1), 1),
('日本', 'Japan', (SELECT id FROM certification_category WHERE name_cn = '亚洲认证' LIMIT 1), 2),
('韩国', 'South Korea', (SELECT id FROM certification_category WHERE name_cn = '亚洲认证' LIMIT 1), 3),
('沙特', 'Saudi Arabia', (SELECT id FROM certification_category WHERE name_cn = '亚洲认证' LIMIT 1), 4),
('俄罗斯', 'Russia', (SELECT id FROM certification_category WHERE name_cn = '亚洲认证' LIMIT 1), 5),
('印度', 'India', (SELECT id FROM certification_category WHERE name_cn = '亚洲认证' LIMIT 1), 6),
('新加坡', 'Singapore', (SELECT id FROM certification_category WHERE name_cn = '亚洲认证' LIMIT 1), 7),
('马来西亚', 'Malaysia', (SELECT id FROM certification_category WHERE name_cn = '亚洲认证' LIMIT 1), 8),
('以色列', 'Israel', (SELECT id FROM certification_category WHERE name_cn = '亚洲认证' LIMIT 1), 9);

-- 插入三级分类：澳洲认证的子分类
INSERT INTO certification_category (name_cn, name_en, parent_id, sort_order) VALUES
('澳洲', 'Australia', (SELECT id FROM certification_category WHERE name_cn = '澳洲认证' LIMIT 1), 1);

-- 插入三级分类：国际认证的子分类
INSERT INTO certification_category (name_cn, name_en, parent_id, sort_order) VALUES
('国际', 'International', (SELECT id FROM certification_category WHERE name_cn = '国际认证' LIMIT 1), 1);

