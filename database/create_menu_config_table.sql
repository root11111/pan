-- 创建菜单配置表
CREATE TABLE IF NOT EXISTS `menu_config` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `menu_key` VARCHAR(50) NOT NULL COMMENT '菜单键值，如：honor, certificate',
  `menu_name_cn` VARCHAR(100) NOT NULL COMMENT '中文名称',
  `menu_name_en` VARCHAR(100) NOT NULL COMMENT '英文名称',
  `enabled` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '是否启用：1-启用，0-禁用',
  `sort_order` INT NOT NULL DEFAULT 0 COMMENT '排序',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_menu_key` (`menu_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单配置表';

-- 插入默认菜单配置
INSERT INTO `menu_config` (`menu_key`, `menu_name_cn`, `menu_name_en`, `enabled`, `sort_order`) VALUES
('honor', '荣誉资质', 'Honor', 1, 1),
('certificate', '证书查询', 'Certificate Query', 1, 2)
ON DUPLICATE KEY UPDATE 
  `menu_name_cn` = VALUES(`menu_name_cn`),
  `menu_name_en` = VALUES(`menu_name_en`);

