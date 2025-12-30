-- 为公司信息表添加手机号字段
-- 执行此脚本前，请确保 company_info 表已存在

USE zht_lab;

-- 添加 mobile 字段
ALTER TABLE company_info 
ADD COLUMN mobile VARCHAR(50) COMMENT '手机号' AFTER phone;

