<template>
  <footer class="footer">
    <div class="footer-content">
      <div class="footer-section">
        <h3>快速导航</h3>
        <ul>
          <li><router-link to="/">首页</router-link></li>
          <li><router-link to="/about">关于我们</router-link></li>
          <li><router-link to="/laboratory">实验室展示</router-link></li>
          <li><router-link to="/certification">认证服务</router-link></li>
        </ul>
      </div>
      <div class="footer-section">
        <h3>服务支持</h3>
        <ul>
          <li><router-link to="/honor">荣誉资质</router-link></li>
          <li><router-link to="/news">新闻资讯</router-link></li>
          <li><router-link to="/message">在线留言</router-link></li>
          <li><router-link to="/contact">联系我们</router-link></li>
        </ul>
      </div>
      <div class="footer-section contact-section">
        <h3>{{ t('contactInfo') }}</h3>
        <ul>
          <li v-if="companyInfo?.phone">{{ t('phone') }}：{{ companyInfo.phone }}</li>
          <li v-if="companyInfo?.mobile">{{ t('mobile') }}：{{ companyInfo.mobile }}</li>
          <li v-if="companyInfo?.email">{{ t('email') }}：{{ companyInfo.email }}</li>
          <li v-if="companyInfo?.qq">{{ t('qq') }}：{{ companyInfo.qq }}</li>
          <li v-if="companyInfo?.address">{{ t('address') }}：{{ companyInfo.address }}</li>
        </ul>
      </div>
    </div>
    <div class="qr-code-section" v-if="companyInfo?.qrCode">
      <div class="qr-code-wrapper">
        <img :src="getImageUrl(companyInfo.qrCode)" :alt="t('officialQRCode')" class="qr-code" />
        <p class="qr-code-label">{{ t('officialQRCode') }}</p>
      </div>
    </div>
    <div class="footer-bottom">
      <p>&copy; {{ new Date().getFullYear() }} {{ companyInfo?.nameCn || '蓝泰检测' }} All Rights Reserved.</p>
    </div>
  </footer>
</template>

<script>
import { ref, onMounted } from 'vue'
import { getCompanyInfo } from '../api/company'
import { useI18n } from '../utils/i18n'
import { getImageUrl } from '../utils/image'

export default {
  name: 'Footer',
  setup() {
    const { t } = useI18n()
    const companyInfo = ref(null)

    onMounted(async () => {
      try {
        const res = await getCompanyInfo()
        if (res.code === 200) {
          companyInfo.value = res.data
        }
      } catch (error) {
        console.error('获取公司信息失败:', error)
      }
    })

    return {
      companyInfo,
      t,
      getImageUrl
    }
  }
}
</script>

<style scoped>
.footer {
  background: #2c3e50;
  color: #fff;
  padding: 60px 0 20px;
  margin-top: 80px;
  width: 100%;
  display: block;
  box-sizing: border-box;
  clear: both;
  position: relative;
}

.footer-content {
  max-width: 1200px;
  margin: 0 auto;
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 40px;
  padding: 0 20px;
}

.footer-section h3 {
  margin-bottom: 20px;
  font-size: 18px;
  color: #409eff;
}

.footer-section ul {
  list-style: none;
}

.footer-section ul li {
  margin-bottom: 10px;
  color: #ccc;
}

.footer-section ul li a {
  color: #ccc;
  transition: color 0.3s;
}

.footer-section ul li a:hover {
  color: #409eff;
}

.contact-section {
  display: flex;
  flex-direction: column;
}

.qr-code-section {
  max-width: 1200px;
  margin: 30px auto 0;
  padding: 0 20px;
  display: flex;
  justify-content: center;
}

.qr-code-wrapper {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.qr-code {
  width: 100px;
  height: 100px;
  border-radius: 8px;
  border: 2px solid rgba(255, 255, 255, 0.2);
  background: #fff;
  padding: 8px;
  box-sizing: border-box;
}

.qr-code-label {
  margin-top: 10px;
  font-size: 12px;
  color: #ccc;
  text-align: center;
}

.footer-bottom {
  max-width: 1200px;
  margin: 40px auto 0;
  padding: 20px;
  text-align: center;
  border-top: 1px solid rgba(255, 255, 255, 0.1);
  color: #999;
  width: 100%;
}

.footer-bottom p {
  margin: 5px 0;
}

/* 移动端适配 */
@media (max-width: 768px) {
  .footer {
    padding: 40px 0 15px;
  }

  .footer-content {
    grid-template-columns: 1fr;
    gap: 30px;
    padding: 0 15px;
  }

  .footer-section h3 {
    font-size: 16px;
  }

  .qr-code-section {
    justify-content: center;
    margin-top: 20px;
  }

  .qr-code {
    width: 100px;
    height: 100px;
  }

  .footer-bottom {
    padding: 15px;
  }

  .footer-bottom p {
    font-size: 12px;
  }
}
</style>

