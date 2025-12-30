-- ============================================
-- 首页配置相关表创建脚本
-- ============================================

-- 1. 创建首页配置表
CREATE TABLE IF NOT EXISTS `home_config` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `config_key` VARCHAR(50) NOT NULL COMMENT '配置键，如：banner_title, banner_subtitle等',
  `config_value_cn` TEXT DEFAULT NULL COMMENT '中文值',
  `config_value_en` TEXT DEFAULT NULL COMMENT '英文值',
  `config_type` VARCHAR(20) DEFAULT 'text' COMMENT '配置类型：text, image, json',
  `sort_order` INT NOT NULL DEFAULT 0 COMMENT '排序',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_config_key` (`config_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='首页配置表';

-- 2. 创建Banner价值观表
CREATE TABLE IF NOT EXISTS `banner_value` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` VARCHAR(100) NOT NULL COMMENT '标题',
  `description` TEXT DEFAULT NULL COMMENT '描述',
  `sort_order` INT NOT NULL DEFAULT 0 COMMENT '排序',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Banner价值观表';

-- 3. 插入默认首页配置数据
INSERT INTO `home_config` (`config_key`, `config_value_cn`, `config_value_en`, `config_type`, `sort_order`) VALUES
('banner_title', '独立且权威的综合性第三方检测认证机构', 'Independent and authoritative comprehensive third-party testing and certification organization', 'text', 1),
('banner_subtitle', 'Independent and authoritative comprehensive third-party testing and certification organization', 'Independent and authoritative comprehensive third-party testing and certification organization', 'text', 2),
('banner_desc', '产品检测/检验/认证/技术服务指导', 'Product Testing/Inspection/Certification/Technical Service Guidance', 'text', 3),
('advantage_intro', '广东中翰检测技术有限公司拥有独立的测试场地，先进的实验设备，专业的服务团队，对各种产品有着丰富的全球认证经验。能够帮助客户顺利获得通行各个国家和地区的安全认证。通过中翰的全球认证服务，厂商不仅可以避免送样到国外测试的困扰，还可以缩短认证周期，有效降低成本，从而保证产品顺利通过全球认证的审核。', 'Guangdong Zhonghan Testing Technology Co., Ltd. has independent testing sites, advanced laboratory equipment, and a professional service team with rich global certification experience for various products. We can help customers successfully obtain safety certifications that are valid in various countries and regions. Through Zhonghan\'s global certification services, manufacturers can not only avoid the trouble of sending samples to foreign testing, but also shorten the certification cycle, effectively reduce costs, and ensure that products successfully pass global certification audits.', 'text', 4),
('honor_intro', '作为独立且权威的第三方检测认证机构，依托强大的技术、人员及网络优势，持续拓展资质领域，已获得多家国内及国际权威机构的认可。业务范围已覆盖了消费品、工业品、纺织品、汽车等各个行业，赢得了客户和社会的信赖，并向社会各界提供更加优质高效的服务。', 'As an independent and authoritative third-party testing and certification organization, relying on strong technical, personnel and network advantages, we continue to expand our qualification fields and have been recognized by many domestic and international authoritative institutions. Our business scope has covered various industries such as consumer goods, industrial products, textiles, automobiles, etc., winning the trust of customers and society, and providing higher quality and more efficient services to all sectors of society.', 'text', 5) AS new
ON DUPLICATE KEY UPDATE 
  `config_value_cn` = new.`config_value_cn`,
  `config_value_en` = new.`config_value_en`,
  `config_type` = new.`config_type`,
  `sort_order` = new.`sort_order`;

-- 4. 插入默认Banner价值观数据
INSERT INTO `banner_value` (`title`, `description`, `sort_order`) VALUES
('客户为先', '以客户为中心，全力协助客户完成目标，建立长期稳定的合作关系', 1),
('专业为本', '将专业作为推动企业发展的根本动力，为客户提供高标准的专业服务', 2),
('团队协作', '强调团队的相互合作，发扬团队奋斗精神，提高效率，共同实现目标', 3),
('拥抱变化', '积极地适应事物的变化，主动学习和变革，推动技术和业务的创新', 4) AS new
ON DUPLICATE KEY UPDATE 
  `title` = new.`title`,
  `description` = new.`description`,
  `sort_order` = new.`sort_order`;

