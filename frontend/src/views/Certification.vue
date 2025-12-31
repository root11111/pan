<template>
  <div class="certification">
    <Header />
    
    <div class="page-banner slide-in-top">
      <div class="banner-content">
        <h1 class="fade-in-immediate">认证服务</h1>
        <p class="fade-in-immediate" style="animation-delay: 0.2s;">Certification Services</p>
      </div>
    </div>

    <div class="container certification-container">
      <div class="certification-layout">
        <!-- 左侧分类导航 -->
        <div class="left-sidebar">
          <div class="left_inside_left">
            <strong>认证服务</strong>
            <ul class="category-tree">
              <li v-for="category in topCategories" :key="category.id" class="category-item">
                <div class="category-row">
                  <span 
                    v-if="category.children && category.children.length > 0"
                    class="expand-icon"
                    :class="{ 'expanded': expandedCategories.has(category.id) }"
                    @click.stop="toggleCategory(category.id)"
                  >
                    ▶
                  </span>
                  <span v-else class="expand-icon-placeholder"></span>
                  <a 
                    :class="{ 'selected_color': activeCategoryId === category.id }"
                    href="javascript:void(0)"
                    @click="handleCategoryChange(category.id)"
                  >
                    {{ currentLang === 'en' ? category.nameEn : category.nameCn }}
                  </a>
                </div>
                <!-- 子分类 -->
                <ul 
                  v-if="category.children && category.children.length > 0 && expandedCategories.has(category.id)"
                  class="sub-category-list"
                >
                  <li v-for="subCategory in category.children" :key="subCategory.id" class="sub-category-item">
                    <a 
                      :class="{ 'selected_color': activeCategoryId === subCategory.id }"
                      href="javascript:void(0)"
                      @click="handleCategoryChange(subCategory.id)"
                    >
                      {{ currentLang === 'en' ? subCategory.nameEn : subCategory.nameCn }}
                    </a>
                  </li>
                </ul>
              </li>
            </ul>
          </div>
        </div>

        <!-- 右侧内容区域 -->
        <div class="right-content">
          <!-- 显示分类标题 -->
          <div v-if="activeCategoryId !== null && currentCategory" style="margin-top: 40px; margin-bottom: 20px;">
            <h2 class="category-title">{{ currentLang === 'en' ? currentCategory.nameEn : currentCategory.nameCn }}</h2>
            <p class="category-subtitle" v-if="currentCategory.nameEn && currentLang === 'zh'">{{ currentCategory.nameEn }}</p>
            <p class="category-subtitle" v-if="currentCategory.nameCn && currentLang === 'en'">{{ currentCategory.nameCn }}</p>
          </div>
          
          <!-- 服务列表 -->
          <div style="margin-top: 40px;">
            <div v-if="isLoading" style="text-align: center; padding: 40px;">
              <p>加载中...</p>
            </div>
            <template v-else-if="displayServices && displayServices.length > 0">
              <div class="service-list-wrapper" style="margin-top: 20px;">
                <div class="service-list" style="display: flex; flex-wrap: wrap; gap: 30px; justify-content: flex-start;">
                  <el-card 
                    v-for="service in displayServices" 
                    :key="`service-${service.id}`"
                    class="service-card hover-lift" 
                    shadow="hover" 
                    @click="goToDetail(service.id)"
                    style="width: calc(33.333% - 20px); min-width: 300px; flex: 0 0 auto; margin-bottom: 0; opacity: 1; visibility: visible;"
                  >
                    <div class="service-image-wrapper" v-if="service.image || (service.descriptionCn && isImage(service.descriptionCn))">
                      <img 
                        :src="getServiceImage(service)" 
                        :alt="service.nameCn" 
                        class="service-image"
                        @error="handleImageError($event)"
                        @load="handleImageLoad($event)"
                      />
                    </div>
                    <div class="service-content">
                      <h3>{{ service.nameCn }}</h3>
                      <p class="service-name-en">{{ service.nameEn }}</p>
                      <p v-if="getSummary(service)" class="service-summary">{{ getSummary(service) }}</p>
                      <el-tag v-if="service.region" type="primary" size="small" style="margin-bottom: 10px;">{{ service.region }}</el-tag>
                      <div class="service-desc" v-if="service.descriptionCn">
                        <div v-if="isHtml(service.descriptionCn)" v-html="formatDescription(service.descriptionCn)"></div>
                        <p v-else>{{ formatDescription(service.descriptionCn) }}</p>
                      </div>
                      <!-- 子菜单 -->
                      <div v-if="getChildServices(service.id).length > 0" class="child-services">
                        <el-divider content-position="left">子服务</el-divider>
                        <div class="child-service-list">
                          <el-tag 
                            v-for="child in getChildServices(service.id)" 
                            :key="child.id"
                            class="child-tag"
                            @click.stop="goToDetail(child.id)"
                          >
                            {{ child.nameCn }}
                          </el-tag>
                        </div>
                      </div>
                    </div>
                  </el-card>
                </div>
              </div>
            </template>
            <div v-else style="text-align: center; padding: 40px; color: #909399;">
              <p v-if="activeCategoryId === null">暂无服务</p>
              <p v-else>该分类下暂无服务</p>
            </div>
            
            <!-- 分页组件 -->
            <div v-if="!isLoading && totalServices > 0" style="margin-top: 40px; display: flex; justify-content: center;">
              <el-pagination
                v-model:current-page="currentPage"
                v-model:page-size="pageSize"
                :page-sizes="[10, 20, 30, 40]"
                :total="totalServices"
                layout="total, sizes, prev, pager, next, jumper"
                @current-change="handlePageChange"
                @size-change="handleSizeChange"
              />
            </div>
          </div>
        </div>
      </div>
    </div>

    <Footer />
  </div>
</template>

<script>
import { ref, computed, watch, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import Header from '../components/Header.vue'
import Footer from '../components/Footer.vue'
import { getCertifications, getCertificationCategories, getCertificationsByCategoryId } from '../api/certification'
import { getImageUrl } from '../utils/image'
import { useI18n } from '../utils/i18n'

export default {
  name: 'Certification',
  components: {
    Header,
    Footer
  },
  setup() {
    const router = useRouter()
    const route = useRoute()
    const { lang: currentLang } = useI18n()
    
    // 响应式数据
    const activeCategoryId = ref(null)
    const categoryTreeData = ref([])
    const allServices = ref([])
    const currentServices = ref([])
    const isLoading = ref(false)
    const expandedCategories = ref(new Set())
    
    // 分页相关
    const currentPage = ref(1)
    const pageSize = ref(10) // 每页显示10个服务

    // 在树形数据中查找分类
    const findCategoryInTree = (tree, id) => {
      for (const cat of tree) {
        if (Number(cat.id) === Number(id)) {
          return cat
        }
        if (cat.children && cat.children.length > 0) {
          const found = findCategoryInTree(cat.children, id)
          if (found) return found
        }
      }
      return null
    }

    // 获取分类及其所有子分类的ID列表
    const getAllCategoryIds = (category) => {
      const ids = [category.id]
      if (category.children && category.children.length > 0) {
        category.children.forEach(child => {
          ids.push(child.id)
          if (child.children && child.children.length > 0) {
            child.children.forEach(grandChild => {
              ids.push(grandChild.id)
            })
          }
        })
      }
      return ids
    }

    // 顶级分类（ID=1的子分类）
    const topCategories = computed(() => {
      if (!categoryTreeData.value || categoryTreeData.value.length === 0) {
        return []
      }
      const parentCategory = findCategoryInTree(categoryTreeData.value, 1)
      if (parentCategory && parentCategory.children && parentCategory.children.length > 0) {
        return parentCategory.children
          .sort((a, b) => (a.sortOrder || 0) - (b.sortOrder || 0))
      }
      return []
    })

    // 当前选中的分类
    const currentCategory = computed(() => {
      if (activeCategoryId.value === null) return null
      return findCategoryInTree(categoryTreeData.value, activeCategoryId.value)
    })

    // 显示的服务列表（分页后）
    const displayServices = computed(() => {
      const start = (currentPage.value - 1) * pageSize.value
      const end = start + pageSize.value
      return currentServices.value.slice(start, end)
    })
    
    // 总页数
    const totalPages = computed(() => {
      return Math.ceil(currentServices.value.length / pageSize.value)
    })
    
    // 总服务数
    const totalServices = computed(() => {
      return currentServices.value.length
    })
    
    // 处理分页变化
    const handlePageChange = (page) => {
      currentPage.value = page
      // 滚动到顶部
      window.scrollTo({ top: 0, behavior: 'smooth' })
    }
    
    // 处理每页显示数量变化
    const handleSizeChange = (size) => {
      pageSize.value = size
      currentPage.value = 1 // 重置到第一页
    }

    // 加载分类数据
    const loadCategories = async () => {
      try {
        const res = await getCertificationCategories()
        if (res.code === 200 && res.data) {
          categoryTreeData.value = res.data
        }
      } catch (error) {
        console.error('加载分类失败:', error)
      }
    }

    // 加载服务数据
    const loadServices = async (categoryId) => {
      if (categoryId === null) {
        // 显示所有服务
        try {
          isLoading.value = true
          const res = await getCertifications()
          if (res.code === 200 && res.data) {
            allServices.value = res.data
            currentServices.value = res.data.filter(s => !s.parentId || s.parentId === 0)
            // 重置到第一页
            currentPage.value = 1
          }
        } catch (error) {
          console.error('加载服务失败:', error)
          currentServices.value = []
        } finally {
          isLoading.value = false
        }
        return
      }

      // 根据分类ID加载服务
      try {
        isLoading.value = true
        const category = findCategoryInTree(categoryTreeData.value, categoryId)
        if (!category) {
          currentServices.value = []
          isLoading.value = false
          return
        }

        // 获取该分类及其所有子分类的ID列表
        const categoryIds = getAllCategoryIds(category)
        
        // 调用接口获取所有相关分类的服务
        const servicePromises = categoryIds.map(catId =>
          getCertificationsByCategoryId(catId).catch(err => {
            console.warn(`获取分类 ${catId} 的服务失败:`, err)
            return { code: 200, data: [] }
          })
        )
        
        const serviceResults = await Promise.all(servicePromises)
        
        // 合并所有服务，去重
        const allServicesList = []
        const serviceIdSet = new Set()
        
        serviceResults.forEach(result => {
          if (result.code === 200 && result.data) {
            result.data.forEach(service => {
              // 只添加顶级服务（parentId 为 0 或 null），避免重复
              if ((!service.parentId || service.parentId === 0) && !serviceIdSet.has(service.id)) {
                serviceIdSet.add(service.id)
                allServicesList.push(service)
              }
            })
          }
        })
        
        // 按 sortOrder 排序
        allServicesList.sort((a, b) => (a.sortOrder || 0) - (b.sortOrder || 0))
        
        // 直接赋值，触发响应式更新
        currentServices.value = allServicesList
        // 重置到第一页
        currentPage.value = 1
      } catch (error) {
        console.error('加载服务失败:', error)
        currentServices.value = []
      } finally {
        isLoading.value = false
      }
    }

    // 处理分类切换
    const handleCategoryChange = async (categoryId) => {
      const numCategoryId = categoryId ? Number(categoryId) : null
      
      if (activeCategoryId.value === numCategoryId) {
        return
      }
      
      activeCategoryId.value = numCategoryId
      
      // 更新 URL 参数
      const url = new URL(window.location.href)
      if (numCategoryId) {
        url.searchParams.set('categoryId', String(numCategoryId))
      } else {
        url.searchParams.delete('categoryId')
      }
      window.history.pushState({}, '', url)
      
      // 加载服务
      await loadServices(numCategoryId)
      // 重置到第一页
      currentPage.value = 1
    }

    // 切换分类展开/折叠
    const toggleCategory = (categoryId) => {
      if (expandedCategories.value.has(categoryId)) {
        expandedCategories.value.delete(categoryId)
      } else {
        expandedCategories.value.add(categoryId)
      }
      expandedCategories.value = new Set(expandedCategories.value)
    }

    // 监听路由变化
    watch(() => route.query.categoryId, (newCategoryId) => {
      if (newCategoryId) {
        const numId = Number(newCategoryId)
        if (activeCategoryId.value !== numId) {
          activeCategoryId.value = numId
          loadServices(numId)
        }
      } else {
        if (activeCategoryId.value !== null) {
          activeCategoryId.value = null
          loadServices(null)
        }
      }
    }, { immediate: true })

    // 工具函数
    const isImage = (str) => {
      if (!str) return false
      return str.startsWith('http://') || str.startsWith('https://') || str.startsWith('/') || str.includes('.jpg') || str.includes('.png') || str.includes('.gif') || str.includes('.jpeg') || str.includes('.webp')
    }

    const isHtml = (str) => {
      if (!str) return false
      return str.includes('<') && str.includes('>')
    }

    const getServiceImage = (service) => {
      if (service.image) {
        return getImageUrl(service.image)
      }
      if (service.descriptionCn && isImage(service.descriptionCn)) {
        return getImageUrl(service.descriptionCn)
      }
      return ''
    }

    const handleImageError = (event) => {
      if (event.target && event.target.parentElement) {
        event.target.parentElement.style.display = 'none'
      }
    }

    const handleImageLoad = (event) => {
      if (event.target && event.target.parentElement) {
        event.target.parentElement.style.display = 'block'
      }
    }

    const getChildServices = (parentId) => {
      return allServices.value.filter(s => s.parentId === parentId)
    }

    const goToDetail = (id) => {
      router.push(`/certification/${id}`)
    }

    const getSummary = (service) => {
      if (currentLang.value === 'en' && service.summaryEn) {
        return service.summaryEn
      }
      return service.summaryCn || ''
    }

    const formatDescription = (desc) => {
      if (!desc) return ''
      if (isHtml(desc)) {
        return desc
      }
      if (isImage(desc)) {
        return ''
      }
      if (desc.length > 100) {
        return desc.substring(0, 100) + '...'
      }
      return desc
    }

    // 初始化
    onMounted(async () => {
      await loadCategories()
      
      // 处理 URL 参数
      const categoryId = route.query.categoryId
      if (categoryId) {
        const numId = Number(categoryId)
        activeCategoryId.value = numId
        await loadServices(numId)
      } else {
        await loadServices(null)
      }
    })

    return {
      activeCategoryId,
      topCategories,
      currentCategory,
      displayServices,
      isLoading,
      expandedCategories,
      currentLang,
      currentPage,
      pageSize,
      totalPages,
      totalServices,
      handleCategoryChange,
      toggleCategory,
      handlePageChange,
      handleSizeChange,
      isImage,
      isHtml,
      getServiceImage,
      handleImageError,
      handleImageLoad,
      getChildServices,
      goToDetail,
      getSummary,
      formatDescription,
      getImageUrl
    }
  }
}
</script>

<style scoped>
.certification {
  min-height: 100vh;
  background: #f5f5f5;
}

.page-banner {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  padding: 40px 0;
  text-align: center;
}

.banner-content h1 {
  font-size: 48px;
  margin-bottom: 10px;
  font-weight: 700;
}

.banner-content p {
  font-size: 20px;
  opacity: 0.9;
}

.certification-container {
  max-width: 1400px;
  margin: 0 auto;
  padding: 10px 20px 40px 20px;
}

.certification-layout {
  display: flex;
  gap: 30px;
  align-items: flex-start;
}

/* 左侧导航 */
.left-sidebar {
  width: 220px;
  flex-shrink: 0;
  background: white;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.left_inside_left {
  width: 100%;
}

.left_inside_left strong {
  display: block;
  font-size: 18px;
  font-weight: 600;
  color: #333;
  margin-bottom: 20px;
  padding-bottom: 10px;
  border-bottom: 2px solid #667eea;
}

.category-tree {
  list-style: none;
  padding: 0;
  margin: 0;
}

.category-item {
  margin-bottom: 5px;
}

.category-row {
  display: flex;
  align-items: center;
}

.expand-icon {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  width: 20px;
  height: 20px;
  line-height: 20px;
  text-align: center;
  cursor: pointer;
  user-select: none;
  font-size: 10px;
  color: #606266;
  transition: transform 0.3s;
  margin-right: 5px;
}

.expand-icon.expanded {
  transform: rotate(90deg);
}

.expand-icon:hover {
  color: #409eff;
}

.expand-icon-placeholder {
  display: inline-block;
  width: 20px;
  height: 20px;
  margin-right: 5px;
}

.category-tree a {
  display: block;
  padding: 10px 15px;
  color: #333;
  text-decoration: none;
  border-radius: 4px;
  transition: all 0.3s;
  font-size: 14px;
}

.category-tree a:hover {
  background: #f0f0f0;
  color: #667eea;
}

.category-tree a.selected_color {
  background: #667eea;
  color: white;
  font-weight: 500;
}

.sub-category-list {
  list-style: none;
  padding: 0;
  margin: 5px 0 5px 35px;
  border-left: 1px solid #e4e7ed;
}

.sub-category-item {
  margin-bottom: 0;
  position: relative;
}

.sub-category-item::before {
  content: '';
  position: absolute;
  left: -1px;
  top: 50%;
  width: 10px;
  height: 1px;
  background: #e4e7ed;
}

.sub-category-item a {
  padding-left: 20px;
  font-size: 13px;
}

/* 右侧内容 */
.right-content {
  flex: 1;
  min-width: 0;
  background: white;
  border-radius: 8px;
  padding: 30px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  width: 100%;
}

.category-title {
  font-size: 28px;
  font-weight: 600;
  color: #333;
  margin-bottom: 10px;
}

.category-subtitle {
  font-size: 16px;
  color: #666;
  margin-top: 5px;
}

.service-list-wrapper {
  width: 100%;
}

.service-card {
  cursor: pointer;
  transition: all 0.3s;
  border-radius: 8px;
  overflow: hidden;
  background: white;
  border: 1px solid #e4e7ed;
  display: block;
  visibility: visible;
  opacity: 1;
}

.service-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.15);
}

/* 动画类 */
.scale-in {
  animation: scaleIn 0.5s ease-out;
}

@keyframes scaleIn {
  from {
    opacity: 0;
    transform: scale(0.9);
  }
  to {
    opacity: 1;
    transform: scale(1);
  }
}

.hover-lift {
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
}

.hover-lift:hover {
  transform: translateY(-8px);
  box-shadow: 0 12px 24px rgba(0, 0, 0, 0.15);
}

.service-image-wrapper {
  width: 100%;
  height: 200px;
  overflow: hidden;
  background: #f5f5f5;
  display: flex;
  align-items: center;
  justify-content: center;
}

.service-image {
  max-width: 100%;
  max-height: 100%;
  object-fit: contain;
}

.service-content {
  padding: 20px;
  background: white;
  color: #333;
}

.service-content h3 {
  font-size: 20px;
  font-weight: 600;
  color: #333;
  margin-bottom: 8px;
  display: block;
  visibility: visible;
}

.service-name-en {
  font-size: 14px;
  color: #666;
  margin-bottom: 10px;
}

.service-summary {
  font-size: 14px;
  color: #555;
  line-height: 1.6;
  margin-bottom: 10px;
}

.service-desc {
  font-size: 13px;
  color: #666;
  line-height: 1.6;
  margin-top: 10px;
}

.child-services {
  margin-top: 15px;
  padding-top: 15px;
  border-top: 1px solid #eee;
}

.child-service-list {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
  margin-top: 10px;
}

.child-tag {
  cursor: pointer;
  transition: all 0.3s;
}

.child-tag:hover {
  transform: scale(1.05);
}

/* 响应式设计 */
@media (max-width: 768px) {
  .certification-layout {
    flex-direction: column;
  }

  .left-sidebar {
    width: 100%;
    margin-bottom: 20px;
  }

  .service-list {
    flex-direction: column;
  }

  .service-card {
    width: 100% !important;
  }
}
</style>

