-- 清理重复数据脚本
-- 使用方法：在数据库中执行此脚本以删除重复记录

USE zht_lab;

-- 1. 清理企业优势重复数据
-- 保留每个title的第一条记录，删除其他重复记录
DELETE t1 FROM enterprise_advantage t1
INNER JOIN enterprise_advantage t2 
WHERE t1.id > t2.id AND t1.title = t2.title;

-- 2. 清理实验室重复数据
-- 保留每个name_cn的第一条记录，删除其他重复记录
DELETE t1 FROM laboratory t1
INNER JOIN laboratory t2 
WHERE t1.id > t2.id AND t1.name_cn = t2.name_cn;

-- 3. 清理新闻重复数据
-- 保留每个title和publish_time组合的第一条记录，删除其他重复记录
DELETE t1 FROM news t1
INNER JOIN news t2 
WHERE t1.id > t2.id AND t1.title = t2.title AND t1.publish_time = t2.publish_time;

-- 查看清理后的数据统计
SELECT 'enterprise_advantage' AS table_name, COUNT(*) AS count FROM enterprise_advantage
UNION ALL
SELECT 'laboratory' AS table_name, COUNT(*) AS count FROM laboratory
UNION ALL
SELECT 'news' AS table_name, COUNT(*) AS count FROM news;

