-- 为认证服务表添加新字段
-- 如果字段已存在，执行会报错，可以先检查表结构

-- 添加 category_id 字段
ALTER TABLE certification_service 
ADD COLUMN category_id BIGINT COMMENT '分类ID，关联certification_category表' AFTER category;

-- 添加 parent_id 字段  
ALTER TABLE certification_service 
ADD COLUMN parent_id BIGINT DEFAULT 0 COMMENT '父服务ID，用于子菜单，0表示顶级服务' AFTER category_id;

-- 添加索引
ALTER TABLE certification_service 
ADD INDEX idx_category_id (category_id);

ALTER TABLE certification_service 
ADD INDEX idx_parent_id (parent_id);

