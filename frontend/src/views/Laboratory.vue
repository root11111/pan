<template>
  <div class="laboratory">
    <Header />
    
    <div class="page-banner slide-in-top">
      <div class="banner-content">
        <h1 class="fade-in-immediate">实验室展示</h1>
        <p class="fade-in-immediate" style="animation-delay: 0.2s;">Laboratory Center</p>
      </div>
    </div>

    <div class="container" style="padding: 0px 20px 60px 20px;">
      <el-row :gutter="30" class="lab-list">
        <el-col :xs="24" :sm="12" :md="8" v-for="lab in laboratories" :key="lab.id">
          <el-card class="lab-card scale-in hover-lift" shadow="hover">
            <div class="lab-image">
              <img v-if="lab.image" :src="getImageUrl(lab.image)" :alt="lab.nameCn" />
              <div v-else class="lab-placeholder">{{ lab.nameCn }}</div>
            </div>
            <div class="lab-content">
              <h3>{{ lab.nameCn }}</h3>
              <p class="lab-name-en">{{ lab.nameEn }}</p>
              <p class="lab-desc">{{ lab.descriptionCn }}</p>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>

    <Footer />
  </div>
</template>

<script>
import { ref, onMounted, nextTick } from 'vue'
import { initAllScrollAnimations } from '../utils/scrollAnimation'
import Header from '../components/Header.vue'
import Footer from '../components/Footer.vue'
import { getLaboratories } from '../api/laboratory'
import { getImageUrl, processImageUrls } from '../utils/image'

export default {
  name: 'Laboratory',
  components: {
    Header,
    Footer
  },
  setup() {
    const laboratories = ref([])

    onMounted(async () => {
      try {
        const res = await getLaboratories()
        if (res.code === 200) {
          laboratories.value = processImageUrls(res.data, ['image'])
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

    return {
      laboratories,
      getImageUrl
    }
  }
}
</script>

<style scoped>
.laboratory {
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

.lab-card {
  margin-bottom: 30px;
  border-radius: 20px;
  overflow: hidden;
  border: 1px solid rgba(0, 0, 0, 0.06);
  transition: all 0.4s cubic-bezier(0.4, 0, 0.2, 1);
}

.lab-card:hover {
  transform: translateY(-12px) scale(1.02);
  box-shadow: 0 20px 40px rgba(102, 126, 234, 0.2);
  border-color: rgba(102, 126, 234, 0.3);
}

.lab-image {
  height: 250px;
  overflow: hidden;
  margin-bottom: 20px;
  border-radius: 4px;
}

.lab-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.lab-placeholder {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: #fff;
  font-size: 24px;
  font-weight: 600;
}

.lab-content h3 {
  font-size: 22px;
  margin-bottom: 10px;
  color: #2c3e50;
}

.lab-name-en {
  color: #909399;
  font-size: 14px;
  margin-bottom: 15px;
}

.lab-desc {
  color: #666;
  line-height: 1.6;
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

  .lab-image {
    height: 180px;
  }

  .lab-card h3 {
    font-size: 18px;
  }
}
</style>

