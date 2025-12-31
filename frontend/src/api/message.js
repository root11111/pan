import api from './index'

export const submitMessage = (data) => {
  // 为提交留言请求设置更长的超时时间（60秒），适应手机端网络慢的情况
  return api.post('/message/submit', data, {
    timeout: 60000
  })
}

