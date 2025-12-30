<template>
  <div class="honor">
    <Header />
    
    <div class="page-banner slide-in-top">
      <div class="banner-content">
        <h1 class="fade-in-immediate">荣誉资质</h1>
        <p class="fade-in-immediate" style="animation-delay: 0.2s;">Honor Qualifications</p>
      </div>
    </div>

    <div class="container" style="padding: 0px 20px 60px 20px;">
      <el-row :gutter="20" class="honor-list">
        <el-col :xs="12" :sm="8" :md="6" v-for="honor in honors" :key="honor.id">
          <el-card class="honor-card scale-in hover-lift" shadow="hover">
            <div class="honor-image">
              <img v-if="honor.image" :src="getImageUrl(honor.image)" :alt="honor.title" />
              <div v-else class="honor-placeholder">荣誉资质</div>
            </div>
            <p class="honor-title">{{ honor.title }}</p>
            <p class="honor-desc" v-if="honor.description">{{ honor.description }}</p>
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
import { getHonors } from '../api/honor'
import { getImageUrl, processImageUrls } from '../utils/image'

export default {
  name: 'Honor',
  components: {
    Header,
    Footer
  },
  setup() {
    const honors = ref([])

    onMounted(async () => {
      try {
        const res = await getHonors()
        if (res.code === 200) {
          honors.value = processImageUrls(res.data, ['image'])
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
      honors,
      getImageUrl
    }
  }
}
</script>

<style scoped>
.honor {
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

.honor-card {
  margin-bottom: 30px;
  text-align: center;
  border-radius: 20px;
  overflow: hidden;
  border: 1px solid rgba(0, 0, 0, 0.06);
  transition: all 0.4s cubic-bezier(0.4, 0, 0.2, 1);
}

.honor-card:hover {
  transform: translateY(-12px) scale(1.02);
  box-shadow: 0 20px 40px rgba(102, 126, 234, 0.2);
  border-color: rgba(102, 126, 234, 0.3);
}

.honor-image {
  height: 200px;
  overflow: hidden;
  margin-bottom: 15px;
  border-radius: 4px;
}

.honor-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.honor-placeholder {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #e4e7ed;
  color: #909399;
  font-size: 16px;
}

.honor-title {
  font-size: 16px;
  font-weight: 600;
  margin-bottom: 10px;
  color: #2c3e50;
}

.honor-desc {
  color: #666;
  font-size: 14px;
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

  .honor-image {
    height: 150px;
  }
}
</style>

