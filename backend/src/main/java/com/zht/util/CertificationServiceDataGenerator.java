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
 * 认证服务数据生成工具
 * 为每个国家/地区生成具体的认证服务项目
 */
public class CertificationServiceDataGenerator {
    
    // 图片保存目录
    private static final String IMAGE_DIR = "uploads/icon/certification_service/";
    
    // 认证服务数据：国家/地区 -> 认证服务列表
    private static final Map<String, List<CertificationServiceInfo>> CERTIFICATION_SERVICES = new HashMap<>();
    
    // 认证服务图片URL映射
    private static final Map<String, String> SERVICE_IMAGE_URLS = new HashMap<>();
    
    static {
        initCertificationServices();
        initServiceImageUrls();
    }
    
    /**
     * 初始化认证服务数据
     * 使用扩展的认证服务数据
     */
    @SuppressWarnings("unchecked")
    private static void initCertificationServices() {
        // 使用扩展的认证服务数据
        Map<String, List<CertificationServiceDataGenerator.CertificationServiceInfo>> expandedServices = 
            CertificationServiceDataExpanded.getAllCertificationServices();
        for (Map.Entry<String, List<CertificationServiceDataGenerator.CertificationServiceInfo>> entry : expandedServices.entrySet()) {
            @SuppressWarnings("unchecked")
            List<CertificationServiceInfo> serviceList = (List<CertificationServiceInfo>) (List<?>) entry.getValue();
            CERTIFICATION_SERVICES.put(entry.getKey(), serviceList);
        }
        
        // 以下是原有的简化数据，现在已被扩展数据替代
        /*
        // ========== 欧盟认证服务 ==========
        List<CertificationServiceInfo> euServices = Arrays.asList(
            new CertificationServiceInfo("CE认证", "CE Marking", 
                "CE标志是欧盟的强制性产品安全认证标志，适用于进入欧洲经济区（EEA）的大部分产品。",
                "CE marking is a mandatory conformity marking for products sold in the European Economic Area (EEA).",
                "CE认证是欧盟法律对产品提出的一种强制性要求，表明产品符合欧盟相关指令的基本要求。CE标志适用于所有在欧盟市场销售的产品，包括电子设备、机械设备、医疗器械、玩具等。",
                "CE marking indicates that products comply with EU safety, health, and environmental protection requirements. It applies to all products sold in the EU market, including electronic devices, machinery, medical devices, toys, etc.",
                "CE", 12L, "欧盟", "欧洲认证"),
            new CertificationServiceInfo("RoHS认证", "RoHS Compliance", 
                "RoHS指令限制在电子电气设备中使用某些有害物质，保护环境和人类健康。",
                "RoHS Directive restricts the use of certain hazardous substances in electrical and electronic equipment.",
                "RoHS（限制有害物质指令）要求电子电气产品中不得含有铅、汞、镉、六价铬、多溴联苯和多溴二苯醚等有害物质。",
                "RoHS (Restriction of Hazardous Substances) requires that electrical and electronic products do not contain hazardous substances such as lead, mercury, cadmium, hexavalent chromium, PBB, and PBDE.",
                "RoHS", 12L, "欧盟", "欧洲认证"),
            new CertificationServiceInfo("REACH认证", "REACH Compliance", 
                "REACH法规是欧盟关于化学品注册、评估、授权和限制的法规。",
                "REACH is the EU regulation concerning the Registration, Evaluation, Authorisation and Restriction of Chemicals.",
                "REACH法规要求所有在欧盟生产或进口到欧盟的化学品必须进行注册，确保化学品的安全使用。",
                "REACH requires registration of all chemicals produced or imported into the EU to ensure safe use of chemicals.",
                "REACH", 12L, "欧盟", "欧洲认证"),
            new CertificationServiceInfo("WEEE认证", "WEEE Compliance", 
                "WEEE指令要求生产商负责电子电气设备的回收和处理。",
                "WEEE Directive requires producers to be responsible for the recovery and treatment of electrical and electronic equipment.",
                "WEEE（废弃电子电气设备指令）要求生产商建立回收系统，确保废弃电子设备得到环保处理。",
                "WEEE (Waste Electrical and Electronic Equipment) requires producers to establish recovery systems to ensure environmentally sound treatment of waste electrical equipment.",
                "WEEE", 12L, "欧盟", "欧洲认证"),
            new CertificationServiceInfo("ErP指令", "ErP Directive", 
                "ErP指令（能源相关产品指令）要求产品满足能效和环保要求。",
                "ErP Directive (Energy-related Products) requires products to meet energy efficiency and environmental requirements.",
                "ErP指令要求能源相关产品必须满足最低能效要求，并在产品上标注能效等级。",
                "ErP Directive requires energy-related products to meet minimum energy efficiency requirements and display energy efficiency labels.",
                "ErP", 12L, "欧盟", "欧洲认证")
        );
        CERTIFICATION_SERVICES.put("欧盟", euServices);
        
        // ========== 德国认证服务 ==========
        List<CertificationServiceInfo> germanyServices = Arrays.asList(
            new CertificationServiceInfo("GS认证", "GS Mark", 
                "GS认证是德国安全认证标志，由德国技术监督协会等机构颁发。",
                "GS (Geprüfte Sicherheit) is a German safety certification mark issued by organizations like TÜV.",
                "GS标志表明产品经过德国安全标准测试，符合德国产品安全法要求。",
                "GS mark indicates that products have been tested according to German safety standards and comply with German Product Safety Act.",
                "GS", 21L, "德国", "欧洲认证"),
            new CertificationServiceInfo("VDE认证", "VDE Certification", 
                "VDE是德国电气工程师协会的认证标志，适用于电气产品。",
                "VDE is a certification mark from the German Association for Electrical, Electronic & Information Technologies.",
                "VDE认证是德国最权威的电气产品安全认证，适用于家用电器、工业设备等。",
                "VDE certification is the most authoritative electrical product safety certification in Germany, applicable to household appliances, industrial equipment, etc.",
                "VDE", 21L, "德国", "欧洲认证")
        );
        CERTIFICATION_SERVICES.put("德国", germanyServices);
        
        // ========== 英国认证服务 ==========
        List<CertificationServiceInfo> ukServices = Arrays.asList(
            new CertificationServiceInfo("UKCA认证", "UKCA Marking", 
                "UKCA是英国脱欧后的新认证标志，替代原有的CE标志。",
                "UKCA (UK Conformity Assessed) marking is the new product marking for products sold in Great Britain after Brexit.",
                "UKCA标志适用于在英国市场销售的产品，表明产品符合英国相关法规要求。",
                "UKCA marking applies to products sold in the UK market, indicating compliance with UK regulations.",
                "UKCA", 23L, "英国", "欧洲认证"),
            new CertificationServiceInfo("BSI认证", "BSI Certification", 
                "BSI是英国标准协会的认证服务。",
                "BSI (British Standards Institution) provides certification services.",
                "BSI认证包括质量管理体系、环境管理体系等多种认证服务。",
                "BSI certification includes quality management systems, environmental management systems, and various certification services.",
                "BSI", 23L, "英国", "欧洲认证")
        );
        CERTIFICATION_SERVICES.put("英国", ukServices);
        
        // ========== 美国认证服务 ==========
        List<CertificationServiceInfo> usServices = Arrays.asList(
            new CertificationServiceInfo("FCC认证", "FCC Certification", 
                "FCC认证是美国联邦通信委员会的强制性认证，适用于所有可能产生射频能量的电子设备。",
                "FCC certification is mandatory for electronic devices that may emit radio frequency energy.",
                "FCC认证分为FCC ID和FCC SDoC两种类型，确保电子设备不会干扰其他设备。",
                "FCC certification is divided into FCC ID and FCC SDoC types, ensuring electronic devices do not interfere with other equipment.",
                "FCC", 24L, "美国", "美洲认证"),
            new CertificationServiceInfo("UL认证", "UL Certification", 
                "UL认证是美国保险商实验室的安全认证，是全球最权威的安全认证之一。",
                "UL certification is a safety certification from Underwriters Laboratories, one of the most authoritative safety certifications globally.",
                "UL认证适用于电气产品、建筑材料、消防设备等，确保产品安全可靠。",
                "UL certification applies to electrical products, building materials, fire protection equipment, etc., ensuring product safety and reliability.",
                "UL", 24L, "美国", "美洲认证"),
            new CertificationServiceInfo("FDA认证", "FDA Approval", 
                "FDA是美国食品药品监督管理局的认证，适用于食品、药品、医疗器械等。",
                "FDA (Food and Drug Administration) approval applies to food, drugs, medical devices, etc.",
                "FDA认证确保食品、药品和医疗器械的安全性和有效性。",
                "FDA approval ensures the safety and effectiveness of food, drugs, and medical devices.",
                "FDA", 24L, "美国", "美洲认证"),
            new CertificationServiceInfo("Energy Star", "Energy Star", 
                "Energy Star是美国环保署的能效认证计划。",
                "Energy Star is an energy efficiency certification program from the US Environmental Protection Agency.",
                "Energy Star认证标识高能效产品，帮助消费者选择节能产品。",
                "Energy Star certification identifies high-efficiency products, helping consumers choose energy-saving products.",
                "EnergyStar", 24L, "美国", "美洲认证"),
            new CertificationServiceInfo("CPSC认证", "CPSC Compliance", 
                "CPSC是美国消费品安全委员会的认证，适用于消费品。",
                "CPSC (Consumer Product Safety Commission) compliance applies to consumer products.",
                "CPSC认证确保消费品符合美国安全标准，保护消费者安全。",
                "CPSC compliance ensures consumer products meet US safety standards, protecting consumer safety.",
                "CPSC", 24L, "美国", "美洲认证")
        );
        CERTIFICATION_SERVICES.put("美国", usServices);
        
        // ========== 加拿大认证服务 ==========
        List<CertificationServiceInfo> canadaServices = Arrays.asList(
            new CertificationServiceInfo("IC认证", "IC Certification", 
                "IC认证是加拿大工业部的强制性认证，适用于所有无线电设备。",
                "IC certification is mandatory for all radio equipment sold in Canada.",
                "IC认证确保无线电设备符合加拿大技术标准，不会干扰其他设备。",
                "IC certification ensures radio equipment complies with Canadian technical standards and does not interfere with other equipment.",
                "IC", 26L, "加拿大", "美洲认证"),
            new CertificationServiceInfo("CSA认证", "CSA Certification", 
                "CSA认证是加拿大标准协会的安全认证。",
                "CSA certification is a safety certification from the Canadian Standards Association.",
                "CSA认证适用于电气产品、燃气设备、建筑材料等，确保产品安全。",
                "CSA certification applies to electrical products, gas equipment, building materials, etc., ensuring product safety.",
                "CSA", 26L, "加拿大", "美洲认证")
        );
        CERTIFICATION_SERVICES.put("加拿大", canadaServices);
        
        // ========== 中国认证服务 ==========
        List<CertificationServiceInfo> chinaServices = Arrays.asList(
            new CertificationServiceInfo("CCC认证", "CCC Certification", 
                "CCC认证是中国强制性产品认证，适用于进入中国市场的特定产品类别。",
                "CCC (China Compulsory Certification) is mandatory for specific product categories entering the Chinese market.",
                "CCC认证是中国最权威的强制性产品认证，适用于电子设备、家用电器、汽车等产品。",
                "CCC certification is China's most authoritative mandatory product certification, applicable to electronic devices, household appliances, automobiles, etc.",
                "CCC", 29L, "中国", "亚洲认证"),
            new CertificationServiceInfo("CQC认证", "CQC Certification", 
                "CQC认证是中国质量认证中心的自愿性认证。",
                "CQC certification is a voluntary certification from China Quality Certification Centre.",
                "CQC认证适用于不在CCC强制认证范围内的产品，提供自愿性认证服务。",
                "CQC certification applies to products not covered by CCC mandatory certification, providing voluntary certification services.",
                "CQC", 29L, "中国", "亚洲认证"),
            new CertificationServiceInfo("SRRC认证", "SRRC Certification", 
                "SRRC认证是中国无线电管理委员会的认证，适用于无线电设备。",
                "SRRC certification is from China's State Radio Regulation Commission, applicable to radio equipment.",
                "SRRC认证是无线电设备进入中国市场的强制性认证。",
                "SRRC certification is mandatory for radio equipment entering the Chinese market.",
                "SRRC", 29L, "中国", "亚洲认证"),
            new CertificationServiceInfo("能效标识", "Energy Label", 
                "能效标识是中国对用能产品的能效等级标识。",
                "Energy label is China's energy efficiency rating label for energy-consuming products.",
                "能效标识分为1-5级，1级最节能，5级最耗能。",
                "Energy labels are divided into levels 1-5, with level 1 being the most energy-efficient and level 5 being the most energy-consuming.",
                "EnergyLabel", 29L, "中国", "亚洲认证")
        );
        CERTIFICATION_SERVICES.put("中国", chinaServices);
        
        // ========== 日本认证服务 ==========
        List<CertificationServiceInfo> japanServices = Arrays.asList(
            new CertificationServiceInfo("PSE认证", "PSE Certification", 
                "PSE认证是日本电气用品安全法的强制性认证，适用于在日本销售的大部分电气产品。",
                "PSE (Product Safety of Electrical Appliance & Materials) certification is mandatory for most electrical products sold in Japan.",
                "PSE认证分为菱形PSE和圆形PSE两种，确保电气产品符合日本安全标准。",
                "PSE certification is divided into diamond PSE and round PSE types, ensuring electrical products comply with Japanese safety standards.",
                "PSE", 30L, "日本", "亚洲认证"),
            new CertificationServiceInfo("VCCI认证", "VCCI Certification", 
                "VCCI认证是日本针对信息技术设备的自愿性认证。",
                "VCCI is a voluntary certification for IT equipment in Japan.",
                "VCCI认证确保IT设备符合日本电磁兼容性要求。",
                "VCCI certification ensures IT equipment complies with Japanese electromagnetic compatibility requirements.",
                "VCCI", 30L, "日本", "亚洲认证"),
            new CertificationServiceInfo("JIS认证", "JIS Certification", 
                "JIS认证是日本工业标准的认证。",
                "JIS (Japanese Industrial Standards) certification applies to industrial products.",
                "JIS认证确保产品符合日本工业标准要求。",
                "JIS certification ensures products comply with Japanese Industrial Standards.",
                "JIS", 30L, "日本", "亚洲认证")
        );
        CERTIFICATION_SERVICES.put("日本", japanServices);
        
        // ========== 韩国认证服务 ==========
        List<CertificationServiceInfo> koreaServices = Arrays.asList(
            new CertificationServiceInfo("KC认证", "KC Marking", 
                "KC认证是韩国知识经济部的强制性认证，适用于在韩国销售的大部分电子和电气产品。",
                "KC (Korea Certification) marking is mandatory for most electronic and electrical products sold in South Korea.",
                "KC认证确保产品符合韩国安全和技术标准。",
                "KC marking ensures products comply with Korean safety and technical standards.",
                "KC", 31L, "韩国", "亚洲认证"),
            new CertificationServiceInfo("KCC认证", "KCC Certification", 
                "KCC认证是韩国通信委员会的认证，适用于无线电设备。",
                "KCC certification is from Korea Communications Commission, applicable to radio equipment.",
                "KCC认证确保无线电设备符合韩国通信标准。",
                "KCC certification ensures radio equipment complies with Korean communication standards.",
                "KCC", 31L, "韩国", "亚洲认证")
        );
        CERTIFICATION_SERVICES.put("韩国", koreaServices);
        
        // ========== 其他国家的认证服务（简化版） ==========
        // 匈牙利
        CERTIFICATION_SERVICES.put("匈牙利", Arrays.asList(
            new CertificationServiceInfo("CE认证", "CE Marking", "CE标志适用于匈牙利市场。", "CE marking applies to the Hungarian market.", 
                "匈牙利作为欧盟成员国，产品需要CE认证才能进入市场。", "As an EU member state, products require CE certification to enter the Hungarian market.", "CE", 13L, "匈牙利", "欧洲认证")
        ));
        
        // 土耳其
        CERTIFICATION_SERVICES.put("土耳其", Arrays.asList(
            new CertificationServiceInfo("CE认证", "CE Marking", "CE标志适用于土耳其市场。", "CE marking applies to the Turkish market.",
                "土耳其采用CE认证标准，产品需要CE认证。", "Turkey adopts CE certification standards, products require CE certification.", "CE", 14L, "土耳其", "欧洲认证")
        ));
        
        // 其他欧洲国家（使用CE认证）
        for (String country : Arrays.asList("爱尔兰", "斯洛文尼亚", "乌克兰", "捷克", "斯洛伐克", "希腊", "西班牙")) {
            CERTIFICATION_SERVICES.put(country, Arrays.asList(
                new CertificationServiceInfo("CE认证", "CE Marking", "CE标志适用于" + country + "市场。", "CE marking applies to the " + country + " market.",
                    country + "作为欧盟成员国，产品需要CE认证。", country + " as an EU member state, products require CE certification.", "CE", getCategoryId(country), country, "欧洲认证")
            ));
        }
        
        // 美洲其他国家
        CERTIFICATION_SERVICES.put("墨西哥", Arrays.asList(
            new CertificationServiceInfo("NOM认证", "NOM Certification", "NOM是墨西哥的强制性认证。", "NOM is mandatory certification in Mexico.",
                "NOM认证适用于在墨西哥销售的产品。", "NOM certification applies to products sold in Mexico.", "NOM", 25L, "墨西哥", "美洲认证")
        ));
        
        CERTIFICATION_SERVICES.put("巴西", Arrays.asList(
            new CertificationServiceInfo("INMETRO认证", "INMETRO Certification", "INMETRO是巴西的强制性认证。", "INMETRO is mandatory certification in Brazil.",
                "INMETRO认证适用于在巴西销售的产品。", "INMETRO certification applies to products sold in Brazil.", "INMETRO", 28L, "巴西", "美洲认证")
        ));
        
        CERTIFICATION_SERVICES.put("阿根廷", Arrays.asList(
            new CertificationServiceInfo("IRAM认证", "IRAM Certification", "IRAM是阿根廷的认证标准。", "IRAM is an Argentine certification standard.",
                "IRAM认证适用于在阿根廷销售的产品。", "IRAM certification applies to products sold in Argentina.", "IRAM", 27L, "阿根廷", "美洲认证")
        ));
        
        // 亚洲其他国家
        CERTIFICATION_SERVICES.put("印度", Arrays.asList(
            new CertificationServiceInfo("BIS认证", "BIS Certification", "BIS是印度标准局的强制性认证。", "BIS is mandatory certification from the Bureau of Indian Standards.",
                "BIS认证适用于在印度销售的产品。", "BIS certification applies to products sold in India.", "BIS", 34L, "印度", "亚洲认证")
        ));
        
        CERTIFICATION_SERVICES.put("新加坡", Arrays.asList(
            new CertificationServiceInfo("IMDA认证", "IMDA Certification", "IMDA是新加坡信息通信媒体发展局的认证。", "IMDA is certification from Singapore's Info-communications Media Development Authority.",
                "IMDA认证适用于在新加坡销售的通信设备。", "IMDA certification applies to communication equipment sold in Singapore.", "IMDA", 35L, "新加坡", "亚洲认证")
        ));
        
        CERTIFICATION_SERVICES.put("马来西亚", Arrays.asList(
            new CertificationServiceInfo("SIRIM认证", "SIRIM Certification", "SIRIM是马来西亚标准与工业研究院的认证。", "SIRIM is certification from Malaysia's Standards and Industrial Research Institute.",
                "SIRIM认证适用于在马来西亚销售的产品。", "SIRIM certification applies to products sold in Malaysia.", "SIRIM", 36L, "马来西亚", "亚洲认证")
        ));
        
        CERTIFICATION_SERVICES.put("俄罗斯", Arrays.asList(
            new CertificationServiceInfo("EAC认证", "EAC Certification", "EAC是欧亚经济联盟的强制性认证。", "EAC is mandatory certification for the Eurasian Economic Union.",
                "EAC认证适用于在俄罗斯等欧亚经济联盟国家销售的产品。", "EAC certification applies to products sold in Russia and other EAEU countries.", "EAC", 33L, "俄罗斯", "亚洲认证")
        ));
        
        CERTIFICATION_SERVICES.put("沙特", Arrays.asList(
            new CertificationServiceInfo("SASO认证", "SASO Certification", "SASO是沙特标准、计量和质量组织的认证。", "SASO is certification from Saudi Standards, Metrology and Quality Organization.",
                "SASO认证适用于在沙特销售的产品。", "SASO certification applies to products sold in Saudi Arabia.", "SASO", 32L, "沙特", "亚洲认证")
        ));
        
        CERTIFICATION_SERVICES.put("以色列", Arrays.asList(
            new CertificationServiceInfo("SII认证", "SII Certification", "SII是以色列标准协会的认证。", "SII is certification from the Standards Institution of Israel.",
                "SII认证适用于在以色列销售的产品。", "SII certification applies to products sold in Israel.", "SII", 37L, "以色列", "亚洲认证")
        ));
        
        // 澳洲
        CERTIFICATION_SERVICES.put("澳洲", Arrays.asList(
            new CertificationServiceInfo("RCM认证", "RCM Marking", "RCM是澳大利亚和新西兰的强制性认证标志。", "RCM is a mandatory certification mark for Australia and New Zealand.",
                "RCM认证适用于在澳洲销售的产品。", "RCM certification applies to products sold in Australia.", "RCM", 38L, "澳洲", "澳洲认证")
        ));
        
        // 国际认证
        CERTIFICATION_SERVICES.put("国际", Arrays.asList(
            new CertificationServiceInfo("ISO认证", "ISO Certification", "ISO是国际标准化组织的认证。", "ISO is certification from the International Organization for Standardization.",
                "ISO认证包括质量管理体系、环境管理体系等多种认证。", "ISO certification includes quality management systems, environmental management systems, and various certifications.", "ISO", 39L, "国际", "国际认证")
        ));
        
        // 能效认证
        CERTIFICATION_SERVICES.put("能效认证", Arrays.asList(
            new CertificationServiceInfo("ErP能效认证", "ErP Energy Efficiency", "ErP指令要求产品满足能效要求。", "ErP Directive requires products to meet energy efficiency requirements.",
                "ErP能效认证适用于能源相关产品。", "ErP energy efficiency certification applies to energy-related products.", "ErP", 7L, null, "能效认证"),
            new CertificationServiceInfo("Energy Star", "Energy Star", "Energy Star是美国环保署的能效认证。", "Energy Star is an energy efficiency certification from the US EPA.",
                "Energy Star认证标识高能效产品。", "Energy Star certification identifies high-efficiency products.", "EnergyStar", 7L, null, "能效认证"),
            new CertificationServiceInfo("中国能效标识", "China Energy Label", "中国能效标识是对用能产品的能效等级标识。", "China Energy Label is an energy efficiency rating label for energy-consuming products.",
                "能效标识分为1-5级，1级最节能。", "Energy labels are divided into levels 1-5, with level 1 being the most energy-efficient.", "EnergyLabel", 7L, null, "能效认证")
        ));
        
        // 体系认证
        CERTIFICATION_SERVICES.put("体系认证", Arrays.asList(
            new CertificationServiceInfo("ISO 9001质量管理体系", "ISO 9001 QMS", "ISO 9001是质量管理体系的国际标准。", "ISO 9001 is an international standard for quality management systems.",
                "ISO 9001认证确保组织建立有效的质量管理体系。", "ISO 9001 certification ensures organizations establish effective quality management systems.", "ISO9001", 8L, null, "体系认证"),
            new CertificationServiceInfo("ISO 14001环境管理体系", "ISO 14001 EMS", "ISO 14001是环境管理体系的国际标准。", "ISO 14001 is an international standard for environmental management systems.",
                "ISO 14001认证确保组织建立有效的环境管理体系。", "ISO 14001 certification ensures organizations establish effective environmental management systems.", "ISO14001", 8L, null, "体系认证"),
            new CertificationServiceInfo("ISO 45001职业健康安全管理体系", "ISO 45001 OHSMS", "ISO 45001是职业健康安全管理体系的国际标准。", "ISO 45001 is an international standard for occupational health and safety management systems.",
                "ISO 45001认证确保组织建立有效的职业健康安全管理体系。", "ISO 45001 certification ensures organizations establish effective occupational health and safety management systems.", "ISO45001", 8L, null, "体系认证")
        ));
        
        // 可靠性测试
        CERTIFICATION_SERVICES.put("可靠性测试", Arrays.asList(
            new CertificationServiceInfo("环境测试", "Environmental Testing", "环境测试评估产品在各种环境条件下的性能。", "Environmental testing evaluates product performance under various environmental conditions.",
                "包括温度、湿度、振动、冲击等环境测试。", "Including temperature, humidity, vibration, shock and other environmental tests.", "Environmental", 9L, null, "可靠性测试"),
            new CertificationServiceInfo("寿命测试", "Life Testing", "寿命测试评估产品的使用寿命和可靠性。", "Life testing evaluates product service life and reliability.",
                "通过加速老化测试等方法评估产品寿命。", "Evaluate product life through accelerated aging tests and other methods.", "LifeTest", 9L, null, "可靠性测试"),
            new CertificationServiceInfo("机械测试", "Mechanical Testing", "机械测试评估产品的机械性能和强度。", "Mechanical testing evaluates product mechanical properties and strength.",
                "包括拉伸、压缩、弯曲、疲劳等机械测试。", "Including tensile, compression, bending, fatigue and other mechanical tests.", "Mechanical", 9L, null, "可靠性测试")
        ));
        
        // 电池检测
        CERTIFICATION_SERVICES.put("电池检测", Arrays.asList(
            new CertificationServiceInfo("电池安全测试", "Battery Safety Testing", "电池安全测试确保电池产品符合安全标准。", "Battery safety testing ensures battery products meet safety standards.",
                "包括过充、过放、短路、热滥用等安全测试。", "Including overcharge, over-discharge, short circuit, thermal abuse and other safety tests.", "BatterySafety", 10L, null, "电池检测"),
            new CertificationServiceInfo("电池性能测试", "Battery Performance Testing", "电池性能测试评估电池的容量、功率等性能。", "Battery performance testing evaluates battery capacity, power and other performance.",
                "包括容量测试、内阻测试、循环寿命测试等。", "Including capacity testing, internal resistance testing, cycle life testing, etc.", "BatteryPerformance", 10L, null, "电池检测")
        ));
        
        // 化学检测
        CERTIFICATION_SERVICES.put("化学检测", Arrays.asList(
            new CertificationServiceInfo("RoHS检测", "RoHS Testing", "RoHS检测检测产品中的有害物质含量。", "RoHS testing detects the content of hazardous substances in products.",
                "检测铅、汞、镉、六价铬等有害物质。", "Testing for lead, mercury, cadmium, hexavalent chromium and other hazardous substances.", "RoHS", 11L, null, "化学检测"),
            new CertificationServiceInfo("REACH检测", "REACH Testing", "REACH检测检测产品中的化学物质。", "REACH testing detects chemicals in products.",
                "确保产品符合REACH法规要求。", "Ensure products comply with REACH regulations.", "REACH", 11L, null, "化学检测"),
            new CertificationServiceInfo("有害物质检测", "Hazardous Substance Testing", "有害物质检测检测产品中的各种有害物质。", "Hazardous substance testing detects various hazardous substances in products.",
                "包括重金属、阻燃剂、增塑剂等有害物质检测。", "Including heavy metals, flame retardants, plasticizers and other hazardous substance testing.", "Hazardous", 11L, null, "化学检测")
        ));
        */
    }
    
    /**
     * 获取国家对应的分类ID
     */
    private static Long getCategoryId(String country) {
        Map<String, Long> countryToId = new HashMap<>();
        countryToId.put("爱尔兰", 15L);
        countryToId.put("斯洛文尼亚", 16L);
        countryToId.put("乌克兰", 17L);
        countryToId.put("捷克", 18L);
        countryToId.put("斯洛伐克", 19L);
        countryToId.put("希腊", 20L);
        countryToId.put("西班牙", 22L);
        return countryToId.getOrDefault(country, 12L);
    }
    
    /**
     * 初始化认证服务图片URL
     */
    private static void initServiceImageUrls() {
        // 这里可以添加认证服务标志图片的URL
        // 由于认证标志图片较难获取，这里使用占位符
        // 实际使用时需要替换为真实的认证标志图片URL
        SERVICE_IMAGE_URLS.put("CE", "https://upload.wikimedia.org/wikipedia/commons/thumb/4/4c/CE_marking.svg/200px-CE_marking.svg.png");
        SERVICE_IMAGE_URLS.put("RoHS", "https://upload.wikimedia.org/wikipedia/commons/thumb/7/7a/RoHS_logo.svg/200px-RoHS_logo.svg.png");
        SERVICE_IMAGE_URLS.put("FCC", "https://upload.wikimedia.org/wikipedia/commons/thumb/8/8a/FCC_logo.svg/200px-FCC_logo.svg.png");
        SERVICE_IMAGE_URLS.put("UL", "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9f/UL_logo.svg/200px-UL_logo.svg.png");
        SERVICE_IMAGE_URLS.put("CCC", "https://upload.wikimedia.org/wikipedia/commons/thumb/8/8c/CCC_mark.svg/200px-CCC_mark.svg.png");
        // 其他认证标志图片URL可以在这里添加
    }
    
    /**
     * 下载认证服务图片
     */
    public static void downloadServiceImage(String serviceCode, String imageUrl, String savePath) throws IOException {
        if (imageUrl == null || imageUrl.isEmpty()) {
            System.out.println("图片URL为空，跳过下载: " + serviceCode);
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
                File file = new File(savePath);
                file.getParentFile().mkdirs();
                
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
     * 下载所有认证服务图片
     */
    public static void downloadAllServiceImages() {
        System.out.println("开始下载认证服务图片...\n");
        
        File dir = new File(IMAGE_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        
        Set<String> downloaded = new HashSet<>();
        for (List<CertificationServiceInfo> services : CERTIFICATION_SERVICES.values()) {
            for (CertificationServiceInfo service : services) {
                String serviceCode = service.serviceCode;
                if (!downloaded.contains(serviceCode) && SERVICE_IMAGE_URLS.containsKey(serviceCode)) {
                    String url = SERVICE_IMAGE_URLS.get(serviceCode);
                    String fileName = serviceCode + ".png";
                    String savePath = IMAGE_DIR + fileName;
                    
                    try {
                        downloadServiceImage(serviceCode, url, savePath);
                        Thread.sleep(500);
                        downloaded.add(serviceCode);
                    } catch (Exception e) {
                        System.err.println("下载失败: " + serviceCode + ", 错误: " + e.getMessage());
                    }
                }
            }
        }
        
        System.out.println("\n图片下载完成！");
    }
    
    /**
     * 生成认证服务SQL插入语句
     */
    public static String generateSQL() {
        StringBuilder sql = new StringBuilder();
        sql.append("-- 认证服务数据导入SQL（固定ID版本）\n");
        sql.append("-- 生成时间: ").append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\n");
        sql.append("-- 注意：请先导入certification_category数据，然后执行此SQL\n");
        sql.append("-- 如果表中有数据，请先备份：CREATE TABLE certification_service_backup AS SELECT * FROM certification_service;\n");
        sql.append("-- 然后清空表：TRUNCATE TABLE certification_service; ALTER TABLE certification_service AUTO_INCREMENT = 1;\n\n");
        
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        int id = 1;
        
        // 按国家/地区生成SQL
        for (Map.Entry<String, List<CertificationServiceInfo>> entry : CERTIFICATION_SERVICES.entrySet()) {
            String region = entry.getKey();
            List<CertificationServiceInfo> services = entry.getValue();
            
            sql.append("-- ============================================\n");
            sql.append("-- ").append(region).append(" 认证服务\n");
            sql.append("-- ============================================\n");
            
            int sortOrder = 1;
            for (CertificationServiceInfo service : services) {
                String imagePath = SERVICE_IMAGE_URLS.containsKey(service.serviceCode) 
                    ? ("icon/certification_service/" + service.serviceCode + ".png") 
                    : "NULL";
                
                sql.append("INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES\n");
                sql.append("(").append(id++).append(", ");
                sql.append("'").append(escapeSql(service.nameCn)).append("', ");
                sql.append("'").append(escapeSql(service.nameEn)).append("', ");
                sql.append("'").append(escapeSql(service.category)).append("', ");
                sql.append(service.categoryId).append(", ");
                sql.append("0, "); // parent_id = 0 表示顶级服务
                sql.append(service.region != null ? "'" + escapeSql(service.region) + "'" : "NULL").append(", ");
                sql.append("'").append(escapeSql(service.summaryCn)).append("', ");
                sql.append("'").append(escapeSql(service.summaryEn)).append("', ");
                sql.append("'").append(escapeSql(service.descriptionCn)).append("', ");
                sql.append("'").append(escapeSql(service.descriptionEn)).append("', ");
                if (imagePath.equals("NULL")) {
                    sql.append("NULL");
                } else {
                    sql.append("'").append(imagePath).append("'");
                }
                sql.append(", ");
                sql.append(sortOrder++).append(", ");
                sql.append("'").append(now).append("', ");
                sql.append("'").append(now).append("');\n");
            }
            sql.append("\n");
        }
        
        return sql.toString();
    }
    
    /**
     * SQL转义
     */
    private static String escapeSql(String str) {
        if (str == null) return "";
        return str.replace("'", "''").replace("\\", "\\\\");
    }
    
    /**
     * Main方法
     */
    public static void main(String[] args) {
        System.out.println("=== 认证服务数据生成工具 ===\n");
        
        // 1. 下载图片
        System.out.println("步骤1: 下载认证服务图片");
        downloadAllServiceImages();
        
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 2. 生成SQL
        System.out.println("步骤2: 生成SQL导入语句");
        String sql = generateSQL();
        
        try {
            String sqlFile = "certification_service_import.sql";
            Files.write(Paths.get(sqlFile), sql.getBytes("UTF-8"));
            System.out.println("SQL文件已生成: " + sqlFile);
            System.out.println("\nSQL内容预览（前1000字符）:");
            System.out.println(sql.substring(0, Math.min(1000, sql.length())) + "...");
        } catch (IOException e) {
            System.err.println("保存SQL文件失败: " + e.getMessage());
        }
        
        System.out.println("\n完成！");
        System.out.println("\n使用说明：");
        System.out.println("1. 图片已下载到: " + IMAGE_DIR);
        System.out.println("2. SQL文件: certification_service_import.sql");
        System.out.println("3. 执行SQL前请先导入certification_category数据");
        System.out.println("4. 如果表中有数据，请先备份再清空表");
    }
    
    /**
     * 认证服务信息内部类
     */
    static class CertificationServiceInfo {
        String nameCn;
        String nameEn;
        String summaryCn;
        String summaryEn;
        String descriptionCn;
        String descriptionEn;
        String serviceCode; // 服务代码，用于图片文件名
        Long categoryId; // 关联的certification_category ID
        String region; // 国家/地区
        String category; // 分类名称
        
        CertificationServiceInfo(String nameCn, String nameEn, String summaryCn, String summaryEn,
                                String descriptionCn, String descriptionEn, String serviceCode,
                                Long categoryId, String region, String category) {
            this.nameCn = nameCn;
            this.nameEn = nameEn;
            this.summaryCn = summaryCn;
            this.summaryEn = summaryEn;
            this.descriptionCn = descriptionCn;
            this.descriptionEn = descriptionEn;
            this.serviceCode = serviceCode;
            this.categoryId = categoryId;
            this.region = region;
            this.category = category;
        }
    }
}

