-- 为认证服务表添加图片字段
-- 如果字段已存在，会报错，可以忽略

-- 方法1：直接添加（如果字段不存在）
ALTER TABLE certification_service ADD COLUMN image VARCHAR(500) COMMENT '图片路径' AFTER description_en;

-- 方法2：如果字段已存在，先删除再添加（谨慎使用）
-- ALTER TABLE certification_service DROP COLUMN image;
-- ALTER TABLE certification_service ADD COLUMN image VARCHAR(500) COMMENT '图片路径' AFTER description_en;

-- 方法3：使用 IF NOT EXISTS（MySQL 8.0.19+）
-- ALTER TABLE certification_service ADD COLUMN IF NOT EXISTS image VARCHAR(500) COMMENT '图片路径' AFTER description_en;
