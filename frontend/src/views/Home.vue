<template>
  <div class="home">
    <Header />
    
    <!-- Banner -->
    <section class="banner">
      <div class="banner-background">
        <div class="banner-gradient"></div>
        <div class="banner-particles">
          <div class="particle" v-for="i in 20" :key="i" :style="{ left: Math.random() * 100 + '%', animationDelay: Math.random() * 3 + 's' }"></div>
        </div>
      </div>
      <div class="banner-content">
        <div class="banner-text fade-in">
          <h1 class="banner-title">{{ homeConfig.banner_title || '独立且权威的综合性第三方检测认证机构' }}</h1>
          <p class="banner-subtitle">{{ homeConfig.banner_subtitle || 'Independent and authoritative comprehensive third-party testing and certification organization' }}</p>
          <p class="banner-desc">{{ homeConfig.banner_desc || '产品检测/检验/认证/技术服务指导' }}</p>
        </div>
        <div class="banner-values">
          <div 
            class="value-item hover-lift" 
            v-for="value in bannerValues" 
            :key="value.id"
          >
            <div class="value-icon">
              <div class="icon-circle"></div>
            </div>
            <h3>{{ value.title }}</h3>
            <p>{{ value.description }}</p>
          </div>
        </div>
        <div class="banner-actions fade-in-immediate" style="animation-delay: 0.3s;">
          <el-button type="primary" size="large" class="btn-primary-glow" @click="$router.push('/contact')">
            <span>{{ t('immediateConsultation') }}</span>
            <el-icon style="margin-left: 8px;"><ArrowRight /></el-icon>
          </el-button>
        </div>
      </div>
    </section>

    <!-- 图标展示 -->
    <section class="section icon-showcase" v-if="filteredIcons.length > 0">
      <div class="container">
        <div class="icon-scroll-wrapper">
          <div class="icon-row">
            <div
              class="icon-item"
              v-for="(icon, index) in filteredIcons"
              :key="`icon-1-${index}`"
            >
              <img :src="icon" :alt="`Icon ${index + 1}`" @error="handleIconError" />
            </div>
            <!-- 复制一遍图标实现无缝循环 -->
            <div
              class="icon-item"
              v-for="(icon, index) in filteredIcons"
              :key="`icon-2-${index}`"
            >
              <img :src="icon" :alt="`Icon ${index + 1}`" @error="handleIconError" />
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- 公司简介 -->
    <section class="section company-intro slide-in-right">
      <div class="company-intro-bg"></div>
      <div class="container">
        <div class="intro-wrapper">
          <h2 class="section-title slide-in-top">
            <span>公司简介</span>
            <span class="company-profile-inline">Company Profile</span>
          </h2>
          <div class="intro-content slide-in-bottom" v-if="companyInfo">
            <p class="intro-text">
              {{ currentLang === 'en' ? companyInfo.descriptionEn : companyInfo.descriptionCn }}
            </p>
            <el-button type="primary" class="hover-lift" @click="$router.push('/about')">{{ t('viewDetails') }}</el-button>
          </div>
        </div>
      </div>
    </section>

    <!-- 全球认证 -->
    <section class="section certification-section">
      <div class="container">
        <h2 class="section-title slide-in-top">{{ currentLang === 'en' ? 'Global Certification' : '全球认证' }}</h2>
        <p class="section-subtitle slide-in-top">{{ currentLang === 'en' ? '全球认证' : 'Global Certification' }}</p>
        <div class="cert-scroll-wrapper" v-if="certifications && certifications.length">
          <div class="cert-row">
            <!-- 第一组分类 -->
            <div
              class="cert-item"
              v-for="item in certifications"
              :key="`cert-1-${item.id}`"
            >
              <el-card class="certification-card hover-lift" shadow="hover">
                <div class="certification-icon">
                  <img
                    v-if="item.icon"
                    :src="item.icon"
                    :alt="item.name"
                    @error="handleImageError"
                    @load="handleImageLoad($event, item)"
                  />
                  <div class="icon-placeholder">
                    <span class="icon-text">{{ item.name.charAt(0) }}</span>
                  </div>
                </div>
                <h3>{{ item.name }}</h3>
              </el-card>
            </div>
            <!-- 复制一组实现左右循环滚动 -->
            <div
              class="cert-item"
              v-for="item in certifications"
              :key="`cert-2-${item.id}`"
            >
              <el-card class="certification-card hover-lift" shadow="hover">
                <div class="certification-icon">
                  <img
                    v-if="item.icon"
                    :src="item.icon"
                    :alt="item.name"
                    @error="handleImageError"
                    @load="handleImageLoad($event, item)"
                  />
                  <div class="icon-placeholder">
                    <span class="icon-text">{{ item.name.charAt(0) }}</span>
                  </div>
                </div>
                <h3>{{ item.name }}</h3>
              </el-card>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- 检测项目 -->
    <section class="section testing-section">
      <div class="container">
        <h2 class="section-title slide-in-top">
          {{ currentLang === 'en' ? 'Testing Projects' : '检测项目' }}
        </h2>
        <p class="section-subtitle slide-in-top">
          {{ currentLang === 'en' ? 'SURVEILLANCE PROJECT' : 'SURVEILLANCE PROJECT' }}
        </p>

        <div class="testing-glass">
          <!-- 参考 CTL 检测项目图标结构：https://www.ctl-lab.com/index.html -->
          <div class="ipro-list" ref="testingIconsRef">
            <ul>
            <li>
              <a href="javascript:void(0);">
                <div class="icon-pro">
                  <img
                    :src="getImageUrl('tubiao/医疗器械.png')"
                    alt="医疗器械"
                  />
                </div>
                <span>{{ currentLang === 'en' ? 'Medical Devices' : '医疗器械' }}</span>
              </a>
            </li>
            <li>
              <a href="javascript:void(0);">
                <div class="icon-pro">
                  <img
                    :src="getImageUrl('tubiao/灯具.png')"
                    alt="灯具"
                  />
                </div>
                <span>{{ currentLang === 'en' ? 'Lighting' : '灯具' }}</span>
              </a>
            </li>
            <li>
              <a href="javascript:void(0);">
                <div class="icon-pro">
                  <img
                    :src="getImageUrl('tubiao/家用电器.png')"
                    alt="家用电器"
                  />
                </div>
                <span>{{ currentLang === 'en' ? 'Household Appliances' : '家用电器' }}</span>
              </a>
            </li>
            <li>
              <a href="javascript:void(0);">
                <div class="icon-pro">
                  <img
                    :src="getImageUrl('tubiao/视听产品.png')"
                    alt="视听产品"
                  />
                </div>
                <span>{{ currentLang === 'en' ? 'AV Products' : '视听产品' }}</span>
              </a>
            </li>
            <li>
              <a href="javascript:void(0);">
                <div class="icon-pro">
                  <img
                    :src="getImageUrl('tubiao/无线通讯.png')"
                    alt="无线通讯"
                  />
                </div>
                <span>{{ currentLang === 'en' ? 'Wireless Communication' : '无线通讯' }}</span>
              </a>
            </li>
            <li>
              <a href="javascript:void(0);">
                <div class="icon-pro">
                  <img
                    :src="getImageUrl('tubiao/激光产品.png')"
                    alt="激光产品"
                  />
                </div>
                <span>{{ currentLang === 'en' ? 'Laser Products' : '激光产品' }}</span>
              </a>
            </li>
            <li>
              <a href="javascript:void(0);">
                <div class="icon-pro">
                  <img
                    :src="getImageUrl('tubiao/机械设备.png')"
                    alt="机械设备"
                  />
                </div>
                <span>{{ currentLang === 'en' ? 'Machinery' : '机械设备' }}</span>
              </a>
            </li>
            <li>
              <a href="javascript:void(0);">
                <div class="icon-pro">
                  <img
                    :src="getImageUrl('tubiao/玩具.png')"
                    alt="玩具"
                  />
                </div>
                <span>{{ currentLang === 'en' ? 'Toys' : '玩具' }}</span>
              </a>
            </li>
            <li>
              <a href="javascript:void(0);">
                <div class="icon-pro">
                  <img
                    :src="getImageUrl('tubiao/工业设备.png')"
                    alt="工业设备"
                  />
                </div>
                <span>{{ currentLang === 'en' ? 'Industrial Equipment' : '工业设备' }}</span>
              </a>
            </li>
              <li>
                <a href="javascript:void(0);">
                  <div class="icon-pro">
                    <img
                      :src="getImageUrl('tubiao/其他.png')"
                      alt="其他"
                    />
                  </div>
                  <span>{{ currentLang === 'en' ? 'Others' : '其他' }}</span>
                </a>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </section>

    <!-- 企业优势 -->
    <section class="section advantages" v-if="advantages.length > 0">
      <div class="container">
        <h2 class="section-title">{{ currentLang === 'en' ? 'Enterprise Advantages' : '企业优势' }}</h2>
        <p class="section-subtitle">{{ currentLang === 'en' ? '企业优势' : 'Enterprise advantages' }}</p>
        <div class="advantage-intro" v-if="companyInfo">
          <p>{{ homeConfig.advantage_intro || '广东中翰检测技术有限公司拥有独立的测试场地，先进的实验设备，专业的服务团队，对各种产品有着丰富的全球认证经验。能够帮助客户顺利获得通行各个国家和地区的安全认证。通过中翰的全球认证服务，厂商不仅可以避免送样到国外测试的困扰，还可以缩短认证周期，有效降低成本，从而保证产品顺利通过全球认证的审核。' }}</p>
        </div>
        <el-row :gutter="20" class="advantage-list" style="margin-top: 40px;">
          <el-col 
            :xs="24" 
            :sm="12" 
            :md="6" 
            v-for="item in advantages" 
            :key="item.id"
          >
            <div class="advantage-item">
              <div class="advantage-image">
                <img v-if="item.icon" :src="getImageUrl(item.icon)" :alt="item.title" />
              </div>
              <div class="advantage-content">
                <h3>{{ item.title }}</h3>
                <div class="advantage-description">{{ formatDescription(item.description) }}</div>
              </div>
            </div>
          </el-col>
        </el-row>
      </div>
    </section>

    <!-- 实验室中心 -->
    <section class="section laboratories">
      <div class="container">
        <h2 class="section-title slide-in-top">
          {{ currentLang === 'en' ? 'Laboratory Center' : '实验室中心' }}
        </h2>
        <p class="section-subtitle slide-in-top">
          {{ currentLang === 'en' ? '实验室中心' : 'Laboratory Center' }}
        </p>
        <el-row :gutter="30">
          <el-col 
            :xs="24" 
            :sm="12" 
            :md="6" 
            v-for="lab in laboratories" 
            :key="lab.id"
          >
            <el-card 
              class="lab-card hover-lift scale-in" 
              shadow="hover" 
              @click="$router.push('/laboratory')"
            >
              <div class="lab-image">
                <div class="image-mask"></div>
                <img v-if="lab.image" :src="getImageUrl(lab.image)" :alt="lab.nameCn" />
                <div v-else class="lab-placeholder">{{ lab.nameCn }}</div>
                <div class="lab-overlay">
                  <el-icon class="overlay-icon"><View /></el-icon>
                </div>
              </div>
              <div class="lab-content">
                <h3>{{ lab.nameCn }}</h3>
                <p>{{ lab.nameEn }}</p>
              </div>
            </el-card>
          </el-col>
        </el-row>
      </div>
    </section>

    <!-- 荣誉资质 -->
    <section class="section honors" v-if="honors.length > 0">
      <div class="container">
        <h2 class="section-title slide-in-top">{{ currentLang === 'en' ? 'Honors' : '荣誉资质' }}</h2>
        <div class="honor-intro slide-in-bottom">
          <p>{{ homeConfig.honor_intro || '作为独立且权威的第三方检测认证机构，依托强大的技术、人员及网络优势，持续拓展资质领域，已获得多家国内及国际权威机构的认可。业务范围已覆盖了消费品、工业品、纺织品、汽车等各个行业，赢得了客户和社会的信赖，并向社会各界提供更加优质高效的服务。' }}</p>
        </div>
        <el-row :gutter="20" style="margin-top: 40px;">
          <el-col :xs="12" :sm="8" :md="6" v-for="honor in honors.slice(0, 8)" :key="honor.id">
            <el-card class="honor-card scale-in hover-lift" shadow="hover">
              <img v-if="honor.image" :src="getImageUrl(honor.image)" :alt="honor.title" />
              <p>{{ honor.title }}</p>
            </el-card>
          </el-col>
        </el-row>
        <div class="text-center" style="margin-top: 30px;">
          <el-button type="primary" @click="$router.push('/honor')">{{ t('more') }}</el-button>
        </div>
      </div>
    </section>

    <!-- 新闻资讯 -->
    <section class="section news-section">
      <div class="container">
        <h2 class="section-title">
          {{ currentLang === 'en' ? '新闻资讯' : 'News Center' }}
        </h2>
        <p class="section-subtitle">
          {{ currentLang === 'en' ? 'News Center' : '新闻资讯' }}
        </p>
        <div 
          class="news-scroll-wrapper" 
          ref="newsScrollRef"
          @mouseenter="pauseAutoScroll"
          @mouseleave="resumeAutoScroll"
        >
          <div class="news-scroll-content">
            <!-- 第一组新闻 -->
            <div 
              class="news-item" 
              v-for="item in latestNews" 
              :key="`first-${item.id}`"
              @click="$router.push(`/news/${item.id}`)"
            >
              <el-card class="news-card" shadow="hover">
                <div class="news-image">
                  <img v-if="item.image" :src="getImageUrl(item.image)" :alt="item.title" />
                  <div v-else class="news-placeholder">新闻</div>
                </div>
                <div class="news-content">
                  <h3>{{ item.title }}</h3>
                  <p class="news-summary">{{ item.summary }}</p>
                  <div class="news-meta">
                    <span>{{ formatDate(item.publishTime) }}</span>
                  </div>
                </div>
              </el-card>
            </div>
            <!-- 第二组新闻（用于无缝循环） -->
            <div 
              class="news-item" 
              v-for="item in latestNews" 
              :key="`second-${item.id}`"
              @click="$router.push(`/news/${item.id}`)"
            >
              <el-card class="news-card" shadow="hover">
                <div class="news-image">
                  <img v-if="item.image" :src="getImageUrl(item.image)" :alt="item.title" />
                  <div v-else class="news-placeholder">新闻</div>
                </div>
                <div class="news-content">
                  <h3>{{ item.title }}</h3>
                  <p class="news-summary">{{ item.summary }}</p>
                  <div class="news-meta">
                    <span>{{ formatDate(item.publishTime) }}</span>
                  </div>
                </div>
              </el-card>
            </div>
          </div>
        </div>
        <div class="text-center" style="margin-top: 30px;">
          <el-button type="primary" @click="$router.push('/news')">{{ t('more') }} {{ t('news') }}</el-button>
        </div>
      </div>
    </section>

    <Footer />
  </div>
</template>

<script>
import { ref, computed, onMounted, onUnmounted, nextTick, watch } from 'vue'
import { ArrowLeft, ArrowRight, View } from '@element-plus/icons-vue'
import Header from '../components/Header.vue'
import Footer from '../components/Footer.vue'
import { getCompanyInfo } from '../api/company'
import { getAdvantages } from '../api/advantage'
import { getLaboratories } from '../api/laboratory'
import { getHonors } from '../api/honor'
import { getLatestNews } from '../api/news'
import { getHomeConfigMap, getBannerValues, getIcons } from '../api/homeConfig'
import { getAllCertificationCategories } from '../api/certification'
import { getImageUrl, processImageUrls } from '../utils/image'
import { initAllScrollAnimations } from '../utils/scrollAnimation'
import { useI18n } from '../utils/i18n'

export default {
  name: 'Home',
  components: {
    Header,
    Footer,
    ArrowLeft,
    ArrowRight,
    View
  },
  setup() {
    const { t, lang: currentLang } = useI18n()
    const companyInfo = ref(null)
    const advantages = ref([])
    const laboratories = ref([])
    const honors = ref([])
    const latestNews = ref([])
    const newsScrollRef = ref(null)
    const homeConfig = ref({})
    const bannerValues = ref([])
    const isPaused = ref(false)
    const icons = ref([])
    const testingIconsRef = ref(null)
    let testingIconsObserver = null
    
    // 过滤掉特定图片的图标列表
    const filteredIcons = computed(() => {
      return icons.value.filter(icon => {
        if (!icon || typeof icon !== 'string') return false
        // 排除不需要在首页图标行展示的图片
        if (icon.includes('v2-374cca7798b851b355142ce05a7fd301_1440w.png')) return false
        if (icon.includes('1767079908439-ckt-抠图.png')) return false
        if (icon.includes('1767080827115-ckt-抠图.png')) return false
        return true
      })
    })
    
    // 全球认证分类数据（只显示分类主类）
    const certifications = ref([])
    
    // 格式化认证分类数据（用于“全球认证”模块）
    // 这里显示的是“认证服务”下面的子分类（即有 parentId 的分类）
    const formatCertifications = (data) => {
      if (!data || !Array.isArray(data)) return []
      return data
        .filter(item => item.parentId && item.parentId !== 0) // 只要子分类
        .map(item => ({
          id: item.id,
          name: currentLang.value === 'en'
            ? (item.nameEn || item.nameCn || '')
            : (item.nameCn || item.nameEn || ''),
          icon: item.icon ? getImageUrl(item.icon) : '',
          iconLoaded: false,
          originalData: item
        }))
        .filter(item => item.name) // 过滤掉没有名称的项
        .sort((a, b) => (a.originalData.sortOrder || 0) - (b.originalData.sortOrder || 0))
    }
    
    // 检测项目数据
    const testingProjects = ref([
      { id: 1, name: currentLang.value === 'en' ? 'Medical Device' : '医疗器械', icon: '/testing/medical.png', iconLoaded: false },
      { id: 2, name: currentLang.value === 'en' ? 'Lighting' : '灯具', icon: '/testing/lighting.png', iconLoaded: false },
      { id: 3, name: currentLang.value === 'en' ? 'Home Appliance' : '家用电器', icon: '/testing/appliance.png', iconLoaded: false },
      { id: 4, name: currentLang.value === 'en' ? 'Audio & Video' : '视听产品', icon: '/testing/audio.png', iconLoaded: false },
      { id: 5, name: currentLang.value === 'en' ? 'Wireless Communication' : '无线通讯', icon: '/testing/wireless.png', iconLoaded: false },
      { id: 6, name: currentLang.value === 'en' ? 'Laser Product' : '激光产品', icon: '/testing/laser.png', iconLoaded: false },
      { id: 7, name: currentLang.value === 'en' ? 'Machinery' : '机械设备', icon: '/testing/machinery.png', iconLoaded: false },
      { id: 8, name: currentLang.value === 'en' ? 'Toy' : '玩具', icon: '/testing/toy.png', iconLoaded: false },
      { id: 9, name: currentLang.value === 'en' ? 'Industrial Equipment' : '工业设备', icon: '/testing/industrial.png', iconLoaded: false },
      { id: 10, name: currentLang.value === 'en' ? 'Other' : '其他', icon: '/testing/other.png', iconLoaded: false }
    ])
    
    // 监听语言变化，更新认证分类名称和检测项目名称
    watch(currentLang, (newLang) => {
      // 更新全球认证分类名称
      certifications.value = certifications.value.map(item => ({
        ...item,
        name: newLang === 'en'
          ? (item.originalData.nameEn || item.originalData.nameCn || '')
          : (item.originalData.nameCn || item.originalData.nameEn || '')
      }))
      
      // 更新检测项目名称（仍使用前端写死的配置）
      testingProjects.value = [
        { id: 1, name: newLang === 'en' ? 'Medical Device' : '医疗器械', icon: '/testing/medical.png', iconLoaded: false },
        { id: 2, name: newLang === 'en' ? 'Lighting' : '灯具', icon: '/testing/lighting.png', iconLoaded: false },
        { id: 3, name: newLang === 'en' ? 'Home Appliance' : '家用电器', icon: '/testing/appliance.png', iconLoaded: false },
        { id: 4, name: newLang === 'en' ? 'Audio & Video' : '视听产品', icon: '/testing/audio.png', iconLoaded: false },
        { id: 5, name: newLang === 'en' ? 'Wireless Communication' : '无线通讯', icon: '/testing/wireless.png', iconLoaded: false },
        { id: 6, name: newLang === 'en' ? 'Laser Product' : '激光产品', icon: '/testing/laser.png', iconLoaded: false },
        { id: 7, name: newLang === 'en' ? 'Machinery' : '机械设备', icon: '/testing/machinery.png', iconLoaded: false },
        { id: 8, name: newLang === 'en' ? 'Toy' : '玩具', icon: '/testing/toy.png', iconLoaded: false },
        { id: 9, name: newLang === 'en' ? 'Industrial Equipment' : '工业设备', icon: '/testing/industrial.png', iconLoaded: false },
        { id: 10, name: newLang === 'en' ? 'Other' : '其他', icon: '/testing/other.png', iconLoaded: false }
      ]
    })

    onMounted(async () => {
      try {
        const [companyRes, advantageRes, labRes, honorRes, newsRes, configRes, valuesRes, iconsRes, categoriesRes] = await Promise.all([
          getCompanyInfo(),
          getAdvantages(),
          getLaboratories(),
          getHonors(),
          getLatestNews(6),
          getHomeConfigMap('cn'),
          getBannerValues(),
          getIcons(),
          getAllCertificationCategories()
        ])

        if (companyRes.code === 200) {
          companyInfo.value = processImageUrls(companyRes.data, ['logo'])
        }
        if (advantageRes.code === 200) {
          advantages.value = processImageUrls(advantageRes.data, ['icon'])
        }
        if (labRes.code === 200) {
          laboratories.value = processImageUrls(labRes.data, ['image'])
        }
        if (honorRes.code === 200) {
          honors.value = processImageUrls(honorRes.data, ['image'])
        }
        if (newsRes.code === 200) {
          latestNews.value = processImageUrls(newsRes.data, ['image'])
        }
        if (configRes.code === 200) {
          homeConfig.value = configRes.data || {}
        }
        if (valuesRes.code === 200) {
          bannerValues.value = valuesRes.data || []
        }
        if (iconsRes.code === 200) {
          icons.value = iconsRes.data || []
        }
        // 使用后台所有分类中的子分类（认证服务下面的分类）作为“全球认证”模块数据
        if (categoriesRes.code === 200) {
          const categoryData = categoriesRes.data || []
          certifications.value = formatCertifications(categoryData)
        }

        // 等待DOM完全渲染后初始化滚动动画
        await nextTick()
        setTimeout(() => {
          initAllScrollAnimations()
        }, 100)

        // 初始化“检测项目”图标滚动进入视口时的弹出动画
        if (typeof window !== 'undefined' && 'IntersectionObserver' in window && testingIconsRef.value) {
          testingIconsObserver = new IntersectionObserver(
            (entries) => {
              entries.forEach((entry) => {
                if (entry.isIntersecting) {
                  // 进入视口时添加动画类，并且只执行一次
                  entry.target.classList.add('ipro-animate-once')
                  if (testingIconsObserver) {
                    testingIconsObserver.unobserve(entry.target)
                  }
                }
              })
            },
            {
              threshold: 0.2
            }
          )
          testingIconsObserver.observe(testingIconsRef.value)
        }

      } catch (error) {
        console.error('加载数据失败:', error)
      }
    })

    onUnmounted(() => {
      if (testingIconsObserver) {
        testingIconsObserver.disconnect()
        testingIconsObserver = null
      }
    })

    // 暂停自动滚动（鼠标悬停时）
    const pauseAutoScroll = () => {
      if (!newsScrollRef.value) return
      const scrollContainer = newsScrollRef.value.querySelector('.news-scroll-content')
      if (scrollContainer) {
        scrollContainer.style.animationPlayState = 'paused'
      }
    }

    // 恢复自动滚动（鼠标离开时）
    const resumeAutoScroll = () => {
      if (!newsScrollRef.value) return
      const scrollContainer = newsScrollRef.value.querySelector('.news-scroll-content')
      if (scrollContainer) {
        scrollContainer.style.animationPlayState = 'running'
      }
    }

    const formatDate = (dateStr) => {
      if (!dateStr) return ''
      const date = new Date(dateStr)
      return date.toLocaleDateString('zh-CN')
    }

    const formatDescription = (description) => {
      if (!description) return ''
      // 按换行分割并去重
      const lines = description.split('\n').map(line => line.trim()).filter(line => line)
      // 去除连续重复的行
      const uniqueLines = []
      for (let i = 0; i < lines.length; i++) {
        if (i === 0 || lines[i] !== lines[i - 1]) {
          uniqueLines.push(lines[i])
        }
      }
      return uniqueLines.join('\n')
    }

    // 处理图片加载错误
    const handleImageError = (event) => {
      // 图片加载失败时，隐藏图片
      event.target.style.display = 'none'
      // 显示占位符
      const placeholder = event.target.nextElementSibling
      if (placeholder && placeholder.classList.contains('icon-placeholder')) {
        placeholder.style.display = 'flex'
      }
    }
    
    // 处理图片加载成功
    const handleImageLoad = (event, item) => {
      // 图片加载成功时，隐藏占位符
      const placeholder = event.target.nextElementSibling
      if (placeholder && placeholder.classList.contains('icon-placeholder')) {
        placeholder.style.display = 'none'
      }
    }
    
    // 处理图标加载错误
    const handleIconError = (event) => {
      // 图标加载失败时，隐藏图标
      event.target.style.display = 'none'
    }

    return {
      companyInfo,
      advantages,
      laboratories,
      honors,
      latestNews,
      homeConfig,
      bannerValues,
      certifications,
      testingProjects,
      formatDate,
      formatDescription,
      t,
      currentLang,
      getImageUrl,
      handleImageError,
      handleImageLoad,
      handleIconError,
      filteredIcons,
      newsScrollRef,
      pauseAutoScroll,
      resumeAutoScroll,
      testingIconsRef
    }
  }
}
</script>

<style scoped>
.home {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

.home > :last-child {
  margin-top: auto;
}

.banner {
  position: relative;
  color: #fff;
  padding: 140px 20px;
  text-align: center;
  overflow: hidden;
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
}

.banner-background {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 0;
}

.banner-gradient {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: var(--gradient-primary);
  background-size: 200% 200%;
  animation: gradient 15s ease infinite;
}

.banner-particles {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
}

.particle {
  position: absolute;
  width: 4px;
  height: 4px;
  background: rgba(255, 255, 255, 0.5);
  border-radius: 50%;
  animation: float 6s ease-in-out infinite;
}

.banner-content {
  position: relative;
  z-index: 1;
  max-width: 1200px;
  margin: 0 auto;
  width: 100%;
}

.banner-text {
  margin-bottom: 60px;
}

.banner-actions {
  margin-top: 50px;
}

.banner-title {
  font-size: 48px;
  font-weight: 800;
  margin-bottom: 24px;
  line-height: 1.2;
  text-shadow: 0 2px 20px rgba(0, 0, 0, 0.2);
  letter-spacing: -0.5px;
}

.banner-subtitle {
  font-size: 20px;
  margin-bottom: 15px;
  opacity: 0.9;
}

.banner-desc {
  font-size: 18px;
  margin-bottom: 30px;
}

.banner-values {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
  gap: 25px;
  margin: 50px auto 30px;
  max-width: 1100px;
  margin-left: auto;
  margin-right: auto;
}

.value-item {
  background: rgba(255, 255, 255, 0.12);
  padding: 32px 24px;
  border-radius: 20px;
  backdrop-filter: blur(20px);
  -webkit-backdrop-filter: blur(20px);
  border: 1px solid rgba(255, 255, 255, 0.25);
  transition: all 0.4s cubic-bezier(0.4, 0, 0.2, 1);
  position: relative;
  overflow: hidden;
  opacity: 0;
  animation: slideUp 0.6s ease-out forwards;
}

.value-icon {
  margin-bottom: 16px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.icon-circle {
  width: 60px;
  height: 60px;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.2);
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;
}

.icon-circle::before {
  content: '';
  position: absolute;
  width: 40px;
  height: 40px;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.3);
  animation: pulse 2s ease-in-out infinite;
}

.value-item::before {
  content: '';
  position: absolute;
  top: 0;
  left: -100%;
  width: 100%;
  height: 100%;
  background: linear-gradient(90deg, transparent, rgba(255, 255, 255, 0.1), transparent);
  transition: left 0.5s;
}

.value-item:hover::before {
  left: 100%;
}

.value-item:hover {
  transform: translateY(-10px) scale(1.02);
  background: rgba(255, 255, 255, 0.25);
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.3);
  border-color: rgba(255, 255, 255, 0.4);
}

.value-item h3 {
  font-size: 24px;
  margin-bottom: 12px;
  color: #fff;
  font-weight: 700;
  position: relative;
  z-index: 1;
  letter-spacing: 0.5px;
}

.value-item p {
  font-size: 15px;
  line-height: 1.8;
  color: rgba(255, 255, 255, 0.9);
  position: relative;
  z-index: 1;
}

.btn-primary-glow {
  position: relative;
  overflow: hidden;
  padding: 16px 40px;
  font-size: 18px;
  font-weight: 600;
  border-radius: 50px;
  box-shadow: 0 8px 24px rgba(102, 126, 234, 0.4);
  transition: all 0.3s ease;
}

.btn-primary-glow:hover {
  transform: translateY(-2px);
  box-shadow: 0 12px 32px rgba(102, 126, 234, 0.5);
}

.btn-primary-glow::before {
  content: '';
  position: absolute;
  top: 50%;
  left: 50%;
  width: 0;
  height: 0;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.3);
  transform: translate(-50%, -50%);
  transition: width 0.6s, height 0.6s;
}

.btn-primary-glow:hover::before {
  width: 300px;
  height: 300px;
}

/* 移动端 Banner 适配 */
@media (max-width: 768px) {
  .banner {
    padding: 60px 15px;
  }

  .banner-title {
    font-size: 28px;
    margin-bottom: 15px;
  }

  .banner-subtitle {
    font-size: 16px;
    margin-bottom: 12px;
  }

  .banner-desc {
    font-size: 14px;
    margin-bottom: 20px;
  }
}

@media (max-width: 480px) {
  .banner-title {
    font-size: 24px;
  }

  .banner-subtitle {
    font-size: 14px;
  }

  .banner-desc {
    font-size: 13px;
  }
}

.section {
  padding: 80px 0;
}

/* 移动端 Section 适配 */
@media (max-width: 768px) {
  .section {
    padding: 40px 0;
  }

  .section-title {
    font-size: 24px;
  }

  .section-subtitle {
    font-size: 14px;
  }
}

.icon-showcase {
  background: #fff;
  padding: 40px 0;
  overflow: hidden;
}

.icon-scroll-wrapper {
  position: relative;
  width: 100%;
  overflow: hidden;
  mask-image: linear-gradient(to right, transparent, black 10%, black 90%, transparent);
  -webkit-mask-image: linear-gradient(to right, transparent, black 10%, black 90%, transparent);
}

.icon-row {
  display: flex;
  align-items: center;
  gap: 40px;
  padding: 20px 0;
  white-space: nowrap;
  width: fit-content;
  animation: scrollIcons 30s linear infinite;
}

.icon-item {
  flex: 0 0 auto;
  display: flex;
  justify-content: center;
  align-items: center;
  transition: transform 0.3s ease;
  animation: floatIcon 3s ease-in-out infinite;
}

.icon-item:nth-child(odd) {
  animation-delay: 0s;
}

.icon-item:nth-child(even) {
  animation-delay: 1.5s;
}

.icon-item:hover {
  transform: scale(1.15);
  animation-play-state: paused;
}

.icon-item img {
  max-width: 120px;
  max-height: 80px;
  width: auto;
  height: auto;
  object-fit: contain;
  filter: grayscale(0%);
  transition: all 0.3s ease;
}

.icon-item:hover img {
  filter: grayscale(0%);
  transform: scale(1.1);
}

@keyframes scrollIcons {
  0% {
    transform: translateX(0);
  }
  100% {
    transform: translateX(-50%);
  }
}

@keyframes floatIcon {
  0%, 100% {
    transform: translateY(0);
  }
  50% {
    transform: translateY(-10px);
  }
}

@media (max-width: 768px) {
  .icon-scroll-wrapper {
    mask-image: linear-gradient(to right, transparent, black 5%, black 95%, transparent);
    -webkit-mask-image: linear-gradient(to right, transparent, black 5%, black 95%, transparent);
  }

  .icon-row {
    gap: 25px;
    animation-duration: 25s;
  }
  
  .icon-item img {
    max-width: 80px;
    max-height: 60px;
  }
}

.company-intro {
  position: relative;
  background: #f8f9fa;
  overflow: hidden;
}

.company-intro-bg {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  opacity: 0.5;
  z-index: 0;
  overflow: hidden;
}

.company-intro-bg::before {
  content: '';
  position: absolute;
  top: 50%;
  left: 50%;
  width: 100vh;
  height: 100vw;
  background-image: url('/company-intro-bg.png');
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  transform: translate(-50%, -50%) rotate(270deg);
  transform-origin: center center;
}

.company-intro-bg::after {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: linear-gradient(135deg, rgba(102, 126, 234, 0.1) 0%, rgba(118, 75, 162, 0.1) 100%);
}

.intro-wrapper {
  position: relative;
  z-index: 1;
}

.company-intro .section-title {
  color: #000;
  font-weight: 800;
  text-shadow: 0 2px 4px rgba(255, 255, 255, 0.9), 0 0 10px rgba(255, 255, 255, 0.5);
  background: rgba(255, 255, 255, 0.7);
  backdrop-filter: blur(10px);
  -webkit-backdrop-filter: blur(10px);
  padding: 15px 30px;
  border-radius: 15px;
  display: inline-block;
  margin: 0 auto 20px;
  display: inline-flex;
  gap: 12px;
  align-items: baseline;
}

.company-profile-inline {
  font-size: 18px;
  font-weight: 700;
  color: #000;
  opacity: 0.9;
}

/* 公司简介动画由 scrollAnimation.js 处理 */

.intro-content {
  text-align: center;
  max-width: 900px;
  margin: 0 auto 0 0;
  background: rgba(255, 255, 255, 0.7);
  backdrop-filter: blur(20px);
  -webkit-backdrop-filter: blur(20px);
  padding: 40px;
  padding-right: 100px;
  border-radius: 20px 0 0 20px;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
  border: 1px solid rgba(255, 255, 255, 0.3);
  border-right: none;
  position: relative;
}

.intro-content::after {
  content: '';
  position: absolute;
  right: -100vw;
  top: 0;
  bottom: 0;
  width: 100vw;
  background: rgba(255, 255, 255, 0.7);
  backdrop-filter: blur(20px);
  -webkit-backdrop-filter: blur(20px);
  z-index: -1;
  border-radius: 0;
}

.intro-text {
  font-size: 16px;
  line-height: 1.8;
  color: #333;
  margin-bottom: 30px;
  text-align: left;
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
  overflow: hidden;
  text-overflow: ellipsis;
  font-weight: 500;
  text-shadow: 0 1px 2px rgba(255, 255, 255, 0.8);
}

.advantage-intro {
  max-width: 900px;
  margin: 0 auto 30px;
}

.advantage-intro p {
  font-size: 16px;
  line-height: 1.8;
  color: #666;
  text-align: left;
}

.advantages {
  background: #fff;
}

.advantage-list {
  display: flex;
  flex-wrap: wrap;
}

.advantage-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 40px 24px;
  background: #fff;
  border-radius: 24px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
  transition: all 0.4s cubic-bezier(0.4, 0, 0.2, 1);
  position: relative;
  overflow: hidden;
  border: 1px solid rgba(102, 126, 234, 0.1);
}

.advantage-item::before {
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

.advantage-item:hover::before {
  transform: scaleX(1);
}

.advantage-item:hover {
  transform: translateY(-12px);
  box-shadow: 0 20px 40px rgba(102, 126, 234, 0.2);
  border-color: rgba(102, 126, 234, 0.3);
}

.advantage-image {
  position: relative;
  margin-bottom: 24px;
  width: 120px;
  height: 120px;
}

.image-wrapper {
  position: relative;
  width: 100%;
  height: 100%;
  border-radius: 20px;
  overflow: hidden;
  background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
}

.image-wrapper img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.4s ease;
}

.advantage-item:hover .image-wrapper img {
  transform: scale(1.1);
}

.image-overlay {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: linear-gradient(135deg, rgba(102, 126, 234, 0.1) 0%, rgba(118, 75, 162, 0.1) 100%);
  opacity: 0;
  transition: opacity 0.4s ease;
}

.advantage-item:hover .image-overlay {
  opacity: 1;
}

.advantage-number {
  position: absolute;
  top: -10px;
  right: -10px;
  width: 40px;
  height: 40px;
  background: var(--gradient-primary);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
  font-weight: 700;
  font-size: 14px;
  box-shadow: 0 4px 12px rgba(102, 126, 234, 0.4);
}

.advantage-content {
  text-align: center;
}

.advantage-content h3 {
  font-size: 20px;
  font-weight: 700;
  margin-bottom: 12px;
  color: var(--text-primary);
  letter-spacing: 0.3px;
}

.advantage-description {
  font-size: 14px;
  line-height: 1.7;
  color: var(--text-secondary);
  white-space: pre-line;
}

/* 移动端适配 */
@media (max-width: 768px) {
  .advantage-item {
    padding: 20px 15px;
  }

  .advantage-image {
    width: 80px;
    height: 80px;
    margin-bottom: 15px;
  }

  .advantage-content h3 {
    font-size: 18px;
    margin-bottom: 10px;
  }

  .advantage-description {
    font-size: 13px;
  }

  .certification-section,
  .testing-section {
    padding: 40px 0;
  }

  .certification-icon {
    height: 60px;
    margin-bottom: 15px;
  }

  .certification-icon img {
    max-height: 60px;
  }

  .certification-card h3 {
    font-size: 14px;
  }

  .ipro-list ul {
    gap: 16px;
  }

  .ipro-list li {
    flex: 0 0 calc(33.333% - 16px);
    min-width: 100px;
  }

  .icon-pro {
    width: 52px;
    height: 52px;
    font-size: 20px;
    margin-bottom: 6px;
  }

  .ipro-list span {
    font-size: 12px;
  }

  .cert-row {
    gap: 18px;
    /* 移动端也放慢一点，但比桌面端略快 */
    animation-duration: 35s;
  }

  .cert-item {
    min-width: 200px;
  }
}

.certification-section,
.testing-section {
  padding: 80px 0;
  background: #fff;
}

/* 检测项目区域毛玻璃卡片背景 */
.testing-section .container {
  position: relative;
}

.testing-glass {
  position: relative;
  margin-top: 30px;
  padding: 28px 26px 32px;
  border-radius: 22px;
  background: rgba(255, 255, 255, 0.16);
  border: 1px solid rgba(255, 255, 255, 0.35);
  backdrop-filter: blur(26px);
  -webkit-backdrop-filter: blur(26px);
  box-shadow: 0 18px 45px rgba(15, 23, 42, 0.25);
}

.certification-section {
  background: #f8f9fa;
}

.cert-scroll-wrapper {
  position: relative;
  width: 100%;
  overflow: hidden;
  margin-top: 30px;
}

.cert-row {
  display: flex;
  align-items: stretch;
  gap: 24px;
  padding: 10px 0;
  white-space: nowrap;
  width: fit-content;
  /* 调整动画时长控制滑动速度；数值越大越慢 */
  animation: scrollCerts 45s linear infinite;
}

.cert-item {
  flex: 0 0 auto;
  min-width: 220px;
}

@keyframes scrollCerts {
  0% {
    transform: translateX(0);
  }
  100% {
    transform: translateX(-50%);
  }
}

.certification-card {
  text-align: center;
  padding: 30px 20px;
  height: 100%;
  border-radius: 15px;
  border: 1px solid rgba(102, 126, 234, 0.1);
  transition: all 0.4s cubic-bezier(0.4, 0, 0.2, 1);
  position: relative;
  overflow: hidden;
  background: #fff;
}

.certification-card::before {
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

.certification-card:hover::before {
  transform: scaleX(1);
}

.certification-card:hover {
  transform: translateY(-8px) scale(1.02);
  box-shadow: 0 15px 30px rgba(102, 126, 234, 0.2);
  border-color: rgba(102, 126, 234, 0.3);
}

.certification-icon {
  margin-bottom: 20px;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 80px;
  position: relative;
}

.certification-icon img {
  max-width: 100%;
  max-height: 80px;
  object-fit: contain;
  transition: transform 0.4s ease;
  position: relative;
  z-index: 1;
}

.icon-placeholder {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 60px;
  height: 60px;
  border-radius: 12px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 0;
}

.icon-text {
  font-size: 24px;
  font-weight: 700;
  color: #fff;
  text-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
}

.certification-card:hover .certification-icon img {
  transform: scale(1.1);
}

.certification-card h3 {
  font-size: 16px;
  font-weight: 600;
  color: var(--text-primary);
  letter-spacing: 0.3px;
  margin: 0;
  line-height: 1.4;
}

/* 检测项目图标列表，参考 CTL 首页样式：https://www.ctl-lab.com/index.html */
.ipro-list {
  margin-top: 40px;
}

.ipro-list ul {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  gap: 24px;
  padding: 0;
  margin: 0;
  list-style: none;
}

.ipro-list li {
  flex: 0 0 calc(20% - 24px);
  min-width: 160px;
  text-align: center;
}

.ipro-list a {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  text-decoration: none;
  color: #333;
  transition: all 0.3s ease;
}

.ipro-list a:hover {
  color: #409eff;
}

/* 滑动到视口时整块图标区域弹出动画（只执行一次） */
@keyframes iproBounceIn {
  0% {
    opacity: 0;
    transform: translateY(40px) scale(0.9);
  }
  100% {
    opacity: 1;
    transform: translateY(0) scale(1);
  }
}

.ipro-animate-once {
  animation: iproBounceIn 0.6s ease-out forwards;
}

/* 彩球发光效果 */
@keyframes iconGlow {
  0% {
    box-shadow: 0 0 10px rgba(255, 255, 255, 0.25);
  }
  100% {
    box-shadow: 0 0 24px rgba(255, 255, 255, 0.6);
  }
}

.icon-pro {
  width: 64px;
  height: 64px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 10px;
  color: #fff;
  overflow: hidden;
  background: transparent;
}

.icon-pro img {
  width: 100%;
  height: 100%;
  object-fit: contain;
  display: block;
}

.ipro-list a:hover .icon-pro {
  transform: translateY(-4px);
  box-shadow: 0 0 28px rgba(255, 255, 255, 0.8);
}

.ipro-list span {
  font-size: 14px;
}

/* 不同检测项目的配色，可根据需要微调 */
.icon-ylqx {
  background: linear-gradient(135deg, #ff7e5f, #feb47b);
}

.icon-dj {
  background: linear-gradient(135deg, #43cea2, #185a9d);
}

.icon-jydq {
  background: linear-gradient(135deg, #667eea, #764ba2);
}

.icon-stcp {
  background: linear-gradient(135deg, #f7971e, #ffd200);
}

.icon-wxtx {
  background: linear-gradient(135deg, #36d1dc, #5b86e5);
}

.icon-jgcp {
  background: linear-gradient(135deg, #ff512f, #dd2476);
}

.icon-jxsb {
  background: linear-gradient(135deg, #1d976c, #93f9b9);
}

.icon-wj {
  background: linear-gradient(135deg, #c471ed, #f64f59);
}

.icon-gysb {
  background: linear-gradient(135deg, #0ba360, #3cba92);
}

.icon-qt {
  background: linear-gradient(135deg, #3a1c71, #d76d77);
}

.laboratories {
  background: #f8f9fa;
}

.lab-card {
  text-align: center;
  padding: 0;
  background: #fff;
  border-radius: 24px;
  transition: all 0.4s cubic-bezier(0.4, 0, 0.2, 1);
  height: 100%;
  box-sizing: border-box;
  overflow: hidden;
  border: 1px solid rgba(0, 0, 0, 0.06);
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
  cursor: pointer;
}

.lab-card:hover {
  transform: translateY(-12px) scale(1.02);
  box-shadow: 0 24px 48px rgba(102, 126, 234, 0.2);
  border-color: rgba(102, 126, 234, 0.3);
}

.lab-image {
  position: relative;
  width: 100%;
  height: 240px;
  margin-bottom: 0;
  overflow: hidden;
  background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
}

.image-mask {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: linear-gradient(to bottom, transparent 0%, rgba(0, 0, 0, 0.3) 100%);
  z-index: 1;
  opacity: 0;
  transition: opacity 0.4s ease;
}

.lab-card:hover .image-mask {
  opacity: 1;
}

.lab-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.6s cubic-bezier(0.4, 0, 0.2, 1);
}

.lab-card:hover .lab-image img {
  transform: scale(1.15);
}

.lab-overlay {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 60px;
  height: 60px;
  background: rgba(255, 255, 255, 0.95);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  opacity: 0;
  transition: all 0.4s ease;
  z-index: 2;
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.2);
}

.lab-card:hover .lab-overlay {
  opacity: 1;
  transform: translate(-50%, -50%) scale(1.1);
}

.overlay-icon {
  font-size: 24px;
  color: var(--primary-color);
}

.lab-content {
  padding: 24px 20px;
}

.lab-content h3 {
  font-size: 20px;
  font-weight: 700;
  margin-bottom: 8px;
  color: var(--text-primary);
  letter-spacing: 0.3px;
}

.lab-content p {
  font-size: 14px;
  color: var(--text-secondary);
  line-height: 1.6;
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

.honors {
  background: #fff;
}

.honor-card {
  text-align: center;
  padding: 15px;
}

.honor-card img {
  width: 100%;
  height: 150px;
  object-fit: cover;
  margin-bottom: 10px;
  border-radius: 4px;
}

.news-section {
  background: #f8f9fa;
}

.news-scroll-wrapper {
  position: relative;
  overflow: hidden;
  margin-top: 40px;
  width: 100%;
  mask-image: linear-gradient(to right, transparent, black 5%, black 95%, transparent);
  -webkit-mask-image: linear-gradient(to right, transparent, black 5%, black 95%, transparent);
}

.news-scroll-content {
  display: flex;
  gap: 20px;
  padding: 10px 0;
  animation: scrollNews 30s linear infinite;
  will-change: transform;
}

.news-scroll-content:hover {
  animation-play-state: paused;
}

@keyframes scrollNews {
  0% {
    transform: translateX(0);
  }
  100% {
    transform: translateX(-50%);
  }
}

.news-item {
  flex: 0 0 320px;
  min-width: 320px;
}

.news-card {
  cursor: pointer;
  transition: transform 0.3s;
  height: 100%;
}

.news-card:hover {
  transform: translateY(-5px);
}

/* 移动端适配 */
@media (max-width: 768px) {
  .news-item {
    flex: 0 0 280px;
    min-width: 280px;
  }
  
  .news-scroll-content {
    animation-duration: 25s;
  }
}

@media (max-width: 480px) {
  .news-item {
    flex: 0 0 260px;
    min-width: 260px;
  }
  
  .news-scroll-content {
    animation-duration: 20s;
  }
}

.news-image {
  height: 200px;
  overflow: hidden;
  margin-bottom: 15px;
  border-radius: 4px;
}

.news-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.news-placeholder {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #e4e7ed;
  color: #909399;
  font-size: 18px;
}

.news-content h3 {
  font-size: 18px;
  margin-bottom: 10px;
  color: #2c3e50;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
}

.news-summary {
  color: #666;
  font-size: 14px;
  line-height: 1.6;
  margin-bottom: 10px;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
}

.news-meta {
  color: #909399;
  font-size: 12px;
}

.text-center {
  text-align: center;
}
</style>

