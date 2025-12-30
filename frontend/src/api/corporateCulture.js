import api from './index'

export const getCorporateCultures = () => {
  return api.get('/corporate-culture/list')
}

