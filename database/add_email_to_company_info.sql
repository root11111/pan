-- 为公司信息表添加邮箱字段
-- 执行此脚本前，请确保 company_info 表已存在

USE zht_lab;

-- 添加 email 字段
ALTER TABLE company_info 
ADD COLUMN email VARCHAR(100) COMMENT '邮箱' AFTER mobile;

