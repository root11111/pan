package com.zht.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("company_info")
public class CompanyInfo {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String nameCn;
    private String nameEn;
    private String descriptionCn;
    private String descriptionEn;
    private String address;
    private String phone;
    private String mobile;
    private String email;
    private String qq;
    private String wechat;
    private String logo;
    private String qrCode;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

