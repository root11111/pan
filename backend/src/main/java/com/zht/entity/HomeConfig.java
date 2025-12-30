package com.zht.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("home_config")
public class HomeConfig {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String configKey; // 配置键，如：banner_title, banner_subtitle等
    private String configValueCn; // 中文值
    private String configValueEn; // 英文值
    private String configType; // 配置类型：text, image, json
    private Integer sortOrder; // 排序
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

