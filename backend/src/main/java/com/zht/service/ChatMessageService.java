package com.zht.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zht.entity.ChatMessage;

import java.util.List;

public interface ChatMessageService extends IService<ChatMessage> {
    List<ChatMessage> getChatHistory(String fromUser, String toUser);
    List<ChatMessage> getUnreadMessages(String toUser);
    void markAsRead(Long id);
}

