import api from './index'

export const getEnabledMenus = () => {
  return api.get('/menu-config/enabled')
}

