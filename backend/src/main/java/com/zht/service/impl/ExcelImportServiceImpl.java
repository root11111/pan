package com.zht.service.impl;

import com.zht.entity.CertificationService;
import com.zht.service.CertificationServiceService;
import com.zht.service.ExcelImportService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ExcelImportServiceImpl implements ExcelImportService {
    
    @Autowired
    private CertificationServiceService certificationServiceService;
    
    // 分类名称到ID的映射
    private static final Map<String, Long> CATEGORY_MAP = new HashMap<>();
    
    static {
        // 二级分类
        CATEGORY_MAP.put("欧洲认证", 2L);
        CATEGORY_MAP.put("European Certification", 2L);
        CATEGORY_MAP.put("美洲认证", 3L);
        CATEGORY_MAP.put("American Certification", 3L);
        CATEGORY_MAP.put("亚洲认证", 4L);
        CATEGORY_MAP.put("Asian Certification", 4L);
        CATEGORY_MAP.put("澳洲认证", 5L);
        CATEGORY_MAP.put("Australian Certification", 5L);
        CATEGORY_MAP.put("国际认证", 6L);
        CATEGORY_MAP.put("International Certification", 6L);
        CATEGORY_MAP.put("能效认证", 7L);
        CATEGORY_MAP.put("Energy Efficiency Certification", 7L);
        CATEGORY_MAP.put("体系认证", 8L);
        CATEGORY_MAP.put("System Certification", 8L);
        CATEGORY_MAP.put("可靠性测试", 9L);
        CATEGORY_MAP.put("Reliability Testing", 9L);
        CATEGORY_MAP.put("电池检测", 10L);
        CATEGORY_MAP.put("Battery Testing", 10L);
        CATEGORY_MAP.put("化学检测", 11L);
        CATEGORY_MAP.put("Chemical Testing", 11L);
        
        // 三级分类：欧洲认证
        CATEGORY_MAP.put("欧盟", 12L);
        CATEGORY_MAP.put("European Union", 12L);
        CATEGORY_MAP.put("匈牙利", 13L);
        CATEGORY_MAP.put("Hungary", 13L);
        CATEGORY_MAP.put("土耳其", 14L);
        CATEGORY_MAP.put("Turkey", 14L);
        CATEGORY_MAP.put("爱尔兰", 15L);
        CATEGORY_MAP.put("Ireland", 15L);
        CATEGORY_MAP.put("斯洛文尼亚", 16L);
        CATEGORY_MAP.put("Slovenia", 16L);
        CATEGORY_MAP.put("乌克兰", 17L);
        CATEGORY_MAP.put("Ukraine", 17L);
        CATEGORY_MAP.put("捷克", 18L);
        CATEGORY_MAP.put("Czech Republic", 18L);
        CATEGORY_MAP.put("斯洛伐克", 19L);
        CATEGORY_MAP.put("Slovakia", 19L);
        CATEGORY_MAP.put("希腊", 20L);
        CATEGORY_MAP.put("Greece", 20L);
        CATEGORY_MAP.put("德国", 21L);
        CATEGORY_MAP.put("Germany", 21L);
        CATEGORY_MAP.put("西班牙", 22L);
        CATEGORY_MAP.put("Spain", 22L);
        CATEGORY_MAP.put("英国", 23L);
        CATEGORY_MAP.put("United Kingdom", 23L);
        
        // 三级分类：美洲认证
        CATEGORY_MAP.put("美国", 24L);
        CATEGORY_MAP.put("United States", 24L);
        CATEGORY_MAP.put("墨西哥", 25L);
        CATEGORY_MAP.put("Mexico", 25L);
        CATEGORY_MAP.put("加拿大", 26L);
        CATEGORY_MAP.put("Canada", 26L);
        CATEGORY_MAP.put("阿根廷", 27L);
        CATEGORY_MAP.put("Argentina", 27L);
        CATEGORY_MAP.put("巴西", 28L);
        CATEGORY_MAP.put("Brazil", 28L);
        
        // 三级分类：亚洲认证
        CATEGORY_MAP.put("中国", 29L);
        CATEGORY_MAP.put("China", 29L);
        CATEGORY_MAP.put("日本", 30L);
        CATEGORY_MAP.put("Japan", 30L);
        CATEGORY_MAP.put("韩国", 31L);
        CATEGORY_MAP.put("South Korea", 31L);
        CATEGORY_MAP.put("沙特", 32L);
        CATEGORY_MAP.put("Saudi Arabia", 32L);
        CATEGORY_MAP.put("俄罗斯", 33L);
        CATEGORY_MAP.put("Russia", 33L);
        CATEGORY_MAP.put("印度", 34L);
        CATEGORY_MAP.put("India", 34L);
        CATEGORY_MAP.put("新加坡", 35L);
        CATEGORY_MAP.put("Singapore", 35L);
        CATEGORY_MAP.put("马来西亚", 36L);
        CATEGORY_MAP.put("Malaysia", 36L);
        CATEGORY_MAP.put("以色列", 37L);
        CATEGORY_MAP.put("Israel", 37L);
        
        // 三级分类：澳洲认证
        CATEGORY_MAP.put("澳洲", 38L);
        CATEGORY_MAP.put("Australia", 38L);
        
        // 三级分类：国际认证
        CATEGORY_MAP.put("国际", 39L);
        CATEGORY_MAP.put("International", 39L);
    }
    
    private static final String UPLOAD_DIR = "D:/workspace/pan/uploads/";
    
    @Override
    public Map<String, Object> importCertificationServices(MultipartFile file) {
        Map<String, Object> result = new HashMap<>();
        int successCount = 0;
        int failCount = 0;
        List<String> errors = new ArrayList<>();
        
        try {
            // 读取 Excel 文件
            List<Map<String, Object>> dataList = readExcelFile(file.getInputStream());
            
            if (dataList.isEmpty()) {
                result.put("success", false);
                result.put("message", "Excel 文件中没有数据（只有表头或所有行都是空行）");
                result.put("successCount", 0);
                result.put("failCount", 0);
                return result;
            }
            
            System.out.println("读取到 " + dataList.size() + " 行数据");
            
            // 处理每一行数据
            for (int i = 0; i < dataList.size(); i++) {
                Map<String, Object> rowData = dataList.get(i);
                try {
                    // 获取分类名称（使用表头名称，而不是列索引）
                    String categoryName = findValueByKeywords(rowData, "分类名称", "分类", "category", "categoryName", "category_name");
                    if (categoryName == null || categoryName.trim().isEmpty()) {
                        // 如果找不到，尝试使用第一列（兼容旧格式）
                        categoryName = getValue(rowData, 0);
                    }
                    
                    System.out.println("第 " + (i + 2) + " 行 - 分类名称: '" + categoryName + "', 数据: " + rowData);
                    
                    if (categoryName == null || categoryName.trim().isEmpty()) {
                        failCount++;
                        errors.add("第 " + (i + 2) + " 行: 分类名称为空");
                        continue;
                    }
                    
                    Long categoryId = findCategoryId(categoryName);
                    
                    if (categoryId == null) {
                        failCount++;
                        errors.add("第 " + (i + 2) + " 行: 未找到分类 '" + categoryName + "'（请检查分类名称是否正确）");
                        continue;
                    }
                    
                    // 构建认证服务对象
                    CertificationService service = buildCertificationService(rowData, categoryId, i + 1);
                    
                    if (service == null) {
                        failCount++;
                        errors.add("第 " + (i + 2) + " 行: 缺少必填字段（中文名称）");
                        continue;
                    }
                    
                    // 保存到数据库
                    certificationServiceService.save(service);
                    successCount++;
                    
                } catch (Exception e) {
                    failCount++;
                    errors.add("第 " + (i + 2) + " 行: " + e.getMessage());
                }
            }
            
            result.put("success", true);
            result.put("successCount", successCount);
            result.put("failCount", failCount);
            result.put("message", "导入完成！成功: " + successCount + " 条，失败: " + failCount + " 条");
            if (!errors.isEmpty() && errors.size() <= 10) {
                result.put("errors", errors);
            }
            
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "导入失败: " + e.getMessage());
            result.put("successCount", 0);
            result.put("failCount", 0);
        }
        
        return result;
    }
    
    /**
     * 读取 Excel 文件
     */
    private List<Map<String, Object>> readExcelFile(InputStream inputStream) throws IOException {
        List<Map<String, Object>> dataList = new ArrayList<>();
        
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        
        // 读取表头
        Row headerRow = sheet.getRow(0);
        if (headerRow == null) {
            workbook.close();
            return dataList;
        }
        
        List<String> headers = new ArrayList<>();
        for (Cell cell : headerRow) {
            headers.add(getCellValueAsString(cell));
        }
        
        // 读取数据行
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;
            
            Map<String, Object> rowData = new HashMap<>();
            for (int j = 0; j < headers.size(); j++) {
                Cell cell = row.getCell(j);
                String header = headers.get(j);
                String value = getCellValueAsString(cell);
                rowData.put(header, value);
            }
            
            // 跳过空行
            if (rowData.values().stream().allMatch(v -> v == null || v.toString().trim().isEmpty())) {
                continue;
            }
            
            dataList.add(rowData);
        }
        
        workbook.close();
        return dataList;
    }
    
    /**
     * 获取单元格值
     */
    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }
        
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return new SimpleDateFormat("yyyy-MM-dd").format(cell.getDateCellValue());
                } else {
                    double numericValue = cell.getNumericCellValue();
                    if (numericValue == (long) numericValue) {
                        return String.valueOf((long) numericValue);
                    } else {
                        return String.valueOf(numericValue);
                    }
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }
    
    /**
     * 根据分类名称查找分类ID
     */
    private Long findCategoryId(String categoryName) {
        if (categoryName == null || categoryName.trim().isEmpty()) {
            return null;
        }
        
        Long id = CATEGORY_MAP.get(categoryName.trim());
        if (id != null) {
            return id;
        }
        
        // 模糊匹配
        for (Map.Entry<String, Long> entry : CATEGORY_MAP.entrySet()) {
            if (categoryName.contains(entry.getKey()) || entry.getKey().contains(categoryName)) {
                return entry.getValue();
            }
        }
        
        return null;
    }
    
    /**
     * 获取指定列的值
     */
    private String getValue(Map<String, Object> rowData, int columnIndex) {
        List<String> keys = new ArrayList<>(rowData.keySet());
        if (columnIndex < keys.size()) {
            Object value = rowData.get(keys.get(columnIndex));
            return value != null ? value.toString() : "";
        }
        return "";
    }
    
    /**
     * 根据关键词查找值
     */
    private String findValueByKeywords(Map<String, Object> rowData, String... keywords) {
        // 精确匹配
        for (String keyword : keywords) {
            Object value = rowData.get(keyword);
            if (value != null && !value.toString().trim().isEmpty()) {
                return value.toString().trim();
            }
        }
        
        // 模糊匹配
        for (String header : rowData.keySet()) {
            for (String keyword : keywords) {
                if (header.contains(keyword) || keyword.contains(header)) {
                    Object value = rowData.get(header);
                    if (value != null && !value.toString().trim().isEmpty()) {
                        return value.toString().trim();
                    }
                }
            }
        }
        
        return "";
    }
    
    /**
     * 判断是否为图片URL
     */
    private boolean isImageUrl(String url) {
        if (url == null || url.trim().isEmpty()) {
            return false;
        }
        url = url.toLowerCase();
        return url.startsWith("http://") || url.startsWith("https://") || 
               url.endsWith(".jpg") || url.endsWith(".jpeg") || 
               url.endsWith(".png") || url.endsWith(".gif") || 
               url.endsWith(".bmp") || url.endsWith(".webp");
    }
    
    /**
     * 下载图片
     */
    private String downloadImage(String imageUrl) {
        if (imageUrl == null || imageUrl.trim().isEmpty()) {
            return null;
        }
        
        try {
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            
            String extension = "";
            if (imageUrl.contains(".")) {
                extension = imageUrl.substring(imageUrl.lastIndexOf("."));
                if (extension.length() > 5) {
                    extension = ".jpg";
                }
            } else {
                extension = ".jpg";
            }
            
            String filename = UUID.randomUUID().toString() + extension;
            String filePath = UPLOAD_DIR + filename;
            
            URL url = new URL(imageUrl);
            try (InputStream in = url.openStream();
                 java.io.FileOutputStream out = new java.io.FileOutputStream(filePath)) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
            }
            
            return "/uploads/" + filename;
            
        } catch (Exception e) {
            System.err.println("下载图片失败: " + imageUrl + " - " + e.getMessage());
            return null;
        }
    }
    
    /**
     * 构建认证服务对象
     */
    private CertificationService buildCertificationService(Map<String, Object> rowData, Long categoryId, int index) {
        String nameCn = findValueByKeywords(rowData, "中文名称", "名称", "name_cn", "nameCn");
        if (nameCn.isEmpty()) {
            return null;
        }
        
        String nameEn = findValueByKeywords(rowData, "英文名称", "name_en", "nameEn", "英文名");
        String region = findValueByKeywords(rowData, "地区", "region", "国家", "国家/地区");
        String imageUrl = findValueByKeywords(rowData, "封面图片", "图片", "image", "封面", "封面图");
        String summaryCn = findValueByKeywords(rowData, "中文简介", "简介", "summary_cn", "summaryCn", "简介(中文)");
        String summaryEn = findValueByKeywords(rowData, "英文简介", "summary_en", "summaryEn", "简介(英文)");
        String descriptionCn = findValueByKeywords(rowData, "中文描述", "描述", "description_cn", "descriptionCn", "描述(中文)", "内容");
        String descriptionEn = findValueByKeywords(rowData, "英文描述", "description_en", "descriptionEn", "描述(英文)");
        String sortOrderStr = findValueByKeywords(rowData, "排序", "sort_order", "sortOrder", "序号", "顺序");
        
        // 下载封面图片
        String imagePath = null;
        if (isImageUrl(imageUrl)) {
            imagePath = downloadImage(imageUrl);
        } else if (!imageUrl.isEmpty()) {
            imagePath = imageUrl;
        }
        
        // 处理描述中的图片
        if (isImageUrl(descriptionCn)) {
            String descImagePath = downloadImage(descriptionCn);
            if (descImagePath != null) {
                descriptionCn = descImagePath;
            }
        }
        if (isImageUrl(descriptionEn)) {
            String descImagePath = downloadImage(descriptionEn);
            if (descImagePath != null) {
                descriptionEn = descImagePath;
            }
        }
        
        // 处理排序
        int sortOrder = 0;
        try {
            if (!sortOrderStr.isEmpty()) {
                sortOrder = Integer.parseInt(sortOrderStr);
            } else {
                sortOrder = index;
            }
        } catch (NumberFormatException e) {
            sortOrder = index;
        }
        
        CertificationService service = new CertificationService();
        service.setNameCn(nameCn);
        service.setNameEn(nameEn);
        service.setCategoryId(categoryId);
        service.setRegion(region);
        service.setSummaryCn(summaryCn);
        service.setSummaryEn(summaryEn);
        service.setImage(imagePath);
        service.setDescriptionCn(descriptionCn);
        service.setDescriptionEn(descriptionEn);
        service.setSortOrder(sortOrder);
        service.setParentId(0L);
        
        return service;
    }
}

