-- 创建证书表
CREATE TABLE IF NOT EXISTS `certificate` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `certificate_no` VARCHAR(100) NOT NULL COMMENT '证书编号',
  `product_name` VARCHAR(200) NOT NULL COMMENT '产品名称',
  `certificate_type` VARCHAR(50) DEFAULT NULL COMMENT '证书类型',
  `issue_date` DATE DEFAULT NULL COMMENT '颁发日期',
  `expiry_date` DATE DEFAULT NULL COMMENT '有效期至',
  `status` VARCHAR(20) DEFAULT '有效' COMMENT '状态：有效、已过期、已撤销',
  `issuer` VARCHAR(200) DEFAULT NULL COMMENT '颁发机构',
  `description` TEXT DEFAULT NULL COMMENT '描述',
  `certificate_file` VARCHAR(500) DEFAULT NULL COMMENT '证书文件路径',
  `sort_order` INT NOT NULL DEFAULT 0 COMMENT '排序',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_certificate_no` (`certificate_no`),
  KEY `idx_product_name` (`product_name`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='证书表';

