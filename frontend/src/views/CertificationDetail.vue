<template>
  <div class="certification-detail">
    <Header />
    
    <div class="container" style="padding: 0px 20px 60px 20px;">
      <el-card v-if="service">
        <div class="detail-header">
          <div class="detail-image" v-if="service.image">
            <img :src="getImageUrl(service.image)" :alt="service.nameCn" />
          </div>
          <div class="detail-info">
            <h1 class="service-title">{{ service.nameCn }}</h1>
            <p class="service-title-en">{{ service.nameEn }}</p>
            <div class="service-tags">
              <el-tag v-if="service.category" type="info" size="large">{{ service.category }}</el-tag>
              <el-tag v-if="service.region" type="primary" size="large" style="margin-left: 10px;">{{ service.region }}</el-tag>
            </div>
          </div>
        </div>
        
        <div class="detail-content">
          <h2>服务介绍</h2>
          <div class="content-text" v-if="service.descriptionCn">
            <p v-for="(para, index) in formatContent(service.descriptionCn)" :key="index">{{ para }}</p>
          </div>
          <div class="content-text-en" v-if="service.descriptionEn">
            <h3>Service Introduction</h3>
            <p v-for="(para, index) in formatContent(service.descriptionEn)" :key="index">{{ para }}</p>
          </div>
        </div>
      </el-card>
      
      <div class="back-button" style="margin-top: 30px;">
        <el-button @click="$router.push('/certification')">返回认证服务列表</el-button>
      </div>
    </div>

    <Footer />
  </div>
</template>

<script>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import Header from '../components/Header.vue'
import Footer from '../components/Footer.vue'
import { getCertificationById } from '../api/certification'
import { getImageUrl } from '../utils/image'

export default {
  name: 'CertificationDetail',
  components: {
    Header,
    Footer
  },
  setup() {
    const route = useRoute()
    const service = ref(null)

    onMounted(async () => {
      try {
        const res = await getCertificationById(route.params.id)
        if (res.code === 200) {
          service.value = res.data
        }
      } catch (error) {
        console.error('加载数据失败:', error)
      }
    })

    const formatContent = (content) => {
      if (!content) return []
      // 按段落分割内容
      return content.split('\n').filter(p => p.trim().length > 0)
    }

    return {
      service,
      formatContent,
      getImageUrl
    }
  }
}
</script>

<style scoped>
.certification-detail {
  min-height: 100vh;
}

.detail-header {
  display: flex;
  gap: 30px;
  margin-bottom: 40px;
  padding-bottom: 30px;
  border-bottom: 1px solid #eee;
}

.detail-image {
  flex-shrink: 0;
  width: 300px;
  height: 300px;
  overflow: hidden;
  border-radius: 8px;
  background: #f5f5f5;
}

.detail-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.detail-info {
  flex: 1;
}

.service-title {
  font-size: 32px;
  margin-bottom: 10px;
  color: #2c3e50;
}

.service-title-en {
  font-size: 18px;
  color: #909399;
  margin-bottom: 20px;
}

.service-tags {
  margin-top: 20px;
}

.detail-content {
  margin-top: 40px;
}

.detail-content h2 {
  font-size: 24px;
  margin-bottom: 20px;
  color: #2c3e50;
  border-left: 4px solid #409eff;
  padding-left: 15px;
}

.detail-content h3 {
  font-size: 20px;
  margin-top: 30px;
  margin-bottom: 15px;
  color: #2c3e50;
}

.content-text,
.content-text-en {
  line-height: 1.8;
  color: #666;
  font-size: 16px;
}

.content-text p,
.content-text-en p {
  margin-bottom: 15px;
  text-indent: 2em;
}

.back-button {
  text-align: center;
}

/* 移动端适配 */
@media (max-width: 768px) {
  .detail-header {
    flex-direction: column;
  }

  .detail-image {
    width: 100%;
    height: 250px;
  }

  .service-title {
    font-size: 24px;
  }
}
</style>

