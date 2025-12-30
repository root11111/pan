-- 为认证服务表添加新字段
-- 注意：如果字段已存在，执行会报错，可以先检查或手动删除后执行

-- 检查并添加 category_id 字段（如果不存在）
SET @dbname = DATABASE();
SET @tablename = 'certification_service';
SET @columnname = 'category_id';
SET @preparedStatement = (SELECT IF(
  (
    SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS
    WHERE
      (table_name = @tablename)
      AND (table_schema = @dbname)
      AND (column_name = @columnname)
  ) > 0,
  'SELECT 1',
  CONCAT('ALTER TABLE ', @tablename, ' ADD COLUMN ', @columnname, ' BIGINT COMMENT ''分类ID，关联certification_category表'' AFTER category')
));
PREPARE alterIfNotExists FROM @preparedStatement;
EXECUTE alterIfNotExists;
DEALLOCATE PREPARE alterIfNotExists;

-- 检查并添加 parent_id 字段（如果不存在）
SET @columnname = 'parent_id';
SET @preparedStatement = (SELECT IF(
  (
    SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS
    WHERE
      (table_name = @tablename)
      AND (table_schema = @dbname)
      AND (column_name = @columnname)
  ) > 0,
  'SELECT 1',
  CONCAT('ALTER TABLE ', @tablename, ' ADD COLUMN ', @columnname, ' BIGINT DEFAULT 0 COMMENT ''父服务ID，用于子菜单，0表示顶级服务'' AFTER category_id')
));
PREPARE alterIfNotExists FROM @preparedStatement;
EXECUTE alterIfNotExists;
DEALLOCATE PREPARE alterIfNotExists;

-- 添加索引（如果不存在）
-- 注意：MySQL没有直接检查索引是否存在的语法，如果索引已存在会报错，可以忽略
ALTER TABLE certification_service 
ADD INDEX idx_category_id (category_id);

ALTER TABLE certification_service 
ADD INDEX idx_parent_id (parent_id);

