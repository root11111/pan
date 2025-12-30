package com.zht.util;

import com.zht.entity.CertificationService;
import com.zht.service.CertificationServiceService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 网页爬虫工具类
 * 用于从网站抓取认证服务数据并导入数据库
 */
@Component
public class WebScraper {
    
    private static final String UPLOAD_DIR = "D:/workspace/pan/uploads/";
    private static final String BASE_URL = "http://www.zht-lab.cn";
    
    @Autowired
    private CertificationServiceService certificationServiceService;
    
    /**
     * 从指定URL抓取认证服务数据
     * @param url 网页URL
     * @param categoryId 分类ID（欧洲认证为2）
     * @return 抓取到的服务列表
     */
    public List<CertificationService> scrapeCertificationServices(String url, Long categoryId) {
        List<CertificationService> services = new ArrayList<>();
        
        try {
            System.out.println("开始抓取网页: " + url);
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                    .timeout(10000)
                    .get();
            
            // 查找所有服务项
            // 根据网页结构，服务可能在特定的div或列表中
            Elements serviceElements = doc.select("div.service-item, div.cert-item, li.service, .service-list > div");
            
            // 如果上面的选择器没找到，尝试更通用的方式
            if (serviceElements.isEmpty()) {
                // 查找包含服务名称的元素（通常是h2, h3, h4或strong标签）
                Elements headings = doc.select("h2, h3, h4, h5, strong");
                for (Element heading : headings) {
                    String text = heading.text().trim();
                    // 过滤掉导航、标题等无关内容
                    if (text.length() > 2 && text.length() < 50 && 
                        !text.contains("首页") && !text.contains("关于我们") && 
                        !text.contains("认证服务") && !text.contains("欧洲认证")) {
                        
                        // 查找该标题下的内容
                        Element serviceElement = heading.parent();
                        if (serviceElement == null) {
                            serviceElement = heading.nextElementSibling();
                        }
                        
                        CertificationService service = extractServiceFromElement(heading, serviceElement, categoryId);
                        if (service != null && service.getNameCn() != null && !service.getNameCn().isEmpty()) {
                            services.add(service);
                        }
                    }
                }
            } else {
                // 如果找到了服务元素，直接解析
                for (Element element : serviceElements) {
                    CertificationService service = extractServiceFromElement(element, element, categoryId);
                    if (service != null && service.getNameCn() != null && !service.getNameCn().isEmpty()) {
                        services.add(service);
                    }
                }
            }
            
            // 如果还是没找到，尝试解析整个内容区域
            if (services.isEmpty()) {
                // 尝试多种可能的内容区域选择器
                Element contentArea = doc.selectFirst("div.content, div.main-content, #content, .article-content, .main, .container, body");
                if (contentArea != null) {
                    services = parseContentArea(contentArea, categoryId);
                } else {
                    // 如果还是找不到，直接解析body
                    services = parseContentArea(doc.body(), categoryId);
                }
            }
            
            System.out.println("共抓取到 " + services.size() + " 个服务");
            
        } catch (Exception e) {
            System.err.println("抓取网页失败: " + e.getMessage());
            e.printStackTrace();
        }
        
        return services;
    }
    
    /**
     * 解析内容区域，提取服务信息
     */
    private List<CertificationService> parseContentArea(Element contentArea, Long categoryId) {
        List<CertificationService> services = new ArrayList<>();
        
        // 首先查找所有图片（包括页面中的图片）
        Elements allImages = contentArea.select("img");
        List<String> imageUrls = new ArrayList<>();
        for (Element img : allImages) {
            String src = img.attr("src");
            if (src != null && !src.isEmpty() && !src.contains("logo") && !src.contains("banner")) {
                if (!src.startsWith("http")) {
                    src = BASE_URL + (src.startsWith("/") ? "" : "/") + src;
                }
                imageUrls.add(src);
                System.out.println("找到图片: " + src);
            }
        }
        
        // 查找所有段落和div，识别服务名称和描述
        Elements allElements = contentArea.select("p, div, span, li, td");
        
        String currentName = null;
        StringBuilder currentDescription = new StringBuilder();
        String currentImage = null;
        boolean isCollectingDescription = false;
        int imageIndex = 0;
        
        for (Element element : allElements) {
            String text = element.text().trim();
            String html = element.html();
            
            // 跳过导航、标题等无关内容
            if (text.contains("首页") || text.contains("关于我们") || 
                text.contains("认证服务") || text.contains("欧洲认证") ||
                text.contains("实验室展示") || text.contains("荣誉资质") ||
                text.contains("证书查询") || text.contains("新闻资讯") ||
                text.contains("在线留言") || text.contains("联系我们") ||
                text.length() < 3) {
                continue;
            }
            
            // 检测服务名称（通常是短文本，包含"认证"字样）
            // 名称可能是：CE认证、MEEI认证等，可能重复出现
            Pattern namePattern = Pattern.compile("([A-Z]{2,10}认证)");
            Matcher nameMatcher = namePattern.matcher(text);
            if (nameMatcher.find() && text.length() <= 50) {
                // 提取名称（去除重复）
                String name = extractServiceName(text);
                
                // 如果之前有服务，先保存
                if (currentName != null && !currentName.isEmpty()) {
                    // 如果没有描述但有名称，也保存（可能描述在HTML中）
                    String desc = currentDescription.length() > 0 ? currentDescription.toString() : "";
                    if (desc.isEmpty()) {
                        // 尝试从HTML中提取
                        desc = extractDescriptionFromHtml(element);
                    }
                    CertificationService service = createService(currentName, desc, currentImage, categoryId);
                    if (service != null) {
                        services.add(service);
                        System.out.println("提取服务: " + currentName + ", 描述长度: " + desc.length() + ", 图片: " + currentImage);
                    }
                }
                
                // 开始新的服务
                currentName = name;
                currentDescription = new StringBuilder();
                currentImage = null;
                isCollectingDescription = true;
                
                // 尝试从当前元素或附近元素查找图片
                Element parent = element.parent();
                if (parent != null) {
                    Elements nearbyImages = parent.select("img");
                    if (nearbyImages.isEmpty()) {
                        nearbyImages = element.select("img");
                    }
                    if (!nearbyImages.isEmpty()) {
                        String src = nearbyImages.first().attr("src");
                        if (src != null && !src.isEmpty()) {
                            if (!src.startsWith("http")) {
                                src = BASE_URL + (src.startsWith("/") ? "" : "/") + src;
                            }
                            currentImage = src;
                        }
                    }
                }
                
                // 如果没有找到图片，使用全局图片列表
                if (currentImage == null && imageIndex < imageUrls.size()) {
                    currentImage = imageUrls.get(imageIndex);
                    imageIndex++;
                }
            } 
            // 如果是描述内容（长文本，不包含服务名称模式）
            else if (isCollectingDescription && text.length() > 15 && !text.matches(".*[A-Z]{2,10}认证.*")) {
                if (currentDescription.length() > 0) {
                    currentDescription.append("\n");
                }
                currentDescription.append(text);
            }
            
            // 在元素内部查找图片
            Elements images = element.select("img");
            for (Element img : images) {
                String src = img.attr("src");
                if (src != null && !src.isEmpty() && !src.contains("logo") && !src.contains("banner")) {
                    if (!src.startsWith("http")) {
                        src = BASE_URL + (src.startsWith("/") ? "" : "/") + src;
                    }
                    if (currentImage == null || !currentImage.equals(src)) {
                        currentImage = src;
                    }
                }
            }
        }
        
        // 保存最后一个服务
        if (currentName != null && !currentName.isEmpty()) {
            String desc = currentDescription.length() > 0 ? currentDescription.toString() : "";
            CertificationService service = createService(currentName, desc, currentImage, categoryId);
            if (service != null) {
                services.add(service);
                System.out.println("提取服务: " + currentName + ", 描述长度: " + desc.length() + ", 图片: " + currentImage);
            }
        }
        
        // 如果上面的方法没找到，尝试更通用的方法
        if (services.isEmpty()) {
            services = parseByTextPattern(contentArea, categoryId);
        }
        
        return services;
    }
    
    /**
     * 从HTML中提取描述内容
     */
    private String extractDescriptionFromHtml(Element element) {
        StringBuilder desc = new StringBuilder();
        Element next = element.nextElementSibling();
        int count = 0;
        while (next != null && count < 3) {
            String text = next.text().trim();
            if (text.length() > 15 && !text.matches(".*[A-Z]{2,10}认证.*")) {
                if (desc.length() > 0) {
                    desc.append("\n");
                }
                desc.append(text);
            }
            next = next.nextElementSibling();
            count++;
        }
        return desc.toString();
    }
    
    /**
     * 从文本中提取服务名称（去除重复）
     */
    private String extractServiceName(String text) {
        // 如果名称重复了（如：CE认证CE认证），只取一次
        if (text.matches("(.+)\\1")) {
            return text.substring(0, text.length() / 2).trim();
        }
        // 提取认证名称部分
        Pattern pattern = Pattern.compile("([A-Z]{2,10}认证)");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return text.trim();
    }
    
    /**
     * 使用文本模式匹配解析服务
     */
    private List<CertificationService> parseByTextPattern(Element contentArea, Long categoryId) {
        List<CertificationService> services = new ArrayList<>();
        String fullText = contentArea.text();
        
        // 根据实际网页内容，服务名称模式：XXX认证XXX认证（可能重复）
        // 然后跟着描述
        Pattern pattern = Pattern.compile("([A-Z]{2,10}认证)(?:\\s*\\1)?\\s+([^A-Z]{20,}?)(?=(?:[A-Z]{2,10}认证)|$)");
        Matcher matcher = pattern.matcher(fullText);
        
        while (matcher.find()) {
            String name = matcher.group(1);
            String description = matcher.group(2).trim();
            
            if (description.length() > 10) {
                CertificationService service = createService(name, description, null, categoryId);
                if (service != null) {
                    services.add(service);
                }
            }
        }
        
        return services;
    }
    
    /**
     * 从元素中提取服务信息
     */
    private CertificationService extractServiceFromElement(Element titleElement, Element contentElement, Long categoryId) {
        if (titleElement == null) {
            return null;
        }
        
        String name = titleElement.text().trim();
        if (name.isEmpty() || name.length() > 50) {
            return null;
        }
        
        // 提取描述
        StringBuilder description = new StringBuilder();
        Element next = titleElement.nextElementSibling();
        int count = 0;
        while (next != null && count < 5) {
            String text = next.text().trim();
            if (!text.isEmpty() && text.length() > 10) {
                if (description.length() > 0) {
                    description.append("\n");
                }
                description.append(text);
            }
            next = next.nextElementSibling();
            count++;
        }
        
        // 如果contentElement存在，也从中提取
        if (contentElement != null && contentElement != titleElement) {
            String contentText = contentElement.text().trim();
            if (!contentText.isEmpty() && contentText.length() > 10) {
                if (description.length() > 0) {
                    description.append("\n");
                }
                description.append(contentText);
            }
        }
        
        // 提取图片
        String imageUrl = null;
        Elements images = titleElement.select("img");
        if (images.isEmpty() && contentElement != null) {
            images = contentElement.select("img");
        }
        if (!images.isEmpty()) {
            String src = images.first().attr("src");
            if (src != null && !src.isEmpty()) {
                if (!src.startsWith("http")) {
                    imageUrl = BASE_URL + (src.startsWith("/") ? "" : "/") + src;
                } else {
                    imageUrl = src;
                }
            }
        }
        
        return createService(name, description.toString(), imageUrl, categoryId);
    }
    
    /**
     * 创建服务对象
     */
    private CertificationService createService(String nameCn, String description, String imageUrl, Long categoryId) {
        if (nameCn == null || nameCn.trim().isEmpty()) {
            return null;
        }
        
        CertificationService service = new CertificationService();
        service.setNameCn(nameCn.trim());
        service.setNameEn(""); // 可以后续手动补充
        service.setCategoryId(categoryId);
        service.setParentId(0L);
        service.setDescriptionCn(description.trim());
        service.setDescriptionEn("");
        service.setSummaryCn(description.length() > 100 ? description.substring(0, 100) : description);
        service.setSummaryEn("");
        service.setSortOrder(0);
        service.setCreateTime(LocalDateTime.now());
        service.setUpdateTime(LocalDateTime.now());
        
        // 下载图片
        if (imageUrl != null && !imageUrl.isEmpty()) {
            try {
                System.out.println("准备下载图片: " + imageUrl);
                String imagePath = downloadImage(imageUrl);
                if (imagePath != null) {
                    service.setImage(imagePath);
                    System.out.println("图片下载成功: " + imagePath);
                } else {
                    System.err.println("图片下载失败，返回null");
                }
            } catch (Exception e) {
                System.err.println("下载图片失败: " + imageUrl + " - " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("服务 " + nameCn + " 没有图片URL");
        }
        
        return service;
    }
    
    /**
     * 下载图片
     */
    private String downloadImage(String imageUrl) {
        try {
            System.out.println("开始下载图片: " + imageUrl);
            
            // 确保URL完整
            if (!imageUrl.startsWith("http://") && !imageUrl.startsWith("https://")) {
                if (imageUrl.startsWith("/")) {
                    imageUrl = BASE_URL + imageUrl;
                } else {
                    imageUrl = BASE_URL + "/" + imageUrl;
                }
            }
            
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                boolean created = uploadDir.mkdirs();
                if (!created) {
                    System.err.println("无法创建上传目录: " + UPLOAD_DIR);
                    return null;
                }
            }
            
            String extension = ".jpg";
            if (imageUrl.contains(".")) {
                int lastDot = imageUrl.lastIndexOf(".");
                int queryIndex = imageUrl.indexOf("?", lastDot);
                String ext = queryIndex > 0 ? imageUrl.substring(lastDot, queryIndex) : imageUrl.substring(lastDot);
                if (ext.length() <= 5 && ext.matches("\\.[a-zA-Z]{2,4}")) {
                    extension = ext.toLowerCase();
                }
            }
            
            String filename = UUID.randomUUID().toString() + extension;
            String filePath = UPLOAD_DIR + filename;
            
            System.out.println("图片保存路径: " + filePath);
            
            URL url = new URL(imageUrl);
            try (InputStream in = url.openStream();
                 FileOutputStream out = new FileOutputStream(filePath)) {
                byte[] buffer = new byte[8192];
                int bytesRead;
                long totalBytes = 0;
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                    totalBytes += bytesRead;
                }
                System.out.println("图片下载完成，大小: " + totalBytes + " 字节");
            }
            
            String result = "/uploads/" + filename;
            System.out.println("图片路径: " + result);
            return result;
            
        } catch (Exception e) {
            System.err.println("下载图片失败: " + imageUrl);
            System.err.println("错误信息: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 保存服务到数据库
     */
    public void saveServices(List<CertificationService> services) {
        if (services == null || services.isEmpty()) {
            System.out.println("没有服务需要保存");
            return;
        }
        
        int successCount = 0;
        int failCount = 0;
        
        for (CertificationService service : services) {
            try {
                // 检查是否已存在（根据名称）
                List<CertificationService> existing = certificationServiceService.list()
                    .stream()
                    .filter(s -> s.getNameCn() != null && s.getNameCn().equals(service.getNameCn()))
                    .filter(s -> s.getCategoryId() != null && s.getCategoryId().equals(service.getCategoryId()))
                    .collect(java.util.stream.Collectors.toList());
                
                if (!existing.isEmpty()) {
                    System.out.println("服务已存在，跳过: " + service.getNameCn());
                    continue;
                }
                
                certificationServiceService.save(service);
                System.out.println("保存成功: " + service.getNameCn());
                successCount++;
            } catch (Exception e) {
                System.err.println("保存失败: " + service.getNameCn() + " - " + e.getMessage());
                e.printStackTrace();
                failCount++;
            }
        }
        
        System.out.println("导入完成！成功: " + successCount + " 条，失败: " + failCount + " 条");
    }
}

