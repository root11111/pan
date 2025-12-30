<template>
  <div class="news-detail">
    <Header />
    
    <div class="container" style="padding: 0px 20px 60px 20px;">
      <el-card v-if="news">
        <h1 class="news-title">{{ news.title }}</h1>
        <div class="news-meta">
          <span>{{ t('publishTime') }}：{{ formatDate(news.publishTime) }}</span>
          <span style="margin-left: 20px;">{{ t('browse') }}：{{ news.viewCount || 0 }}</span>
        </div>
        <div class="news-image" v-if="news.image">
          <img :src="getImageUrl(news.image)" :alt="news.title" />
        </div>
        <div class="news-content" v-html="formatContent(news.content)"></div>
      </el-card>
      
      <div class="back-button" style="margin-top: 30px;">
        <el-button @click="$router.push('/news')">{{ t('backToNewsList') }}</el-button>
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
import { getNewsById } from '../api/news'
import { getImageUrl, processImageUrls } from '../utils/image'
import { useI18n } from '../utils/i18n'

export default {
  name: 'NewsDetail',
  components: {
    Header,
    Footer
  },
  setup() {
    const { t } = useI18n()
    const route = useRoute()
    const news = ref(null)

    onMounted(async () => {
      try {
        const res = await getNewsById(route.params.id)
        if (res.code === 200) {
          news.value = processImageUrls(res.data, ['image'])
        }
      } catch (error) {
        console.error('加载数据失败:', error)
      }
    })

    const formatDate = (dateStr) => {
      if (!dateStr) return ''
      const date = new Date(dateStr)
      return date.toLocaleString('zh-CN')
    }

    const formatContent = (content) => {
      if (!content) return ''
      return content.replace(/\n/g, '<br>')
    }

    return {
      news,
      formatDate,
      formatContent,
      getImageUrl,
      t
    }
  }
}
</script>

<style scoped>
.news-detail {
  min-height: 100vh;
}

.news-title {
  font-size: 32px;
  font-weight: 600;
  margin-bottom: 20px;
  color: #2c3e50;
}

.news-meta {
  color: #909399;
  font-size: 14px;
  margin-bottom: 30px;
  padding-bottom: 20px;
  border-bottom: 1px solid #e4e7ed;
}

.news-image {
  margin-bottom: 30px;
}

.news-image img {
  width: 100%;
  max-height: 500px;
  object-fit: cover;
  border-radius: 4px;
}

.news-content {
  font-size: 16px;
  line-height: 1.8;
  color: #333;
  padding: 20px 0;
}

.back-button {
  text-align: center;
}

/* 移动端适配 */
@media (max-width: 768px) {
  .news-title {
    font-size: 24px;
  }

  .news-meta {
    font-size: 12px;
  }

  .news-content {
    font-size: 14px;
    padding: 15px 0;
  }

  .news-image img {
    max-height: 300px;
  }
}
</style>

