package com.zht.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zht.entity.News;
import com.zht.mapper.NewsMapper;
import com.zht.service.NewsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements NewsService {
    @Override
    public List<News> getLatestNews(Integer limit) {
        LambdaQueryWrapper<News> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(News::getPublishTime);
        Page<News> page = new Page<>(1, limit * 2); // 多查询一些，以便去重后仍有足够数据
        Page<News> result = this.page(page, wrapper);
        List<News> records = result.getRecords();
        // 去重：保留每个title和publish_time组合的第一条记录
        List<News> uniqueNews = records.stream()
                .collect(java.util.stream.Collectors.toMap(
                        news -> news.getTitle() + "_" + (news.getPublishTime() != null ? news.getPublishTime().toString() : ""),
                        item -> item,
                        (existing, replacement) -> existing
                ))
                .values()
                .stream()
                .sorted((a, b) -> {
                    if (a.getPublishTime() == null && b.getPublishTime() == null) return 0;
                    if (a.getPublishTime() == null) return 1;
                    if (b.getPublishTime() == null) return -1;
                    return b.getPublishTime().compareTo(a.getPublishTime());
                })
                .limit(limit)
                .collect(java.util.stream.Collectors.toList());
        return uniqueNews;
    }
}

