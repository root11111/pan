<template>
  <div class="message">
    <Header />
    
    <div class="page-banner slide-in-top">
      <div class="banner-content">
        <h1 class="fade-in-immediate">{{ currentLang === 'en' ? 'Online Message' : '在线留言' }}</h1>
        <p class="fade-in-immediate" style="animation-delay: 0.2s;">{{ currentLang === 'en' ? '在线留言' : 'Online Message' }}</p>
      </div>
    </div>

    <div class="container" style="padding: 0px 20px 60px 20px;">
      <el-card style="max-width: 800px; margin: 0 auto;" class="slide-in-bottom">
        <el-form :model="form" :rules="rules" ref="formRef" label-width="100px">
          <el-form-item :label="t('name')" prop="name">
            <el-input v-model="form.name" :placeholder="t('pleaseEnterName')"></el-input>
          </el-form-item>
          <el-form-item :label="t('phone')" prop="phone">
            <el-input v-model="form.phone" :placeholder="t('pleaseEnterPhone')"></el-input>
          </el-form-item>
          <el-form-item :label="t('email')" prop="email">
            <el-input v-model="form.email" :placeholder="t('pleaseEnterEmail')"></el-input>
          </el-form-item>
          <el-form-item :label="t('company')" prop="company">
            <el-input v-model="form.company" :placeholder="t('pleaseEnterCompany')"></el-input>
          </el-form-item>
          <el-form-item :label="t('messageContent')" prop="content">
            <el-input
              v-model="form.content"
              type="textarea"
              :rows="6"
              :placeholder="t('pleaseEnterMessage')"
            ></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitForm" :loading="submitting">{{ t('submitMessage') }}</el-button>
            <el-button @click="resetForm">{{ t('reset') }}</el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </div>

    <Footer />
  </div>
</template>

<script>
import { ref, reactive } from 'vue'
import { ElMessage } from 'element-plus'
import Header from '../components/Header.vue'
import Footer from '../components/Footer.vue'
import { submitMessage } from '../api/message'
import { useI18n } from '../utils/i18n'

export default {
  name: 'Message',
  components: {
    Header,
    Footer
  },
  setup() {
    const { t } = useI18n()
    const formRef = ref(null)
    const submitting = ref(false)
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
      ],
      email: [
        { type: 'email', message: t('pleaseEnterEmail'), trigger: 'blur' }
      ]
    }

    const submitForm = async () => {
      if (!formRef.value) return
      
      await formRef.value.validate(async (valid) => {
        if (valid) {
          submitting.value = true
          try {
            const res = await submitMessage(form)
            if (res.code === 200) {
              ElMessage.success(t('messageSubmitSuccess'))
              resetForm()
            } else {
              ElMessage.error(res.message || t('submitFailed'))
            }
          } catch (error) {
            ElMessage.error(t('submitFailedRetry'))
          } finally {
            submitting.value = false
          }
        }
      })
    }

    const resetForm = () => {
      if (formRef.value) {
        formRef.value.resetFields()
      }
    }

    return {
      formRef,
      form,
      rules,
      submitting,
      submitForm,
      resetForm,
      t,
      currentLang: useI18n().lang
    }
  }
}
</script>

<style scoped>
.message {
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
}
</style>

