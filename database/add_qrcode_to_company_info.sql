-- 为公司信息表添加官方账号二维码字段
-- 执行此脚本前，请确保 company_info 表已存在

USE zht_lab;

-- 添加 qr_code 字段
ALTER TABLE company_info 
ADD COLUMN qr_code VARCHAR(500) COMMENT '官方账号二维码' AFTER logo;

