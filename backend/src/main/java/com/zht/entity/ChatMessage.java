package com.zht.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("chat_message")
public class ChatMessage {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String fromUser; // 发送者（admin或用户标识）
    private String toUser; // 接收者
    private String content; // 消息内容
    private Integer type; // 0-用户消息 1-管理员消息
    private Integer status; // 0-未读 1-已读
    private LocalDateTime createTime;
}

