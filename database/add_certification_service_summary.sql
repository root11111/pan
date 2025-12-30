-- 为认证服务表添加简介字段
-- 执行前请确保已连接到正确的数据库（zht_lab）

USE zht_lab;

-- 检查字段是否存在，如果不存在则添加
-- 注意：如果字段已存在，执行会报错，这是正常的，可以忽略

-- 添加中文简介字段
ALTER TABLE certification_service 
ADD COLUMN summary_cn VARCHAR(500) COMMENT '中文简介' AFTER region;

-- 添加英文简介字段
ALTER TABLE certification_service 
ADD COLUMN summary_en VARCHAR(500) COMMENT '英文简介' AFTER summary_cn;

