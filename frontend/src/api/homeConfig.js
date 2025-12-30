import api from './index'

export const getHomeConfigMap = (lang = 'cn') => {
  return api.get('/home-config/map', { params: { lang } })
}

export const getHomeConfigs = () => {
  return api.get('/home-config/list')
}

export const getBannerValues = () => {
  return api.get('/banner-value/list')
}

export const getIcons = () => {
  return api.get('/file/icons')
}

