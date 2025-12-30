-- ============================================
-- 使用原网站 http://www.zht-lab.cn/ 的实际内容初始化数据库
-- ============================================

USE zht_lab;

-- 清空现有数据（可选，谨慎使用）
-- TRUNCATE TABLE company_info;
-- TRUNCATE TABLE enterprise_advantage;
-- TRUNCATE TABLE laboratory;
-- TRUNCATE TABLE certification_service;
-- TRUNCATE TABLE news;
-- TRUNCATE TABLE honor;

-- 插入公司信息
INSERT INTO company_info (name_cn, name_en, description_cn, description_en, address, phone, qq, wechat) VALUES
('深圳泰和检测技术服务有限公司', 'Shenzhen Taihe Testing Technology Service Co., Ltd.', 
'自成立以来，致力于为客户提供专业的一站式产品检测认证服务，是一家集检测、检验、认证及技术服务为一体的独立且权威的综合性第三方检测认证机构。广东中翰检测技术有限公司总部位于深圳市宝安区福海街道，立足于深圳，放眼于全球。目前拥有安全 (Safety)、 电磁兼容 (EMC)、 无线电射频 (RF)、 新能源(电池)、化学、环境可靠性检测实验室。中翰检测严格按照国际标准ISO/IEC17025规范建立和运行。业务领域已覆盖家用电器，信息资讯，视听产品，无线及通讯产品，电池，灯具，机械产品，医疗器械，玩具，纺织品，食品接触材料等多种产品提供检测认证服务。仪器设备由Aglient,Tektronix,EMTEST等多家世界优秀仪器厂商提供，设备先进精良，场地环境控制可靠，溯源性强，工程技术团队专业高效，与多家国际权威机构保持紧密合作，并获得CNAS，CMA，UL，TUV，Intertek，SGS，NEMKO，VCCI，KEMA，KETI等国内和国际权威机构的认可。中翰检测遵循"科学、公正、准确、高效"的质量方针，遵循国际准则和惯例以及国家的法律法规，在严格的程序下开展工作。对所有委托方均持科学、公正的态度，坚持保密的原则，凭借先进的技术和卓越的服务理念，为广大企业解决了众多品质难题，赢得了客户和社会的信赖，并向社会各界提供优质高效的服务。',
'Since its establishment, it has been committed to providing professional one-stop product testing and certification services for customers. It is an independent and authoritative comprehensive third-party testing and certification organization integrating testing, inspection, certification and technical services.',
'深圳市宝安区福海街道', '18307618653', '405705201', '二维码')
ON DUPLICATE KEY UPDATE 
    name_cn = VALUES(name_cn),
    name_en = VALUES(name_en),
    description_cn = VALUES(description_cn),
    description_en = VALUES(description_en),
    address = VALUES(address),
    phone = VALUES(phone),
    qq = VALUES(qq),
    wechat = VALUES(wechat);

-- 插入企业优势
INSERT INTO enterprise_advantage (title, description, icon, sort_order) VALUES
('专业的检测认证团队', '拥有先进的检测设备和专业的测试人员，检测范围广、精准度高、周期短', 'upload/20231130161735.png', 1),
('一对一客服专业服务', '给客户提供专业知识咨询，客户资料统一专人管理，为产品整个生命周期提供一站式服务', 'upload/20240119150129.png', 2),
('客户满意的价格体系', '透明化市场价格，相比同行更加优惠，采取市场化运作方式，超高检测能力，不一样的价格，超额的服务体验', 'upload/20240119150054.png', 3),
('优秀的服务体系', '产品样品检测报告追踪，专业工程师为您提供高效方案，随时提供专业知识咨询，终身证书更新及维护', 'upload/20240119150032.png', 4)
ON DUPLICATE KEY UPDATE 
    title = VALUES(title),
    description = VALUES(description),
    icon = VALUES(icon),
    sort_order = VALUES(sort_order);

-- 插入实验室
INSERT INTO laboratory (name_cn, name_en, type, description_cn, description_en, image, sort_order) VALUES
('EMC&无线实验室', 'EMC&Wireless Laboratory', 'EMC&无线实验室', '电磁兼容和无线电射频检测实验室，拥有先进的测试设备和专业的测试人员', 'EMC and Radio Frequency Testing Laboratory with advanced testing equipment and professional testers', 'upload/20241217133436938_52889.jpg', 1),
('安规实验室', 'Safety Laboratory', '安规实验室', '安全规范检测实验室，严格按照国际标准进行测试', 'Safety Testing Laboratory, strictly following international standards', 'upload/20241217133436940_528810.jpg', 2),
('电池实验室', 'Battery Laboratory', '电池实验室', '新能源电池检测实验室，专注于电池安全性和性能测试', 'New Energy Battery Testing Laboratory, focusing on battery safety and performance testing', 'upload/20241217133436944_528811.jpg', 3),
('化学实验室', 'Chemical Laboratory', '化学实验室', '化学检测实验室，提供化学成分分析和检测服务', 'Chemical Testing Laboratory, providing chemical composition analysis and testing services', 'upload/20241217133436946_528812.jpg', 4),
('整改服务区', 'Rectification Service Area', '整改服务区', '提供产品整改服务，帮助客户解决检测中的问题', 'Product rectification service area, helping customers solve testing issues', 'upload/20241217133436948_381613.jpg', 5),
('谐波电流测试、电压波动和闪烁测试实验室', 'Harmonic Current, Voltage Fluctuation and Flicker Testing Laboratory', 'EMC&无线实验室', '专业的谐波电流测试、电压波动和闪烁测试设备', 'Professional harmonic current, voltage fluctuation and flicker testing equipment', 'upload/20241217133436940_528810.jpg', 6),
('骚扰功率测试实验室', 'Disturbance Power Testing Laboratory', 'EMC&无线实验室', '电磁骚扰功率测试专用实验室', 'Electromagnetic disturbance power testing laboratory', 'upload/20241217133436936_52888.jpg', 7),
('三环天线测试实验室', 'Three-Loop Antenna Testing Laboratory', 'EMC&无线实验室', '三环天线测试专用实验室', 'Three-loop antenna testing laboratory', 'upload/20241217133436932_52886.jpg', 8)
ON DUPLICATE KEY UPDATE 
    name_cn = VALUES(name_cn),
    name_en = VALUES(name_en),
    type = VALUES(type),
    description_cn = VALUES(description_cn),
    description_en = VALUES(description_en),
    image = VALUES(image),
    sort_order = VALUES(sort_order);

-- 插入认证服务（欧洲认证）
INSERT INTO certification_service (name_cn, name_en, category, region, description_cn, sort_order) VALUES
('欧盟认证', 'EU Certification', '欧洲认证', '欧盟', '欧盟CE认证服务', 1),
('匈牙利认证', 'Hungary Certification', '欧洲认证', '匈牙利', '匈牙利产品认证服务', 2),
('土耳其认证', 'Turkey Certification', '欧洲认证', '土耳其', '土耳其产品认证服务', 3),
('爱尔兰认证', 'Ireland Certification', '欧洲认证', '爱尔兰', '爱尔兰产品认证服务', 4),
('斯洛文尼亚认证', 'Slovenia Certification', '欧洲认证', '斯洛文尼亚', '斯洛文尼亚产品认证服务', 5),
('乌克兰认证', 'Ukraine Certification', '欧洲认证', '乌克兰', '乌克兰产品认证服务', 6),
('捷克认证', 'Czech Certification', '欧洲认证', '捷克', '捷克产品认证服务', 7),
('斯洛伐克认证', 'Slovakia Certification', '欧洲认证', '斯洛伐克', '斯洛伐克产品认证服务', 8),
('希腊认证', 'Greece Certification', '欧洲认证', '希腊', '希腊产品认证服务', 9),
('德国认证', 'Germany Certification', '欧洲认证', '德国', '德国产品认证服务', 10),
('西班牙认证', 'Spain Certification', '欧洲认证', '西班牙', '西班牙产品认证服务', 11),
('英国认证', 'UK Certification', '欧洲认证', '英国', '英国产品认证服务', 12)
ON DUPLICATE KEY UPDATE 
    name_cn = VALUES(name_cn),
    name_en = VALUES(name_en),
    category = VALUES(category),
    region = VALUES(region),
    description_cn = VALUES(description_cn),
    sort_order = VALUES(sort_order);

-- 插入认证服务（美洲认证）
INSERT INTO certification_service (name_cn, name_en, category, region, description_cn, sort_order) VALUES
('美国认证', 'USA Certification', '美洲认证', '美国', '美国FCC、UL等认证服务', 1),
('墨西哥认证', 'Mexico Certification', '美洲认证', '墨西哥', '墨西哥产品认证服务', 2),
('加拿大认证', 'Canada Certification', '美洲认证', '加拿大', '加拿大产品认证服务', 3),
('阿根廷认证', 'Argentina Certification', '美洲认证', '阿根廷', '阿根廷产品认证服务', 4),
('巴西认证', 'Brazil Certification', '美洲认证', '巴西', '巴西产品认证服务', 5)
ON DUPLICATE KEY UPDATE 
    name_cn = VALUES(name_cn),
    name_en = VALUES(name_en),
    category = VALUES(category),
    region = VALUES(region),
    description_cn = VALUES(description_cn),
    sort_order = VALUES(sort_order);

-- 插入认证服务（亚洲认证）
INSERT INTO certification_service (name_cn, name_en, category, region, description_cn, sort_order) VALUES
('中国认证', 'China Certification', '亚洲认证', '中国', '中国3C、CQC等认证服务', 1),
('日本认证', 'Japan Certification', '亚洲认证', '日本', '日本PSE、VCCI等认证服务', 2),
('韩国认证', 'Korea Certification', '亚洲认证', '韩国', '韩国KC认证服务', 3),
('沙特认证', 'Saudi Arabia Certification', '亚洲认证', '沙特', '沙特SASO认证服务', 4),
('俄罗斯认证', 'Russia Certification', '亚洲认证', '俄罗斯', '俄罗斯EAC认证服务', 5),
('印度认证', 'India Certification', '亚洲认证', '印度', '印度BIS认证服务', 6),
('新加坡认证', 'Singapore Certification', '亚洲认证', '新加坡', '新加坡产品认证服务', 7),
('马来西亚认证', 'Malaysia Certification', '亚洲认证', '马来西亚', '马来西亚SIRIM认证服务', 8),
('以色列认证', 'Israel Certification', '亚洲认证', '以色列', '以色列产品认证服务', 9)
ON DUPLICATE KEY UPDATE 
    name_cn = VALUES(name_cn),
    name_en = VALUES(name_en),
    category = VALUES(category),
    region = VALUES(region),
    description_cn = VALUES(description_cn),
    sort_order = VALUES(sort_order);

-- 插入认证服务（其他）
INSERT INTO certification_service (name_cn, name_en, category, region, description_cn, sort_order) VALUES
('澳洲认证', 'Australia Certification', '澳洲认证', '澳洲', '澳洲产品认证服务', 1),
('国际认证', 'International Certification', '国际认证', '国际', '国际通用产品认证服务', 1),
('能效认证', 'Energy Efficiency Certification', '能效认证', '', '产品能效等级认证服务', 1),
('体系认证', 'System Certification', '体系认证', '', 'ISO体系认证服务', 1),
('可靠性测试', 'Reliability Testing', '可靠性测试', '', '产品可靠性测试服务', 1),
('电池检测', 'Battery Testing', '电池检测', '', '电池安全性和性能检测服务', 1),
('化学检测', 'Chemical Testing', '化学检测', '', '化学成分分析和检测服务', 1)
ON DUPLICATE KEY UPDATE 
    name_cn = VALUES(name_cn),
    name_en = VALUES(name_en),
    category = VALUES(category),
    region = VALUES(region),
    description_cn = VALUES(description_cn),
    sort_order = VALUES(sort_order);

-- 插入新闻
INSERT INTO news (title, summary, content, image, category, publish_time) VALUES
('化学成分常见的检测方法', '化学成分检测方法多样，可根据样品类型和分析需求选择合适的方法', 
'化学成分检测方法多样，可根据样品类型和分析需求选择合适的方法。常见的检测方法包括：\n\n1. 光谱分析法\n光谱分析法是利用物质与电磁辐射相互作用产生的光谱特征进行分析的方法，包括原子吸收光谱、原子发射光谱、紫外可见光谱、红外光谱等。\n\n2. 色谱分析法\n色谱分析法是利用不同物质在固定相和流动相之间的分配系数不同进行分离和分析的方法，包括气相色谱、液相色谱、薄层色谱等。\n\n3. 质谱分析法\n质谱分析法是通过测量样品分子的质量-电荷比来确定分子结构的方法，包括质谱、气相色谱-质谱联用等。\n\n4. 电化学分析法\n电化学分析法是利用电化学原理进行分析的方法，包括电位法、电导法、极谱法等。\n\n5. 其他方法\n还包括重量法、容量法、比色法等传统分析方法。',
'upload/20250610144522.jpg', '行业新闻', '2025-06-10 14:45:22'),

('气相色谱质谱联用仪操作步骤', '气相色谱质谱联用仪（GC-MS）的操作步骤主要包括开机准备、仪器调谐、方法编辑、进样检测、数据采集与处理以及关机维护', 
'气相色谱质谱联用仪（GC-MS）的操作步骤主要包括：\n\n1. 开机准备\n- 检查气源压力是否正常\n- 打开载气（如氦气）总阀\n- 打开GC和MS电源\n- 等待系统自检完成\n\n2. 仪器调谐\n- 进行自动调谐或手动调谐\n- 检查质量校准\n- 确认仪器状态正常\n\n3. 方法编辑\n- 设置色谱柱温度程序\n- 设置进样口温度\n- 设置质谱参数\n- 保存方法文件\n\n4. 进样检测\n- 准备样品\n- 设置进样参数\n- 开始运行\n- 监控实时数据\n\n5. 数据采集与处理\n- 采集质谱数据\n- 进行谱图解析\n- 定性定量分析\n- 生成检测报告\n\n6. 关机维护\n- 降低柱温\n- 关闭加热系统\n- 关闭载气\n- 进行日常维护',
'upload/20250609143908.png', '行业新闻', '2025-06-09 14:39:08'),

('谐波电流测试方法主要包括直接测量法和计算分析法', '谐波电流测试方法主要包括直接测量法和计算分析法，以下是具体介绍：', 
'谐波电流测试方法主要包括直接测量法和计算分析法：\n\n一、直接测量法\n直接测量法是使用谐波分析仪直接测量设备在工作状态下的谐波电流。\n\n1. 测试设备\n- 谐波分析仪\n- 电流传感器\n- 测试负载\n\n2. 测试步骤\n- 连接测试设备\n- 设置测试参数\n- 启动被测设备\n- 记录谐波数据\n- 分析测试结果\n\n3. 测试标准\n按照IEC 61000-3-2等标准进行测试。\n\n二、计算分析法\n计算分析法是通过理论计算或仿真分析来评估谐波电流。\n\n1. 计算方法\n- 傅里叶变换\n- 频谱分析\n- 数学建模\n\n2. 分析软件\n- MATLAB\n- Simulink\n- 专业谐波分析软件\n\n3. 应用场景\n适用于产品设计阶段和理论分析。',
'upload/20250606151148.jpg', '行业新闻', '2025-06-06 15:11:48'),

('谐波电流测试标准', '谐波电流测试标准要求主要包括测试标准、豁免要求、产品分类、测试方法及注意事项等方面，以下是详细介绍：', 
'谐波电流测试标准要求：\n\n一、测试标准\n主要参考标准包括：\n- IEC 61000-3-2：低压电气及电子设备发出的谐波电流限值\n- GB 17625.1：电磁兼容 限值 谐波电流发射限值\n\n二、豁免要求\n以下设备可以豁免谐波电流测试：\n- 额定功率小于75W的设备（特定类别除外）\n- 专业设备\n- 对称控制加热元件\n- 白炽灯调光器\n\n三、产品分类\n产品分为四类：\n- A类：平衡三相设备、家用电器、工具、白炽灯调光器、音频设备\n- B类：便携式工具、非专业弧焊设备\n- C类：照明设备\n- D类：功率小于600W的个人计算机、显示器、电视接收机\n\n四、测试方法\n- 使用谐波分析仪\n- 按照标准规定的测试条件\n- 记录各次谐波电流值\n- 与限值进行比较\n\n五、注意事项\n- 确保测试环境符合要求\n- 正确连接测试设备\n- 记录完整的测试数据\n- 注意测试结果的准确性',
'upload/20250605152329.png', '行业新闻', '2025-06-05 15:23:29'),

('荧光光度法和红外分光光度法有什么区别吗', '荧光光度法和红外分光光度法在原理、仪器结构、灵敏度、应用范围等方面存在明显区别', 
'荧光光度法和红外分光光度法的主要区别：\n\n一、原理不同\n1. 荧光光度法\n- 利用物质吸收光能后发射荧光的特性\n- 测量荧光强度进行定量分析\n- 灵敏度高，选择性好\n\n2. 红外分光光度法\n- 利用物质对红外光的吸收特性\n- 测量吸收强度进行定性定量分析\n- 主要用于结构分析\n\n二、仪器结构\n1. 荧光光度法\n- 激发光源\n- 单色器\n- 样品池\n- 检测器\n\n2. 红外分光光度法\n- 红外光源\n- 分光系统\n- 样品池\n- 检测器\n\n三、应用范围\n1. 荧光光度法\n- 适用于能产生荧光的物质\n- 主要用于定量分析\n- 灵敏度可达ppb级别\n\n2. 红外分光光度法\n- 适用于所有有机化合物\n- 主要用于结构分析\n- 可进行定性定量分析\n\n四、优缺点\n荧光光度法：灵敏度高，但适用范围有限\n红外分光光度法：适用范围广，但灵敏度相对较低',
'upload/20250604152354.png', '行业新闻', '2025-06-04 15:23:54'),

('实验室精准水质检测——石油类及动植物油检测', '水质检测中石油类及动植物油的检测，常用方法包括重量法、紫外分光光度法、荧光光度法、红外分光光度法等，其中红外分光光度法是常用且具有代表性的方法，以下为你展开介绍：', 
'水质检测中石油类及动植物油的检测方法：\n\n一、检测方法\n1. 重量法\n- 原理：通过萃取、蒸发、称重测定\n- 适用范围：高浓度样品\n- 优点：操作简单，结果准确\n- 缺点：灵敏度低，耗时较长\n\n2. 紫外分光光度法\n- 原理：利用石油类物质在紫外区的吸收特性\n- 适用范围：低浓度样品\n- 优点：灵敏度较高\n- 缺点：干扰因素多\n\n3. 荧光光度法\n- 原理：利用石油类物质的荧光特性\n- 适用范围：低浓度样品\n- 优点：灵敏度高，选择性好\n- 缺点：需要特定设备\n\n4. 红外分光光度法（推荐）\n- 原理：利用C-H键在红外区的特征吸收\n- 适用范围：各种浓度样品\n- 优点：准确度高，重现性好\n- 缺点：需要专业设备\n\n二、红外分光光度法操作步骤\n1. 样品预处理\n2. 萃取分离\n3. 红外光谱测定\n4. 结果计算\n\n三、注意事项\n- 避免样品污染\n- 控制萃取条件\n- 确保仪器校准\n- 注意干扰因素',
'upload/20250603151544.jpg', '行业新闻', '2025-06-03 15:15:44'),

('物理机械性能检测的项目是什么', '在液体食品无菌包装用复合袋的物理机械性能检测中，主要涉及以下关键项目，这些项目对于评估复合袋的强度、耐用性以及在实际应用中的可靠性至关重要：', 
'液体食品无菌包装用复合袋的物理机械性能检测项目：\n\n一、拉伸性能\n1. 拉伸强度\n- 测试材料在拉伸过程中的最大应力\n- 反映材料的抗拉能力\n\n2. 断裂伸长率\n- 测试材料断裂时的伸长率\n- 反映材料的韧性\n\n3. 弹性模量\n- 测试材料的弹性特性\n- 反映材料的刚度\n\n二、撕裂性能\n1. 撕裂强度\n- 测试材料的抗撕裂能力\n- 反映材料的耐用性\n\n2. 撕裂伸长率\n- 测试撕裂过程中的伸长率\n\n三、冲击性能\n1. 冲击强度\n- 测试材料抵抗冲击的能力\n- 反映材料的韧性\n\n2. 落镖冲击\n- 模拟实际使用中的冲击情况\n\n四、其他性能\n1. 热封强度\n- 测试热封部位的强度\n- 确保包装密封性\n\n2. 剥离强度\n- 测试复合层间的结合强度\n\n3. 摩擦系数\n- 测试材料的表面摩擦特性\n\n4. 厚度\n- 测量材料的厚度均匀性\n\n五、测试标准\n- GB/T 10004\n- GB/T 10005\n- ISO 527等',
'upload/20250530150503.png', '行业新闻', '2025-05-30 15:05:03'),

('液体食品无菌包装用复合袋检测', '液体食品无菌包装用复合袋的检测涉及多个方面，以确保其质量和安全性，以下是对其检测的详细介绍：', 
'液体食品无菌包装用复合袋的检测内容：\n\n一、物理性能检测\n1. 外观质量\n- 检查袋子的外观缺陷\n- 检查印刷质量\n- 检查尺寸规格\n\n2. 物理机械性能\n- 拉伸强度\n- 撕裂强度\n- 冲击强度\n- 热封强度\n\n二、阻隔性能检测\n1. 氧气透过率\n- 测试材料的氧气阻隔能力\n- 影响食品保质期\n\n2. 水蒸气透过率\n- 测试材料的水分阻隔能力\n- 防止食品受潮\n\n三、卫生性能检测\n1. 微生物检测\n- 细菌总数\n- 大肠菌群\n- 致病菌\n\n2. 化学物质检测\n- 重金属含量\n- 有害物质迁移\n- 溶剂残留\n\n四、功能性能检测\n1. 热封性能\n- 热封强度\n- 热封温度范围\n\n2. 耐压性能\n- 耐压强度\n- 耐压变形\n\n五、安全性检测\n1. 食品接触材料安全性\n2. 有害物质迁移\n3. 包装完整性\n\n六、测试标准\n- GB 9683\n- GB 9685\n- GB/T 10004等',
'upload/20250529160958.png', '行业新闻', '2025-05-29 16:09:58')
ON DUPLICATE KEY UPDATE 
    title = VALUES(title),
    summary = VALUES(summary),
    content = VALUES(content),
    image = VALUES(image),
    category = VALUES(category),
    publish_time = VALUES(publish_time);

-- 插入荣誉资质（示例，实际图片需要下载）
INSERT INTO honor (title, description, image, sort_order) VALUES
('CNAS认可证书', '中国合格评定国家认可委员会认可证书', 'upload/img/20241217105815.png', 1),
('CMA资质证书', '检验检测机构资质认定证书', 'upload/img/20241217105815.png', 2),
('UL认可证书', '美国保险商实验室认可证书', 'upload/img/20241217105815.png', 3),
('TUV认可证书', '德国TUV认可证书', 'upload/img/20241217105815.png', 4),
('Intertek认可证书', 'Intertek认可证书', 'upload/img/20241217105815.png', 5),
('SGS认可证书', 'SGS认可证书', 'upload/img/20241217105815.png', 6),
('NEMKO认可证书', 'NEMKO认可证书', 'upload/img/20241217105815.png', 7),
('VCCI认可证书', '日本VCCI认可证书', 'upload/img/20241217105815.png', 8)
ON DUPLICATE KEY UPDATE 
    title = VALUES(title),
    description = VALUES(description),
    image = VALUES(image),
    sort_order = VALUES(sort_order);

