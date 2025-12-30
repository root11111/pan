package com.zht.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Excel 模板生成器
 */
public class ExcelTemplateGenerator {
    
    /**
     * 生成认证服务导入模板
     */
    public static void generateTemplate(OutputStream outputStream) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("认证服务导入模板");
        
        // 创建样式
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 12);
        headerStyle.setFont(headerFont);
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);
        
        // 创建表头
        Row headerRow = sheet.createRow(0);
        String[] headers = {
            "分类名称",
            "中文名称",
            "英文名称",
            "地区",
            "封面图片",
            "中文简介",
            "英文简介",
            "中文描述",
            "英文描述",
            "排序"
        };
        
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }
        
        // 设置列宽
        for (int i = 0; i < headers.length; i++) {
            sheet.setColumnWidth(i, 5000);
        }
        sheet.setColumnWidth(0, 15000); // 分类名称列宽
        sheet.setColumnWidth(4, 30000); // 封面图片列宽
        sheet.setColumnWidth(7, 20000); // 中文描述列宽
        sheet.setColumnWidth(8, 20000); // 英文描述列宽
        
        // 冻结表头
        sheet.createFreezePane(0, 1);
        
        workbook.write(outputStream);
        workbook.close();
    }
    
    /**
     * 生成模板文件到指定路径
     */
    public static void generateTemplateFile(String filePath) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            generateTemplate(fos);
        }
    }
    
    public static void main(String[] args) {
        try {
            generateTemplateFile("D:/workspace/pan/docs/认证服务导入模板.xlsx");
            System.out.println("模板文件已生成: D:/workspace/pan/docs/认证服务导入模板.xlsx");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

