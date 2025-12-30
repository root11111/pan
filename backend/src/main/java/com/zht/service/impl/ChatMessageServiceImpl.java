package com.zht.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zht.entity.ChatMessage;
import com.zht.mapper.ChatMessageMapper;
import com.zht.service.ChatMessageService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatMessageServiceImpl extends ServiceImpl<ChatMessageMapper, ChatMessage> implements ChatMessageService {
    
    @Override
    public List<ChatMessage> getChatHistory(String fromUser, String toUser) {
        LambdaQueryWrapper<ChatMessage> wrapper = new LambdaQueryWrapper<>();
        wrapper.and(w -> w.eq(ChatMessage::getFromUser, fromUser).eq(ChatMessage::getToUser, toUser)
                .or()
                .eq(ChatMessage::getFromUser, toUser).eq(ChatMessage::getToUser, fromUser));
        wrapper.orderByAsc(ChatMessage::getCreateTime);
        return this.list(wrapper);
    }
    
    @Override
    public List<ChatMessage> getUnreadMessages(String toUser) {
        LambdaQueryWrapper<ChatMessage> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ChatMessage::getToUser, toUser);
        wrapper.eq(ChatMessage::getStatus, 0);
        wrapper.orderByAsc(ChatMessage::getCreateTime);
        return this.list(wrapper);
    }
    
    @Override
    public void markAsRead(Long id) {
        LambdaUpdateWrapper<ChatMessage> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(ChatMessage::getId, id);
        wrapper.set(ChatMessage::getStatus, 1);
        this.update(wrapper);
    }
}

