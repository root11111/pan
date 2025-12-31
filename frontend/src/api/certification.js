import api from './index'

export const getCertifications = () => {
  return api.get('/certification/list')
}

export const getCertificationsByCategory = (category) => {
  return api.get('/certification/category', { params: { category } })
}

export const getCertificationsByCategoryId = (categoryId) => {
  return api.get('/certification/category-id', { params: { categoryId } })
}

export const getCertificationById = (id) => {
  return api.get(`/certification/${id}`)
}

export const getCertificationCategories = () => {
  return api.get('/certification-category/tree')
}

export const getAllCertificationCategories = () => {
  return api.get('/certification-category/list')
}

// 获取顶级认证分类（主类，用于首页“全球认证”模块）
export const getTopCertificationCategories = () => {
  return api.get('/certification-category/top')
}

