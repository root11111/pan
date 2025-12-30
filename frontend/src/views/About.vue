<template>
  <div class="about">
    <Header />
    
    <div class="page-banner slide-in-top">
      <div class="banner-content">
        <h1 class="fade-in-immediate">关于我们</h1>
        <p class="fade-in-immediate" style="animation-delay: 0.2s;">About Us</p>
      </div>
    </div>

    <div class="container" style="padding: 0px 20px 60px 20px;">
      <!-- 公司简介 -->
      <section class="section-content slide-in-bottom">
        <h2 class="section-title slide-in-top">公司简介</h2>
        <div class="intro-text slide-in-bottom" v-if="companyInfo">
          <p>{{ companyInfo.descriptionCn }}</p>
          <p>{{ companyInfo.descriptionEn }}</p>
        </div>
      </section>

      <!-- 企业优势 -->
      <section class="section-content advantages-section" id="advantage">
        <h2 class="section-title slide-in-top">{{ currentLang === 'en' ? 'Enterprise Advantages' : '企业优势' }}</h2>
        <p class="section-subtitle slide-in-top">{{ currentLang === 'en' ? '企业优势' : 'Enterprise advantages' }}</p>
        <el-row :gutter="30" class="advantage-list">
          <el-col :xs="24" :sm="12" :md="6" v-for="item in advantages" :key="item.id">
            <el-card class="advantage-card scale-in hover-lift" shadow="hover">
              <div class="advantage-icon">
                <div class="icon-wrapper">
                  <img v-if="item.icon" :src="getImageUrl(item.icon)" alt="" />
                </div>
              </div>
              <h3>{{ item.title }}</h3>
              <p>{{ item.description }}</p>
            </el-card>
          </el-col>
        </el-row>
      </section>

      <!-- 企业文化 -->
      <section class="section-content culture-section" id="culture">
        <h2 class="section-title slide-in-top">{{ currentLang === 'en' ? 'Corporate Culture' : '企业文化' }}</h2>
        <p class="section-subtitle slide-in-top">{{ currentLang === 'en' ? '企业文化' : 'Corporate Culture' }}</p>
        <el-row :gutter="30" class="culture-list">
          <el-col :xs="24" :sm="12" :md="6" v-for="(value, index) in cultureValues" :key="index">
            <el-card class="culture-card scale-in hover-lift" shadow="hover">
              <div class="culture-icon-wrapper">
                <div class="culture-icon-bg"></div>
              </div>
              <h3>{{ value.title }}</h3>
              <p>{{ value.desc }}</p>
            </el-card>
          </el-col>
        </el-row>
      </section>
    </div>

    <Footer />
  </div>
</template>

<script>
import { ref, onMounted, nextTick, watch } from 'vue'
import { useRoute } from 'vue-router'
import { initAllScrollAnimations } from '../utils/scrollAnimation'
import Header from '../components/Header.vue'
import Footer from '../components/Footer.vue'
import { getCompanyInfo } from '../api/company'
import { getAdvantages } from '../api/advantage'
import { getCorporateCultures } from '../api/corporateCulture'
import { getImageUrl, processImageUrls } from '../utils/image'
import { useI18n } from '../utils/i18n'

export default {
  name: 'About',
  components: {
    Header,
    Footer
  },
  setup() {
    const { t, lang: currentLang } = useI18n()
    const route = useRoute()
    const companyInfo = ref(null)
    const advantages = ref([])
    const cultureValues = ref([])

    onMounted(async () => {
      try {
        const [companyRes, advantageRes, cultureRes] = await Promise.all([
          getCompanyInfo(),
          getAdvantages(),
          getCorporateCultures()
        ])

        if (companyRes.code === 200) companyInfo.value = companyRes.data
        if (advantageRes.code === 200) {
          advantages.value = processImageUrls(advantageRes.data, ['icon'])
        }
        if (cultureRes.code === 200) {
          // 转换数据格式，将description映射到desc
          cultureValues.value = cultureRes.data.map(item => ({
            title: item.title,
            desc: item.description
          }))
        }
      } catch (error) {
        console.error('加载数据失败:', error)
      }
      
      // 初始化滚动动画
      await nextTick()
      setTimeout(() => {
        initAllScrollAnimations()
        // 如果URL中有hash，滚动到对应位置
        if (route.hash) {
          scrollToHash(route.hash)
        }
      }, 100)
    })

    // 监听路由hash变化
    watch(() => route.hash, (newHash) => {
      if (newHash) {
        nextTick(() => {
          scrollToHash(newHash)
        })
      }
    })

    // 滚动到hash对应的元素
    const scrollToHash = (hash) => {
      if (!hash) return
      const element = document.querySelector(hash)
      if (element) {
        setTimeout(() => {
          const headerHeight = 80 // 顶部菜单高度
          const elementPosition = element.getBoundingClientRect().top + window.pageYOffset
          const offsetPosition = elementPosition - headerHeight
          window.scrollTo({
            top: offsetPosition,
            behavior: 'smooth'
          })
        }, 300) // 等待页面渲染完成
      }
    }

    // 处理图片加载错误
    const handleImageError = (event) => {
      // 图片加载失败时，隐藏图片或显示占位符
      event.target.style.display = 'none'
    }

    return {
      companyInfo,
      advantages,
      cultureValues,
      getImageUrl,
      scrollToHash,
      t,
      currentLang
    }
  }
}
</script>

<style scoped>
.about {
  min-height: 100vh;
  position: relative;
  background-image: url('/about-bg.jpg');
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  background-attachment: fixed;
}

.about::before {
  content: '';
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-image: url('/about-bg.jpg');
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  background-attachment: fixed;
  z-index: 0;
  pointer-events: none;
}

.page-banner {
  position: relative;
  z-index: 1;
}

.container {
  position: relative;
  z-index: 1;
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

.section-content {
  margin-bottom: 80px;
}

.intro-text {
  max-width: 900px;
  margin: 0 auto;
  text-align: center;
  background: rgba(255, 255, 255, 0.95);
  backdrop-filter: blur(10px);
  -webkit-backdrop-filter: blur(10px);
  padding: 40px;
  border-radius: 20px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.15);
}

.intro-text p {
  font-size: 17px;
  line-height: 2;
  margin-bottom: 25px;
  color: var(--text-secondary);
  text-align: justify;
}

.intro-text p:last-child {
  margin-bottom: 0;
}

.advantages-section {
  background: rgba(248, 249, 250, 0.95);
  backdrop-filter: blur(10px);
  -webkit-backdrop-filter: blur(10px);
  padding: 60px 20px;
  margin: 60px -20px;
}

.advantage-card {
  text-align: center;
  padding: 40px 24px;
  height: 100%;
  border-radius: 20px;
  border: 1px solid rgba(102, 126, 234, 0.1);
  transition: all 0.4s cubic-bezier(0.4, 0, 0.2, 1);
  position: relative;
  overflow: hidden;
}

.advantage-card::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 4px;
  background: var(--gradient-primary);
  transform: scaleX(0);
  transform-origin: left;
  transition: transform 0.4s ease;
}

.advantage-card:hover::before {
  transform: scaleX(1);
}

.advantage-card:hover {
  transform: translateY(-12px) scale(1.02);
  box-shadow: 0 20px 40px rgba(102, 126, 234, 0.2);
  border-color: rgba(102, 126, 234, 0.3);
}

.advantage-icon {
  margin-bottom: 24px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.icon-wrapper {
  width: 100px;
  height: 100px;
  border-radius: 20px;
  background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 15px;
  transition: transform 0.4s ease;
}

.advantage-card:hover .icon-wrapper {
  transform: scale(1.1) rotate(5deg);
}

.advantage-icon img {
  width: 100%;
  height: 100%;
  object-fit: contain;
}

.advantage-card h3 {
  font-size: 20px;
  font-weight: 700;
  margin-bottom: 15px;
  color: var(--text-primary);
  letter-spacing: 0.3px;
}

.advantage-card p {
  color: var(--text-secondary);
  line-height: 1.7;
  font-size: 15px;
}

.culture-section {
  padding: 60px 0;
}

.culture-card {
  text-align: center;
  padding: 40px 24px;
  height: 100%;
  border-radius: 20px;
  border: 1px solid rgba(102, 126, 234, 0.1);
  transition: all 0.4s cubic-bezier(0.4, 0, 0.2, 1);
  position: relative;
  overflow: hidden;
}

.culture-card::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 4px;
  background: var(--gradient-primary);
  transform: scaleX(0);
  transform-origin: left;
  transition: transform 0.4s ease;
}

.culture-card:hover::before {
  transform: scaleX(1);
}

.culture-card:hover {
  transform: translateY(-12px) scale(1.02);
  box-shadow: 0 20px 40px rgba(102, 126, 234, 0.2);
  border-color: rgba(102, 126, 234, 0.3);
}

.culture-icon-wrapper {
  width: 80px;
  height: 80px;
  margin: 0 auto 20px;
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
}

.culture-icon-bg {
  width: 100%;
  height: 100%;
  border-radius: 50%;
  background: var(--gradient-primary);
  opacity: 0.1;
  transition: all 0.4s ease;
}

.culture-card:hover .culture-icon-bg {
  opacity: 0.2;
  transform: scale(1.2);
}

.culture-card h3 {
  font-size: 22px;
  font-weight: 700;
  margin-bottom: 15px;
  color: var(--primary-color);
  letter-spacing: 0.3px;
}

.culture-card p {
  color: var(--text-secondary);
  line-height: 1.8;
  text-align: left;
  font-size: 15px;
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

  .section-content {
    margin-bottom: 40px;
  }

  .advantages-section {
    padding: 40px 15px;
    margin: 40px -15px;
  }

  .advantage-card,
  .culture-card {
    padding: 20px 15px;
  }
}

@media (max-width: 480px) {
  .page-banner h1 {
    font-size: 24px;
  }
}
</style>

