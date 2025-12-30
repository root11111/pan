package com.zht.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zht.common.Result;
import com.zht.entity.Laboratory;
import com.zht.service.LaboratoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/laboratory")
public class AdminLaboratoryController {
    
    @Autowired
    private LaboratoryService laboratoryService;
    
    @GetMapping("/list")
    public Result<List<Laboratory>> list() {
        List<Laboratory> list = laboratoryService.getAllLaboratories();
        return Result.success(list);
    }
    
    @PostMapping("/save")
    public Result<String> save(@RequestBody Laboratory laboratory) {
        laboratoryService.saveOrUpdate(laboratory);
        return Result.success("保存成功");
    }
    
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Long id) {
        laboratoryService.removeById(id);
        return Result.success("删除成功");
    }
}

