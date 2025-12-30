-- ============================================
-- 关于我们相关表创建脚本
-- ============================================

-- 1. 创建企业文化表
CREATE TABLE IF NOT EXISTS `corporate_culture` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` VARCHAR(100) NOT NULL COMMENT '标题，如：质量方针、愿景等',
  `description` TEXT DEFAULT NULL COMMENT '描述',
  `sort_order` INT NOT NULL DEFAULT 0 COMMENT '排序',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='企业文化表';

-- 2. 确保企业优势表存在（如果不存在则创建）
CREATE TABLE IF NOT EXISTS `enterprise_advantage` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` VARCHAR(200) NOT NULL COMMENT '标题',
  `description` TEXT DEFAULT NULL COMMENT '描述',
  `icon` VARCHAR(500) DEFAULT NULL COMMENT '图标',
  `sort_order` INT NOT NULL DEFAULT 0 COMMENT '排序',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='企业优势表';

-- 3. 插入默认企业文化数据
INSERT INTO `corporate_culture` (`title`, `description`, `sort_order`) VALUES
('质量方针', '科学、公正、准确、诚信', 1),
('愿景', '塑造国际检验认证知名品牌，为全球顾客提供公正、快捷、可靠的本地服务', 2),
('企业精神', '专业、专注、用心为您服务', 3),
('人本基石', '坚持奉行以人为本，把尊重知识，尊重人才的政策和策略思想落实到实处', 4),
('总体目标', '创办专业机构，确保优级质量，提供高效服务', 5),
('坚持原则', '坚持客户为本的服务态度，坚持公平公正的检测态度，坚持独立的认证态度', 6) AS new
ON DUPLICATE KEY UPDATE 
  `title` = new.`title`,
  `description` = new.`description`,
  `sort_order` = new.`sort_order`;

-- 4. 插入默认企业优势数据（如果表中没有数据则插入）
INSERT INTO `enterprise_advantage` (`title`, `description`, `icon`, `sort_order`) 
SELECT '专业的检测认证团队', 
       '拥有先进的检测设备和专业的测试人员，检测范围广、精准度高、周期短', 
       'upload/20231130161735.png', 
       1
WHERE NOT EXISTS (SELECT 1 FROM `enterprise_advantage` LIMIT 1);

INSERT INTO `enterprise_advantage` (`title`, `description`, `icon`, `sort_order`) 
SELECT '一对一客服专业服务', 
       '给客户提供专业知识咨询，客户资料统一专人管理，为产品整个生命周期提供一站式服务', 
       'upload/20240119150129.png', 
       2
WHERE NOT EXISTS (SELECT 1 FROM `enterprise_advantage` WHERE `title` = '一对一客服专业服务');

INSERT INTO `enterprise_advantage` (`title`, `description`, `icon`, `sort_order`) 
SELECT '客户满意的价格体系', 
       '透明化市场价格，相比同行更加优惠，采取市场化运作方式，超高检测能力，不一样的价格，超额的服务体验', 
       'upload/20240119150054.png', 
       3
WHERE NOT EXISTS (SELECT 1 FROM `enterprise_advantage` WHERE `title` = '客户满意的价格体系');

INSERT INTO `enterprise_advantage` (`title`, `description`, `icon`, `sort_order`) 
SELECT '优秀的服务体系', 
       '产品样品检测报告追踪，专业工程师为您提供高效方案，随时提供专业知识咨询，终身证书更新及维护', 
       'upload/20240119150032.png', 
       4
WHERE NOT EXISTS (SELECT 1 FROM `enterprise_advantage` WHERE `title` = '优秀的服务体系');

