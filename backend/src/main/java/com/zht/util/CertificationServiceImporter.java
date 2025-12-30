package com.zht.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 认证服务数据导入工具
 * 从 Excel 文件导入认证服务数据到数据库
 */
public class CertificationServiceImporter {
    
    // 分类名称到ID的映射（根据 init_certification_category.sql）
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
    
    private static final String EXCEL_FILE_PATH = "D:/workspace/pan/docs/检测技术有限公司.xlsx";
    private static final String UPLOAD_DIR = "D:/workspace/pan/uploads/";
    private static final String SQL_OUTPUT_FILE = "D:/workspace/pan/database/import_certification_service_data.sql";
    
    // 数据库配置
    private static final String DB_URL = "jdbc:mysql://localhost:3306/zht_lab?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "15002525887_muht_WM";
    
    public static void main(String[] args) {
        try {
            System.out.println("开始导入认证服务数据...");
            
            // 读取 Excel 文件
            List<Map<String, Object>> dataList = readExcelFile(EXCEL_FILE_PATH);
            System.out.println("读取到 " + dataList.size() + " 条数据");
            
            // 下载图片并生成 SQL
            List<String> sqlStatements = processData(dataList);
            
            // 写入 SQL 文件
            writeSqlFile(sqlStatements);
            System.out.println("SQL 文件已生成: " + SQL_OUTPUT_FILE);
            
            // 可选：直接导入数据库
            System.out.println("\n是否直接导入数据库？(y/n)");
            Scanner scanner = new Scanner(System.in);
            String answer = scanner.nextLine();
            if ("y".equalsIgnoreCase(answer)) {
                importToDatabase(sqlStatements);
                System.out.println("数据导入完成！");
            } else {
                System.out.println("请手动执行 SQL 文件: " + SQL_OUTPUT_FILE);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("导入失败: " + e.getMessage());
        }
    }
    
    /**
     * 读取 Excel 文件
     */
    private static List<Map<String, Object>> readExcelFile(String filePath) throws IOException {
        List<Map<String, Object>> dataList = new ArrayList<>();
        
        FileInputStream fis = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(0); // 读取第一个工作表
        
        // 读取表头（第一行）
        Row headerRow = sheet.getRow(0);
        if (headerRow == null) {
            workbook.close();
            return dataList;
        }
        
        List<String> headers = new ArrayList<>();
        for (Cell cell : headerRow) {
            headers.add(getCellValueAsString(cell));
        }
        
        System.out.println("表头: " + headers);
        
        // 读取数据行（从第二行开始）
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
        fis.close();
        
        return dataList;
    }
    
    /**
     * 获取单元格值（字符串格式）
     */
    private static String getCellValueAsString(Cell cell) {
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
                    // 处理数字，避免科学计数法
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
     * 处理数据：下载图片、生成 SQL
     */
    private static List<String> processData(List<Map<String, Object>> dataList) throws IOException {
        List<String> sqlStatements = new ArrayList<>();
        sqlStatements.add("-- 认证服务数据导入");
        sqlStatements.add("-- 生成时间: " + new Date());
        sqlStatements.add("");
        
        int index = 1;
        for (Map<String, Object> rowData : dataList) {
            try {
                // 获取分类名称（第一列）
                String categoryName = getValue(rowData, 0);
                Long categoryId = findCategoryId(categoryName);
                
                if (categoryId == null) {
                    System.err.println("警告: 未找到分类 '" + categoryName + "'，跳过该行");
                    continue;
                }
                
                // 构建 SQL 插入语句
                String sql = buildInsertSql(rowData, categoryId, index++);
                if (sql != null) {
                    sqlStatements.add(sql);
                }
                
            } catch (Exception e) {
                System.err.println("处理数据行失败: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        return sqlStatements;
    }
    
    /**
     * 根据分类名称查找分类ID
     */
    private static Long findCategoryId(String categoryName) {
        if (categoryName == null || categoryName.trim().isEmpty()) {
            return null;
        }
        
        // 直接匹配
        Long id = CATEGORY_MAP.get(categoryName.trim());
        if (id != null) {
            return id;
        }
        
        // 模糊匹配（包含关系）
        for (Map.Entry<String, Long> entry : CATEGORY_MAP.entrySet()) {
            if (categoryName.contains(entry.getKey()) || entry.getKey().contains(categoryName)) {
                return entry.getValue();
            }
        }
        
        return null;
    }
    
    /**
     * 获取指定列的值（按列索引）
     */
    private static String getValue(Map<String, Object> rowData, int columnIndex) {
        List<String> keys = new ArrayList<>(rowData.keySet());
        if (columnIndex < keys.size()) {
            Object value = rowData.get(keys.get(columnIndex));
            return value != null ? value.toString() : "";
        }
        return "";
    }
    
    /**
     * 根据表头名称获取值
     */
    private static String getValueByHeader(Map<String, Object> rowData, String header) {
        Object value = rowData.get(header);
        return value != null ? value.toString().trim() : "";
    }
    
    /**
     * 根据多个关键词查找值（支持模糊匹配）
     */
    private static String findValueByKeywords(Map<String, Object> rowData, String... keywords) {
        // 精确匹配
        for (String keyword : keywords) {
            String value = getValueByHeader(rowData, keyword);
            if (!value.isEmpty()) {
                return value;
            }
        }
        
        // 模糊匹配（表头包含关键词）
        for (String header : rowData.keySet()) {
            for (String keyword : keywords) {
                if (header.contains(keyword) || keyword.contains(header)) {
                    String value = getValueByHeader(rowData, header);
                    if (!value.isEmpty()) {
                        return value;
                    }
                }
            }
        }
        
        return "";
    }
    
    /**
     * 判断是否为图片URL
     */
    private static boolean isImageUrl(String url) {
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
    private static String downloadImage(String imageUrl) throws IOException {
        if (imageUrl == null || imageUrl.trim().isEmpty()) {
            return null;
        }
        
        try {
            // 确保上传目录存在
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            
            // 生成文件名
            String extension = "";
            if (imageUrl.contains(".")) {
                extension = imageUrl.substring(imageUrl.lastIndexOf("."));
                if (extension.length() > 5) {
                    extension = ".jpg"; // 默认 jpg
                }
            } else {
                extension = ".jpg";
            }
            
            String filename = UUID.randomUUID().toString() + extension;
            String filePath = UPLOAD_DIR + filename;
            
            // 下载图片
            URL url = new URL(imageUrl);
            try (InputStream in = url.openStream();
                 FileOutputStream out = new FileOutputStream(filePath)) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
            }
            
            System.out.println("下载图片: " + imageUrl + " -> " + filename);
            return "/uploads/" + filename;
            
        } catch (Exception e) {
            System.err.println("下载图片失败: " + imageUrl + " - " + e.getMessage());
            return null;
        }
    }
    
    /**
     * 构建 INSERT SQL 语句
     */
    private static String buildInsertSql(Map<String, Object> rowData, Long categoryId, int index) throws IOException {
        // 根据实际 Excel 列名调整，支持多种可能的列名
        String nameCn = findValueByKeywords(rowData, "中文名称", "名称", "name_cn", "nameCn");
        if (nameCn.isEmpty()) {
            return null; // 跳过没有名称的行
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
            imagePath = imageUrl; // 可能是相对路径
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
        
        // 构建 SQL
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO certification_service ");
        sql.append("(name_cn, name_en, category_id, region, summary_cn, summary_en, image, description_cn, description_en, sort_order, parent_id, create_time, update_time) ");
        sql.append("VALUES (");
        sql.append(escapeSql(nameCn)).append(", ");
        sql.append(escapeSql(nameEn)).append(", ");
        sql.append(categoryId).append(", ");
        sql.append(escapeSql(region)).append(", ");
        sql.append(escapeSql(summaryCn)).append(", ");
        sql.append(escapeSql(summaryEn)).append(", ");
        sql.append(escapeSql(imagePath)).append(", ");
        sql.append(escapeSql(descriptionCn)).append(", ");
        sql.append(escapeSql(descriptionEn)).append(", ");
        sql.append(sortOrder).append(", ");
        sql.append("0, "); // parent_id 默认为 0
        sql.append("NOW(), NOW()");
        sql.append(");");
        
        return sql.toString();
    }
    
    /**
     * SQL 转义
     */
    private static String escapeSql(String value) {
        if (value == null || value.isEmpty()) {
            return "NULL";
        }
        // 转义单引号
        value = value.replace("'", "''");
        return "'" + value + "'";
    }
    
    /**
     * 写入 SQL 文件
     */
    private static void writeSqlFile(List<String> sqlStatements) throws IOException {
        File file = new File(SQL_OUTPUT_FILE);
        file.getParentFile().mkdirs();
        
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(SQL_OUTPUT_FILE))) {
            for (String sql : sqlStatements) {
                writer.write(sql);
                writer.newLine();
            }
        }
    }
    
    /**
     * 直接导入数据库
     */
    private static void importToDatabase(List<String> sqlStatements) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            conn.setAutoCommit(false);
            
            int successCount = 0;
            int failCount = 0;
            
            for (String sql : sqlStatements) {
                // 跳过注释和空行
                if (sql.trim().isEmpty() || sql.trim().startsWith("--")) {
                    continue;
                }
                
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.executeUpdate();
                    successCount++;
                } catch (SQLException e) {
                    System.err.println("执行 SQL 失败: " + sql);
                    System.err.println("错误: " + e.getMessage());
                    failCount++;
                }
            }
            
            conn.commit();
            System.out.println("导入完成！成功: " + successCount + ", 失败: " + failCount);
            
        } catch (SQLException e) {
            System.err.println("数据库连接失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

