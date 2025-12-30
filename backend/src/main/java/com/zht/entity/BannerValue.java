package com.zht.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("banner_value")
public class BannerValue {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String title; // 标题
    private String description; // 描述
    private Integer sortOrder; // 排序
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

