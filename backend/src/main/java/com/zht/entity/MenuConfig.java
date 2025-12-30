package com.zht.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("menu_config")
public class MenuConfig {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String menuKey; // 菜单键值，如：honor, certificate
    private String menuNameCn; // 中文名称
    private String menuNameEn; // 英文名称
    private Boolean enabled; // 是否启用
    private Integer sortOrder; // 排序
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

