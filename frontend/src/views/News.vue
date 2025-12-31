<template>
  <div class="news">
    <Header />
    
    <div class="page-banner slide-in-top">
      <div class="banner-content">
        <h1 class="fade-in-immediate">新闻资讯</h1>
        <p class="fade-in-immediate" style="animation-delay: 0.2s;">News Center</p>
      </div>
    </div>

    <div class="container" style="padding: 0px 20px 60px 20px;">
      <el-row :gutter="30" class="news-list">
        <el-col :xs="24" :sm="12" :md="8" v-for="item in newsList" :key="item.id">
          <el-card class="news-card scale-in hover-lift" shadow="hover" @click="$router.push(`/news/${item.id}`)">
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
        </el-col>
      </el-row>

      <el-pagination
        v-model:current-page="currentPage"
        v-model:page-size="pageSize"
        :total="total"
        :pager-count="pagerCount"
        :layout="paginationLayout"
        @current-change="handlePageChange"
        class="news-pagination"
      />
    </div>

    <Footer />
  </div>
</template>

<script>
import { ref, computed, onMounted, onUnmounted, nextTick } from 'vue'
import { initAllScrollAnimations } from '../utils/scrollAnimation'
import Header from '../components/Header.vue'
import Footer from '../components/Footer.vue'
import { getNewsPage } from '../api/news'
import { getImageUrl, processImageUrls } from '../utils/image'
import { useI18n } from '../utils/i18n'

export default {
  name: 'News',
  components: {
    Header,
    Footer
  },
  setup() {
    const { t } = useI18n()
    const newsList = ref([])
    const currentPage = ref(1)
    const pageSize = ref(9)
    const total = ref(0)
    
    // 响应式分页配置
    const isMobile = ref(false)
    
    const checkMobile = () => {
      if (typeof window !== 'undefined') {
        isMobile.value = window.innerWidth <= 768
      }
    }
    
    // 分页器显示的页码数量：移动端4个，桌面端7个（默认）
    const pagerCount = computed(() => {
      return isMobile.value ? 4 : 7
    })
    
    // 分页布局：移动端简化，桌面端完整
    const paginationLayout = computed(() => {
      return isMobile.value ? 'prev, pager, next' : 'prev, pager, next, jumper'
    })

    const loadNews = async () => {
      try {
        const res = await getNewsPage(currentPage.value, pageSize.value)
        if (res.code === 200) {
          newsList.value = processImageUrls(res.data.records, ['image'])
          total.value = res.data.total
        }
      } catch (error) {
        console.error('加载数据失败:', error)
      }
    }

    onMounted(async () => {
      checkMobile()
      window.addEventListener('resize', checkMobile)
      
      await loadNews()
      // 初始化滚动动画
      await nextTick()
      setTimeout(() => {
        initAllScrollAnimations()
      }, 100)
    })
    
    onUnmounted(() => {
      if (typeof window !== 'undefined') {
        window.removeEventListener('resize', checkMobile)
      }
    })

    const handlePageChange = () => {
      loadNews()
      window.scrollTo({ top: 0, behavior: 'smooth' })
    }

    const formatDate = (dateStr) => {
      if (!dateStr) return ''
      const date = new Date(dateStr)
      return date.toLocaleDateString('zh-CN')
    }

    return {
      newsList,
      currentPage,
      pageSize,
      total,
      pagerCount,
      paginationLayout,
      handlePageChange,
      formatDate,
      getImageUrl,
      t
    }
  }
}
</script>

<style scoped>
.news {
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

.news-card {
  margin-bottom: 30px;
  cursor: pointer;
  border-radius: 20px;
  overflow: hidden;
  border: 1px solid rgba(0, 0, 0, 0.06);
  transition: all 0.4s cubic-bezier(0.4, 0, 0.2, 1);
}

.news-card:hover {
  transform: translateY(-12px) scale(1.02);
  box-shadow: 0 20px 40px rgba(102, 126, 234, 0.2);
  border-color: rgba(102, 126, 234, 0.3);
}

.news-image {
  height: 220px;
  overflow: hidden;
  margin: -1px -1px 0 -1px;
  position: relative;
  background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
}

.news-image::after {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: linear-gradient(to bottom, transparent 0%, rgba(0, 0, 0, 0.3) 100%);
  opacity: 0;
  transition: opacity 0.4s ease;
}

.news-card:hover .news-image::after {
  opacity: 1;
}

.news-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.6s cubic-bezier(0.4, 0, 0.2, 1);
}

.news-card:hover .news-image img {
  transform: scale(1.15);
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

.news-content {
  padding: 20px;
}

.news-content h3 {
  font-size: 20px;
  font-weight: 700;
  margin-bottom: 12px;
  color: var(--text-primary);
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  line-height: 1.5;
  letter-spacing: 0.3px;
  transition: color 0.3s ease;
}

.news-card:hover .news-content h3 {
  color: var(--primary-color);
}

.news-summary {
  color: var(--text-secondary);
  font-size: 14px;
  line-height: 1.7;
  margin-bottom: 15px;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
}

.news-meta {
  color: var(--text-tertiary);
  font-size: 13px;
  display: flex;
  align-items: center;
  gap: 15px;
  padding-top: 15px;
  border-top: 1px solid var(--border-color);
}

/* 分页组件基础样式 */
.news-pagination {
  margin-top: 40px;
  display: flex;
  justify-content: center;
  width: 100%;
  padding: 0 20px;
  box-sizing: border-box;
}

.news-pagination :deep(.el-pagination) {
  justify-content: center;
  flex-wrap: wrap;
}

.news-pagination :deep(.el-pagination .el-pager),
.news-pagination :deep(.el-pagination .btn-prev),
.news-pagination :deep(.el-pagination .btn-next),
.news-pagination :deep(.el-pagination .el-pagination__jump) {
  margin: 0 5px;
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

  .news-image {
    height: 150px;
  }

  .news-content h3 {
    font-size: 18px;
  }

  /* 分页组件移动端样式 */
  .news-pagination {
    margin-top: 40px;
    width: 100%;
    overflow: hidden;
    padding: 0 10px;
    box-sizing: border-box;
  }

  .news-pagination :deep(.el-pagination) {
    justify-content: center;
    flex-wrap: wrap;
    width: 100%;
  }

  .news-pagination :deep(.el-pagination .el-pager),
  .news-pagination :deep(.el-pagination .btn-prev),
  .news-pagination :deep(.el-pagination .btn-next),
  .news-pagination :deep(.el-pagination .el-pagination__jump) {
    margin: 5px 2px;
    flex-shrink: 0;
  }

  .news-pagination :deep(.el-pagination .el-pager li) {
    min-width: 32px;
    height: 32px;
    line-height: 32px;
    font-size: 13px;
  }

  .news-pagination :deep(.el-pagination .btn-prev),
  .news-pagination :deep(.el-pagination .btn-next) {
    min-width: 32px;
    height: 32px;
    padding: 0 8px;
  }
}
</style>

