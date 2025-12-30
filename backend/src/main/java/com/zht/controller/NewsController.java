package com.zht.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zht.common.Result;
import com.zht.entity.News;
import com.zht.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/news")
public class NewsController {
    @Autowired
    private NewsService newsService;

    @GetMapping("/latest")
    public Result<List<News>> getLatestNews(@RequestParam(defaultValue = "10") Integer limit) {
        List<News> list = newsService.getLatestNews(limit);
        return Result.success(list);
    }

    @GetMapping("/page")
    public Result<Page<News>> getNewsPage(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size) {
        LambdaQueryWrapper<News> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(News::getPublishTime);
        Page<News> page = new Page<>(current, size);
        Page<News> result = newsService.page(page, wrapper);
        return Result.success(result);
    }

    @GetMapping("/{id}")
    public Result<News> getNewsById(@PathVariable Long id) {
        News news = newsService.getById(id);
        return Result.success(news);
    }
}

