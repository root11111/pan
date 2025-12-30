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
              <el-icon :size="48"><DocumentSearch /></el-icon>
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
      <div v-if="queryResult" class="result-section slide-in-bottom">
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
              :key="item.id" 
              class="certificate-item scale-in"
              :style="{ animationDelay: `${index * 0.1}s` }"
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
        width="900px"
        :close-on-click-modal="false"
        class="certificate-dialog"
      >
        <div v-if="certificateDetail" class="certificate-detail">
          <div class="detail-header">
            <div class="detail-badge">
              <el-icon :size="40"><Trophy /></el-icon>
            </div>
            <div class="detail-title">
              <h3>{{ certificateDetail.productName }}</h3>
              <p>{{ certificateDetail.certificateNo }}</p>
            </div>
            <el-tag 
              :type="certificateDetail.status === '有效' ? 'success' : certificateDetail.status === '已过期' ? 'danger' : 'warning'"
              size="large"
              class="detail-status"
            >
              {{ certificateDetail.status }}
            </el-tag>
          </div>
          <el-descriptions :column="2" border class="detail-descriptions">
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
            <div class="image-container">
              <img 
                :src="getImageUrl(certificateDetail.certificateFile)" 
                :alt="certificateDetail.certificateNo"
                class="certificate-image"
              />
            </div>
          </div>
        </div>
      </el-dialog>
    </div>

    <Footer />
  </div>
</template>

<script>
import { ref, nextTick } from 'vue'
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
  View 
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
    View
  },
  setup() {
    const { t, lang: currentLang } = useI18n()
    const queryForm = ref({
      certificateNo: '',
      productName: ''
    })
    const queryResult = ref(null)
    const loading = ref(false)
    const detailVisible = ref(false)
    const certificateDetail = ref(null)

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
        if (res.code === 200) {
          queryResult.value = res.data || []
          if (queryResult.value.length === 0) {
            ElMessage.info(t('noCertificateFound'))
          } else {
            ElMessage.success(`${t('totalRecords')} ${queryResult.value.length} ${t('records')}`)
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
      queryForm,
      queryResult,
      loading,
      detailVisible,
      certificateDetail,
      handleQuery,
      viewDetail,
      getImageUrl,
      t,
      currentLang
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
}

.certificate-item {
  animation: scaleIn 0.5s ease-out forwards;
  opacity: 0;
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
}

.certificate-info h4 {
  font-size: 20px;
  font-weight: 700;
  color: var(--text-primary);
  margin: 0 0 8px 0;
  line-height: 1.4;
}

.certificate-no {
  color: var(--text-secondary);
  font-size: 14px;
  margin: 0;
  font-family: 'Courier New', monospace;
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

  .certificate-details {
    grid-template-columns: 1fr;
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
  padding: 30px;
}

.detail-header {
  display: flex;
  align-items: center;
  gap: 20px;
  padding: 24px;
  background: linear-gradient(135deg, #f8f9ff 0%, #ffffff 100%);
  border-radius: 16px;
  margin-bottom: 24px;
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
}

.detail-title h3 {
  font-size: 24px;
  font-weight: 700;
  color: var(--text-primary);
  margin: 0 0 8px 0;
}

.detail-title p {
  color: var(--text-secondary);
  font-size: 14px;
  margin: 0;
  font-family: 'Courier New', monospace;
}

.detail-status {
  font-weight: 600;
  padding: 10px 20px;
  border-radius: 20px;
  font-size: 16px;
}

.detail-descriptions {
  margin-bottom: 24px;
}

.detail-descriptions :deep(.el-descriptions__label) {
  font-weight: 600;
  color: var(--text-primary);
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
</style>

