<template>
  <div class="certificate">
    <Header />
    
    <div class="page-banner slide-in-top">
      <div class="banner-content">
        <h1 class="fade-in-immediate">{{ currentLang === 'en' ? 'Certificate Query' : '证书查询' }}</h1>
        <p class="fade-in-immediate" style="animation-delay: 0.2s;">{{ currentLang === 'en' ? '证书查询' : 'Certificate Query' }}</p>
      </div>
    </div>

    <div class="container" style="padding: 0px 20px 60px 20px;">
      <!-- 查询表单区域 -->
      <el-card class="query-card slide-in-bottom">
        <div class="query-form">
          <div class="form-header">
            <div class="form-icon">
              <img :src="certificateIcon" alt="Certificate Icon" class="form-icon-image" />
            </div>
            <h2>{{ t('certificate') }}</h2>
            <p class="form-subtitle">{{ t('pleaseEnterCertificateNoOrProductName') }}</p>
          </div>
          <el-form :model="queryForm" label-width="120px" class="query-form-content">
            <el-form-item :label="t('certificateNo')">
              <el-input 
                v-model="queryForm.certificateNo" 
                :placeholder="t('pleaseEnterCertificateNo')"
                clearable
                size="large"
                class="form-input"
              >
                <template #prefix>
                  <el-icon><Document /></el-icon>
                </template>
              </el-input>
            </el-form-item>
            <el-form-item :label="t('productName')">
              <el-input 
                v-model="queryForm.productName" 
                :placeholder="t('pleaseEnterProductName')"
                clearable
                size="large"
                class="form-input"
              >
                <template #prefix>
                  <el-icon><Goods /></el-icon>
                </template>
              </el-input>
            </el-form-item>
            <el-form-item>
              <el-button 
                type="primary" 
                @click="handleQuery" 
                :loading="loading" 
                size="large"
                class="query-button"
              >
                <el-icon v-if="!loading"><Search /></el-icon>
                <span style="margin-left: 8px;">{{ t('queryCertificate') }}</span>
              </el-button>
            </el-form-item>
          </el-form>
        </div>
      </el-card>

      <!-- 查询结果区域 -->
      <div v-if="queryResult !== null" class="result-section slide-in-bottom">
        <el-card class="result-card">
          <div class="result-header">
            <h3>
              <el-icon><List /></el-icon>
              <span style="margin-left: 10px;">{{ t('queryResult') }}</span>
            </h3>
            <el-tag type="info" size="large">
              {{ t('totalRecords') }} {{ queryResult.length }} {{ t('records') }}
            </el-tag>
          </div>
          
          <div v-if="queryResult.length === 0" class="empty-result">
            <el-empty :description="t('noCertificateFound')" :image-size="120">
              <el-button type="primary" @click="queryForm.certificateNo = ''; queryForm.productName = ''; queryResult = null">
                {{ t('reQuery') }}
              </el-button>
            </el-empty>
          </div>
          
          <div v-else class="result-list">
            <div 
              v-for="(item, index) in queryResult" 
              :key="item.id || index" 
              class="certificate-item scale-in"
              :style="{ animationDelay: `${index * 0.1}s`, opacity: 1, visibility: 'visible' }"
            >
              <div class="certificate-card">
                <div class="certificate-header">
                  <div class="certificate-badge">
                    <el-icon><Trophy /></el-icon>
                  </div>
                  <div class="certificate-info">
                    <h4>{{ item.productName }}</h4>
                    <p class="certificate-no">{{ item.certificateNo }}</p>
                  </div>
                  <el-tag 
                    :type="item.status === '有效' ? 'success' : item.status === '已过期' ? 'danger' : 'warning'"
                    size="large"
                    class="status-tag"
                  >
                    {{ item.status }}
                  </el-tag>
                </div>
                
                <div class="certificate-details">
                  <div class="detail-item">
                    <el-icon><Files /></el-icon>
                    <span>{{ t('certificateType') }}：{{ item.certificateType || '-' }}</span>
                  </div>
                  <div class="detail-item">
                    <el-icon><Calendar /></el-icon>
                    <span>{{ t('issueDate') }}：{{ item.issueDate || '-' }}</span>
                  </div>
                  <div class="detail-item">
                    <el-icon><Clock /></el-icon>
                    <span>{{ t('expiryDate') }}：{{ item.expiryDate || '-' }}</span>
                  </div>
                </div>
                
                <div class="certificate-actions">
                  <el-button type="primary" @click="viewDetail(item)" :icon="View">
                    {{ t('viewDetails') }}
                  </el-button>
                </div>
              </div>
            </div>
          </div>
        </el-card>
      </div>

      <!-- 证书详情对话框 -->
      <el-dialog
        v-model="detailVisible"
        :title="t('certificateDetails')"
        width="95%"
        :close-on-click-modal="false"
        class="certificate-dialog"
        style="max-width: 1200px;"
      >
        <div v-if="certificateDetail" class="certificate-detail">
          <div class="detail-header">
            <div class="detail-badge-wrapper">
              <div class="detail-badge">
                <el-icon :size="40"><Trophy /></el-icon>
              </div>
              <el-tag 
                :type="certificateDetail.status === '有效' ? 'success' : certificateDetail.status === '已过期' ? 'danger' : 'warning'"
                size="large"
                class="detail-status"
              >
                {{ certificateDetail.status }}
              </el-tag>
            </div>
          </div>
          <el-descriptions :column="isMobile ? 1 : 2" border class="detail-descriptions">
            <el-descriptions-item :label="t('certificateNo')">{{ certificateDetail.certificateNo }}</el-descriptions-item>
            <el-descriptions-item :label="t('productName')">{{ certificateDetail.productName }}</el-descriptions-item>
            <el-descriptions-item :label="t('certificateType')">{{ certificateDetail.certificateType || '-' }}</el-descriptions-item>
            <el-descriptions-item :label="t('status')">
              <el-tag :type="certificateDetail.status === (currentLang === 'en' ? 'Valid' : '有效') ? 'success' : 'danger'">
                {{ certificateDetail.status }}
              </el-tag>
            </el-descriptions-item>
            <el-descriptions-item :label="t('issueDate')">
              {{ certificateDetail.issueDate || '-' }}
            </el-descriptions-item>
            <el-descriptions-item :label="t('expiryDate')">
              {{ certificateDetail.expiryDate || '-' }}
            </el-descriptions-item>
            <el-descriptions-item :label="t('issuer')" :span="2">
              {{ certificateDetail.issuer || '-' }}
            </el-descriptions-item>
            <el-descriptions-item :label="t('description')" :span="2">
              {{ certificateDetail.description || '-' }}
            </el-descriptions-item>
          </el-descriptions>
          <div v-if="certificateDetail.certificateFile" class="certificate-image-wrapper">
            <!-- 图片显示 -->
            <div v-if="isImageFile(certificateDetail.certificateFile)" class="image-container">
              <img 
                :src="getImageUrl(certificateDetail.certificateFile)" 
                :alt="certificateDetail.certificateNo"
                class="certificate-image"
                @error="handleImageError"
              />
            </div>
            <!-- PDF显示 -->
            <div v-else-if="isPdfFile(certificateDetail.certificateFile)" class="pdf-container">
              <iframe 
                :src="getImageUrl(certificateDetail.certificateFile)"
                class="certificate-pdf"
              ></iframe>
            </div>
            <!-- 下载按钮 -->
            <div style="text-align: center; margin-top: 20px;">
              <el-button type="primary" @click="downloadCertificateFile(certificateDetail.certificateFile)">
                <el-icon><Download /></el-icon>
                {{ t('download') || '下载证书文件' }}
              </el-button>
            </div>
          </div>
        </div>
      </el-dialog>
    </div>

    <Footer />
  </div>
</template>

<script>
import { ref, nextTick, onMounted, onUnmounted } from 'vue'
import Header from '../components/Header.vue'
import Footer from '../components/Footer.vue'
import { ElMessage, ElDialog } from 'element-plus'
import { 
  Search, 
  Document, 
  Goods, 
  List, 
  Trophy, 
  Files, 
  Calendar, 
  Clock, 
  View,
  Download
} from '@element-plus/icons-vue'
import { queryCertificate, getCertificateById } from '../api/certificate'
import { getImageUrl } from '../utils/image'
import { initAllScrollAnimations } from '../utils/scrollAnimation'
import { useI18n } from '../utils/i18n'

export default {
  name: 'Certificate',
  components: {
    Header,
    Footer,
    Search,
    Document,
    Goods,
    List,
    Trophy,
    Files,
    Calendar,
    Clock,
    View,
    Download
  },
  setup() {
    const { t, lang: currentLang } = useI18n()
    const certificateIcon = ref('/api/uploads/icon/1767079908439-ckt-抠图.png')
    const queryForm = ref({
      certificateNo: '',
      productName: ''
    })
    const queryResult = ref(null)
    const loading = ref(false)
    const detailVisible = ref(false)
    const certificateDetail = ref(null)
    const isMobile = ref(false)

    // 检测是否为手机端
    const checkMobile = () => {
      isMobile.value = window.innerWidth <= 768
    }

    onMounted(() => {
      checkMobile()
      window.addEventListener('resize', checkMobile)
    })

    onUnmounted(() => {
      window.removeEventListener('resize', checkMobile)
    })

    const handleQuery = async () => {
      if (!queryForm.value.certificateNo && !queryForm.value.productName) {
        ElMessage.warning(t('pleaseEnterCertificateNoOrProductName'))
        return
      }

      loading.value = true
      try {
        const res = await queryCertificate({
          certificateNo: queryForm.value.certificateNo,
          productName: queryForm.value.productName
        })
        console.log('查询结果:', res)
        if (res.code === 200) {
          // 确保 queryResult 被设置为数组，即使数据为空
          const data = res.data || []
          queryResult.value = Array.isArray(data) ? data : []
          console.log('查询结果数据:', queryResult.value)
          console.log('查询结果数量:', queryResult.value.length)
          if (queryResult.value.length === 0) {
            ElMessage.info(t('noCertificateFound'))
            // 即使没有结果，也要显示结果区域（显示空状态）
            queryResult.value = []
          } else {
            ElMessage.success(`${t('totalRecords')} ${queryResult.value.length} ${t('records')}`)
            // 确保结果区域显示，滚动到结果区域
            await nextTick()
            setTimeout(() => {
              const resultSection = document.querySelector('.result-section')
              if (resultSection) {
                resultSection.scrollIntoView({ behavior: 'smooth', block: 'start' })
              }
            }, 200)
          }
        } else {
          ElMessage.error(res.message || t('queryFailed'))
          queryResult.value = []
        }
      } catch (error) {
        console.error('查询证书失败:', error)
        ElMessage.error(t('queryFailedRetry'))
        queryResult.value = []
      } finally {
        loading.value = false
      }
    }

    // 判断是否为图片文件
    const isImageFile = (filePath) => {
      if (!filePath) return false
      const imageExtensions = ['.jpg', '.jpeg', '.png', '.gif', '.bmp', '.webp', '.svg']
      const lowerPath = filePath.toLowerCase()
      return imageExtensions.some(ext => lowerPath.endsWith(ext))
    }

    // 判断是否为PDF文件
    const isPdfFile = (filePath) => {
      if (!filePath) return false
      return filePath.toLowerCase().endsWith('.pdf')
    }

    // 获取文件名
    const getFileName = (filePath) => {
      if (!filePath) return ''
      const parts = filePath.split('/')
      return parts[parts.length - 1]
    }

    // 处理图片加载错误
    const handleImageError = (event) => {
      if (event && event.target) {
        event.target.style.display = 'none'
      }
    }

    // 下载证书文件
    const downloadCertificateFile = (filePath) => {
      if (!filePath) return
      const fileUrl = getImageUrl(filePath)
      const link = document.createElement('a')
      link.href = fileUrl
      link.download = getFileName(filePath) || 'certificate'
      link.target = '_blank'
      document.body.appendChild(link)
      link.click()
      document.body.removeChild(link)
    }

    const viewDetail = async (row) => {
      try {
        const res = await getCertificateById(row.id)
        if (res.code === 200) {
          certificateDetail.value = res.data
          detailVisible.value = true
        } else {
          ElMessage.error('获取证书详情失败')
        }
      } catch (error) {
        console.error('获取证书详情失败:', error)
        ElMessage.error('获取证书详情失败')
      }
    }

    // 初始化滚动动画
    nextTick(() => {
      setTimeout(() => {
        initAllScrollAnimations()
      }, 100)
    })

    return {
      certificateIcon,
      queryForm,
      queryResult,
      loading,
      detailVisible,
      certificateDetail,
      handleQuery,
      viewDetail,
      getImageUrl,
      t,
      currentLang,
      View,
      Download,
      isImageFile,
      isPdfFile,
      getFileName,
      handleImageError,
      downloadCertificateFile,
      isMobile
    }
  }
}
</script>

<style scoped>
.certificate {
  min-height: 100vh;
}

.page-banner {
  position: relative;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  background-size: 200% 200%;
  animation: gradient 15s ease infinite;
  color: #fff;
  padding: 100px 20px;
  text-align: center;
  overflow: hidden;
}

.page-banner::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: url('data:image/svg+xml,<svg width="100" height="100" xmlns="http://www.w3.org/2000/svg"><circle cx="50" cy="50" r="2" fill="rgba(255,255,255,0.1)"/></svg>');
  opacity: 0.3;
  animation: float 20s linear infinite;
}

.banner-content {
  position: relative;
  z-index: 1;
}

.page-banner h1 {
  font-size: 48px;
  font-weight: 800;
  margin-bottom: 15px;
  text-shadow: 0 2px 20px rgba(0, 0, 0, 0.2);
  letter-spacing: -0.5px;
}

.page-banner p {
  font-size: 22px;
  opacity: 0.95;
  font-weight: 300;
}

.query-card {
  border-radius: 24px;
  overflow: hidden;
  box-shadow: 0 8px 32px rgba(102, 126, 234, 0.12);
  border: none;
  background: linear-gradient(135deg, #ffffff 0%, #f8f9ff 100%);
  margin-top: -100px;
  position: relative;
  z-index: 10;
}

.query-form {
  padding: 50px 40px;
}

.form-header {
  text-align: center;
  margin-bottom: 40px;
}

.form-icon {
  width: 100px;
  height: 100px;
  margin: 0 auto 20px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
  box-shadow: 0 8px 24px rgba(102, 126, 234, 0.3);
  animation: pulse 2s ease-in-out infinite;
  overflow: hidden;
  position: relative;
}

.form-icon-image {
  width: 95%;
  height: 95%;
  object-fit: contain;
  border-radius: 50%;
}

.form-header h2 {
  font-size: 32px;
  font-weight: 700;
  color: var(--text-primary);
  margin-bottom: 10px;
  letter-spacing: -0.5px;
}

.form-subtitle {
  color: var(--text-secondary);
  font-size: 16px;
  margin: 0;
}

.query-form-content {
  max-width: 600px;
  margin: 0 auto;
}

.form-input {
  border-radius: 12px;
}

.form-input :deep(.el-input__wrapper) {
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
  transition: all 0.3s ease;
}

.form-input :deep(.el-input__wrapper:hover) {
  box-shadow: 0 4px 12px rgba(102, 126, 234, 0.15);
}

.form-input :deep(.el-input__wrapper.is-focus) {
  box-shadow: 0 4px 16px rgba(102, 126, 234, 0.25);
}

.query-button {
  width: 100%;
  height: 50px;
  font-size: 16px;
  font-weight: 600;
  border-radius: 12px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border: none;
  box-shadow: 0 4px 16px rgba(102, 126, 234, 0.3);
  transition: all 0.3s ease;
}

.query-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 20px rgba(102, 126, 234, 0.4);
}

.query-button:active {
  transform: translateY(0);
}

.result-section {
  margin-top: 40px;
  display: block;
  visibility: visible;
  opacity: 1;
}

.result-card {
  border-radius: 24px;
  overflow: hidden;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.08);
  border: none;
}

.result-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 30px 40px;
  border-bottom: 1px solid var(--border-color);
  background: linear-gradient(135deg, #f8f9ff 0%, #ffffff 100%);
}

.result-header h3 {
  font-size: 24px;
  font-weight: 700;
  color: var(--text-primary);
  display: flex;
  align-items: center;
  margin: 0;
}

.empty-result {
  padding: 60px 20px;
}

.result-list {
  padding: 30px 40px;
  display: grid;
  gap: 24px;
  min-height: 100px;
}

.certificate-item {
  animation: scaleIn 0.5s ease-out forwards;
  opacity: 1;
  visibility: visible;
}

.certificate-item.animate-in {
  opacity: 1;
}

.certificate-card {
  background: #fff;
  border-radius: 20px;
  padding: 30px;
  border: 2px solid var(--border-color);
  transition: all 0.4s cubic-bezier(0.4, 0, 0.2, 1);
  position: relative;
  overflow: hidden;
}

.certificate-card::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  width: 4px;
  height: 100%;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  transform: scaleY(0);
  transform-origin: top;
  transition: transform 0.4s ease;
}

.certificate-card:hover {
  transform: translateY(-8px);
  box-shadow: 0 12px 40px rgba(102, 126, 234, 0.2);
  border-color: rgba(102, 126, 234, 0.3);
}

.certificate-card:hover::before {
  transform: scaleY(1);
}

.certificate-header {
  display: flex;
  align-items: flex-start;
  gap: 20px;
  margin-bottom: 24px;
}

.certificate-badge {
  width: 60px;
  height: 60px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
  font-size: 28px;
  flex-shrink: 0;
  box-shadow: 0 4px 12px rgba(102, 126, 234, 0.3);
}

.certificate-info {
  flex: 1;
  min-width: 0;
  overflow: hidden;
}

.certificate-info h4 {
  font-size: 20px;
  font-weight: 700;
  color: var(--text-primary);
  margin: 0 0 8px 0;
  line-height: 1.4;
  word-break: break-word;
  overflow-wrap: break-word;
  word-wrap: break-word;
}

.certificate-no {
  color: var(--text-secondary);
  font-size: 14px;
  margin: 0;
  font-family: 'Courier New', monospace;
  word-break: break-all;
  overflow-wrap: break-word;
  word-wrap: break-word;
}

.status-tag {
  font-weight: 600;
  padding: 8px 16px;
  border-radius: 20px;
}

.certificate-details {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 16px;
  margin-bottom: 24px;
  padding: 20px;
  background: #f8f9ff;
  border-radius: 12px;
}

.detail-item {
  display: flex;
  align-items: center;
  gap: 10px;
  color: var(--text-secondary);
  font-size: 14px;
}

.detail-item .el-icon {
  color: var(--primary-color);
  font-size: 18px;
}

.certificate-actions {
  display: flex;
  justify-content: flex-end;
  gap: 12px;
}

.certificate-actions .el-button {
  border-radius: 10px;
  font-weight: 600;
  padding: 12px 24px;
}

@keyframes pulse {
  0%, 100% {
    transform: scale(1);
  }
  50% {
    transform: scale(1.05);
  }
}

@keyframes scaleIn {
  from {
    opacity: 0;
    transform: scale(0.9) translateY(20px);
  }
  to {
    opacity: 1;
    transform: scale(1) translateY(0);
  }
}

/* 移动端适配 */
@media (max-width: 768px) {
  .page-banner {
    padding: 50px 15px;
  }

  .page-banner h1 {
    font-size: 28px;
  }

  .page-banner p {
    font-size: 16px;
  }

  .query-form {
    padding: 30px 20px;
  }

  /* 手机端：让输入框和按钮整体居中，标签稍微靠左 */
  .query-form-content {
    max-width: 320px;
    margin: 0 auto;
  }

  /* 整体居中对齐 */
  .query-form-content :deep(.el-form-item) {
    width: 100%;
    display: block;
    text-align: center;
  }

  .query-form-content :deep(.el-form-item__label) {
    width: 100%;
    display: block;
    text-align: center;
    padding: 0 0 6px 0;
    line-height: 1.4;
  }

  .query-form-content :deep(.el-form-item__content) {
    margin-left: 0 !important;
    width: 100%;
  }

  .query-form-content :deep(.el-input) {
    width: 100%;
  }

  .query-form-content :deep(.el-input__wrapper) {
    width: 100%;
  }

  .query-button {
    display: block;
    width: 100%;
    max-width: 280px;
    margin: 0 auto;
    text-align: center;
  }

  .result-header {
    padding: 20px;
    flex-direction: column;
    align-items: flex-start;
    gap: 15px;
  }

  .result-list {
    padding: 20px;
  }

  .certificate-card {
    padding: 20px;
  }

  .certificate-header {
    flex-wrap: wrap;
  }

  .certificate-info {
    min-width: 0;
    flex: 1 1 100%;
    margin-bottom: 12px;
  }

  .status-tag {
    flex-shrink: 0;
    margin-left: auto;
  }

  .certificate-details {
    grid-template-columns: 1fr;
  }

  .detail-header {
    flex-wrap: wrap;
    gap: 15px;
    justify-content: center;
  }

  .detail-badge-wrapper {
    width: auto;
    margin-bottom: 0;
  }

  .detail-descriptions {
    width: 100%;
  }

  .detail-descriptions :deep(.el-descriptions__table) {
    width: 100% !important;
  }

  .detail-descriptions :deep(.el-descriptions__body .el-descriptions__table .el-descriptions__cell) {
    display: block;
    width: 100% !important;
    padding: 12px 0;
    border-bottom: 1px solid var(--el-border-color-lighter);
  }

  .detail-descriptions :deep(.el-descriptions__body .el-descriptions__table .el-descriptions__label) {
    width: 100% !important;
    display: block;
    padding-bottom: 4px;
    font-weight: 600;
  }

  .detail-descriptions :deep(.el-descriptions__body .el-descriptions__table .el-descriptions__content) {
    width: 100% !important;
    display: block;
  }
}

.certificate-dialog :deep(.el-dialog) {
  border-radius: 24px;
  overflow: hidden;
}

.certificate-dialog :deep(.el-dialog__header) {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: #fff;
  padding: 24px 30px;
  margin: 0;
}

.certificate-dialog :deep(.el-dialog__title) {
  color: #fff;
  font-weight: 700;
  font-size: 20px;
}

.certificate-dialog :deep(.el-dialog__headerbtn .el-dialog__close) {
  color: #fff;
  font-size: 24px;
}

.certificate-dialog :deep(.el-dialog__body) {
  padding: 20px 30px;
  max-height: 85vh;
  overflow-y: auto;
}

.detail-header {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 20px;
  padding: 24px;
  background: linear-gradient(135deg, #f8f9ff 0%, #ffffff 100%);
  border-radius: 16px;
  margin-bottom: 24px;
}

.detail-badge-wrapper {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 12px;
  flex-shrink: 0;
}

.detail-badge {
  width: 80px;
  height: 80px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 16px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
  flex-shrink: 0;
  box-shadow: 0 4px 16px rgba(102, 126, 234, 0.3);
}

.detail-title {
  flex: 1;
  min-width: 0;
  overflow: hidden;
}

.detail-title h3 {
  font-size: 24px;
  font-weight: 700;
  color: var(--text-primary);
  margin: 0 0 8px 0;
  word-break: break-word;
  overflow-wrap: break-word;
  word-wrap: break-word;
}

.detail-title p {
  color: var(--text-secondary);
  font-size: 14px;
  margin: 0;
  font-family: 'Courier New', monospace;
  word-break: break-all;
  overflow-wrap: break-word;
  word-wrap: break-word;
}

.detail-status {
  font-weight: 600;
  padding: 10px 20px;
  border-radius: 20px;
  font-size: 16px;
  margin: 0;
}

.detail-descriptions {
  margin-bottom: 24px;
  width: 100%;
  overflow-x: auto;
}

.detail-descriptions :deep(.el-descriptions__label) {
  font-weight: 600;
  color: var(--text-primary);
}

.detail-descriptions :deep(.el-descriptions__table) {
  width: 100%;
  table-layout: auto;
}

.detail-descriptions :deep(.el-descriptions__cell) {
  word-break: break-word;
  overflow-wrap: break-word;
}

.certificate-image-wrapper {
  margin-top: 24px;
  text-align: center;
}

.image-container {
  display: inline-block;
  padding: 20px;
  background: #f8f9ff;
  border-radius: 16px;
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.08);
}

.pdf-container {
  width: 100%;
  padding: 0;
  background: #f8f9ff;
  border-radius: 16px;
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.08);
  overflow: hidden;
}

.certificate-image {
  max-width: 100%;
  max-height: 500px;
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s ease;
}

.certificate-image:hover {
  transform: scale(1.02);
}

.certificate-pdf {
  width: 100%;
  height: 75vh;
  min-height: 650px;
  max-height: 800px;
  border: none;
  display: block;
}
</style>

