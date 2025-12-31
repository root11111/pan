package com.zht.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 认证分类数据导入工具
 * 1. 下载认证图片
 * 2. 生成SQL导入语句
 */
public class CertificationCategoryImporter {
    
    // 图片保存目录
    private static final String IMAGE_DIR = "uploads/icon/certification/";
    
    // 图片URL映射（认证名称 -> 图片URL）
    private static final Map<String, String> IMAGE_URLS = new HashMap<>();
    
    // 认证描述信息（中文）
    private static final Map<String, String> DESCRIPTIONS_CN = new HashMap<>();
    
    // 认证描述信息（英文）
    private static final Map<String, String> DESCRIPTIONS_EN = new HashMap<>();
    
    static {
        initImageUrls();
        initDescriptions();
    }
    
    /**
     * 初始化图片URL（这里使用一些常见的认证标志图片URL，实际使用时需要替换为真实URL）
     */
    private static void initImageUrls() {
        // 欧洲认证
        IMAGE_URLS.put("欧盟", "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b7/Flag_of_Europe.svg/320px-Flag_of_Europe.svg.png");
        IMAGE_URLS.put("匈牙利", "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c1/Flag_of_Hungary.svg/320px-Flag_of_Hungary.svg.png");
        IMAGE_URLS.put("土耳其", "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b4/Flag_of_Turkey.svg/320px-Flag_of_Turkey.svg.png");
        IMAGE_URLS.put("爱尔兰", "https://upload.wikimedia.org/wikipedia/commons/thumb/4/45/Flag_of_Ireland.svg/320px-Flag_of_Ireland.svg.png");
        IMAGE_URLS.put("斯洛文尼亚", "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f0/Flag_of_Slovenia.svg/320px-Flag_of_Slovenia.svg.png");
        IMAGE_URLS.put("乌克兰", "https://upload.wikimedia.org/wikipedia/commons/thumb/4/49/Flag_of_Ukraine.svg/320px-Flag_of_Ukraine.svg.png");
        IMAGE_URLS.put("捷克", "https://upload.wikimedia.org/wikipedia/commons/thumb/c/cb/Flag_of_the_Czech_Republic.svg/320px-Flag_of_the_Czech_Republic.svg.png");
        IMAGE_URLS.put("斯洛伐克", "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e6/Flag_of_Slovakia.svg/320px-Flag_of_Slovakia.svg.png");
        IMAGE_URLS.put("希腊", "https://upload.wikimedia.org/wikipedia/commons/thumb/5/5c/Flag_of_Greece.svg/320px-Flag_of_Greece.svg.png");
        IMAGE_URLS.put("德国", "https://upload.wikimedia.org/wikipedia/commons/thumb/b/ba/Flag_of_Germany.svg/320px-Flag_of_Germany.svg.png");
        IMAGE_URLS.put("西班牙", "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9a/Flag_of_Spain.svg/320px-Flag_of_Spain.svg.png");
        IMAGE_URLS.put("英国", "https://upload.wikimedia.org/wikipedia/commons/thumb/a/ae/Flag_of_the_United_Kingdom.svg/320px-Flag_of_the_United_Kingdom.svg.png");
        
        // 美洲认证
        IMAGE_URLS.put("美国", "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a4/Flag_of_the_United_States.svg/320px-Flag_of_the_United_States.svg.png");
        IMAGE_URLS.put("墨西哥", "https://upload.wikimedia.org/wikipedia/commons/thumb/f/fc/Flag_of_Mexico.svg/320px-Flag_of_Mexico.svg.png");
        IMAGE_URLS.put("加拿大", "https://upload.wikimedia.org/wikipedia/commons/thumb/c/cf/Flag_of_Canada.svg/320px-Flag_of_Canada.svg.png");
        IMAGE_URLS.put("阿根廷", "https://upload.wikimedia.org/wikipedia/commons/thumb/1/1a/Flag_of_Argentina.svg/320px-Flag_of_Argentina.svg.png");
        IMAGE_URLS.put("巴西", "https://upload.wikimedia.org/wikipedia/commons/thumb/0/05/Flag_of_Brazil.svg/320px-Flag_of_Brazil.svg.png");
        
        // 亚洲认证
        IMAGE_URLS.put("中国", "https://upload.wikimedia.org/wikipedia/commons/thumb/f/fa/Flag_of_the_People%27s_Republic_of_China.svg/320px-Flag_of_the_People%27s_Republic_of_China.svg.png");
        IMAGE_URLS.put("日本", "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9e/Flag_of_Japan.svg/320px-Flag_of_Japan.svg.png");
        IMAGE_URLS.put("韩国", "https://upload.wikimedia.org/wikipedia/commons/thumb/0/09/Flag_of_South_Korea.svg/320px-Flag_of_South_Korea.svg.png");
        IMAGE_URLS.put("沙特", "https://upload.wikimedia.org/wikipedia/commons/thumb/0/0d/Flag_of_Saudi_Arabia.svg/320px-Flag_of_Saudi_Arabia.svg.png");
        IMAGE_URLS.put("俄罗斯", "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f3/Flag_of_Russia.svg/320px-Flag_of_Russia.svg.png");
        IMAGE_URLS.put("印度", "https://upload.wikimedia.org/wikipedia/commons/thumb/4/41/Flag_of_India.svg/320px-Flag_of_India.svg.png");
        IMAGE_URLS.put("新加坡", "https://upload.wikimedia.org/wikipedia/commons/thumb/4/48/Flag_of_Singapore.svg/320px-Flag_of_Singapore.svg.png");
        IMAGE_URLS.put("马来西亚", "https://upload.wikimedia.org/wikipedia/commons/thumb/6/66/Flag_of_Malaysia.svg/320px-Flag_of_Malaysia.svg.png");
        IMAGE_URLS.put("以色列", "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d4/Flag_of_Israel.svg/320px-Flag_of_Israel.svg.png");
        
        // 澳洲认证
        IMAGE_URLS.put("澳洲", "https://upload.wikimedia.org/wikipedia/commons/thumb/8/88/Flag_of_Australia.svg/320px-Flag_of_Australia.svg.png");
        
        // 国际认证
        IMAGE_URLS.put("国际", "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2f/Flag_of_the_United_Nations.svg/320px-Flag_of_the_United_Nations.svg.png");
        
        // 二级分类图标（使用通用认证图标）
        IMAGE_URLS.put("欧洲认证", "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b7/Flag_of_Europe.svg/320px-Flag_of_Europe.svg.png");
        IMAGE_URLS.put("美洲认证", "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a4/Flag_of_the_United_States.svg/320px-Flag_of_the_United_States.svg.png");
        IMAGE_URLS.put("亚洲认证", "https://upload.wikimedia.org/wikipedia/commons/thumb/f/fa/Flag_of_the_People%27s_Republic_of_China.svg/320px-Flag_of_the_People%27s_Republic_of_China.svg.png");
        IMAGE_URLS.put("澳洲认证", "https://upload.wikimedia.org/wikipedia/commons/thumb/8/88/Flag_of_Australia.svg/320px-Flag_of_Australia.svg.png");
        IMAGE_URLS.put("国际认证", "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2f/Flag_of_the_United_Nations.svg/320px-Flag_of_the_United_Nations.svg.png");
        IMAGE_URLS.put("能效认证", "https://upload.wikimedia.org/wikipedia/commons/thumb/e/ec/Energy_efficiency_label.svg/320px-Energy_efficiency_label.svg.png");
        IMAGE_URLS.put("体系认证", "https://upload.wikimedia.org/wikipedia/commons/thumb/8/8c/ISO_9001_logo.svg/320px-ISO_9001_logo.svg.png");
        IMAGE_URLS.put("可靠性测试", "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9a/Test_tube_icon.svg/320px-Test_tube_icon.svg.png");
        IMAGE_URLS.put("电池检测", "https://upload.wikimedia.org/wikipedia/commons/thumb/4/4a/Battery_icon.svg/320px-Battery_icon.svg.png");
        IMAGE_URLS.put("化学检测", "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9a/Test_tube_icon.svg/320px-Test_tube_icon.svg.png");
    }
    
    /**
     * 初始化认证描述信息
     */
    private static void initDescriptions() {
        // 欧洲认证描述
        DESCRIPTIONS_CN.put("欧盟", "CE认证是欧盟的强制性产品安全认证，适用于进入欧洲经济区（EEA）的大部分产品。CE标志表明产品符合欧盟相关指令的基本要求。");
        DESCRIPTIONS_EN.put("欧盟", "CE marking is a mandatory conformity marking for products sold in the European Economic Area (EEA). It indicates that products comply with EU safety, health, and environmental protection requirements.");
        
        DESCRIPTIONS_CN.put("德国", "GS认证是德国安全认证标志，由德国技术监督协会（TÜV）等机构颁发，表明产品符合德国安全标准。");
        DESCRIPTIONS_EN.put("德国", "GS (Geprüfte Sicherheit) is a German safety certification mark issued by organizations like TÜV, indicating products meet German safety standards.");
        
        DESCRIPTIONS_CN.put("英国", "UKCA认证是英国脱欧后的新认证标志，适用于在英国市场销售的产品，替代原有的CE标志。");
        DESCRIPTIONS_EN.put("英国", "UKCA (UK Conformity Assessed) marking is the new product marking for products sold in Great Britain, replacing CE marking after Brexit.");
        
        // 美洲认证描述
        DESCRIPTIONS_CN.put("美国", "FCC认证是美国联邦通信委员会的强制性认证，适用于所有可能产生射频能量的电子设备。UL认证是美国保险商实验室的安全认证。");
        DESCRIPTIONS_EN.put("美国", "FCC certification is mandatory for electronic devices that may emit radio frequency energy. UL certification is a safety certification from Underwriters Laboratories.");
        
        DESCRIPTIONS_CN.put("加拿大", "IC认证是加拿大工业部的强制性认证，适用于在加拿大销售的所有无线电设备。CSA认证是加拿大标准协会的安全认证。");
        DESCRIPTIONS_EN.put("加拿大", "IC certification is mandatory for all radio equipment sold in Canada. CSA certification is a safety certification from the Canadian Standards Association.");
        
        // 亚洲认证描述
        DESCRIPTIONS_CN.put("中国", "CCC认证是中国强制性产品认证，适用于进入中国市场的特定产品类别。CQC认证是中国质量认证中心的自愿性认证。");
        DESCRIPTIONS_EN.put("中国", "CCC (China Compulsory Certification) is mandatory for specific product categories entering the Chinese market. CQC is a voluntary certification from China Quality Certification Centre.");
        
        DESCRIPTIONS_CN.put("日本", "PSE认证是日本电气用品安全法的强制性认证，适用于在日本销售的大部分电气产品。VCCI认证是日本针对信息技术设备的自愿性认证。");
        DESCRIPTIONS_EN.put("日本", "PSE (Product Safety of Electrical Appliance & Materials) certification is mandatory for most electrical products sold in Japan. VCCI is a voluntary certification for IT equipment.");
        
        DESCRIPTIONS_CN.put("韩国", "KC认证是韩国知识经济部的强制性认证，适用于在韩国销售的大部分电子和电气产品。");
        DESCRIPTIONS_EN.put("韩国", "KC (Korea Certification) marking is mandatory for most electronic and electrical products sold in South Korea.");
        
        // 其他认证描述
        DESCRIPTIONS_CN.put("能效认证", "能效认证是对产品能源效率的评估和认证，包括欧盟ErP指令、美国Energy Star、中国能效标识等。");
        DESCRIPTIONS_EN.put("能效认证", "Energy efficiency certification evaluates and certifies product energy efficiency, including EU ErP Directive, US Energy Star, China Energy Label, etc.");
        
        DESCRIPTIONS_CN.put("体系认证", "体系认证包括ISO 9001质量管理体系、ISO 14001环境管理体系、ISO 45001职业健康安全管理体系等。");
        DESCRIPTIONS_EN.put("体系认证", "System certification includes ISO 9001 Quality Management System, ISO 14001 Environmental Management System, ISO 45001 Occupational Health and Safety Management System, etc.");
        
        DESCRIPTIONS_CN.put("可靠性测试", "可靠性测试是对产品在预期使用条件下性能稳定性的测试，包括环境测试、寿命测试、加速老化测试等。");
        DESCRIPTIONS_EN.put("可靠性测试", "Reliability testing evaluates product performance stability under expected use conditions, including environmental testing, life testing, accelerated aging testing, etc.");
        
        DESCRIPTIONS_CN.put("电池检测", "电池检测包括电池安全测试、性能测试、环境适应性测试等，确保电池产品符合相关安全标准。");
        DESCRIPTIONS_EN.put("电池检测", "Battery testing includes safety testing, performance testing, environmental adaptability testing, ensuring batteries meet relevant safety standards.");
        
        DESCRIPTIONS_CN.put("化学检测", "化学检测包括RoHS检测、REACH检测、有害物质检测等，确保产品符合环保法规要求。");
        DESCRIPTIONS_EN.put("化学检测", "Chemical testing includes RoHS testing, REACH testing, hazardous substance testing, ensuring products comply with environmental regulations.");
    }
    
    /**
     * 下载图片
     */
    public static void downloadImage(String imageUrl, String savePath) throws IOException {
        if (imageUrl == null || imageUrl.isEmpty()) {
            System.out.println("图片URL为空，跳过下载: " + savePath);
            return;
        }
        
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36");
            
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // 创建目录
                File file = new File(savePath);
                file.getParentFile().mkdirs();
                
                // 下载文件
                try (InputStream inputStream = connection.getInputStream();
                     FileOutputStream outputStream = new FileOutputStream(file)) {
                    
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                }
                
                System.out.println("下载成功: " + savePath);
            } else {
                System.out.println("下载失败，HTTP状态码: " + responseCode + ", URL: " + imageUrl);
            }
        } catch (Exception e) {
            System.out.println("下载图片失败: " + imageUrl + ", 错误: " + e.getMessage());
        }
    }
    
    /**
     * 生成SQL插入语句
     * 注意：由于使用AUTO_INCREMENT，parent_id需要使用LAST_INSERT_ID()或者先查询ID
     * 这里使用变量方式，实际执行时需要按顺序执行或使用存储过程
     */
    public static String generateSQL() {
        StringBuilder sql = new StringBuilder();
        sql.append("-- 认证分类数据导入SQL\n");
        sql.append("-- 生成时间: ").append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\n");
        sql.append("-- 注意：请先清空表或确保ID从1开始，然后按顺序执行以下SQL\n\n");
        
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // 一级分类：认证服务 (假设ID=1)
        sql.append("-- ============================================\n");
        sql.append("-- 一级分类：认证服务\n");
        sql.append("-- ============================================\n");
        sql.append("INSERT INTO certification_category (name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES\n");
        sql.append("('认证服务', 'Certification Services', NULL, 0, 1, '").append(now).append("', '").append(now).append("');\n");
        sql.append("SET @parent_id_1 = LAST_INSERT_ID();\n\n");
        
        // 二级分类 (假设ID从2开始)
        List<CategoryInfo> level2Categories = Arrays.asList(
            new CategoryInfo("欧洲认证", "European Certification", "欧洲认证", 1),
            new CategoryInfo("美洲认证", "American Certification", "美洲认证", 2),
            new CategoryInfo("亚洲认证", "Asian Certification", "亚洲认证", 3),
            new CategoryInfo("澳洲认证", "Australian Certification", "澳洲认证", 4),
            new CategoryInfo("国际认证", "International Certification", "国际认证", 5),
            new CategoryInfo("能效认证", "Energy Efficiency Certification", "能效认证", 6),
            new CategoryInfo("体系认证", "System Certification", "体系认证", 7),
            new CategoryInfo("可靠性测试", "Reliability Testing", "可靠性测试", 8),
            new CategoryInfo("电池检测", "Battery Testing", "电池检测", 9),
            new CategoryInfo("化学检测", "Chemical Testing", "化学检测", 10)
        );
        
        sql.append("-- ============================================\n");
        sql.append("-- 二级分类\n");
        sql.append("-- ============================================\n");
        for (int i = 0; i < level2Categories.size(); i++) {
            CategoryInfo cat = level2Categories.get(i);
            String iconPath = cat.iconName != null ? ("icon/certification/" + cat.iconName + ".png") : "NULL";
            sql.append("INSERT INTO certification_category (name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES\n");
            sql.append("('").append(cat.nameCn).append("', '").append(cat.nameEn).append("', '")
               .append(iconPath != null && !iconPath.equals("NULL") ? iconPath : "NULL").append("', @parent_id_1, ")
               .append(cat.sortOrder).append(", '").append(now).append("', '").append(now).append("');\n");
            sql.append("SET @parent_id_").append(i + 2).append(" = LAST_INSERT_ID();\n");
        }
        sql.append("\n");
        
        // 三级分类：欧洲认证下的国家
        List<CategoryInfo> europeCountries = Arrays.asList(
            new CategoryInfo("欧盟", "European Union", "欧盟", 1),
            new CategoryInfo("匈牙利", "Hungary", "匈牙利", 2),
            new CategoryInfo("土耳其", "Turkey", "土耳其", 3),
            new CategoryInfo("爱尔兰", "Ireland", "爱尔兰", 4),
            new CategoryInfo("斯洛文尼亚", "Slovenia", "斯洛文尼亚", 5),
            new CategoryInfo("乌克兰", "Ukraine", "乌克兰", 6),
            new CategoryInfo("捷克", "Czech Republic", "捷克", 7),
            new CategoryInfo("斯洛伐克", "Slovakia", "斯洛伐克", 8),
            new CategoryInfo("希腊", "Greece", "希腊", 9),
            new CategoryInfo("德国", "Germany", "德国", 10),
            new CategoryInfo("西班牙", "Spain", "西班牙", 11),
            new CategoryInfo("英国", "United Kingdom", "英国", 12)
        );
        
        sql.append("-- ============================================\n");
        sql.append("-- 三级分类：欧洲认证 (@parent_id_2)\n");
        sql.append("-- ============================================\n");
        for (CategoryInfo cat : europeCountries) {
            String iconPath = cat.iconName != null ? ("icon/certification/" + cat.iconName + ".png") : "NULL";
            sql.append("INSERT INTO certification_category (name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES\n");
            sql.append("('").append(cat.nameCn).append("', '").append(cat.nameEn).append("', '")
               .append(iconPath != null && !iconPath.equals("NULL") ? iconPath : "NULL").append("', @parent_id_2, ")
               .append(cat.sortOrder).append(", '").append(now).append("', '").append(now).append("');\n");
        }
        sql.append("\n");
        
        // 三级分类：美洲认证下的国家
        List<CategoryInfo> americaCountries = Arrays.asList(
            new CategoryInfo("美国", "United States", "美国", 1),
            new CategoryInfo("墨西哥", "Mexico", "墨西哥", 2),
            new CategoryInfo("加拿大", "Canada", "加拿大", 3),
            new CategoryInfo("阿根廷", "Argentina", "阿根廷", 4),
            new CategoryInfo("巴西", "Brazil", "巴西", 5)
        );
        
        sql.append("-- ============================================\n");
        sql.append("-- 三级分类：美洲认证 (@parent_id_3)\n");
        sql.append("-- ============================================\n");
        for (CategoryInfo cat : americaCountries) {
            String iconPath = cat.iconName != null ? ("icon/certification/" + cat.iconName + ".png") : "NULL";
            sql.append("INSERT INTO certification_category (name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES\n");
            sql.append("('").append(cat.nameCn).append("', '").append(cat.nameEn).append("', '")
               .append(iconPath != null && !iconPath.equals("NULL") ? iconPath : "NULL").append("', @parent_id_3, ")
               .append(cat.sortOrder).append(", '").append(now).append("', '").append(now).append("');\n");
        }
        sql.append("\n");
        
        // 三级分类：亚洲认证下的国家
        List<CategoryInfo> asiaCountries = Arrays.asList(
            new CategoryInfo("中国", "China", "中国", 1),
            new CategoryInfo("日本", "Japan", "日本", 2),
            new CategoryInfo("韩国", "South Korea", "韩国", 3),
            new CategoryInfo("沙特", "Saudi Arabia", "沙特", 4),
            new CategoryInfo("俄罗斯", "Russia", "俄罗斯", 5),
            new CategoryInfo("印度", "India", "印度", 6),
            new CategoryInfo("新加坡", "Singapore", "新加坡", 7),
            new CategoryInfo("马来西亚", "Malaysia", "马来西亚", 8),
            new CategoryInfo("以色列", "Israel", "以色列", 9)
        );
        
        sql.append("-- ============================================\n");
        sql.append("-- 三级分类：亚洲认证 (@parent_id_4)\n");
        sql.append("-- ============================================\n");
        for (CategoryInfo cat : asiaCountries) {
            String iconPath = cat.iconName != null ? ("icon/certification/" + cat.iconName + ".png") : "NULL";
            sql.append("INSERT INTO certification_category (name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES\n");
            sql.append("('").append(cat.nameCn).append("', '").append(cat.nameEn).append("', '")
               .append(iconPath != null && !iconPath.equals("NULL") ? iconPath : "NULL").append("', @parent_id_4, ")
               .append(cat.sortOrder).append(", '").append(now).append("', '").append(now).append("');\n");
        }
        sql.append("\n");
        
        // 三级分类：澳洲认证
        sql.append("-- ============================================\n");
        sql.append("-- 三级分类：澳洲认证 (@parent_id_5)\n");
        sql.append("-- ============================================\n");
        CategoryInfo australia = new CategoryInfo("澳洲", "Australia", "澳洲", 1);
        String iconPath = australia.iconName != null ? ("icon/certification/" + australia.iconName + ".png") : "NULL";
        sql.append("INSERT INTO certification_category (name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES\n");
        sql.append("('").append(australia.nameCn).append("', '").append(australia.nameEn).append("', '")
           .append(iconPath != null && !iconPath.equals("NULL") ? iconPath : "NULL").append("', @parent_id_5, ")
           .append(australia.sortOrder).append(", '").append(now).append("', '").append(now).append("');\n\n");
        
        // 三级分类：国际认证
        sql.append("-- ============================================\n");
        sql.append("-- 三级分类：国际认证 (@parent_id_6)\n");
        sql.append("-- ============================================\n");
        CategoryInfo international = new CategoryInfo("国际", "International", "国际", 1);
        iconPath = international.iconName != null ? ("icon/certification/" + international.iconName + ".png") : "NULL";
        sql.append("INSERT INTO certification_category (name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES\n");
        sql.append("('").append(international.nameCn).append("', '").append(international.nameEn).append("', '")
           .append(iconPath != null && !iconPath.equals("NULL") ? iconPath : "NULL").append("', @parent_id_6, ")
           .append(international.sortOrder).append(", '").append(now).append("', '").append(now).append("');\n");
        
        return sql.toString();
    }
    
    /**
     * 下载所有认证图片
     */
    public static void downloadAllImages() {
        System.out.println("开始下载认证图片...\n");
        
        // 创建保存目录
        File dir = new File(IMAGE_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        
        // 下载所有图片
        for (Map.Entry<String, String> entry : IMAGE_URLS.entrySet()) {
            String name = entry.getKey();
            String url = entry.getValue();
            String fileName = name + ".png";
            String savePath = IMAGE_DIR + fileName;
            
            try {
                downloadImage(url, savePath);
                Thread.sleep(500); // 避免请求过快
            } catch (Exception e) {
                System.err.println("下载失败: " + name + ", 错误: " + e.getMessage());
            }
        }
        
        System.out.println("\n图片下载完成！");
    }
    
    /**
     * 生成固定ID版本的SQL（更简单直接，适合直接导入）
     */
    public static String generateSQLWithFixedIds() {
        StringBuilder sql = new StringBuilder();
        sql.append("-- 认证分类数据导入SQL（固定ID版本）\n");
        sql.append("-- 生成时间: ").append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\n");
        sql.append("-- 注意：请先清空表或使用TRUNCATE TABLE certification_category;\n");
        sql.append("-- 然后设置AUTO_INCREMENT起始值：ALTER TABLE certification_category AUTO_INCREMENT = 1;\n\n");
        
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // 一级分类：认证服务 (ID=1)
        sql.append("-- ============================================\n");
        sql.append("-- 一级分类：认证服务 (ID=1)\n");
        sql.append("-- ============================================\n");
        sql.append("INSERT INTO certification_category (id, name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES\n");
        sql.append("(1, '认证服务', 'Certification Services', NULL, 0, 1, '").append(now).append("', '").append(now).append("');\n\n");
        
        // 二级分类 (ID=2-11)
        List<CategoryInfo> level2Categories = Arrays.asList(
            new CategoryInfo("欧洲认证", "European Certification", "欧洲认证", 1),
            new CategoryInfo("美洲认证", "American Certification", "美洲认证", 2),
            new CategoryInfo("亚洲认证", "Asian Certification", "亚洲认证", 3),
            new CategoryInfo("澳洲认证", "Australian Certification", "澳洲认证", 4),
            new CategoryInfo("国际认证", "International Certification", "国际认证", 5),
            new CategoryInfo("能效认证", "Energy Efficiency Certification", "能效认证", 6),
            new CategoryInfo("体系认证", "System Certification", "体系认证", 7),
            new CategoryInfo("可靠性测试", "Reliability Testing", "可靠性测试", 8),
            new CategoryInfo("电池检测", "Battery Testing", "电池检测", 9),
            new CategoryInfo("化学检测", "Chemical Testing", "化学检测", 10)
        );
        
        sql.append("-- ============================================\n");
        sql.append("-- 二级分类 (ID=2-11)\n");
        sql.append("-- ============================================\n");
        for (int i = 0; i < level2Categories.size(); i++) {
            CategoryInfo cat = level2Categories.get(i);
            String iconPath = cat.iconName != null ? ("icon/certification/" + cat.iconName + ".png") : "NULL";
            sql.append("INSERT INTO certification_category (id, name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES\n");
            sql.append("(").append(i + 2).append(", '").append(cat.nameCn).append("', '").append(cat.nameEn).append("', ");
            if (iconPath != null && !iconPath.equals("NULL")) {
                sql.append("'").append(iconPath).append("'");
            } else {
                sql.append("NULL");
            }
            sql.append(", 1, ").append(cat.sortOrder).append(", '").append(now).append("', '").append(now).append("');\n");
        }
        sql.append("\n");
        
        // 三级分类：欧洲认证下的国家 (ID=12-23, parent_id=2)
        List<CategoryInfo> europeCountries = Arrays.asList(
            new CategoryInfo("欧盟", "European Union", "欧盟", 1),
            new CategoryInfo("匈牙利", "Hungary", "匈牙利", 2),
            new CategoryInfo("土耳其", "Turkey", "土耳其", 3),
            new CategoryInfo("爱尔兰", "Ireland", "爱尔兰", 4),
            new CategoryInfo("斯洛文尼亚", "Slovenia", "斯洛文尼亚", 5),
            new CategoryInfo("乌克兰", "Ukraine", "乌克兰", 6),
            new CategoryInfo("捷克", "Czech Republic", "捷克", 7),
            new CategoryInfo("斯洛伐克", "Slovakia", "斯洛伐克", 8),
            new CategoryInfo("希腊", "Greece", "希腊", 9),
            new CategoryInfo("德国", "Germany", "德国", 10),
            new CategoryInfo("西班牙", "Spain", "西班牙", 11),
            new CategoryInfo("英国", "United Kingdom", "英国", 12)
        );
        
        sql.append("-- ============================================\n");
        sql.append("-- 三级分类：欧洲认证 (ID=12-23, parent_id=2)\n");
        sql.append("-- ============================================\n");
        for (int i = 0; i < europeCountries.size(); i++) {
            CategoryInfo cat = europeCountries.get(i);
            String iconPath = cat.iconName != null ? ("icon/certification/" + cat.iconName + ".png") : "NULL";
            sql.append("INSERT INTO certification_category (id, name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES\n");
            sql.append("(").append(i + 12).append(", '").append(cat.nameCn).append("', '").append(cat.nameEn).append("', ");
            if (iconPath != null && !iconPath.equals("NULL")) {
                sql.append("'").append(iconPath).append("'");
            } else {
                sql.append("NULL");
            }
            sql.append(", 2, ").append(cat.sortOrder).append(", '").append(now).append("', '").append(now).append("');\n");
        }
        sql.append("\n");
        
        // 三级分类：美洲认证下的国家 (ID=24-28, parent_id=3)
        List<CategoryInfo> americaCountries = Arrays.asList(
            new CategoryInfo("美国", "United States", "美国", 1),
            new CategoryInfo("墨西哥", "Mexico", "墨西哥", 2),
            new CategoryInfo("加拿大", "Canada", "加拿大", 3),
            new CategoryInfo("阿根廷", "Argentina", "阿根廷", 4),
            new CategoryInfo("巴西", "Brazil", "巴西", 5)
        );
        
        sql.append("-- ============================================\n");
        sql.append("-- 三级分类：美洲认证 (ID=24-28, parent_id=3)\n");
        sql.append("-- ============================================\n");
        for (int i = 0; i < americaCountries.size(); i++) {
            CategoryInfo cat = americaCountries.get(i);
            String iconPath = cat.iconName != null ? ("icon/certification/" + cat.iconName + ".png") : "NULL";
            sql.append("INSERT INTO certification_category (id, name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES\n");
            sql.append("(").append(i + 24).append(", '").append(cat.nameCn).append("', '").append(cat.nameEn).append("', ");
            if (iconPath != null && !iconPath.equals("NULL")) {
                sql.append("'").append(iconPath).append("'");
            } else {
                sql.append("NULL");
            }
            sql.append(", 3, ").append(cat.sortOrder).append(", '").append(now).append("', '").append(now).append("');\n");
        }
        sql.append("\n");
        
        // 三级分类：亚洲认证下的国家 (ID=29-37, parent_id=4)
        List<CategoryInfo> asiaCountries = Arrays.asList(
            new CategoryInfo("中国", "China", "中国", 1),
            new CategoryInfo("日本", "Japan", "日本", 2),
            new CategoryInfo("韩国", "South Korea", "韩国", 3),
            new CategoryInfo("沙特", "Saudi Arabia", "沙特", 4),
            new CategoryInfo("俄罗斯", "Russia", "俄罗斯", 5),
            new CategoryInfo("印度", "India", "印度", 6),
            new CategoryInfo("新加坡", "Singapore", "新加坡", 7),
            new CategoryInfo("马来西亚", "Malaysia", "马来西亚", 8),
            new CategoryInfo("以色列", "Israel", "以色列", 9)
        );
        
        sql.append("-- ============================================\n");
        sql.append("-- 三级分类：亚洲认证 (ID=29-37, parent_id=4)\n");
        sql.append("-- ============================================\n");
        for (int i = 0; i < asiaCountries.size(); i++) {
            CategoryInfo cat = asiaCountries.get(i);
            String iconPath = cat.iconName != null ? ("icon/certification/" + cat.iconName + ".png") : "NULL";
            sql.append("INSERT INTO certification_category (id, name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES\n");
            sql.append("(").append(i + 29).append(", '").append(cat.nameCn).append("', '").append(cat.nameEn).append("', ");
            if (iconPath != null && !iconPath.equals("NULL")) {
                sql.append("'").append(iconPath).append("'");
            } else {
                sql.append("NULL");
            }
            sql.append(", 4, ").append(cat.sortOrder).append(", '").append(now).append("', '").append(now).append("');\n");
        }
        sql.append("\n");
        
        // 三级分类：澳洲认证 (ID=38, parent_id=5)
        sql.append("-- ============================================\n");
        sql.append("-- 三级分类：澳洲认证 (ID=38, parent_id=5)\n");
        sql.append("-- ============================================\n");
        CategoryInfo australia = new CategoryInfo("澳洲", "Australia", "澳洲", 1);
        String iconPath = australia.iconName != null ? ("icon/certification/" + australia.iconName + ".png") : "NULL";
        sql.append("INSERT INTO certification_category (id, name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES\n");
        sql.append("(38, '").append(australia.nameCn).append("', '").append(australia.nameEn).append("', ");
        if (iconPath != null && !iconPath.equals("NULL")) {
            sql.append("'").append(iconPath).append("'");
        } else {
            sql.append("NULL");
        }
        sql.append(", 5, ").append(australia.sortOrder).append(", '").append(now).append("', '").append(now).append("');\n\n");
        
        // 三级分类：国际认证 (ID=39, parent_id=6)
        sql.append("-- ============================================\n");
        sql.append("-- 三级分类：国际认证 (ID=39, parent_id=6)\n");
        sql.append("-- ============================================\n");
        CategoryInfo international = new CategoryInfo("国际", "International", "国际", 1);
        iconPath = international.iconName != null ? ("icon/certification/" + international.iconName + ".png") : "NULL";
        sql.append("INSERT INTO certification_category (id, name_cn, name_en, icon, parent_id, sort_order, create_time, update_time) VALUES\n");
        sql.append("(39, '").append(international.nameCn).append("', '").append(international.nameEn).append("', ");
        if (iconPath != null && !iconPath.equals("NULL")) {
            sql.append("'").append(iconPath).append("'");
        } else {
            sql.append("NULL");
        }
        sql.append(", 6, ").append(international.sortOrder).append(", '").append(now).append("', '").append(now).append("');\n");
        
        return sql.toString();
    }
    
    /**
     * Main方法
     */
    public static void main(String[] args) {
        System.out.println("=== 认证分类数据导入工具 ===\n");
        
        // 1. 下载图片
        System.out.println("步骤1: 下载认证图片");
        downloadAllImages();
        
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 2. 生成SQL（使用LAST_INSERT_ID版本）
        System.out.println("步骤2: 生成SQL导入语句（LAST_INSERT_ID版本）");
        String sql1 = generateSQL();
        
        // 保存SQL到文件
        try {
            String sqlFile1 = "certification_category_import.sql";
            Files.write(Paths.get(sqlFile1), sql1.getBytes("UTF-8"));
            System.out.println("SQL文件已生成: " + sqlFile1);
        } catch (IOException e) {
            System.err.println("保存SQL文件失败: " + e.getMessage());
        }
        
        // 3. 生成固定ID版本SQL（推荐使用）
        System.out.println("\n步骤3: 生成SQL导入语句（固定ID版本，推荐）");
        String sql2 = generateSQLWithFixedIds();
        
        try {
            String sqlFile2 = "certification_category_import_fixed_ids.sql";
            Files.write(Paths.get(sqlFile2), sql2.getBytes("UTF-8"));
            System.out.println("SQL文件已生成: " + sqlFile2);
            System.out.println("\nSQL内容预览（前800字符）:");
            System.out.println(sql2.substring(0, Math.min(800, sql2.length())) + "...");
        } catch (IOException e) {
            System.err.println("保存SQL文件失败: " + e.getMessage());
        }
        
        System.out.println("\n完成！");
        System.out.println("\n使用说明：");
        System.out.println("1. 图片已下载到: " + IMAGE_DIR);
        System.out.println("2. 推荐使用固定ID版本的SQL文件: certification_category_import_fixed_ids.sql");
        System.out.println("3. 执行SQL前请先备份数据库，然后执行：");
        System.out.println("   TRUNCATE TABLE certification_category;");
        System.out.println("   ALTER TABLE certification_category AUTO_INCREMENT = 1;");
        System.out.println("   然后执行生成的SQL文件");
    }
    
    /**
     * 分类信息内部类
     */
    static class CategoryInfo {
        String nameCn;
        String nameEn;
        String iconName;
        int sortOrder;
        
        CategoryInfo(String nameCn, String nameEn, String iconName, int sortOrder) {
            this.nameCn = nameCn;
            this.nameEn = nameEn;
            this.iconName = iconName;
            this.sortOrder = sortOrder;
        }
    }
}

