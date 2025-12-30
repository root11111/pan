/**
 * 页面美化工具函数
 * 为页面添加统一的现代化设计和动画效果
 */

import { nextTick } from 'vue'
import { initAllScrollAnimations } from './scrollAnimation'

/**
 * 美化页面Banner
 * @param {HTMLElement} bannerElement - Banner元素
 */
export function beautifyBanner(bannerElement) {
  if (!bannerElement) return
  
  // 添加动画类
  bannerElement.classList.add('slide-in-top')
  
  // 添加内容包装器
  const h1 = bannerElement.querySelector('h1')
  const p = bannerElement.querySelector('p')
  
  if (h1 && p && !bannerElement.querySelector('.banner-content')) {
    const bannerContent = document.createElement('div')
    bannerContent.className = 'banner-content'
    
    const h1Clone = h1.cloneNode(true)
    h1Clone.classList.add('fade-in-immediate')
    bannerContent.appendChild(h1Clone)
    
    const pClone = p.cloneNode(true)
    pClone.classList.add('fade-in-immediate')
    pClone.style.animationDelay = '0.2s'
    bannerContent.appendChild(pClone)
    
    h1.remove()
    p.remove()
    bannerElement.appendChild(bannerContent)
  }
}

/**
 * 初始化页面美化
 * @param {Object} options - 配置选项
 */
export async function initPageBeautify(options = {}) {
  const {
    initScrollAnimation = true,
    beautifyBannerElement = true
  } = options
  
  await nextTick()
  
  // 美化Banner
  if (beautifyBannerElement) {
    const banner = document.querySelector('.page-banner')
    if (banner) {
      beautifyBanner(banner)
    }
  }
  
  // 初始化滚动动画
  if (initScrollAnimation) {
    setTimeout(() => {
      initAllScrollAnimations()
    }, 100)
  }
}

