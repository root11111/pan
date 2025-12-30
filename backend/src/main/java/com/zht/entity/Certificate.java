package com.zht.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("certificate")
public class Certificate {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String certificateNo; // 证书编号
    private String productName; // 产品名称
    private String certificateType; // 证书类型
    private LocalDate issueDate; // 颁发日期
    private LocalDate expiryDate; // 有效期至
    private String status; // 状态：有效、已过期、已撤销
    private String issuer; // 颁发机构
    private String description; // 描述
    private String certificateFile; // 证书文件路径
    private Integer sortOrder; // 排序
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

