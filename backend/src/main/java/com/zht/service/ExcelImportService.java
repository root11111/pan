package com.zht.service;

import com.zht.entity.CertificationService;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * Excel 导入服务接口
 */
public interface ExcelImportService {
    /**
     * 导入认证服务数据
     * @param file Excel 文件
     * @return 导入结果（成功数量、失败数量、错误信息）
     */
    Map<String, Object> importCertificationServices(MultipartFile file);
}

