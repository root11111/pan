import api from './index'

export const getLaboratories = () => {
  return api.get('/laboratory/list')
}

