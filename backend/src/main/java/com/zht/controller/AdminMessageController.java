package com.zht.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zht.common.Result;
import com.zht.entity.Message;
import com.zht.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/message")
public class AdminMessageController {
    
    @Autowired
    private MessageService messageService;
    
    @GetMapping("/page")
    public Result<Page<Message>> page(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) Integer status) {
        LambdaQueryWrapper<Message> wrapper = new LambdaQueryWrapper<>();
        if (status != null) {
            wrapper.eq(Message::getStatus, status);
        }
        wrapper.orderByDesc(Message::getCreateTime);
        Page<Message> page = new Page<>(current, size);
        Page<Message> result = messageService.page(page, wrapper);
        return Result.success(result);
    }
    
    @GetMapping("/{id}")
    public Result<Message> getById(@PathVariable Long id) {
        Message message = messageService.getById(id);
        return Result.success(message);
    }
    
    @PutMapping("/{id}/status")
    public Result<String> updateStatus(@PathVariable Long id, @RequestParam Integer status) {
        Message message = messageService.getById(id);
        if (message != null) {
            message.setStatus(status);
            messageService.updateById(message);
        }
        return Result.success("更新成功");
    }
    
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Long id) {
        messageService.removeById(id);
        return Result.success("删除成功");
    }
}

