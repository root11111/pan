import api from './index'

export const getLatestNews = (limit = 10) => {
  return api.get('/news/latest', { params: { limit } })
}

export const getNewsPage = (current = 1, size = 10) => {
  return api.get('/news/page', { params: { current, size } })
}

export const getNewsById = (id) => {
  return api.get(`/news/${id}`)
}

