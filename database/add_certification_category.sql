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

-- 为认证服务表添加新字段
ALTER TABLE certification_service 
ADD COLUMN IF NOT EXISTS category_id BIGINT COMMENT '分类ID，关联certification_category表' AFTER category,
ADD COLUMN IF NOT EXISTS parent_id BIGINT DEFAULT 0 COMMENT '父服务ID，用于子菜单，0表示顶级服务' AFTER category_id;

-- 添加索引
ALTER TABLE certification_service 
ADD INDEX IF NOT EXISTS idx_category_id (category_id),
ADD INDEX IF NOT EXISTS idx_parent_id (parent_id);

