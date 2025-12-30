-- 创建企业文化表
CREATE TABLE IF NOT EXISTS `corporate_culture` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` VARCHAR(100) NOT NULL COMMENT '标题，如：质量方针、愿景等',
  `description` TEXT DEFAULT NULL COMMENT '描述',
  `sort_order` INT NOT NULL DEFAULT 0 COMMENT '排序',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='企业文化表';

-- 插入默认企业文化数据
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

