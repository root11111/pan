<template>
  <div class="contact">
    <Header />
    
    <div class="page-banner slide-in-top">
      <div class="banner-content">
        <h1 class="fade-in-immediate">联系我们</h1>
        <p class="fade-in-immediate" style="animation-delay: 0.2s;">Contact Us</p>
      </div>
    </div>

    <div class="container" style="padding: 0px 20px 60px 20px;">
      <el-row :gutter="40">
        <el-col :xs="24" :md="12">
          <el-card class="slide-in-left">
            <h2 style="margin-bottom: 30px;">{{ t('contactInfo') }}</h2>
            <div class="contact-info" v-if="companyInfo">
              <div class="contact-item">
                <el-icon><Phone /></el-icon>
                <div>
                  <h3>{{ t('phone') }}</h3>
                  <p>{{ companyInfo.phone }}</p>
                </div>
              </div>
              <div class="contact-item" v-if="companyInfo?.mobile">
                <el-icon><Phone /></el-icon>
                <div>
                  <h3>{{ t('mobile') }}</h3>
                  <p>{{ companyInfo.mobile }}</p>
                </div>
              </div>
              <div class="contact-item" v-if="companyInfo?.email">
                <el-icon><Message /></el-icon>
                <div>
                  <h3>{{ t('email') }}</h3>
                  <p>{{ companyInfo.email }}</p>
                </div>
              </div>
              <div class="contact-item">
                <el-icon><Message /></el-icon>
                <div>
                  <h3>QQ</h3>
                  <p>{{ companyInfo.qq }}</p>
                </div>
              </div>
              <div class="contact-item">
                <el-icon><ChatDotRound /></el-icon>
                <div>
                  <h3>微信</h3>
                  <p>{{ companyInfo.wechat }}</p>
                </div>
              </div>
              <div class="contact-item">
                <el-icon><Location /></el-icon>
                <div>
                  <h3>地址</h3>
                  <p>{{ companyInfo.address }}</p>
                </div>
              </div>
            </div>
          </el-card>
        </el-col>
        <el-col :xs="24" :md="12">
          <el-card class="slide-in-right">
            <h2 style="margin-bottom: 30px;">{{ t('onlineMessage') }}</h2>
            <el-form :model="form" :rules="rules" ref="formRef" label-width="80px">
              <el-form-item :label="t('name')" prop="name">
                <el-input v-model="form.name" :placeholder="t('pleaseEnterName')"></el-input>
              </el-form-item>
              <el-form-item :label="t('phone')" prop="phone">
                <el-input v-model="form.phone" :placeholder="t('pleaseEnterPhone')"></el-input>
              </el-form-item>
              <el-form-item :label="t('email')" prop="email">
                <el-input v-model="form.email" :placeholder="t('pleaseEnterEmail')"></el-input>
              </el-form-item>
              <el-form-item :label="t('messageContent')" prop="content">
                <el-input
                  v-model="form.content"
                  type="textarea"
                  :rows="4"
                  :placeholder="t('pleaseEnterMessage')"
                ></el-input>
              </el-form-item>
              <el-form-item>
                <el-button 
                  type="primary" 
                  @click="submitForm" 
                  :loading="submitting"
                  style="width: 100%;"
                  size="large"
                >
                  {{ submitting ? t('submitting') : t('submitMessage') }}
                </el-button>
              </el-form-item>
            </el-form>
          </el-card>
        </el-col>
      </el-row>
    </div>

    <Footer />
  </div>
</template>

<script>
import { ref, reactive, onMounted, nextTick } from 'vue'
import { initAllScrollAnimations } from '../utils/scrollAnimation'
import { ElMessage } from 'element-plus'
import { Phone, Message, ChatDotRound, Location } from '@element-plus/icons-vue'
import Header from '../components/Header.vue'
import Footer from '../components/Footer.vue'
import { getCompanyInfo } from '../api/company'
import { submitMessage } from '../api/message'
import { useI18n } from '../utils/i18n'

export default {
  name: 'Contact',
  components: {
    Header,
    Footer,
    Phone,
    Message,
    ChatDotRound,
    Location
  },
  setup() {
    const { t } = useI18n()
    const formRef = ref(null)
    const submitting = ref(false)
    const companyInfo = ref(null)
    const form = reactive({
      name: '',
      phone: '',
      email: '',
      company: '',
      content: ''
    })

    const rules = {
      name: [
        { required: true, message: t('pleaseEnterName'), trigger: 'blur' }
      ],
      phone: [
        { required: true, message: t('pleaseEnterPhone'), trigger: 'blur' }
      ],
      content: [
        { required: true, message: t('pleaseEnterMessage'), trigger: 'blur' }
      ]
    }

    onMounted(async () => {
      try {
        const res = await getCompanyInfo()
        if (res.code === 200) {
          companyInfo.value = res.data
        }
      } catch (error) {
        console.error('加载数据失败:', error)
      }
      
      // 初始化滚动动画
      await nextTick()
      setTimeout(() => {
        initAllScrollAnimations()
      }, 100)
    })

    const submitForm = async () => {
      console.log('submitForm 被调用')
      if (!formRef.value) {
        console.error('formRef 未初始化')
        ElMessage.warning('表单未初始化，请刷新页面重试')
        return
      }
      
      try {
        await formRef.value.validate(async (valid) => {
          console.log('表单验证结果:', valid)
          if (valid) {
            submitting.value = true
            try {
              console.log('提交表单数据:', form)
              const res = await submitMessage(form)
              console.log('提交响应:', res)
            if (res.code === 200) {
              ElMessage.success(t('messageSubmitSuccess'))
              formRef.value.resetFields()
              // 重置表单数据
              Object.assign(form, {
                name: '',
                phone: '',
                email: '',
                company: '',
                content: ''
              })
            } else {
              ElMessage.error(res.message || t('submitFailed'))
            }
          } catch (error) {
            console.error('提交失败:', error)
            ElMessage.error(t('submitFailedRetry'))
          } finally {
            submitting.value = false
          }
        } else {
          console.log('表单验证失败')
          ElMessage.warning(t('pleaseFillCompleteForm'))
        }
        })
      } catch (error) {
        console.error('表单验证异常:', error)
        ElMessage.error('表单验证失败，请检查输入')
      }
    }

    return {
      formRef,
      form,
      rules,
      submitting,
      companyInfo,
      submitForm,
      t
    }
  }
}
</script>

<style scoped>
.contact {
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

.contact-info {
  display: flex;
  flex-direction: column;
  gap: 30px;
}

.contact-item {
  display: flex;
  align-items: flex-start;
  gap: 20px;
}

.contact-item .el-icon {
  font-size: 24px;
  color: #409eff;
  margin-top: 5px;
}

.contact-item h3 {
  font-size: 18px;
  margin-bottom: 8px;
  color: #2c3e50;
}

.contact-item p {
  color: #666;
  font-size: 16px;
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

  .contact-info {
    gap: 20px;
  }

  .contact-item {
    gap: 15px;
  }

  .contact-item .el-icon {
    font-size: 20px;
  }

  .contact-item h3 {
    font-size: 16px;
  }

  .contact-item p {
    font-size: 14px;
  }
}
</style>

