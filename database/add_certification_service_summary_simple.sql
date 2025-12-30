-- 为认证服务表添加简介字段（简单版本）
-- 如果字段已存在，执行会报错，可以先检查表结构

-- 添加中文简介字段
ALTER TABLE certification_service 
ADD COLUMN IF NOT EXISTS summary_cn VARCHAR(500) COMMENT '中文简介' AFTER region;

-- 添加英文简介字段
ALTER TABLE certification_service 
ADD COLUMN IF NOT EXISTS summary_en VARCHAR(500) COMMENT '英文简介' AFTER summary_cn;

