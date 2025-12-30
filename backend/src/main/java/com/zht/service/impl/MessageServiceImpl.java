package com.zht.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zht.entity.Message;
import com.zht.mapper.MessageMapper;
import com.zht.service.MessageService;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {
}

