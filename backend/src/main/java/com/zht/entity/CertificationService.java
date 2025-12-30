package com.zht.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("certification_service")
public class CertificationService {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String nameCn;
    private String nameEn;
    private String category; // 欧洲认证、美洲认证、亚洲认证、澳洲认证、国际认证、能效认证、体系认证、可靠性测试、电池检测、化学检测
    private Long categoryId; // 分类ID，关联certification_category表
    private Long parentId; // 父服务ID，用于子菜单，0表示顶级服务
    private String region; // 具体国家或地区
    private String summaryCn; // 中文简介
    private String summaryEn; // 英文简介
    private String descriptionCn;
    private String descriptionEn;
    private String image; // 图片路径
    private Integer sortOrder;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

