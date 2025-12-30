import api from './index'

export const submitMessage = (data) => {
  return api.post('/message/submit', data)
}

