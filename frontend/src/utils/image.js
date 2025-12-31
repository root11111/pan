/**
 * 图片URL处理工具
 * 将数据库中的相对路径转换为完整的访问URL
 */

// 后端API基础URL（生产环境使用相对路径，开发环境使用 localhost）
const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || (import.meta.env.DEV ? 'http://localhost:8082/api' : '/api')

/**
 * 简单的字符串哈希函数（用于生成稳定的缓存键）
 * @param {string} str - 输入字符串
 * @returns {number} 哈希值
 */
function simpleHash(str) {
  let hash = 0
  for (let i = 0; i < str.length; i++) {
    const char = str.charCodeAt(i)
    hash = ((hash << 5) - hash) + char
    hash = hash & hash // 转换为32位整数
  }
  return hash
}

/**
 * 将图片路径转换为完整的URL
 * @param {string} imagePath - 数据库中的图片路径，如 'upload/20231130161735.png'
 * @param {boolean|string} cacheControl - 缓存控制：true=使用路径哈希（可缓存），false=不添加参数，'force'=强制刷新（使用时间戳）
 * @returns {string} 完整的图片URL
 */
export function getImageUrl(imagePath, cacheControl = true) {
  if (!imagePath) {
    return ''
  }
  
  let url = ''
  
  // 如果已经是完整URL，直接使用
  if (imagePath.startsWith('http://') || imagePath.startsWith('https://')) {
    url = imagePath
    // 如果已经是完整URL且包含参数，直接返回（不添加额外参数）
    if (cacheControl === false) {
      return url
    }
  }
  // 处理已经包含 /api/uploads/ 或 /api/upload/ 的路径（避免重复拼接）
  else if (imagePath.startsWith('/api/uploads/') || imagePath.startsWith('/api/upload/')) {
    // 检查是否有重复的 /api/uploads/ 路径（如：/api/uploads//api/uploads/xxx.png）
    if (imagePath.includes('/api/uploads//api/uploads/') || imagePath.includes('/api/uploads//api/upload/')) {
      // 移除重复的部分，只保留最后一个 /api/uploads/ 及其后面的路径
      // 例如：/api/uploads//api/uploads/img/xxx.png -> /api/uploads/img/xxx.png
      url = imagePath.replace(/\/api\/uploads?\/+\/api\/uploads?\/+/g, '/api/uploads/')
    } else {
      // 直接使用，因为已经是完整的API路径
      url = imagePath
    }
  }
  // 处理以 /uploads/ 开头的路径（如：/uploads/xxx.jpg）
  else if (imagePath.startsWith('/uploads/')) {
    // 移除开头的斜杠，因为 API_BASE_URL 已经包含了 /api
    const path = imagePath.substring(1) // 移除开头的 /
    url = `${API_BASE_URL}/${path}`
  }
  // 处理以 uploads/ 开头的路径（没有斜杠）
  else if (imagePath.startsWith('uploads/')) {
    url = `${API_BASE_URL}/${imagePath}`
  }
  // 如果是相对路径，转换为完整URL
  // 数据库中的路径格式：upload/xxx.jpg 或 upload/img/xxx.png
  // 后端映射：/api/uploads/** -> file:D:/workspace/pan/uploads/
  else if (imagePath.startsWith('upload/')) {
    // 移除开头的 upload/，因为后端映射是 /api/uploads/
    const path = imagePath.replace(/^upload\//, '')
    url = `${API_BASE_URL}/uploads/${path}`
  }
  // 如果路径不包含 upload/，默认添加到 uploads 目录
  else {
    url = `${API_BASE_URL}/uploads/${imagePath}`
  }
  
  // 添加版本参数来控制缓存
  if (cacheControl !== false && url && !url.includes('_v=') && !url.includes('_t=')) {
    const separator = url.includes('?') ? '&' : '?'
    if (cacheControl === 'force') {
      // 强制刷新：使用时间戳（每次页面加载都会重新获取）
      url = `${url}${separator}_t=${Date.now()}`
    } else {
      // 默认：使用路径哈希作为版本（相同路径可以缓存，不同路径会重新加载）
      const version = simpleHash(imagePath)
      url = `${url}${separator}_v=${version}`
    }
  }
  
  return url
}

/**
 * 批量处理图片URL
 * @param {Array|Object} data - 包含图片路径的数据
 * @param {Array<string>} fields - 需要处理的图片字段名数组，如 ['image', 'icon', 'logo']
 * @returns {Array|Object} 处理后的数据
 */
export function processImageUrls(data, fields = ['image', 'icon', 'logo']) {
  if (!data) {
    return data
  }
  
  if (Array.isArray(data)) {
    return data.map(item => processImageUrls(item, fields))
  }
  
  if (typeof data === 'object') {
    const processed = { ...data }
    fields.forEach(field => {
      if (processed[field]) {
        processed[field] = getImageUrl(processed[field])
      }
    })
    return processed
  }
  
  return data
}

