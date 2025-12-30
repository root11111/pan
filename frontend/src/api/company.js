import api from './index'

export const getCompanyInfo = () => {
  return api.get('/company/info')
}

