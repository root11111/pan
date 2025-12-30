package com.zht.controller;

import com.zht.common.Result;
import com.zht.entity.Honor;
import com.zht.service.HonorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/honor")
public class HonorController {
    @Autowired
    private HonorService honorService;

    @GetMapping("/list")
    public Result<List<Honor>> getAllHonors() {
        List<Honor> list = honorService.getAllHonors();
        return Result.success(list);
    }
}

