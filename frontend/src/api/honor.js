import api from './index'

export const getHonors = () => {
  return api.get('/honor/list')
}

