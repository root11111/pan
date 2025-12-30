package com.zht.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zht.entity.News;

import java.util.List;

public interface NewsService extends IService<News> {
    List<News> getLatestNews(Integer limit);
}

