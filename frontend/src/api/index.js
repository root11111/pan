import axios from 'axios'
import { ElMessage } from 'element-plus'
import router from '../router'

const api = axios.create({
  baseURL: '/api',
  timeout: 30000  // 增加到30秒，适应手机端网络慢的情况
})

// 请求拦截器
api.interceptors.request.use(
  config => {
    // 添加token
    const token = localStorage.getItem('admin_token')
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  error => {
    return Promise.reject(error)
  }
)

// 响应拦截器
api.interceptors.response.use(
  response => {
    return response.data
  },
  error => {
    if (error.response) {
      const status = error.response.status
      const url = error.config?.url || ''
      
      // 静默处理404错误（资源不存在，可能是图片、文件等）
      // 不显示错误提示，避免在后台响应慢时弹出大量错误
      if (status === 404) {
        // 如果是资源请求（图片、文件等），静默处理
        if (url.includes('/uploads/') || url.includes('/upload/') || url.includes('/file/') || url.includes('/icon/')) {
          console.debug(`资源不存在: ${url}`)
          // 返回一个空对象，避免后续处理出错
          return Promise.resolve({ code: 404, data: null, message: '资源不存在' })
        }
        // 对于API请求的404，也静默处理（可能是后台还没完全启动）
        console.debug(`API请求404: ${url}`)
        return Promise.resolve({ code: 404, data: null, message: '资源不存在' })
      }
      
      if (status === 401) {
        ElMessage.error('登录已过期，请重新登录')
        localStorage.removeItem('admin_token')
        localStorage.removeItem('admin_username')
        localStorage.removeItem('admin_nickname')
        if (router.currentRoute.value.path.startsWith('/admin')) {
          router.push('/admin/login')
        }
      } else if (status >= 500) {
        // 服务器错误才显示提示
        ElMessage.error(error.response.data?.message || '服务器错误，请稍后重试')
      } else if (status !== 404) {
        // 其他非404错误才显示提示
        ElMessage.error(error.response.data?.message || '请求失败')
      }
    } else {
      // 处理超时错误和网络错误
      const isTimeout = error.code === 'ECONNABORTED' || error.message?.includes('timeout')
      const url = error.config?.url || ''
      
      // 对于提交留言的请求，如果是超时，给出更友好的提示
      if (isTimeout && url.includes('/message/submit')) {
        ElMessage.error('网络较慢，请求超时，请检查网络后重试')
      } else if (isTimeout) {
        // 其他请求的超时，静默处理
        console.debug('请求超时:', url)
        return Promise.resolve({ code: 408, data: null, message: '请求超时' })
      } else {
        // 其他网络错误（可能是后台还没启动）
        // 不显示错误提示，避免在后台响应慢时弹出大量错误
        console.debug('网络错误:', error.message)
        return Promise.resolve({ code: 500, data: null, message: '网络错误' })
      }
    }
    return Promise.reject(error)
  }
)

export default api

