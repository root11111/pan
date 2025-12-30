import api from './index'

export const adminLogin = (data) => {
  return api.post('/admin/login', data)
}

export const getAdminInfo = () => {
  return api.get('/admin/info')
}

export const getCompanyInfo = () => {
  return api.get('/admin/company/info')
}

export const updateCompanyInfo = (data) => {
  return api.post('/admin/company/info', data)
}

export const getLaboratories = () => {
  return api.get('/admin/laboratory/list')
}

export const saveLaboratory = (data) => {
  return api.post('/admin/laboratory/save', data)
}

export const deleteLaboratory = (id) => {
  return api.delete(`/admin/laboratory/${id}`)
}

export const getNewsPage = (current = 1, size = 10, keyword = '') => {
  return api.get('/admin/news/page', { params: { current, size, keyword } })
}

export const getNewsById = (id) => {
  return api.get(`/admin/news/${id}`)
}

export const saveNews = (data) => {
  return api.post('/admin/news/save', data)
}

export const deleteNews = (id) => {
  return api.delete(`/admin/news/${id}`)
}

export const getMessagePage = (current = 1, size = 10, status = null) => {
  return api.get('/admin/message/page', { params: { current, size, status } })
}

export const updateMessageStatus = (id, status) => {
  return api.put(`/admin/message/${id}/status`, null, { params: { status } })
}

export const deleteMessage = (id) => {
  return api.delete(`/admin/message/${id}`)
}

export const uploadFile = (file) => {
  const formData = new FormData()
  formData.append('file', file)
  return api.post('/file/upload', formData, {
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}

export const uploadFiles = (files) => {
  const formData = new FormData()
  files.forEach(file => {
    formData.append('files', file)
  })
  return api.post('/file/upload/multiple', formData, {
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}

