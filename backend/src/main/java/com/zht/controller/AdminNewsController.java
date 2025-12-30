package com.zht.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zht.common.Result;
import com.zht.entity.News;
import com.zht.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/admin/news")
public class AdminNewsController {
    
    @Autowired
    private NewsService newsService;
    
    @GetMapping("/page")
    public Result<Page<News>> page(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String keyword) {
        LambdaQueryWrapper<News> wrapper = new LambdaQueryWrapper<>();
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.like(News::getTitle, keyword);
        }
        wrapper.orderByDesc(News::getPublishTime);
        Page<News> page = new Page<>(current, size);
        Page<News> result = newsService.page(page, wrapper);
        return Result.success(result);
    }
    
    @GetMapping("/{id}")
    public Result<News> getById(@PathVariable Long id) {
        News news = newsService.getById(id);
        return Result.success(news);
    }
    
    @PostMapping("/save")
    public Result<String> save(@RequestBody News news) {
        if (news.getId() == null) {
            news.setPublishTime(LocalDateTime.now());
            news.setViewCount(0);
        }
        newsService.saveOrUpdate(news);
        return Result.success("保存成功");
    }
    
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Long id) {
        newsService.removeById(id);
        return Result.success("删除成功");
    }
    
    /**
     * 批量删除新闻
     */
    @DeleteMapping("/batch")
    public Result<String> batchDelete(@RequestBody List<Long> ids) {
        newsService.removeByIds(ids);
        return Result.success("批量删除成功");
    }
}

