package com.zht.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zht.common.Result;
import com.zht.entity.CertificationService;
import com.zht.service.CertificationServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/certification")
public class AdminCertificationServiceController {
    
    @Autowired
    private CertificationServiceService certificationServiceService;
    
    /**
     * 获取所有认证服务
     */
    @GetMapping("/list")
    public Result<List<CertificationService>> list() {
        List<CertificationService> list = certificationServiceService.getAll();
        return Result.success(list);
    }
    
    /**
     * 分页查询认证服务
     */
    @GetMapping("/page")
    public Result<Page<CertificationService>> page(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String region) {
        Page<CertificationService> page = new Page<>(current, size);
        LambdaQueryWrapper<CertificationService> wrapper = new LambdaQueryWrapper<>();
        
        if (category != null && !category.isEmpty()) {
            wrapper.eq(CertificationService::getCategory, category);
        }
        if (region != null && !region.isEmpty()) {
            wrapper.eq(CertificationService::getRegion, region);
        }
        
        wrapper.orderByAsc(CertificationService::getSortOrder);
        Page<CertificationService> result = certificationServiceService.page(page, wrapper);
        return Result.success(result);
    }
    
    /**
     * 根据分类获取认证服务
     */
    @GetMapping("/category/{category}")
    public Result<List<CertificationService>> getByCategory(@PathVariable String category) {
        List<CertificationService> list = certificationServiceService.getByCategory(category);
        return Result.success(list);
    }
    
    /**
     * 根据ID获取认证服务
     */
    @GetMapping("/{id}")
    public Result<CertificationService> getById(@PathVariable Long id) {
        CertificationService service = certificationServiceService.getById(id);
        return Result.success(service);
    }
    
    /**
     * 保存或更新认证服务
     */
    @PostMapping("/save")
    public Result<String> save(@RequestBody CertificationService certificationService) {
        certificationServiceService.saveOrUpdate(certificationService);
        return Result.success("保存成功");
    }
    
    /**
     * 删除认证服务
     */
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Long id) {
        certificationServiceService.removeById(id);
        return Result.success("删除成功");
    }
    
    /**
     * 批量删除认证服务
     */
    @DeleteMapping("/batch")
    public Result<String> batchDelete(@RequestBody List<Long> ids) {
        certificationServiceService.removeByIds(ids);
        return Result.success("批量删除成功");
    }
    
    @Autowired
    private com.zht.service.ExcelImportService excelImportService;
    
    /**
     * 批量导入认证服务（从 Excel 文件）
     */
    @PostMapping("/import")
    public Result<Map<String, Object>> importFromExcel(@RequestParam("file") org.springframework.web.multipart.MultipartFile file) {
        if (file.isEmpty()) {
            return Result.error("文件不能为空");
        }
        
        try {
            Map<String, Object> result = excelImportService.importCertificationServices(file);
            return Result.success(result);
        } catch (Exception e) {
            return Result.error("导入失败: " + e.getMessage());
        }
    }
    
    /**
     * 下载导入模板
     */
    @GetMapping("/template")
    public void downloadTemplate(HttpServletResponse response) throws IOException {
        try {
            // 设置响应头
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("UTF-8");
            
            // 处理文件名编码，兼容不同浏览器
            String fileName = "认证服务导入模板.xlsx";
            String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8).replaceAll("\\+", "%20");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedFileName + "\"; filename*=UTF-8''" + encodedFileName);
            
            // 生成并写入Excel文件
            com.zht.util.ExcelTemplateGenerator.generateTemplate(response.getOutputStream());
            response.getOutputStream().flush();
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":500,\"message\":\"模板生成失败: " + e.getMessage() + "\"}");
        }
    }
}

