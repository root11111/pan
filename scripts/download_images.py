#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
图片下载脚本
从原网站下载所有需要的图片到本地 uploads 目录
"""

import os
import requests
from urllib.parse import urljoin

# 原网站基础URL
BASE_URL = "http://www.zht-lab.cn/"

# 需要下载的图片列表（从数据库配置中提取）
IMAGES = [
    # 企业优势图标（4张）
    "upload/20231130161735.png",
    "upload/20240119150129.png",
    "upload/20240119150054.png",
    "upload/20240119150032.png",
    
    # 实验室图片（8张）
    "upload/20241217133436948_381613.jpg",
    "upload/20241217133436946_528812.jpg",
    "upload/20241217133436944_528811.jpg",
    "upload/20241217133436940_528810.jpg",
    "upload/20241217133436938_52889.jpg",
    "upload/20241217133436936_52888.jpg",
    "upload/20241217133436934_52887.jpg",
    "upload/20241217133436932_52886.jpg",
    
    # 新闻图片（8张）
    "upload/20250610144522.jpg",
    "upload/20250609143908.png",
    "upload/20250606151148.jpg",
    "upload/20250605152329.png",
    "upload/20250604152354.png",
    "upload/20250603151544.jpg",
    "upload/20250530150503.png",
    "upload/20250529160958.png",
    
    # 荣誉资质图片（1张）
    "upload/img/20241217105815.png",
    
    # 其他图片（1张）
    "upload/20241224111919.jpg",
]

# 本地保存目录（根据实际情况修改）
SAVE_DIR = "D:/workspace/pan/uploads/"

def download_image(url, save_path):
    """下载单个图片"""
    try:
        response = requests.get(url, timeout=10)
        response.raise_for_status()
        
        # 创建目录
        os.makedirs(os.path.dirname(save_path), exist_ok=True)
        
        # 保存文件
        with open(save_path, 'wb') as f:
            f.write(response.content)
        
        print(f"✓ 下载成功: {save_path}")
        return True
    except Exception as e:
        print(f"✗ 下载失败: {url} - {str(e)}")
        return False

def main():
    """主函数"""
    print("开始下载图片...")
    print(f"保存目录: {SAVE_DIR}")
    print("-" * 50)
    
    success_count = 0
    fail_count = 0
    
    for image_path in IMAGES:
        url = urljoin(BASE_URL, image_path)
        save_path = os.path.join(SAVE_DIR, image_path)
        
        if download_image(url, save_path):
            success_count += 1
        else:
            fail_count += 1
    
    print("-" * 50)
    print(f"下载完成！成功: {success_count}, 失败: {fail_count}")

if __name__ == "__main__":
    main()

