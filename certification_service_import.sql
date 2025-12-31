-- 认证服务数据导入SQL（固定ID版本）
-- 生成时间: 2025-12-30 20:15:10
-- 注意：请先导入certification_category数据，然后执行此SQL
-- 如果表中有数据，请先备份：CREATE TABLE certification_service_backup AS SELECT * FROM certification_service;
-- 然后清空表：TRUNCATE TABLE certification_service; ALTER TABLE certification_service AUTO_INCREMENT = 1;

-- ============================================
-- 巴西 认证服务
-- ============================================
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(1, 'INMETRO认证', 'INMETRO Certification', '美洲认证', 28, 0, '巴西', 'INMETRO是巴西的强制性认证。', 'INMETRO is mandatory certification in Brazil.', 'INMETRO认证适用于在巴西销售的产品，确保产品符合巴西标准。', 'INMETRO certification applies to products sold in Brazil, ensuring products comply with Brazilian standards.', NULL, 1, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(2, 'ANATEL认证', 'ANATEL Certification', '美洲认证', 28, 0, '巴西', 'ANATEL是巴西国家电信局的认证。', 'ANATEL is certification from Brazilian National Telecommunications Agency.', 'ANATEL认证适用于电信设备。', 'ANATEL certification applies to telecommunications equipment.', NULL, 2, '2025-12-30 20:15:10', '2025-12-30 20:15:10');

-- ============================================
-- 俄罗斯 认证服务
-- ============================================
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(3, 'EAC认证', 'EAC Certification', '亚洲认证', 33, 0, '俄罗斯', 'EAC是欧亚经济联盟的强制性认证。', 'EAC is mandatory certification for the Eurasian Economic Union.', 'EAC认证适用于在俄罗斯等欧亚经济联盟国家销售的产品。', 'EAC certification applies to products sold in Russia and other EAEU countries.', NULL, 1, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(4, 'GOST认证', 'GOST Certification', '亚洲认证', 33, 0, '俄罗斯', 'GOST是俄罗斯国家标准的认证。', 'GOST is certification from Russian National Standards.', 'GOST认证确保产品符合俄罗斯国家标准。', 'GOST certification ensures products comply with Russian national standards.', NULL, 2, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(5, 'FSS认证', 'FSS Certification', '亚洲认证', 33, 0, '俄罗斯', 'FSS是俄罗斯联邦通信、信息技术和大众传媒监督局的认证。', 'FSS is certification from Russian Federal Service for Supervision of Communications, Information Technology and Mass Media.', 'FSS认证适用于通信设备。', 'FSS certification applies to communication equipment.', NULL, 3, '2025-12-30 20:15:10', '2025-12-30 20:15:10');

-- ============================================
-- 沙特 认证服务
-- ============================================
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(6, 'SASO认证', 'SASO Certification', '亚洲认证', 32, 0, '沙特', 'SASO是沙特标准、计量和质量组织的认证。', 'SASO is certification from Saudi Standards, Metrology and Quality Organization.', 'SASO认证适用于在沙特销售的产品，确保产品符合沙特标准。', 'SASO certification applies to products sold in Saudi Arabia, ensuring products comply with Saudi standards.', NULL, 1, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(7, 'CITC认证', 'CITC Certification', '亚洲认证', 32, 0, '沙特', 'CITC是沙特通信和信息技术委员会的认证。', 'CITC is certification from Communications and Information Technology Commission of Saudi Arabia.', 'CITC认证适用于通信设备。', 'CITC certification applies to communication equipment.', NULL, 2, '2025-12-30 20:15:10', '2025-12-30 20:15:10');

-- ============================================
-- 斯洛文尼亚 认证服务
-- ============================================
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(8, 'CE认证', 'CE Marking', '欧洲认证', 16, 0, '斯洛文尼亚', 'CE标志适用于斯洛文尼亚市场。', 'CE marking applies to the Slovenian market.', '斯洛文尼亚作为欧盟成员国，产品需要CE认证。', 'As an EU member state, products require CE certification.', 'icon/certification_service/CE.png', 1, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(9, 'SIST认证', 'SIST Certification', '欧洲认证', 16, 0, '斯洛文尼亚', 'SIST是斯洛文尼亚标准与计量研究所的认证。', 'SIST is certification from Slovenian Institute for Standardization.', 'SIST认证确保产品符合斯洛文尼亚标准。', 'SIST certification ensures products comply with Slovenian standards.', NULL, 2, '2025-12-30 20:15:10', '2025-12-30 20:15:10');

-- ============================================
-- 体系认证 认证服务
-- ============================================
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(10, 'ISO 9001质量管理体系', 'ISO 9001 QMS', '体系认证', 8, 0, NULL, 'ISO 9001是质量管理体系的国际标准。', 'ISO 9001 is an international standard for quality management systems.', 'ISO 9001认证确保组织建立有效的质量管理体系。', 'ISO 9001 certification ensures organizations establish effective quality management systems.', NULL, 1, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(11, 'ISO 14001环境管理体系', 'ISO 14001 EMS', '体系认证', 8, 0, NULL, 'ISO 14001是环境管理体系的国际标准。', 'ISO 14001 is an international standard for environmental management systems.', 'ISO 14001认证确保组织建立有效的环境管理体系。', 'ISO 14001 certification ensures organizations establish effective environmental management systems.', NULL, 2, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(12, 'ISO 45001职业健康安全管理体系', 'ISO 45001 OHSMS', '体系认证', 8, 0, NULL, 'ISO 45001是职业健康安全管理体系的国际标准。', 'ISO 45001 is an international standard for occupational health and safety management systems.', 'ISO 45001认证确保组织建立有效的职业健康安全管理体系。', 'ISO 45001 certification ensures organizations establish effective occupational health and safety management systems.', NULL, 3, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(13, 'ISO 22000食品安全管理体系', 'ISO 22000 FSMS', '体系认证', 8, 0, NULL, 'ISO 22000是食品安全管理体系的国际标准。', 'ISO 22000 is an international standard for food safety management systems.', 'ISO 22000认证确保组织建立有效的食品安全管理体系。', 'ISO 22000 certification ensures organizations establish effective food safety management systems.', NULL, 4, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(14, 'ISO/IEC 27001信息安全管理体系', 'ISO/IEC 27001 ISMS', '体系认证', 8, 0, NULL, 'ISO/IEC 27001是信息安全管理体系的国际标准。', 'ISO/IEC 27001 is an international standard for information security management systems.', 'ISO/IEC 27001认证确保组织建立有效的信息安全管理体系。', 'ISO/IEC 27001 certification ensures organizations establish effective information security management systems.', NULL, 5, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(15, 'ISO 50001能源管理体系', 'ISO 50001 EnMS', '体系认证', 8, 0, NULL, 'ISO 50001是能源管理体系的国际标准。', 'ISO 50001 is an international standard for energy management systems.', 'ISO 50001认证确保组织建立有效的能源管理体系。', 'ISO 50001 certification ensures organizations establish effective energy management systems.', NULL, 6, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(16, 'IATF 16949汽车行业质量管理体系', 'IATF 16949', '体系认证', 8, 0, NULL, 'IATF 16949是汽车行业质量管理体系标准。', 'IATF 16949 is a quality management system standard for the automotive industry.', 'IATF 16949认证适用于汽车行业供应链。', 'IATF 16949 certification applies to the automotive industry supply chain.', NULL, 7, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(17, 'ISO 13485医疗器械质量管理体系', 'ISO 13485', '体系认证', 8, 0, NULL, 'ISO 13485是医疗器械质量管理体系标准。', 'ISO 13485 is a quality management system standard for medical devices.', 'ISO 13485认证适用于医疗器械行业。', 'ISO 13485 certification applies to the medical device industry.', NULL, 8, '2025-12-30 20:15:10', '2025-12-30 20:15:10');

-- ============================================
-- 澳洲 认证服务
-- ============================================
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(18, 'RCM认证', 'RCM Marking', '澳洲认证', 38, 0, '澳洲', 'RCM是澳大利亚和新西兰的强制性认证标志。', 'RCM is a mandatory certification mark for Australia and New Zealand.', 'RCM认证适用于在澳洲销售的产品，确保产品符合澳洲标准。', 'RCM certification applies to products sold in Australia, ensuring products comply with Australian standards.', NULL, 1, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(19, 'C-Tick认证', 'C-Tick Marking', '澳洲认证', 38, 0, '澳洲', 'C-Tick是澳大利亚的电磁兼容性认证标志。', 'C-Tick is an electromagnetic compatibility certification mark in Australia.', 'C-Tick认证适用于电子设备，确保设备符合电磁兼容性要求。', 'C-Tick certification applies to electronic equipment, ensuring equipment meets electromagnetic compatibility requirements.', NULL, 2, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(20, 'A-Tick认证', 'A-Tick Marking', '澳洲认证', 38, 0, '澳洲', 'A-Tick是澳大利亚的电信设备认证标志。', 'A-Tick is a telecommunications equipment certification mark in Australia.', 'A-Tick认证适用于电信设备。', 'A-Tick certification applies to telecommunications equipment.', NULL, 3, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(21, 'SAA认证', 'SAA Certification', '澳洲认证', 38, 0, '澳洲', 'SAA是澳大利亚标准协会的认证。', 'SAA is certification from Standards Australia.', 'SAA认证确保产品符合澳大利亚标准。', 'SAA certification ensures products comply with Australian standards.', NULL, 4, '2025-12-30 20:15:10', '2025-12-30 20:15:10');

-- ============================================
-- 土耳其 认证服务
-- ============================================
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(22, 'CE认证', 'CE Marking', '欧洲认证', 14, 0, '土耳其', 'CE标志适用于土耳其市场。', 'CE marking applies to the Turkish market.', '土耳其采用CE认证标准，产品需要CE认证。', 'Turkey adopts CE certification standards, products require CE certification.', 'icon/certification_service/CE.png', 1, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(23, 'TSE认证', 'TSE Certification', '欧洲认证', 14, 0, '土耳其', 'TSE是土耳其标准协会的认证。', 'TSE is certification from Turkish Standards Institution.', 'TSE认证确保产品符合土耳其国家标准。', 'TSE certification ensures products comply with Turkish national standards.', NULL, 2, '2025-12-30 20:15:10', '2025-12-30 20:15:10');

-- ============================================
-- 欧盟 认证服务
-- ============================================
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(24, 'CE认证', 'CE Marking', '欧洲认证', 12, 0, '欧盟', 'CE标志是欧盟的强制性产品安全认证标志。', 'CE marking is a mandatory conformity marking for products sold in the European Economic Area (EEA).', 'CE认证是欧盟法律对产品提出的一种强制性要求，表明产品符合欧盟相关指令的基本要求。CE标志适用于所有在欧盟市场销售的产品，包括电子设备、机械设备、医疗器械、玩具、个人防护设备等。', 'CE marking indicates that products comply with EU safety, health, and environmental protection requirements. It applies to all products sold in the EU market, including electronic devices, machinery, medical devices, toys, personal protective equipment, etc.', 'icon/certification_service/CE.png', 1, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(25, 'RoHS认证', 'RoHS Compliance', '欧洲认证', 12, 0, '欧盟', 'RoHS指令限制在电子电气设备中使用某些有害物质。', 'RoHS Directive restricts the use of certain hazardous substances in electrical and electronic equipment.', 'RoHS（限制有害物质指令）要求电子电气产品中不得含有铅、汞、镉、六价铬、多溴联苯和多溴二苯醚等有害物质。', 'RoHS (Restriction of Hazardous Substances) requires that electrical and electronic products do not contain hazardous substances such as lead, mercury, cadmium, hexavalent chromium, PBB, and PBDE.', 'icon/certification_service/RoHS.png', 2, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(26, 'REACH认证', 'REACH Compliance', '欧洲认证', 12, 0, '欧盟', 'REACH法规是欧盟关于化学品注册、评估、授权和限制的法规。', 'REACH is the EU regulation concerning the Registration, Evaluation, Authorisation and Restriction of Chemicals.', 'REACH法规要求所有在欧盟生产或进口到欧盟的化学品必须进行注册，确保化学品的安全使用。', 'REACH requires registration of all chemicals produced or imported into the EU to ensure safe use of chemicals.', NULL, 3, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(27, 'WEEE认证', 'WEEE Compliance', '欧洲认证', 12, 0, '欧盟', 'WEEE指令要求生产商负责电子电气设备的回收和处理。', 'WEEE Directive requires producers to be responsible for the recovery and treatment of electrical and electronic equipment.', 'WEEE（废弃电子电气设备指令）要求生产商建立回收系统，确保废弃电子设备得到环保处理。', 'WEEE (Waste Electrical and Electronic Equipment) requires producers to establish recovery systems to ensure environmentally sound treatment of waste electrical equipment.', NULL, 4, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(28, 'ErP指令', 'ErP Directive', '欧洲认证', 12, 0, '欧盟', 'ErP指令（能源相关产品指令）要求产品满足能效和环保要求。', 'ErP Directive (Energy-related Products) requires products to meet energy efficiency and environmental requirements.', 'ErP指令要求能源相关产品必须满足最低能效要求，并在产品上标注能效等级。', 'ErP Directive requires energy-related products to meet minimum energy efficiency requirements and display energy efficiency labels.', NULL, 5, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(29, 'EMC指令', 'EMC Directive', '欧洲认证', 12, 0, '欧盟', 'EMC指令确保电子设备的电磁兼容性。', 'EMC Directive ensures electromagnetic compatibility of electronic equipment.', 'EMC（电磁兼容性）指令要求电子设备在正常工作时不会产生过度的电磁干扰，同时能够抵抗来自其他设备的干扰。', 'EMC (Electromagnetic Compatibility) Directive requires electronic equipment not to generate excessive electromagnetic interference during normal operation and to resist interference from other equipment.', NULL, 6, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(30, 'LVD指令', 'LVD Directive', '欧洲认证', 12, 0, '欧盟', 'LVD指令确保低电压设备的安全性。', 'LVD Directive ensures the safety of low voltage equipment.', 'LVD（低电压指令）适用于额定电压在50-1000V AC或75-1500V DC的电气设备。', 'LVD (Low Voltage Directive) applies to electrical equipment with rated voltage between 50-1000V AC or 75-1500V DC.', NULL, 7, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(31, 'RED指令', 'RED Directive', '欧洲认证', 12, 0, '欧盟', 'RED指令适用于无线电设备。', 'RED Directive applies to radio equipment.', 'RED（无线电设备指令）适用于所有能够发射和/或接收无线电波的设备。', 'RED (Radio Equipment Directive) applies to all equipment capable of transmitting and/or receiving radio waves.', NULL, 8, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(32, 'MD指令', 'MD Directive', '欧洲认证', 12, 0, '欧盟', 'MD指令适用于机械设备。', 'MD Directive applies to machinery.', 'MD（机械指令）确保机械设备符合欧盟安全要求。', 'MD (Machinery Directive) ensures machinery complies with EU safety requirements.', NULL, 9, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(33, 'MDR指令', 'MDR Regulation', '欧洲认证', 12, 0, '欧盟', 'MDR是欧盟医疗器械法规。', 'MDR is the EU Medical Device Regulation.', 'MDR（医疗器械法规）适用于在欧盟市场销售的医疗器械。', 'MDR (Medical Device Regulation) applies to medical devices sold in the EU market.', NULL, 10, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(34, 'IVDR指令', 'IVDR Regulation', '欧洲认证', 12, 0, '欧盟', 'IVDR是欧盟体外诊断医疗器械法规。', 'IVDR is the EU In Vitro Diagnostic Medical Device Regulation.', 'IVDR（体外诊断医疗器械法规）适用于体外诊断设备。', 'IVDR (In Vitro Diagnostic Medical Device Regulation) applies to in vitro diagnostic devices.', NULL, 11, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(35, 'ENEC认证', 'ENEC Mark', '欧洲认证', 12, 0, '欧盟', 'ENEC是欧洲电气产品认证标志。', 'ENEC is the European mark for electrical products.', 'ENEC（欧洲电气产品认证）是欧洲统一的电气产品安全认证标志。', 'ENEC (European Norms Electrical Certification) is a unified European safety certification mark for electrical products.', NULL, 12, '2025-12-30 20:15:10', '2025-12-30 20:15:10');

-- ============================================
-- 西班牙 认证服务
-- ============================================
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(36, 'CE认证', 'CE Marking', '欧洲认证', 22, 0, '西班牙', 'CE标志适用于西班牙市场。', 'CE marking applies to the Spanish market.', '西班牙作为欧盟成员国，产品需要CE认证。', 'As an EU member state, products require CE certification.', 'icon/certification_service/CE.png', 1, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(37, 'UNE认证', 'UNE Certification', '欧洲认证', 22, 0, '西班牙', 'UNE是西班牙国家标准的认证。', 'UNE is certification from Spanish Association for Standardization.', 'UNE认证确保产品符合西班牙国家标准。', 'UNE certification ensures products comply with Spanish national standards.', NULL, 2, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(38, 'AENOR认证', 'AENOR Certification', '欧洲认证', 22, 0, '西班牙', 'AENOR是西班牙标准化与认证协会的认证。', 'AENOR is certification from Spanish Association for Standardization and Certification.', 'AENOR认证包括产品认证和管理体系认证。', 'AENOR certification includes product certification and management system certification.', NULL, 3, '2025-12-30 20:15:10', '2025-12-30 20:15:10');

-- ============================================
-- 斯洛伐克 认证服务
-- ============================================
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(39, 'CE认证', 'CE Marking', '欧洲认证', 19, 0, '斯洛伐克', 'CE标志适用于斯洛伐克市场。', 'CE marking applies to the Slovak market.', '斯洛伐克作为欧盟成员国，产品需要CE认证。', 'As an EU member state, products require CE certification.', 'icon/certification_service/CE.png', 1, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(40, 'STN认证', 'STN Certification', '欧洲认证', 19, 0, '斯洛伐克', 'STN是斯洛伐克国家标准的认证。', 'STN is certification from Slovak National Standards.', 'STN认证确保产品符合斯洛伐克国家标准。', 'STN certification ensures products comply with Slovak national standards.', NULL, 2, '2025-12-30 20:15:10', '2025-12-30 20:15:10');

-- ============================================
-- 阿根廷 认证服务
-- ============================================
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(41, 'IRAM认证', 'IRAM Certification', '美洲认证', 27, 0, '阿根廷', 'IRAM是阿根廷的认证标准。', 'IRAM is an Argentine certification standard.', 'IRAM认证适用于在阿根廷销售的产品，确保产品符合阿根廷标准。', 'IRAM certification applies to products sold in Argentina, ensuring products comply with Argentine standards.', NULL, 1, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(42, 'CNC认证', 'CNC Certification', '美洲认证', 27, 0, '阿根廷', 'CNC是阿根廷国家通信委员会的认证。', 'CNC is certification from Argentine National Communications Commission.', 'CNC认证适用于通信设备。', 'CNC certification applies to communication equipment.', NULL, 2, '2025-12-30 20:15:10', '2025-12-30 20:15:10');

-- ============================================
-- 美国 认证服务
-- ============================================
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(43, 'FCC认证', 'FCC Certification', '美洲认证', 24, 0, '美国', 'FCC认证是美国联邦通信委员会的强制性认证。', 'FCC certification is mandatory for electronic devices that may emit radio frequency energy.', 'FCC认证分为FCC ID和FCC SDoC两种类型，确保电子设备不会干扰其他设备。', 'FCC certification is divided into FCC ID and FCC SDoC types, ensuring electronic devices do not interfere with other equipment.', 'icon/certification_service/FCC.png', 1, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(44, 'UL认证', 'UL Certification', '美洲认证', 24, 0, '美国', 'UL认证是美国保险商实验室的安全认证。', 'UL certification is a safety certification from Underwriters Laboratories.', 'UL认证适用于电气产品、建筑材料、消防设备等，确保产品安全可靠。', 'UL certification applies to electrical products, building materials, fire protection equipment, etc., ensuring product safety and reliability.', 'icon/certification_service/UL.png', 2, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(45, 'FDA认证', 'FDA Approval', '美洲认证', 24, 0, '美国', 'FDA是美国食品药品监督管理局的认证。', 'FDA (Food and Drug Administration) approval applies to food, drugs, medical devices, etc.', 'FDA认证确保食品、药品和医疗器械的安全性和有效性。', 'FDA approval ensures the safety and effectiveness of food, drugs, and medical devices.', NULL, 3, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(46, 'Energy Star', 'Energy Star', '美洲认证', 24, 0, '美国', 'Energy Star是美国环保署的能效认证计划。', 'Energy Star is an energy efficiency certification program from the US Environmental Protection Agency.', 'Energy Star认证标识高能效产品，帮助消费者选择节能产品。', 'Energy Star certification identifies high-efficiency products, helping consumers choose energy-saving products.', NULL, 4, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(47, 'CPSC认证', 'CPSC Compliance', '美洲认证', 24, 0, '美国', 'CPSC是美国消费品安全委员会的认证。', 'CPSC (Consumer Product Safety Commission) compliance applies to consumer products.', 'CPSC认证确保消费品符合美国安全标准，保护消费者安全。', 'CPSC compliance ensures consumer products meet US safety standards, protecting consumer safety.', NULL, 5, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(48, 'ETL认证', 'ETL Certification', '美洲认证', 24, 0, '美国', 'ETL是Intertek的认证标志。', 'ETL is a certification mark from Intertek.', 'ETL认证适用于电气产品，证明产品符合安全标准。', 'ETL certification applies to electrical products, proving products meet safety standards.', NULL, 6, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(49, 'CSA认证', 'CSA Certification', '美洲认证', 24, 0, '美国', 'CSA是加拿大标准协会的认证，在美国也广泛认可。', 'CSA is certification from Canadian Standards Association, also widely recognized in the US.', 'CSA认证适用于电气产品、燃气设备等。', 'CSA certification applies to electrical products, gas equipment, etc.', NULL, 7, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(50, 'NRTL认证', 'NRTL Certification', '美洲认证', 24, 0, '美国', 'NRTL是美国国家认可测试实验室的认证。', 'NRTL (Nationally Recognized Testing Laboratory) certification is recognized by OSHA.', 'NRTL认证包括UL、ETL、CSA等机构的认证。', 'NRTL certification includes certifications from UL, ETL, CSA and other organizations.', NULL, 8, '2025-12-30 20:15:10', '2025-12-30 20:15:10');

-- ============================================
-- 乌克兰 认证服务
-- ============================================
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(51, 'CE认证', 'CE Marking', '欧洲认证', 17, 0, '乌克兰', 'CE标志适用于乌克兰市场。', 'CE marking applies to the Ukrainian market.', '乌克兰采用CE认证标准。', 'Ukraine adopts CE certification standards.', 'icon/certification_service/CE.png', 1, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(52, 'DSTU认证', 'DSTU Certification', '欧洲认证', 17, 0, '乌克兰', 'DSTU是乌克兰国家标准的认证。', 'DSTU is certification from Ukrainian National Standards.', 'DSTU认证确保产品符合乌克兰国家标准。', 'DSTU certification ensures products comply with Ukrainian national standards.', NULL, 2, '2025-12-30 20:15:10', '2025-12-30 20:15:10');

-- ============================================
-- 国际 认证服务
-- ============================================
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(53, 'ISO认证', 'ISO Certification', '国际认证', 39, 0, '国际', 'ISO是国际标准化组织的认证。', 'ISO is certification from the International Organization for Standardization.', 'ISO认证包括质量管理体系、环境管理体系等多种认证。', 'ISO certification includes quality management systems, environmental management systems, and various certifications.', NULL, 1, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(54, 'IEC认证', 'IEC Certification', '国际认证', 39, 0, '国际', 'IEC是国际电工委员会的认证。', 'IEC is certification from International Electrotechnical Commission.', 'IEC认证适用于电气和电子设备。', 'IEC certification applies to electrical and electronic equipment.', NULL, 2, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(55, 'ITU认证', 'ITU Certification', '国际认证', 39, 0, '国际', 'ITU是国际电信联盟的认证。', 'ITU is certification from International Telecommunication Union.', 'ITU认证适用于电信设备。', 'ITU certification applies to telecommunications equipment.', NULL, 3, '2025-12-30 20:15:10', '2025-12-30 20:15:10');

-- ============================================
-- 韩国 认证服务
-- ============================================
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(56, 'KC认证', 'KC Marking', '亚洲认证', 31, 0, '韩国', 'KC认证是韩国知识经济部的强制性认证。', 'KC (Korea Certification) marking is mandatory for most electronic and electrical products sold in South Korea.', 'KC认证确保产品符合韩国安全和技术标准。', 'KC marking ensures products comply with Korean safety and technical standards.', NULL, 1, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(57, 'KCC认证', 'KCC Certification', '亚洲认证', 31, 0, '韩国', 'KCC认证是韩国通信委员会的认证。', 'KCC certification is from Korea Communications Commission, applicable to radio equipment.', 'KCC认证确保无线电设备符合韩国通信标准。', 'KCC certification ensures radio equipment complies with Korean communication standards.', NULL, 2, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(58, 'KATS认证', 'KATS Certification', '亚洲认证', 31, 0, '韩国', 'KATS是韩国技术标准院的认证。', 'KATS is certification from Korea Agency for Technology and Standards.', 'KATS认证确保产品符合韩国技术标准。', 'KATS certification ensures products comply with Korean technical standards.', NULL, 3, '2025-12-30 20:15:10', '2025-12-30 20:15:10');

-- ============================================
-- 加拿大 认证服务
-- ============================================
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(59, 'IC认证', 'IC Certification', '美洲认证', 26, 0, '加拿大', 'IC认证是加拿大工业部的强制性认证。', 'IC certification is mandatory for all radio equipment sold in Canada.', 'IC认证确保无线电设备符合加拿大技术标准，不会干扰其他设备。', 'IC certification ensures radio equipment complies with Canadian technical standards and does not interfere with other equipment.', NULL, 1, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(60, 'CSA认证', 'CSA Certification', '美洲认证', 26, 0, '加拿大', 'CSA认证是加拿大标准协会的安全认证。', 'CSA certification is a safety certification from the Canadian Standards Association.', 'CSA认证适用于电气产品、燃气设备、建筑材料等，确保产品安全。', 'CSA certification applies to electrical products, gas equipment, building materials, etc., ensuring product safety.', NULL, 2, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(61, 'ULC认证', 'ULC Certification', '美洲认证', 26, 0, '加拿大', 'ULC是加拿大保险商实验室的认证。', 'ULC is certification from Underwriters'' Laboratories of Canada.', 'ULC认证适用于消防设备、建筑材料等。', 'ULC certification applies to fire protection equipment, building materials, etc.', NULL, 3, '2025-12-30 20:15:10', '2025-12-30 20:15:10');

-- ============================================
-- 日本 认证服务
-- ============================================
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(62, 'PSE认证', 'PSE Certification', '亚洲认证', 30, 0, '日本', 'PSE认证是日本电气用品安全法的强制性认证。', 'PSE (Product Safety of Electrical Appliance & Materials) certification is mandatory for most electrical products sold in Japan.', 'PSE认证分为菱形PSE和圆形PSE两种，确保电气产品符合日本安全标准。', 'PSE certification is divided into diamond PSE and round PSE types, ensuring electrical products comply with Japanese safety standards.', NULL, 1, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(63, 'VCCI认证', 'VCCI Certification', '亚洲认证', 30, 0, '日本', 'VCCI认证是日本针对信息技术设备的自愿性认证。', 'VCCI is a voluntary certification for IT equipment in Japan.', 'VCCI认证确保IT设备符合日本电磁兼容性要求。', 'VCCI certification ensures IT equipment complies with Japanese electromagnetic compatibility requirements.', NULL, 2, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(64, 'JIS认证', 'JIS Certification', '亚洲认证', 30, 0, '日本', 'JIS认证是日本工业标准的认证。', 'JIS (Japanese Industrial Standards) certification applies to industrial products.', 'JIS认证确保产品符合日本工业标准要求。', 'JIS certification ensures products comply with Japanese Industrial Standards.', NULL, 3, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(65, 'JQA认证', 'JQA Certification', '亚洲认证', 30, 0, '日本', 'JQA是日本质量保证组织的认证。', 'JQA is certification from Japan Quality Assurance Organization.', 'JQA认证包括产品认证和管理体系认证。', 'JQA certification includes product certification and management system certification.', NULL, 4, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(66, 'TELEC认证', 'TELEC Certification', '亚洲认证', 30, 0, '日本', 'TELEC是日本总务省的认证。', 'TELEC is certification from Japan''s Ministry of Internal Affairs and Communications.', 'TELEC认证适用于无线电设备。', 'TELEC certification applies to radio equipment.', NULL, 5, '2025-12-30 20:15:10', '2025-12-30 20:15:10');

-- ============================================
-- 德国 认证服务
-- ============================================
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(67, 'GS认证', 'GS Mark', '欧洲认证', 21, 0, '德国', 'GS认证是德国安全认证标志。', 'GS (Geprüfte Sicherheit) is a German safety certification mark.', 'GS标志表明产品经过德国安全标准测试，符合德国产品安全法要求。', 'GS mark indicates that products have been tested according to German safety standards and comply with German Product Safety Act.', NULL, 1, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(68, 'VDE认证', 'VDE Certification', '欧洲认证', 21, 0, '德国', 'VDE是德国电气工程师协会的认证标志。', 'VDE is a certification mark from the German Association for Electrical, Electronic & Information Technologies.', 'VDE认证是德国最权威的电气产品安全认证，适用于家用电器、工业设备等。', 'VDE certification is the most authoritative electrical product safety certification in Germany, applicable to household appliances, industrial equipment, etc.', NULL, 2, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(69, 'TÜV认证', 'TÜV Certification', '欧洲认证', 21, 0, '德国', 'TÜV是德国技术监督协会的认证。', 'TÜV is certification from German Technical Inspection Association.', 'TÜV认证包括产品安全认证、质量管理体系认证等多种服务。', 'TÜV certification includes product safety certification, quality management system certification, and various services.', NULL, 3, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(70, 'DIN认证', 'DIN Certification', '欧洲认证', 21, 0, '德国', 'DIN是德国标准化协会的标准认证。', 'DIN is certification from the German Institute for Standardization.', 'DIN认证确保产品符合德国工业标准。', 'DIN certification ensures products comply with German industrial standards.', NULL, 4, '2025-12-30 20:15:10', '2025-12-30 20:15:10');

-- ============================================
-- 可靠性测试 认证服务
-- ============================================
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(71, '环境测试', 'Environmental Testing', '可靠性测试', 9, 0, NULL, '环境测试评估产品在各种环境条件下的性能。', 'Environmental testing evaluates product performance under various environmental conditions.', '包括温度、湿度、振动、冲击、盐雾、霉菌等环境测试。', 'Including temperature, humidity, vibration, shock, salt spray, mold and other environmental tests.', NULL, 1, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(72, '寿命测试', 'Life Testing', '可靠性测试', 9, 0, NULL, '寿命测试评估产品的使用寿命和可靠性。', 'Life testing evaluates product service life and reliability.', '通过加速老化测试等方法评估产品寿命。', 'Evaluate product life through accelerated aging tests and other methods.', NULL, 2, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(73, '机械测试', 'Mechanical Testing', '可靠性测试', 9, 0, NULL, '机械测试评估产品的机械性能和强度。', 'Mechanical testing evaluates product mechanical properties and strength.', '包括拉伸、压缩、弯曲、疲劳、冲击等机械测试。', 'Including tensile, compression, bending, fatigue, impact and other mechanical tests.', NULL, 3, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(74, 'EMC测试', 'EMC Testing', '可靠性测试', 9, 0, NULL, 'EMC测试评估产品的电磁兼容性。', 'EMC testing evaluates product electromagnetic compatibility.', '包括电磁干扰和电磁抗扰度测试。', 'Including electromagnetic interference and electromagnetic immunity testing.', NULL, 4, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(75, '安全测试', 'Safety Testing', '可靠性测试', 9, 0, NULL, '安全测试确保产品符合安全标准。', 'Safety testing ensures products meet safety standards.', '包括电气安全、机械安全、防火安全等测试。', 'Including electrical safety, mechanical safety, fire safety and other tests.', NULL, 5, '2025-12-30 20:15:10', '2025-12-30 20:15:10');

-- ============================================
-- 以色列 认证服务
-- ============================================
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(76, 'SII认证', 'SII Certification', '亚洲认证', 37, 0, '以色列', 'SII是以色列标准协会的认证。', 'SII is certification from the Standards Institution of Israel.', 'SII认证适用于在以色列销售的产品，确保产品符合以色列标准。', 'SII certification applies to products sold in Israel, ensuring products comply with Israeli standards.', NULL, 1, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(77, 'MOC认证', 'MOC Certification', '亚洲认证', 37, 0, '以色列', 'MOC是以色列通信部的认证。', 'MOC is certification from Ministry of Communications of Israel.', 'MOC认证适用于通信设备。', 'MOC certification applies to communication equipment.', NULL, 2, '2025-12-30 20:15:10', '2025-12-30 20:15:10');

-- ============================================
-- 化学检测 认证服务
-- ============================================
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(78, 'RoHS检测', 'RoHS Testing', '化学检测', 11, 0, NULL, 'RoHS检测检测产品中的有害物质含量。', 'RoHS testing detects the content of hazardous substances in products.', '检测铅、汞、镉、六价铬、多溴联苯和多溴二苯醚等有害物质。', 'Testing for lead, mercury, cadmium, hexavalent chromium, PBB, and PBDE and other hazardous substances.', 'icon/certification_service/RoHS.png', 1, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(79, 'REACH检测', 'REACH Testing', '化学检测', 11, 0, NULL, 'REACH检测检测产品中的化学物质。', 'REACH testing detects chemicals in products.', '确保产品符合REACH法规要求。', 'Ensure products comply with REACH regulations.', NULL, 2, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(80, '有害物质检测', 'Hazardous Substance Testing', '化学检测', 11, 0, NULL, '有害物质检测检测产品中的各种有害物质。', 'Hazardous substance testing detects various hazardous substances in products.', '包括重金属、阻燃剂、增塑剂、多环芳烃等有害物质检测。', 'Including heavy metals, flame retardants, plasticizers, polycyclic aromatic hydrocarbons and other hazardous substance testing.', NULL, 3, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(81, 'PAHs检测', 'PAHs Testing', '化学检测', 11, 0, NULL, 'PAHs检测检测产品中的多环芳烃含量。', 'PAHs testing detects the content of polycyclic aromatic hydrocarbons in products.', '多环芳烃是一类有害化学物质，需要严格控制。', 'Polycyclic aromatic hydrocarbons are a class of hazardous chemicals that need to be strictly controlled.', NULL, 4, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(82, 'Phthalates检测', 'Phthalates Testing', '化学检测', 11, 0, NULL, 'Phthalates检测检测产品中的邻苯二甲酸酯含量。', 'Phthalates testing detects the content of phthalates in products.', '邻苯二甲酸酯是一类增塑剂，某些种类被限制使用。', 'Phthalates are a class of plasticizers, and certain types are restricted.', NULL, 5, '2025-12-30 20:15:10', '2025-12-30 20:15:10');

-- ============================================
-- 墨西哥 认证服务
-- ============================================
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(83, 'NOM认证', 'NOM Certification', '美洲认证', 25, 0, '墨西哥', 'NOM是墨西哥的强制性认证。', 'NOM is mandatory certification in Mexico.', 'NOM认证适用于在墨西哥销售的产品，确保产品符合墨西哥标准。', 'NOM certification applies to products sold in Mexico, ensuring products comply with Mexican standards.', NULL, 1, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(84, 'NYCE认证', 'NYCE Certification', '美洲认证', 25, 0, '墨西哥', 'NYCE是墨西哥电子通信标准的认证。', 'NYCE is certification from Mexican standards for electronic communications.', 'NYCE认证适用于电子通信设备。', 'NYCE certification applies to electronic communication equipment.', NULL, 2, '2025-12-30 20:15:10', '2025-12-30 20:15:10');

-- ============================================
-- 马来西亚 认证服务
-- ============================================
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(85, 'SIRIM认证', 'SIRIM Certification', '亚洲认证', 36, 0, '马来西亚', 'SIRIM是马来西亚标准与工业研究院的认证。', 'SIRIM is certification from Malaysia''s Standards and Industrial Research Institute.', 'SIRIM认证适用于在马来西亚销售的产品，确保产品符合马来西亚标准。', 'SIRIM certification applies to products sold in Malaysia, ensuring products comply with Malaysian standards.', NULL, 1, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(86, 'MCMC认证', 'MCMC Certification', '亚洲认证', 36, 0, '马来西亚', 'MCMC是马来西亚通信与多媒体委员会的认证。', 'MCMC is certification from Malaysian Communications and Multimedia Commission.', 'MCMC认证适用于通信设备。', 'MCMC certification applies to communication equipment.', NULL, 2, '2025-12-30 20:15:10', '2025-12-30 20:15:10');

-- ============================================
-- 爱尔兰 认证服务
-- ============================================
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(87, 'CE认证', 'CE Marking', '欧洲认证', 15, 0, '爱尔兰', 'CE标志适用于爱尔兰市场。', 'CE marking applies to the Irish market.', '爱尔兰作为欧盟成员国，产品需要CE认证。', 'As an EU member state, products require CE certification.', 'icon/certification_service/CE.png', 1, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(88, 'NSAI认证', 'NSAI Certification', '欧洲认证', 15, 0, '爱尔兰', 'NSAI是爱尔兰国家标准化局的认证。', 'NSAI is certification from National Standards Authority of Ireland.', 'NSAI认证确保产品符合爱尔兰标准。', 'NSAI certification ensures products comply with Irish standards.', NULL, 2, '2025-12-30 20:15:10', '2025-12-30 20:15:10');

-- ============================================
-- 希腊 认证服务
-- ============================================
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(89, 'CE认证', 'CE Marking', '欧洲认证', 20, 0, '希腊', 'CE标志适用于希腊市场。', 'CE marking applies to the Greek market.', '希腊作为欧盟成员国，产品需要CE认证。', 'As an EU member state, products require CE certification.', 'icon/certification_service/CE.png', 1, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(90, 'ELOT认证', 'ELOT Certification', '欧洲认证', 20, 0, '希腊', 'ELOT是希腊标准化组织的认证。', 'ELOT is certification from Hellenic Organization for Standardization.', 'ELOT认证确保产品符合希腊标准。', 'ELOT certification ensures products comply with Greek standards.', NULL, 2, '2025-12-30 20:15:10', '2025-12-30 20:15:10');

-- ============================================
-- 捷克 认证服务
-- ============================================
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(91, 'CE认证', 'CE Marking', '欧洲认证', 18, 0, '捷克', 'CE标志适用于捷克市场。', 'CE marking applies to the Czech market.', '捷克作为欧盟成员国，产品需要CE认证。', 'As an EU member state, products require CE certification.', 'icon/certification_service/CE.png', 1, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(92, 'ČSN认证', 'ČSN Certification', '欧洲认证', 18, 0, '捷克', 'ČSN是捷克国家标准的认证。', 'ČSN is certification from Czech National Standards.', 'ČSN认证确保产品符合捷克国家标准。', 'ČSN certification ensures products comply with Czech national standards.', NULL, 2, '2025-12-30 20:15:10', '2025-12-30 20:15:10');

-- ============================================
-- 能效认证 认证服务
-- ============================================
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(93, 'ErP能效认证', 'ErP Energy Efficiency', '能效认证', 7, 0, NULL, 'ErP指令要求产品满足能效要求。', 'ErP Directive requires products to meet energy efficiency requirements.', 'ErP能效认证适用于能源相关产品。', 'ErP energy efficiency certification applies to energy-related products.', NULL, 1, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(94, 'Energy Star', 'Energy Star', '能效认证', 7, 0, NULL, 'Energy Star是美国环保署的能效认证。', 'Energy Star is an energy efficiency certification from the US EPA.', 'Energy Star认证标识高能效产品。', 'Energy Star certification identifies high-efficiency products.', NULL, 2, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(95, '中国能效标识', 'China Energy Label', '能效认证', 7, 0, NULL, '中国能效标识是对用能产品的能效等级标识。', 'China Energy Label is an energy efficiency rating label for energy-consuming products.', '能效标识分为1-5级，1级最节能。', 'Energy labels are divided into levels 1-5, with level 1 being the most energy-efficient.', NULL, 3, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(96, 'EU能效标签', 'EU Energy Label', '能效认证', 7, 0, NULL, 'EU能效标签是欧盟的能效等级标识。', 'EU Energy Label is an energy efficiency rating label in the EU.', 'EU能效标签分为A到G等级，A+++最节能。', 'EU energy labels are divided into levels A to G, with A+++ being the most energy-efficient.', NULL, 4, '2025-12-30 20:15:10', '2025-12-30 20:15:10');

-- ============================================
-- 中国 认证服务
-- ============================================
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(97, 'CCC认证', 'CCC Certification', '亚洲认证', 29, 0, '中国', 'CCC认证是中国强制性产品认证。', 'CCC (China Compulsory Certification) is mandatory for specific product categories entering the Chinese market.', 'CCC认证是中国最权威的强制性产品认证，适用于电子设备、家用电器、汽车、玩具等产品。', 'CCC certification is China''s most authoritative mandatory product certification, applicable to electronic devices, household appliances, automobiles, toys, etc.', 'icon/certification_service/CCC.png', 1, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(98, 'CQC认证', 'CQC Certification', '亚洲认证', 29, 0, '中国', 'CQC认证是中国质量认证中心的自愿性认证。', 'CQC certification is a voluntary certification from China Quality Certification Centre.', 'CQC认证适用于不在CCC强制认证范围内的产品，提供自愿性认证服务。', 'CQC certification applies to products not covered by CCC mandatory certification, providing voluntary certification services.', NULL, 2, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(99, 'SRRC认证', 'SRRC Certification', '亚洲认证', 29, 0, '中国', 'SRRC认证是中国无线电管理委员会的认证。', 'SRRC certification is from China''s State Radio Regulation Commission, applicable to radio equipment.', 'SRRC认证是无线电设备进入中国市场的强制性认证。', 'SRRC certification is mandatory for radio equipment entering the Chinese market.', NULL, 3, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(100, '能效标识', 'Energy Label', '亚洲认证', 29, 0, '中国', '能效标识是中国对用能产品的能效等级标识。', 'Energy label is China''s energy efficiency rating label for energy-consuming products.', '能效标识分为1-5级，1级最节能，5级最耗能。', 'Energy labels are divided into levels 1-5, with level 1 being the most energy-efficient and level 5 being the most energy-consuming.', NULL, 4, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(101, '进网许可', 'Network Access License', '亚洲认证', 29, 0, '中国', '进网许可是中国工业和信息化部的认证。', 'Network Access License is certification from China''s Ministry of Industry and Information Technology.', '进网许可适用于电信设备，确保设备符合中国通信标准。', 'Network Access License applies to telecommunications equipment, ensuring equipment complies with Chinese communication standards.', NULL, 5, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(102, '3C认证', '3C Certification', '亚洲认证', 29, 0, '中国', '3C认证是CCC认证的俗称。', '3C certification is the common name for CCC certification.', '3C认证即中国强制性产品认证。', '3C certification is China Compulsory Certification.', NULL, 6, '2025-12-30 20:15:10', '2025-12-30 20:15:10');

-- ============================================
-- 新加坡 认证服务
-- ============================================
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(103, 'IMDA认证', 'IMDA Certification', '亚洲认证', 35, 0, '新加坡', 'IMDA是新加坡信息通信媒体发展局的认证。', 'IMDA is certification from Singapore''s Info-communications Media Development Authority.', 'IMDA认证适用于在新加坡销售的通信设备。', 'IMDA certification applies to communication equipment sold in Singapore.', NULL, 1, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(104, 'SPRING认证', 'SPRING Certification', '亚洲认证', 35, 0, '新加坡', 'SPRING是新加坡标准、生产力与创新局的认证。', 'SPRING is certification from Standards, Productivity and Innovation Board of Singapore.', 'SPRING认证确保产品符合新加坡标准。', 'SPRING certification ensures products comply with Singapore standards.', NULL, 2, '2025-12-30 20:15:10', '2025-12-30 20:15:10');

-- ============================================
-- 英国 认证服务
-- ============================================
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(105, 'UKCA认证', 'UKCA Marking', '欧洲认证', 23, 0, '英国', 'UKCA是英国脱欧后的新认证标志。', 'UKCA (UK Conformity Assessed) marking is the new product marking for products sold in Great Britain after Brexit.', 'UKCA标志适用于在英国市场销售的产品，表明产品符合英国相关法规要求。', 'UKCA marking applies to products sold in the UK market, indicating compliance with UK regulations.', NULL, 1, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(106, 'BSI认证', 'BSI Certification', '欧洲认证', 23, 0, '英国', 'BSI是英国标准协会的认证服务。', 'BSI (British Standards Institution) provides certification services.', 'BSI认证包括质量管理体系、环境管理体系等多种认证服务。', 'BSI certification includes quality management systems, environmental management systems, and various certification services.', NULL, 2, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(107, 'BS认证', 'BS Mark', '欧洲认证', 23, 0, '英国', 'BS是英国标准的认证标志。', 'BS is the British Standards certification mark.', 'BS认证确保产品符合英国国家标准。', 'BS certification ensures products comply with British national standards.', NULL, 3, '2025-12-30 20:15:10', '2025-12-30 20:15:10');

-- ============================================
-- 印度 认证服务
-- ============================================
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(108, 'BIS认证', 'BIS Certification', '亚洲认证', 34, 0, '印度', 'BIS是印度标准局的强制性认证。', 'BIS is mandatory certification from the Bureau of Indian Standards.', 'BIS认证适用于在印度销售的产品，确保产品符合印度标准。', 'BIS certification applies to products sold in India, ensuring products comply with Indian standards.', NULL, 1, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(109, 'WPC认证', 'WPC Certification', '亚洲认证', 34, 0, '印度', 'WPC是印度无线规划与协调局的认证。', 'WPC is certification from Wireless Planning and Coordination Wing of India.', 'WPC认证适用于无线电设备。', 'WPC certification applies to radio equipment.', NULL, 2, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(110, 'TEC认证', 'TEC Certification', '亚洲认证', 34, 0, '印度', 'TEC是印度电信工程中心的认证。', 'TEC is certification from Telecom Engineering Centre of India.', 'TEC认证适用于电信设备。', 'TEC certification applies to telecommunications equipment.', NULL, 3, '2025-12-30 20:15:10', '2025-12-30 20:15:10');

-- ============================================
-- 匈牙利 认证服务
-- ============================================
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(111, 'CE认证', 'CE Marking', '欧洲认证', 13, 0, '匈牙利', 'CE标志适用于匈牙利市场。', 'CE marking applies to the Hungarian market.', '匈牙利作为欧盟成员国，产品需要CE认证才能进入市场。', 'As an EU member state, products require CE certification to enter the Hungarian market.', 'icon/certification_service/CE.png', 1, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(112, 'MSZ认证', 'MSZ Certification', '欧洲认证', 13, 0, '匈牙利', 'MSZ是匈牙利国家标准的认证。', 'MSZ is certification from Hungarian National Standards.', 'MSZ认证确保产品符合匈牙利国家标准。', 'MSZ certification ensures products comply with Hungarian national standards.', NULL, 2, '2025-12-30 20:15:10', '2025-12-30 20:15:10');

-- ============================================
-- 电池检测 认证服务
-- ============================================
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(113, '电池安全测试', 'Battery Safety Testing', '电池检测', 10, 0, NULL, '电池安全测试确保电池产品符合安全标准。', 'Battery safety testing ensures battery products meet safety standards.', '包括过充、过放、短路、热滥用、挤压、针刺等安全测试。', 'Including overcharge, over-discharge, short circuit, thermal abuse, crush, nail penetration and other safety tests.', NULL, 1, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(114, '电池性能测试', 'Battery Performance Testing', '电池检测', 10, 0, NULL, '电池性能测试评估电池的容量、功率等性能。', 'Battery performance testing evaluates battery capacity, power and other performance.', '包括容量测试、内阻测试、循环寿命测试、倍率性能测试等。', 'Including capacity testing, internal resistance testing, cycle life testing, rate performance testing, etc.', NULL, 2, '2025-12-30 20:15:10', '2025-12-30 20:15:10');
INSERT INTO certification_service (id, name_cn, name_en, category, category_id, parent_id, region, summary_cn, summary_en, description_cn, description_en, image, sort_order, create_time, update_time) VALUES
(115, '电池环境测试', 'Battery Environmental Testing', '电池检测', 10, 0, NULL, '电池环境测试评估电池在不同环境条件下的性能。', 'Battery environmental testing evaluates battery performance under different environmental conditions.', '包括高低温测试、湿度测试、振动测试等。', 'Including high and low temperature testing, humidity testing, vibration testing, etc.', NULL, 3, '2025-12-30 20:15:10', '2025-12-30 20:15:10');

