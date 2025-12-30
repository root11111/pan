import api from './index'

export const queryCertificate = (params) => {
  return api.get('/certificate/query', { params })
}

export const getCertificateById = (id) => {
  return api.get(`/certificate/${id}`)
}

