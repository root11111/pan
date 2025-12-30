/**
 * 滚动动画工具
 * 使用 Intersection Observer 实现元素进入视口时的动画效果
 */

/**
 * 初始化滚动动画
 * @param {string} selector - 要观察的元素选择器
 * @param {Object} options - 配置选项
 */
export function initScrollAnimation(selector, options = {}) {
  const {
    root = null,
    rootMargin = '0px 0px -100px 0px', // 提前100px触发
    threshold = 0.1,
    animationClass = 'animate-in',
    once = true // 是否只触发一次
  } = options

  const observer = new IntersectionObserver(
    (entries) => {
      entries.forEach((entry) => {
        if (entry.isIntersecting) {
          entry.target.classList.add(animationClass)
          if (once) {
            observer.unobserve(entry.target)
          }
        } else if (!once) {
          entry.target.classList.remove(animationClass)
        }
      })
    },
    {
      root,
      rootMargin,
      threshold
    }
  )

  // 观察所有匹配的元素
  const elements = document.querySelectorAll(selector)
  elements.forEach((el) => {
    observer.observe(el)
  })

  return observer
}

/**
 * 为多个元素添加堆叠动画（stagger effect）
 * @param {string} selector - 父容器选择器
 * @param {string} itemSelector - 子元素选择器
 * @param {Object} options - 配置选项
 */
export function initStaggerAnimation(selector, itemSelector, options = {}) {
  const {
    root = null,
    rootMargin = '0px 0px -100px 0px',
    threshold = 0.1,
    animationClass = 'animate-in',
    staggerDelay = 0.1, // 每个元素的延迟时间（秒）
    once = true
  } = options

  const observer = new IntersectionObserver(
    (entries) => {
      entries.forEach((entry) => {
        if (entry.isIntersecting) {
          const container = entry.target
          const items = container.querySelectorAll(itemSelector)
          
          items.forEach((item, index) => {
            setTimeout(() => {
              item.classList.add(animationClass)
            }, index * staggerDelay * 1000)
          })

          if (once) {
            observer.unobserve(container)
          }
        } else if (!once) {
          const items = entry.target.querySelectorAll(itemSelector)
          items.forEach((item) => {
            item.classList.remove(animationClass)
          })
        }
      })
    },
    {
      root,
      rootMargin,
      threshold
    }
  )

  const containers = document.querySelectorAll(selector)
  containers.forEach((container) => {
    observer.observe(container)
  })

  return observer
}

/**
 * 初始化所有滚动动画
 */
export function initAllScrollAnimations() {
  // 从右侧滑入
  initScrollAnimation('.slide-in-right', {
    animationClass: 'animate-slide-right'
  })

  // 从左侧滑入
  initScrollAnimation('.slide-in-left', {
    animationClass: 'animate-slide-left'
  })

  // 从顶部滑入
  initScrollAnimation('.slide-in-top', {
    animationClass: 'animate-slide-top'
  })

  // 从底部滑入
  initScrollAnimation('.slide-in-bottom', {
    animationClass: 'animate-slide-bottom'
  })

  // 缩放进入
  initScrollAnimation('.scale-in', {
    animationClass: 'animate-scale-in'
  })

  // 旋转进入
  initScrollAnimation('.rotate-in', {
    animationClass: 'animate-rotate-in'
  })

  // 淡入
  initScrollAnimation('.fade-in', {
    animationClass: 'animate-fade-in'
  })

  // 堆叠动画 - 企业优势
  initStaggerAnimation('.advantage-list', '.advantage-item', {
    animationClass: 'animate-scale-in',
    staggerDelay: 0.15
  })

  // 堆叠动画 - 实验室
  initStaggerAnimation('.laboratories .el-row', '.lab-card', {
    animationClass: 'animate-scale-in',
    staggerDelay: 0.1
  })

  // 堆叠动画 - 荣誉资质
  initStaggerAnimation('.honors .el-row', '.honor-card', {
    animationClass: 'animate-scale-in',
    staggerDelay: 0.1
  })

  // 堆叠动画 - 新闻列表
  initStaggerAnimation('.news-list', '.news-card', {
    animationClass: 'animate-scale-in',
    staggerDelay: 0.1
  })

  // 堆叠动画 - 认证服务列表
  initStaggerAnimation('.service-list', '.service-card', {
    animationClass: 'animate-scale-in',
    staggerDelay: 0.1
  })

  // 堆叠动画 - 企业优势列表
  initStaggerAnimation('.advantage-list', '.advantage-card', {
    animationClass: 'animate-scale-in',
    staggerDelay: 0.15
  })

  // 堆叠动画 - 企业文化列表
  initStaggerAnimation('.culture-list', '.culture-card', {
    animationClass: 'animate-scale-in',
    staggerDelay: 0.15
  })

  // 堆叠动画 - 实验室列表
  initStaggerAnimation('.lab-list', '.lab-card', {
    animationClass: 'animate-scale-in',
    staggerDelay: 0.1
  })

  // 堆叠动画 - Banner 值
  initStaggerAnimation('.banner-values', '.value-item', {
    animationClass: 'animate-slide-up',
    staggerDelay: 0.1,
    rootMargin: '0px'
  })
}

