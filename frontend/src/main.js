import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import './style.css'
import './styles/variables.css'
import './styles/animations.css'

const app = createApp(App)

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}

// 全局错误处理：静默处理404错误（图片、资源加载失败）
if (typeof window !== 'undefined') {
  // 拦截图片加载错误，静默处理404
  window.addEventListener('error', (event) => {
    // 如果是图片加载错误且是404
    if (event.target && event.target.tagName === 'IMG') {
      const img = event.target
      // 检查是否是404错误
      if (img.src && (img.src.includes('/api/') || img.src.includes('/uploads/'))) {
        // 静默处理，不显示在控制台
        event.preventDefault()
        event.stopPropagation()
        // 隐藏图片，显示占位符
        if (img.style) {
          img.style.display = 'none'
        }
        return false
      }
    }
  }, true) // 使用捕获阶段，确保优先处理

  // 拦截未捕获的Promise错误（可能是API请求失败）
  window.addEventListener('unhandledrejection', (event) => {
    // 如果是404错误，静默处理
    if (event.reason && event.reason.response && event.reason.response.status === 404) {
      event.preventDefault()
      return false
    }
  })
}

app.use(router)
app.use(ElementPlus)
app.mount('#app')

