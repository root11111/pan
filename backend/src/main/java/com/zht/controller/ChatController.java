package com.zht.controller;

import com.zht.common.Result;
import com.zht.entity.ChatMessage;
import com.zht.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/admin/chat")
public class ChatController {
    
    @Autowired
    private ChatMessageService chatMessageService;
    
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    
    @MessageMapping("/send")
    @SendTo("/topic/messages")
    public ChatMessage sendMessage(ChatMessage message) {
        message.setCreateTime(LocalDateTime.now());
        message.setStatus(0);
        chatMessageService.save(message);
        
        // 发送给特定用户
        messagingTemplate.convertAndSend("/queue/messages/" + message.getToUser(), message);
        
        return message;
    }
    
    @GetMapping("/history")
    public Result<List<ChatMessage>> getHistory(
            @RequestParam String fromUser,
            @RequestParam String toUser) {
        List<ChatMessage> messages = chatMessageService.getChatHistory(fromUser, toUser);
        return Result.success(messages);
    }
    
    @GetMapping("/unread")
    public Result<List<ChatMessage>> getUnread(@RequestParam String toUser) {
        List<ChatMessage> messages = chatMessageService.getUnreadMessages(toUser);
        return Result.success(messages);
    }
    
    @PutMapping("/read/{id}")
    public Result<String> markAsRead(@PathVariable Long id) {
        chatMessageService.markAsRead(id);
        return Result.success("已标记为已读");
    }
}

