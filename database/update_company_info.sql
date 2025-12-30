-- 更新公司信息（如果数据库已存在）
-- 执行此脚本前，请确保 company_info 表已存在

USE zht_lab;

-- 更新公司信息
UPDATE company_info 
SET 
    name_cn = '深圳泰和检测技术服务有限公司',
    name_en = 'Shenzhen Taihe Testing Technology Service Co., Ltd.',
    description_cn = '自成立以来，致力于为客户提供专业的一站式产品检测认证服务，是一家集检测、检验、认证及技术服务为一体的独立且权威的综合性第三方检测认证机构。',
    description_en = 'Since its establishment, it has been committed to providing professional one-stop product testing and certification services for customers. It is an independent and authoritative comprehensive third-party testing and certification organization integrating testing, inspection, certification and technical services.',
    address = '深圳市',
    phone = '18307618653',
    qq = '405705201',
    wechat = '二维码'
WHERE id = 1;

-- 如果表中没有数据，则插入
INSERT INTO company_info (name_cn, name_en, description_cn, description_en, address, phone, qq, wechat)
SELECT 
    '深圳泰和检测技术服务有限公司',
    'Shenzhen Taihe Testing Technology Service Co., Ltd.',
    '自成立以来，致力于为客户提供专业的一站式产品检测认证服务，是一家集检测、检验、认证及技术服务为一体的独立且权威的综合性第三方检测认证机构。',
    'Since its establishment, it has been committed to providing professional one-stop product testing and certification services for customers. It is an independent and authoritative comprehensive third-party testing and certification organization integrating testing, inspection, certification and technical services.',
    '深圳市',
    '18307618653',
    '405705201',
    '二维码'
WHERE NOT EXISTS (SELECT 1 FROM company_info WHERE id = 1);

