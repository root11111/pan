package com.zht.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@TableName("certification_category")
public class CertificationCategory {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String nameCn; // 中文名称
    private String nameEn; // 英文名称
    private String icon;   // 分类图标路径
    private Long parentId; // 父分类ID，0表示顶级分类
    private Integer sortOrder; // 排序
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    
    @TableField(exist = false)
    private List<CertificationCategory> children; // 子分类列表（非数据库字段）
}

