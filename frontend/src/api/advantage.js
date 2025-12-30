import api from './index'

export const getAdvantages = () => {
  return api.get('/advantage/list')
}

