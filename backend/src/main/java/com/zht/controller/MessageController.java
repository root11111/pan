package com.zht.controller;

import com.zht.common.Result;
import com.zht.entity.Message;
import com.zht.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @PostMapping("/submit")
    public Result<String> submitMessage(@RequestBody Message message) {
        message.setStatus(0);
        messageService.save(message);
        return Result.success("留言提交成功");
    }
}

