-- 荣誉资质数据
-- 来源: http://www.zht-lab.cn/PicList.aspx?ClassID=27

INSERT INTO honor (title, image, sort_order) VALUES
('公司LOGO', 'upload/img/20241224115314.png', 1)
ON DUPLICATE KEY UPDATE image = VALUES(image);

INSERT INTO honor (title, image, sort_order) VALUES
('荣誉资质 2', 'upload/img/20241224115314.png', 2)
ON DUPLICATE KEY UPDATE image = VALUES(image);

INSERT INTO honor (title, image, sort_order) VALUES
('A2LA证书', '/upload/20241217104828953.jpg', 3)
ON DUPLICATE KEY UPDATE image = VALUES(image);

INSERT INTO honor (title, image, sort_order) VALUES
('CMA资质认定证书', '/upload/20241217104820433.jpg', 4)
ON DUPLICATE KEY UPDATE image = VALUES(image);

INSERT INTO honor (title, image, sort_order) VALUES
('CNAS实验室认可证书', '/upload/20241217104727155.jpg', 5)
ON DUPLICATE KEY UPDATE image = VALUES(image);

INSERT INTO honor (title, image, sort_order) VALUES
('upload/img/20241217105815.png', 'upload/img/20241217105815.png', 6)
ON DUPLICATE KEY UPDATE image = VALUES(image);

INSERT INTO honor (title, image, sort_order) VALUES
('二维码', 'upload/20241217091418.jpg', 7)
ON DUPLICATE KEY UPDATE image = VALUES(image);

