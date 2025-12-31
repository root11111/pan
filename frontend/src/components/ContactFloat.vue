<template>
  <div class="contact-float" v-if="!isAdminPage">
    <!-- 主按钮 -->
    <div class="contact-btn" @click="showPanel = !showPanel">
      <el-icon><Phone /></el-icon>
    </div>
    
    <!-- 联系方式面板 -->
    <transition name="slide">
      <div v-if="showPanel" class="contact-panel">
        <div class="panel-header">
          <span>{{ t('contactMethods') }}</span>
          <el-icon class="close-btn" @click="showPanel = false"><Close /></el-icon>
        </div>
        <div class="panel-content">
          <div class="contact-item" v-if="companyInfo?.phone">
            <el-icon><Phone /></el-icon>
            <div class="contact-info">
              <span class="label">{{ t('phone') }}</span>
              <span class="value">{{ companyInfo.phone }}</span>
            </div>
            <el-button 
              type="primary" 
              size="small" 
              @click="callPhone(companyInfo.phone)"
              class="action-btn"
            >
              {{ t('call') }}
            </el-button>
          </div>
          
          <div class="contact-item" v-if="companyInfo?.mobile">
            <el-icon><Phone /></el-icon>
            <div class="contact-info">
              <span class="label">{{ t('mobile') }}</span>
              <span class="value">{{ companyInfo.mobile }}</span>
            </div>
            <el-button 
              type="primary" 
              size="small" 
              @click="callPhone(companyInfo.mobile)"
              class="action-btn"
            >
              {{ t('call') }}
            </el-button>
          </div>
          
          <div class="contact-item" v-if="companyInfo?.email">
            <el-icon><Message /></el-icon>
            <div class="contact-info">
              <span class="label">{{ t('email') }}</span>
              <span class="value">{{ companyInfo.email }}</span>
            </div>
            <el-button 
              type="primary" 
              size="small" 
              @click="sendEmail(companyInfo.email)"
              class="action-btn"
            >
              {{ t('sendEmail') }}
            </el-button>
          </div>
          
          <div class="contact-item" v-if="companyInfo?.qq">
            <el-icon><ChatLineRound /></el-icon>
            <div class="contact-info">
              <span class="label">QQ</span>
              <span class="value">{{ companyInfo.qq }}</span>
            </div>
            <el-button 
              type="primary" 
              size="small" 
              @click="openQQ(companyInfo.qq)"
              class="action-btn"
            >
              {{ t('consult') }}
            </el-button>
          </div>
          
          <div class="contact-item" v-if="companyInfo?.wechat">
            <el-icon><ChatDotRound /></el-icon>
            <div class="contact-info">
              <span class="label">{{ t('wechat') }}</span>
              <span class="value">{{ companyInfo.wechat }}</span>
            </div>
            <el-button 
              type="primary" 
              size="small" 
              @click="showWechatQR = true"
              class="action-btn"
            >
              {{ t('scan') }}
            </el-button>
          </div>
          
          <div class="contact-item">
            <el-icon><Message /></el-icon>
            <div class="contact-info">
              <span class="label">{{ t('onlineMessage') }}</span>
              <span class="value">{{ t('quickConsultation') }}</span>
            </div>
            <el-button 
              type="primary" 
              size="small" 
              @click="goToMessage"
              class="action-btn"
            >
              {{ t('leaveMessage') }}
            </el-button>
          </div>
        </div>
      </div>
    </transition>
    
    <!-- 返回顶部按钮 -->
    <div 
      class="back-to-top" 
      v-show="showBackTop"
      @click="scrollToTop"
    >
      <el-icon><Top /></el-icon>
    </div>
    
    <!-- 微信二维码弹窗 -->
    <el-dialog
      v-model="showWechatQR"
      :title="t('wechatQRCode')"
      width="300px"
      center
    >
      <div class="qr-code-placeholder">
        <img 
          v-if="companyInfo?.qrCode" 
          :src="getImageUrl(companyInfo.qrCode)" 
          :alt="t('wechatQRCode')" 
          class="qr-code-image"
        />
        <el-icon v-else :size="100"><ChatDotRound /></el-icon>
        <p>{{ t('scanQRCodeToAddWechat') }}</p>
        <p v-if="companyInfo?.wechat" class="wechat-text">{{ companyInfo.wechat }}</p>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { ref, onMounted, onUnmounted, computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { Phone, Close, ChatLineRound, ChatDotRound, Message, Top } from '@element-plus/icons-vue'
import { getCompanyInfo } from '../api/company'
import { useI18n } from '../utils/i18n'
import { getImageUrl } from '../utils/image'

export default {
  name: 'ContactFloat',
  components: {
    Phone,
    Close,
    ChatLineRound,
    ChatDotRound,
    Message,
    Top
  },
  setup() {
    const router = useRouter()
    const route = useRoute()
    const { t } = useI18n()
    const showPanel = ref(false)
    const showWechatQR = ref(false)
    const companyInfo = ref(null)
    const showBackTop = ref(false)
    
    // 判断是否是后台管理页面
    const isAdminPage = computed(() => {
      return route.path.startsWith('/admin')
    })

    onMounted(async () => {
      try {
        const res = await getCompanyInfo()
        if (res.code === 200) {
          companyInfo.value = res.data
        }
      } catch (error) {
        console.error('获取公司信息失败:', error)
      }
      
      // 监听滚动事件
      window.addEventListener('scroll', handleScroll)
    })
    
    onUnmounted(() => {
      // 移除滚动事件监听
      window.removeEventListener('scroll', handleScroll)
    })
    
    const handleScroll = () => {
      showBackTop.value = window.scrollY > 300
    }
    
    const scrollToTop = () => {
      window.scrollTo({
        top: 0,
        behavior: 'smooth'
      })
    }

    const callPhone = (phone) => {
      if (phone) {
        window.location.href = `tel:${phone}`
      }
    }

    const sendEmail = (email) => {
      if (email) {
        window.location.href = `mailto:${email}`
      }
    }

    const openQQ = (qq) => {
      if (qq) {
        window.open(`tencent://message/?uin=${qq}&Site=&Menu=yes`, '_blank')
      }
    }

    const goToMessage = () => {
      router.push('/contact')
      showPanel.value = false
    }

    return {
      showPanel,
      showWechatQR,
      companyInfo,
      showBackTop,
      isAdminPage,
      t,
      getImageUrl,
      callPhone,
      sendEmail,
      openQQ,
      goToMessage,
      scrollToTop
    }
  }
}
</script>

<style scoped>
.contact-float {
  position: fixed !important;
  right: 20px !important;
  left: auto !important;
  bottom: 100px;
  z-index: 999;
  width: auto !important;
}

.contact-btn {
  width: 60px;
  height: 60px;
  background: linear-gradient(135deg, #409eff 0%, #67c23a 100%);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  box-shadow: 0 4px 12px rgba(64, 158, 255, 0.4);
  transition: all 0.3s;
  color: #fff;
  font-size: 24px;
  position: relative;
  animation: contactPulse 1.5s ease-in-out infinite, contactBounce 3s ease-in-out infinite;
}

.contact-btn::before {
  content: '';
  position: absolute;
  top: 50%;
  left: 50%;
  width: 100%;
  height: 100%;
  border-radius: 50%;
  background: linear-gradient(135deg, #409eff 0%, #67c23a 100%);
  opacity: 0.6;
  transform: translate(-50%, -50%) scale(1);
  animation: contactRipple 1.5s ease-out infinite;
  z-index: -1;
}

.contact-btn::after {
  content: '';
  position: absolute;
  top: 50%;
  left: 50%;
  width: 100%;
  height: 100%;
  border-radius: 50%;
  background: linear-gradient(135deg, #409eff 0%, #67c23a 100%);
  opacity: 0.4;
  transform: translate(-50%, -50%) scale(1);
  animation: contactRipple2 1.5s ease-out infinite 0.5s;
  z-index: -1;
}

.contact-btn:hover {
  transform: scale(1.2);
  box-shadow: 0 12px 30px rgba(64, 158, 255, 0.9);
  animation-play-state: paused;
}

@keyframes contactPulse {
  0%, 100% {
    transform: scale(1);
  }
  50% {
    transform: scale(1.2);
  }
}

@keyframes contactBounce {
  0%, 100% {
    transform: translateY(0) scale(1);
  }
  25% {
    transform: translateY(-8px) scale(1.15);
  }
  50% {
    transform: translateY(0) scale(1);
  }
  75% {
    transform: translateY(-5px) scale(1.1);
  }
}

@keyframes contactRipple {
  0% {
    transform: translate(-50%, -50%) scale(1);
    opacity: 0.6;
  }
  100% {
    transform: translate(-50%, -50%) scale(2);
    opacity: 0;
  }
}

@keyframes contactRipple2 {
  0% {
    transform: translate(-50%, -50%) scale(1);
    opacity: 0.4;
  }
  100% {
    transform: translate(-50%, -50%) scale(2.2);
    opacity: 0;
  }
}

.contact-panel {
  position: absolute;
  right: 0;
  bottom: 70px;
  width: 360px;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.15);
  overflow: hidden;
}

.panel-header {
  background: linear-gradient(135deg, #409eff 0%, #67c23a 100%);
  color: #fff;
  padding: 15px 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-weight: 600;
}

.close-btn {
  cursor: pointer;
  font-size: 18px;
  transition: transform 0.3s;
}

.close-btn:hover {
  transform: rotate(90deg);
}

.panel-content {
  padding: 15px;
}

.contact-item {
  display: flex;
  align-items: center;
  padding: 12px 0;
  border-bottom: 1px solid #f0f0f0;
}

.contact-item:last-child {
  border-bottom: none;
}

.contact-item .el-icon {
  font-size: 24px;
  color: #409eff;
  margin-right: 12px;
  flex-shrink: 0;
}

.contact-info {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.contact-info .label {
  font-size: 12px;
  color: #909399;
}

.contact-info .value {
  font-size: 16px;
  color: #303133;
  font-weight: 500;
  word-break: break-all;
  word-wrap: break-word;
  line-height: 1.4;
}

.action-btn {
  flex-shrink: 0;
  margin-left: 10px;
}

.qr-code-placeholder {
  text-align: center;
  padding: 20px;
}

.qr-code-placeholder .el-icon {
  color: #409eff;
  margin-bottom: 15px;
}

.qr-code-image {
  width: 200px;
  height: 200px;
  margin: 0 auto 15px;
  display: block;
  border-radius: 8px;
  border: 2px solid #f0f0f0;
  background: #fff;
  padding: 8px;
  box-sizing: border-box;
}

.qr-code-placeholder p {
  margin: 10px 0;
  color: #666;
}

.wechat-text {
  font-weight: 600;
  color: #303133;
}

/* 动画效果 */
.slide-enter-active,
.slide-leave-active {
  transition: all 0.3s ease;
}

.slide-enter-from,
.slide-leave-to {
  opacity: 0;
  transform: translateX(20px);
}

.back-to-top {
  width: 50px;
  height: 50px;
  background: #909399;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
  transition: all 0.3s;
  color: #fff;
  font-size: 20px;
  margin-top: 15px;
}

.back-to-top:hover {
  background: #606266;
  transform: translateY(-3px);
  box-shadow: 0 6px 16px rgba(0, 0, 0, 0.2);
}

/* 移动端适配 */
@media (max-width: 768px) {
  .contact-float {
    right: 15px;
    bottom: 80px;
  }

  .contact-btn {
    width: 50px;
    height: 50px;
    font-size: 20px;
  }

  .contact-panel {
    width: 320px;
    bottom: 60px;
  }

  .panel-header {
    padding: 12px 15px;
    font-size: 14px;
  }

  .panel-content {
    padding: 12px;
  }

  .contact-item {
    padding: 10px 0;
  }

  .contact-info .value {
    font-size: 14px;
  }

  .back-to-top {
    width: 45px;
    height: 45px;
    font-size: 18px;
  }
}

@media (max-width: 480px) {
  .contact-panel {
    width: 300px;
  }
}
</style>

