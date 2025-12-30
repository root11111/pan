package com.zht.controller;

import com.zht.common.Result;
import com.zht.entity.Laboratory;
import com.zht.service.LaboratoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/laboratory")
public class LaboratoryController {
    @Autowired
    private LaboratoryService laboratoryService;

    @GetMapping("/list")
    public Result<List<Laboratory>> getAllLaboratories() {
        List<Laboratory> list = laboratoryService.getAllLaboratories();
        return Result.success(list);
    }
}

