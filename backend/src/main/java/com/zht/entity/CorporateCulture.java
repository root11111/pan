package com.zht.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("corporate_culture")
public class CorporateCulture {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String title; // 标题，如：质量方针、愿景等
    private String description; // 描述
    private Integer sortOrder; // 排序
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

