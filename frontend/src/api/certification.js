import api from './index'

export const getCertifications = () => {
  return api.get('/certification/list')
}

export const getCertificationsByCategory = (category) => {
  return api.get('/certification/category', { params: { category } })
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

