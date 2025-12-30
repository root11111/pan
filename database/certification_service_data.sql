-- 认证服务详细数据（从各认证页面爬取）
-- 来源: http://www.zht-lab.cn/

DELETE FROM certification_service;

INSERT INTO certification_service (name_cn, name_en, category, region, description_cn, description_en, sort_order) VALUES
('欧盟认证', 'EU Certification', '欧洲认证', '欧盟', 'CE标志是一种安全认证标志，被视为制造商打开并进入欧洲市场的护照。CE代表欧洲统一（CONFORMITE EUROPEENNE）。凡是贴有“CE”标志的产品就可在欧盟各成员国内销售，以表明产品符合欧盟《技术协调与标准化新方法》指令的基本要求，无须符合每个成员国的要求，从而实现了商品在欧盟成员国范围内的自由流通。', 'CE标志是一种安全认证标志，被视为制造商打开并进入欧洲市场的护照。CE代表欧洲统一（CONFORMITE EUROPEENNE）。凡是贴有“CE”标志的产品就可在欧盟各成员国内销售，以表明产品符合欧盟《技术协调与标准化新方法》指令的基本要求，无须符合每个成员国的要求，从而实现了商品在欧盟成员国范围内的自由流通。', 1);

INSERT INTO certification_service (name_cn, name_en, category, region, description_cn, description_en, sort_order) VALUES
('匈牙利认证', 'Hungary Certification', '欧洲认证', '匈牙利', '德国莱因可为在匈牙利市场上销售的产品取得安全及EMC标志 全球的德国莱因经由我们的伙伴MEEI办理匈牙利产品安全验证德国莱因可以经办匈牙利S-Mark验证，MEEI型式验证和MEEI标志验证', '德国莱因可为在匈牙利市场上销售的产品取得安全及EMC标志 全球的德国莱因经由我们的伙伴MEEI办理匈牙利产品安全验证德国莱因可以经办匈牙利S-Mark验证，MEEI型式验证和MEEI标志验证', 2);

INSERT INTO certification_service (name_cn, name_en, category, region, description_cn, description_en, sort_order) VALUES
('土耳其认证', 'Turkey Certification', '欧洲认证', '土耳其', '对于欧盟指令未涵盖的产品及虽不在欧盟指令范围内但仍然包含在土耳其本国强制标准范围内的产品，在进口前必须按照土耳其强制标准的要求，进行检测，获得TSE认证，方可通过海关，进入土耳其市场销售。', '对于欧盟指令未涵盖的产品及虽不在欧盟指令范围内但仍然包含在土耳其本国强制标准范围内的产品，在进口前必须按照土耳其强制标准的要求，进行检测，获得TSE认证，方可通过海关，进入土耳其市场销售。', 3);

INSERT INTO certification_service (name_cn, name_en, category, region, description_cn, description_en, sort_order) VALUES
('爱尔兰认证', 'Ireland Certification', '欧洲认证', '爱尔兰', 'NSAI 是爱尔兰的标准化机构。NSAI 促进了自发标准文档的开发，这些标准用于帮助制造商或服务供货商满足安全或客户需求。它也是国内外产品和系统认证服务的主要供货商，是爱尔兰法定度量衡服务的提供者。', 'NSAI 是爱尔兰的标准化机构。NSAI 促进了自发标准文档的开发，这些标准用于帮助制造商或服务供货商满足安全或客户需求。它也是国内外产品和系统认证服务的主要供货商，是爱尔兰法定度量衡服务的提供者。', 4);

INSERT INTO certification_service (name_cn, name_en, category, region, description_cn, description_en, sort_order) VALUES
('斯洛文尼亚认证', 'Slovenia Certification', '欧洲认证', '斯洛文尼亚', '斯洛文尼亚质量与计量研究院（SIQ）是一家有四十多年历史的国际机构。它是IQnet，CB体系和CCA协议的成员之一，拥有自己的实验室，是一家集体系认证，产品测试、认证和计量于一体的国际级认证机构，总部位于斯洛文尼亚，在CB体系中，它具有Member Body、NCB和CBTL三位一体的功能，在欧洲，它是近十个指令的授权机构(Notified Body)，产品通过测试后，可以颁发的测试报告，认证证书或标志有CB、CE、FCC、GS、E-mark等。同时，可提供欧盟通用的KEYMARK（用于家用电器及类似产品）、ENEC mark（用于照明电器，IT及音视频和变压器等）和SIQ EMC mark。', '斯洛文尼亚质量与计量研究院（SIQ）是一家有四十多年历史的国际机构。它是IQnet，CB体系和CCA协议的成员之一，拥有自己的实验室，是一家集体系认证，产品测试、认证和计量于一体的国际级认证机构，总部位于斯洛文尼亚，在CB体系中，它具有Member Body、NCB和CBTL三位一体的功能，在欧洲，它是近十个指令的授权机构(Notified Body)，产品通过测试后，可以颁发的测试报告，认证证书或标志有CB、CE、FCC、GS、E-mark等。同时，可提供欧盟通用的KEYMARK（用于家用电器及类似产品）、ENEC mark（用于照明电器，IT及音视频和变压器等）和SIQ EMC mark。', 5);

INSERT INTO certification_service (name_cn, name_en, category, region, description_cn, description_en, sort_order) VALUES
('乌克兰认证', 'Ukraine Certification', '欧洲认证', '乌克兰', '乌克兰电子设备的测试和认证机构（ukrtest）是一个stateenterprise，和国家认证体系，产品测试和certificationservices。在乌克兰，电子产品认证是强制性的，为ukraine''sproducts取得了ukrtest证书，该证书的有效期为三年，包括安全和电磁兼容性要求', '乌克兰电子设备的测试和认证机构（ukrtest）是一个stateenterprise，和国家认证体系，产品测试和certificationservices。在乌克兰，电子产品认证是强制性的，为ukraine''sproducts取得了ukrtest证书，该证书的有效期为三年，包括安全和电磁兼容性要求', 6);

INSERT INTO certification_service (name_cn, name_en, category, region, description_cn, description_en, sort_order) VALUES
('捷克认证', 'Czech Certification', '欧洲认证', '捷克', '德国莱因与EZU （捷克共和国之发证单位） 有合作关系，EZU可依德国莱因所提供的文件发证。捷克标准与欧洲标准可以共通，相互调合EZU 认证是非强制性的。', '德国莱因与EZU （捷克共和国之发证单位） 有合作关系，EZU可依德国莱因所提供的文件发证。捷克标准与欧洲标准可以共通，相互调合EZU 认证是非强制性的。', 7);

INSERT INTO certification_service (name_cn, name_en, category, region, description_cn, description_en, sort_order) VALUES
('斯洛伐克认证', 'Slovakia Certification', '欧洲认证', '斯洛伐克', 'EVPU认证，是捷克与斯洛伐克解体后，成为两个独立的共各国，并依据欧盟的标准制定了本国的产品检测及认证标准，EVPU机构并与2003年在中国设立了办室处，处理亚洲地区的认证申请项目，并与俄罗斯，等国的认证机构建立良好的合作关系，从而推动本国认证事业的发展。', 'EVPU认证，是捷克与斯洛伐克解体后，成为两个独立的共各国，并依据欧盟的标准制定了本国的产品检测及认证标准，EVPU机构并与2003年在中国设立了办室处，处理亚洲地区的认证申请项目，并与俄罗斯，等国的认证机构建立良好的合作关系，从而推动本国认证事业的发展。', 8);

INSERT INTO certification_service (name_cn, name_en, category, region, description_cn, description_en, sort_order) VALUES
('希腊认证', 'Greece Certification', '欧洲认证', '希腊', 'ELOT可以适用认证程序和体系（如：ISO认证体系）。经法律1682/1997及总统法令155/1997《<希腊公司标准化组织>公司的整合及法令》修订， 法令372/76《ELOT的建立和运作》中委托ELOT进行认证活动的开发。', 'ELOT可以适用认证程序和体系（如：ISO认证体系）。经法律1682/1997及总统法令155/1997《<希腊公司标准化组织>公司的整合及法令》修订， 法令372/76《ELOT的建立和运作》中委托ELOT进行认证活动的开发。', 9);

INSERT INTO certification_service (name_cn, name_en, category, region, description_cn, description_en, sort_order) VALUES
('德国认证', 'Germany Certification', '欧洲认证', '德国', 'GS的含义是德语"Geprufte Sicherheit"（安全性已认证），也有"Germany Safety"（德国安全）的意思。GS认证以德国产品安全法（GPGS）为依据，按照欧盟统一标准EN或德国工业标准DIN进行检测的一种自愿性认证，是欧洲市场公认的德国安全认证标志。', 'GS的含义是德语"Geprufte Sicherheit"（安全性已认证），也有"Germany Safety"（德国安全）的意思。GS认证以德国产品安全法（GPGS）为依据，按照欧盟统一标准EN或德国工业标准DIN进行检测的一种自愿性认证，是欧洲市场公认的德国安全认证标志。', 10);

INSERT INTO certification_service (name_cn, name_en, category, region, description_cn, description_en, sort_order) VALUES
('西班牙认证', 'Spain Certification', '欧洲认证', '西班牙', 'AENOR 是一个致力于在所有的工业和服务部门的标准化和认证 ( S+C) 的发展的组织。 它的目标是在公司的质量和竞争力和环保的进步。', 'AENOR 是一个致力于在所有的工业和服务部门的标准化和认证 ( S+C) 的发展的组织。 它的目标是在公司的质量和竞争力和环保的进步。', 11);

INSERT INTO certification_service (name_cn, name_en, category, region, description_cn, description_en, sort_order) VALUES
('英国认证', 'UK Certification', '欧洲认证', '英国', '英国政府宣布推出新合格标志UKCA（代表已获英国合格评定）。汽车、航空航天、医药产品、医疗器械、化学品，以及受国家法规管制的物品（非协调物品）等特定产品在英国脱欧后再在英国市场销售时，将不可以继续使用欧盟规范及符合性标志。 首页 关于我们 实验室展示 认证服务 荣誉资质 证书查询 新闻资讯 在线留言 联系我们 © 2025 广东中翰检测技术有限公司 All Rights Reserved. 备案号：粤ICP备2024348941号-1 首页 电话 留言 回到顶部 电话 18307618653 QQ 405705201 微信 返回顶部', '英国政府宣布推出新合格标志UKCA（代表已获英国合格评定）。汽车、航空航天、医药产品、医疗器械、化学品，以及受国家法规管制的物品（非协调物品）等特定产品在英国脱欧后再在英国市场销售时，将不可以继续使用欧盟规范及符合性标志。 首页 关于我们 实验室展示 认证服务 荣誉资质 证书查询 新闻资讯 在线留言 联系我们 © 2025 广东中翰检测技术有限公司 All Rights Reserved. 备案号：粤ICP备2024348941号-1 首页 电话 留言 回到顶部 电话 18307618653 QQ 405705201 微信 返回顶部', 12);

INSERT INTO certification_service (name_cn, name_en, category, region, description_cn, description_en, sort_order) VALUES
('美国认证', 'USA Certification', '美洲认证', '美国', 'FCC全称美国联邦通讯委员会Federal Communications Commission。直接对国会负责，通过控制无线电广播、电视、电信、卫星和电缆来协调国内和国际的通信，负责授权和管理除联邦政府使用之外的射频传输装置和设备。', 'FCC全称美国联邦通讯委员会Federal Communications Commission。直接对国会负责，通过控制无线电广播、电视、电信、卫星和电缆来协调国内和国际的通信，负责授权和管理除联邦政府使用之外的射频传输装置和设备。', 13);

INSERT INTO certification_service (name_cn, name_en, category, region, description_cn, description_en, sort_order) VALUES
('墨西哥认证', 'Mexico Certification', '美洲认证', '墨西哥', '墨西哥认证认证服务', 'Mexico Certification certification service', 14);

INSERT INTO certification_service (name_cn, name_en, category, region, description_cn, description_en, sort_order) VALUES
('加拿大认证', 'Canada Certification', '美洲认证', '加拿大', 'CSA是加拿大标准协会（Canadian Standards Association）的简称它成立于1919年，是加拿大首家专为制定工业标准的非盈利性机构。在北美市场上销售的电子、电器等产品都需要取得安全方面的认证。目前CSA是加拿大***的安全认证机构，也是世界上******的安全认证机构之一 。它能对机械、建材、电器、电脑设备、办公设备、环保、医疗防火安全、运动及娱乐等方面的所有类型的产品提供安全认证。CSA已为遍布全球的数千厂商提供了认证服务，每年均有上亿个附有CSA标志的产品在北美市场销售。', 'CSA是加拿大标准协会（Canadian Standards Association）的简称它成立于1919年，是加拿大首家专为制定工业标准的非盈利性机构。在北美市场上销售的电子、电器等产品都需要取得安全方面的认证。目前CSA是加拿大***的安全认证机构，也是世界上******的安全认证机构之一 。它能对机械、建材、电器、电脑设备、办公设备、环保、医疗防火安全、运动及娱乐等方面的所有类型的产品提供安全认证。CSA已为遍布全球的数千厂商提供了认证服务，每年均有上亿个附有CSA标志的产品在北美市场销售。', 15);

INSERT INTO certification_service (name_cn, name_en, category, region, description_cn, description_en, sort_order) VALUES
('阿根廷认证', 'Argentina Certification', '美洲认证', '阿根廷', '阿根廷认证认证服务', 'Argentina Certification certification service', 16);

INSERT INTO certification_service (name_cn, name_en, category, region, description_cn, description_en, sort_order) VALUES
('巴西认证', 'Brazil Certification', '美洲认证', '巴西', 'INMETRO是巴西的国家认可机构（Accreditation Body），负责制定巴西国家标准。巴西的产品标准大部分以IEC和ISO标准为基础，需要把产品出口到巴西的制造商在设计产品时应该参考这两套标准。凡符合巴西标准及其他技术性要求的产品，必须加上强制性的INMETRO标志及经认可的第三方认证机构的标志，才能进入巴西市场。', 'INMETRO是巴西的国家认可机构（Accreditation Body），负责制定巴西国家标准。巴西的产品标准大部分以IEC和ISO标准为基础，需要把产品出口到巴西的制造商在设计产品时应该参考这两套标准。凡符合巴西标准及其他技术性要求的产品，必须加上强制性的INMETRO标志及经认可的第三方认证机构的标志，才能进入巴西市场。', 17);

INSERT INTO certification_service (name_cn, name_en, category, region, description_cn, description_en, sort_order) VALUES
('中国认证', 'China Certification', '亚洲认证', '中国', '2024-12-17 CQC是代表中国加入国际电工委员会电工产品合格测试与认证组织（IECEE）多边互认（CB）体系的国家认证机构（NCB）,是加入国际认证联盟（IQNet）和国际有机农业运动联盟（IFOAM）的国家认证机构，CQC与国外诸多知名认证机构间的国际互认业务，以及广泛的国际交流，使CQC赢得了良好的国际形象。', '2024-12-17 CQC是代表中国加入国际电工委员会电工产品合格测试与认证组织（IECEE）多边互认（CB）体系的国家认证机构（NCB）,是加入国际认证联盟（IQNet）和国际有机农业运动联盟（IFOAM）的国家认证机构，CQC与国外诸多知名认证机构间的国际互认业务，以及广泛的国际交流，使CQC赢得了良好的国际形象。', 18);

INSERT INTO certification_service (name_cn, name_en, category, region, description_cn, description_en, sort_order) VALUES
('日本认证', 'Japan Certification', '亚洲认证', '日本', '2024-12-17 PSE 认证是日本强制性安全认证，用以证明电机电子产品已通过日本电气和原料安全法 (DENAN Law) 或国际IEC标准的安全标准测试。日本的DENTORL法（电器装置和材料控制法）规定，498种产品进入日本市场必须通过安全认证。其中，165种A类产品应取得菱形的PSE标志，333种B类产品应取得圆形PSE标志。 KC-Mark认证2024-12-17 韩国知识经济部(MKE)于2008年8月25日宣布，国家标准委员会将在2009年7月至2010年12月实行一个新的国家统一认证标志，名为"KC Mark"标志。', '2024-12-17 PSE 认证是日本强制性安全认证，用以证明电机电子产品已通过日本电气和原料安全法 (DENAN Law) 或国际IEC标准的安全标准测试。日本的DENTORL法（电器装置和材料控制法）规定，498种产品进入日本市场必须通过安全认证。其中，165种A类产品应取得菱形的PSE标志，333种B类产品应取得圆形PSE标志。 KC-Mark认证2024-12-17 韩国知识经济部(MKE)于2008年8月25日宣布，国家标准委员会将在2009年7月至2010年12月实行一个新的国家统一认证标志，名为"KC Mark"标志。', 19);

INSERT INTO certification_service (name_cn, name_en, category, region, description_cn, description_en, sort_order) VALUES
('韩国认证', 'Korea Certification', '亚洲认证', '韩国', '韩国认证认证服务', 'Korea Certification certification service', 20);

INSERT INTO certification_service (name_cn, name_en, category, region, description_cn, description_en, sort_order) VALUES
('沙特认证', 'Saudi Arabia Certification', '亚洲认证', '沙特', '2024-12-17 SASO是英文Saudi Arabian Standards Organization的缩写 ，即沙特阿拉伯标准组织。SASO负责为所有的日用品及产品制定国家标准，标准中还涉及度量制度，标识等。 PCT认证2024-12-17 PCT认证即GOST认证，因GOST认证的标志为PCT，是产品进入俄罗斯独联体（包括俄罗斯、白俄罗斯、哈萨克斯坦、乌克兰等）的强制性安全认证，俄语为ГОСТ Государственный общесоюзный стандарт，即全苏国家标准，是进入俄罗斯独联体的通行证。 BIS/STQC认证2024-12-17 ISI标志是由印度标准局 (BIS) 核发的认证标志，范围几乎涵盖各行各业的规范，从农业、纺织到电子等。 PSB认证2024-12-17 列管电气产品需强制申请PSB认证，产品进入新加坡之前，必须由在新加坡有税籍之公司行号做为申请PSB之持证者（本公司提供新加坡商，可作为进行申请PSB认证时之持证者）。产品于取得PSB之后，方可进入新加坡销售。', '2024-12-17 SASO是英文Saudi Arabian Standards Organization的缩写 ，即沙特阿拉伯标准组织。SASO负责为所有的日用品及产品制定国家标准，标准中还涉及度量制度，标识等。 PCT认证2024-12-17 PCT认证即GOST认证，因GOST认证的标志为PCT，是产品进入俄罗斯独联体（包括俄罗斯、白俄罗斯、哈萨克斯坦、乌克兰等）的强制性安全认证，俄语为ГОСТ Государственный общесоюзный стандарт，即全苏国家标准，是进入俄罗斯独联体的通行证。 BIS/STQC认证2024-12-17 ISI标志是由印度标准局 (BIS) 核发的认证标志，范围几乎涵盖各行各业的规范，从农业、纺织到电子等。 PSB认证2024-12-17 列管电气产品需强制申请PSB认证，产品进入新加坡之前，必须由在新加坡有税籍之公司行号做为申请PSB之持证者（本公司提供新加坡商，可作为进行申请PSB认证时之持证者）。产品于取得PSB之后，方可进入新加坡销售。', 21);

INSERT INTO certification_service (name_cn, name_en, category, region, description_cn, description_en, sort_order) VALUES
('俄罗斯认证', 'Russia Certification', '亚洲认证', '俄罗斯', '俄罗斯认证认证服务', 'Russia Certification certification service', 22);

INSERT INTO certification_service (name_cn, name_en, category, region, description_cn, description_en, sort_order) VALUES
('印度认证', 'India Certification', '亚洲认证', '印度', '印度认证认证服务', 'India Certification certification service', 23);

INSERT INTO certification_service (name_cn, name_en, category, region, description_cn, description_en, sort_order) VALUES
('新加坡认证', 'Singapore Certification', '亚洲认证', '新加坡', '新加坡认证认证服务', 'Singapore Certification certification service', 24);

INSERT INTO certification_service (name_cn, name_en, category, region, description_cn, description_en, sort_order) VALUES
('马来西亚认证', 'Malaysia Certification', '亚洲认证', '马来西亚', '2024-12-17 SIRIM 是马来西亚***一家引导认证的机构·任何的工厂或者公司都可以向SIRIM提出申请，并依照产品认证体系下公认的标准进行审核及获得认可·这些认证是自愿的，但是在很多情况下，应政府管理的要求会要求强制性认证 SII认证2024-12-17 以色列SII机构是Standards Institution of Israel的缩写，负责以色列国家的标准研究制定。以色列也是中东地区***成为NCB的国家，SII作为非政府机构，向以色列本国及国外的输入品提供安全，环保，检测等服务。申请SII认证时，必须执行相应的工厂检查，且产品的包装，色彩也需符合阿拉伯国家的宗教要求。 首页 关于我们 实验室展示 认证服务 荣誉资质 证书查询 新闻资讯 在线留言 联系我们 © 2025 广东中翰检测技术有限公司 All Rights Reserved. 备案号：粤ICP备2024348941号-1 首页 电话 留言 回到顶部 电话 18307618653 QQ 405705201 微信 返回顶部', '2024-12-17 SIRIM 是马来西亚***一家引导认证的机构·任何的工厂或者公司都可以向SIRIM提出申请，并依照产品认证体系下公认的标准进行审核及获得认可·这些认证是自愿的，但是在很多情况下，应政府管理的要求会要求强制性认证 SII认证2024-12-17 以色列SII机构是Standards Institution of Israel的缩写，负责以色列国家的标准研究制定。以色列也是中东地区***成为NCB的国家，SII作为非政府机构，向以色列本国及国外的输入品提供安全，环保，检测等服务。申请SII认证时，必须执行相应的工厂检查，且产品的包装，色彩也需符合阿拉伯国家的宗教要求。 首页 关于我们 实验室展示 认证服务 荣誉资质 证书查询 新闻资讯 在线留言 联系我们 © 2025 广东中翰检测技术有限公司 All Rights Reserved. 备案号：粤ICP备2024348941号-1 首页 电话 留言 回到顶部 电话 18307618653 QQ 405705201 微信 返回顶部', 25);

INSERT INTO certification_service (name_cn, name_en, category, region, description_cn, description_en, sort_order) VALUES
('以色列认证', 'Israel Certification', '亚洲认证', '以色列', '以色列认证认证服务', 'Israel Certification certification service', 26);

INSERT INTO certification_service (name_cn, name_en, category, region, description_cn, description_en, sort_order) VALUES
('澳洲认证', 'Australia Certification', '澳洲认证', '澳洲', '澳洲认证认证服务', 'Australia Certification certification service', 27);

INSERT INTO certification_service (name_cn, name_en, category, region, description_cn, description_en, sort_order) VALUES
('国际认证', 'International Certification', '国际认证', '国际', '国际认证认证服务', 'International Certification certification service', 28);

INSERT INTO certification_service (name_cn, name_en, category, region, description_cn, description_en, sort_order) VALUES
('能效认证', 'Energy Efficiency Certification', '能效认证', '', '能效认证认证服务', 'Energy Efficiency Certification certification service', 29);

INSERT INTO certification_service (name_cn, name_en, category, region, description_cn, description_en, sort_order) VALUES
('体系认证', 'System Certification', '体系认证', '', '体系认证认证服务', 'System Certification certification service', 30);

INSERT INTO certification_service (name_cn, name_en, category, region, description_cn, description_en, sort_order) VALUES
('可靠性测试', 'Reliability Testing', '可靠性测试', '', '可靠性测试认证服务', 'Reliability Testing certification service', 31);

INSERT INTO certification_service (name_cn, name_en, category, region, description_cn, description_en, sort_order) VALUES
('电池检测', 'Battery Testing', '电池检测', '', '电池检测认证服务', 'Battery Testing certification service', 32);

INSERT INTO certification_service (name_cn, name_en, category, region, description_cn, description_en, sort_order) VALUES
('化学检测', 'Chemical Testing', '化学检测', '', '化学检测认证服务', 'Chemical Testing certification service', 33);

