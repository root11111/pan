<template>
  <div class="certification-service-list" :key="`list-${activeCategoryId}`">
    <!-- 显示分类标题 -->
    <div v-if="activeCategoryId !== null && category" style="margin-top: 40px; margin-bottom: 20px;">
      <h2 class="category-title">{{ currentLang === 'en' ? category.nameEn : category.nameCn }}</h2>
      <p class="category-subtitle" v-if="category.nameEn && currentLang === 'zh'">{{ category.nameEn }}</p>
      <p class="category-subtitle" v-if="category.nameCn && currentLang === 'en'">{{ category.nameCn }}</p>
    </div>
    
    <!-- 服务列表 -->
    <div style="margin-top: 40px;">
      <!-- 调试信息 -->
      <div v-if="false" style="background: yellow; padding: 10px; margin-bottom: 10px;">
        <p>activeCategoryId: {{ activeCategoryId }}</p>
        <p>services.length: {{ services.length }}</p>
        <p>services: {{ services.map(s => s.nameCn).join(', ') }}</p>
      </div>
      <template v-if="services && services.length > 0">
        <div class="service-list-wrapper" style="margin-top: 20px;">
          <div class="service-list" style="display: flex; flex-wrap: wrap; gap: 30px; justify-content: flex-start;">
            <el-card 
              v-for="(service, index) in services" 
              :key="`service-${activeCategoryId}-${service.id}-${index}`"
              class="service-card scale-in hover-lift" 
              shadow="hover" 
              @click="goToDetail(service.id)"
              style="width: calc(33.333% - 20px); min-width: 300px; flex: 0 0 auto; margin-bottom: 0;"
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
    </div>
  </div>
</template>

<script>
import { ref, computed, watch, onMounted, nextTick } from 'vue'
import { useRouter } from 'vue-router'
import { getCertificationsByCategoryId } from '../api/certification'
import { getImageUrl } from '../utils/image'
import { useI18n, getCurrentLang } from '../utils/i18n'

export default {
  name: 'CertificationServiceList',
  props: {
    activeCategoryId: {
      type: Number,
      default: null
    },
    category: {
      type: Object,
      default: null
    },
    allServices: {
      type: Array,
      default: () => []
    },
    categoryTreeData: {
      type: Array,
      default: () => []
    }
  },
  emits: ['update:services'],
  setup(props, { emit }) {
    const router = useRouter()
    const { lang: currentLang } = useI18n()
    const services = ref([])
    const isLoading = ref(false)

    // 在树形数据中查找分类的辅助函数
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

    // 加载服务数据
    const loadServices = async () => {
      if (props.activeCategoryId === null) {
        // 显示所有服务
        const allServices = props.allServices.filter(s => !s.parentId || s.parentId === 0)
        services.value = [...allServices]
        emit('update:services', services.value)
        return
      }

      // 查找分类
      let category = props.category
      if (!category && props.categoryTreeData.length > 0) {
        category = findCategoryInTree(props.categoryTreeData, props.activeCategoryId)
      }

      if (!category) {
        services.value = []
        emit('update:services', [])
        return
      }

      try {
        isLoading.value = true
        
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
        const allServices = []
        const serviceIdSet = new Set()
        
        serviceResults.forEach(result => {
          if (result.code === 200 && result.data) {
            result.data.forEach(service => {
              // 只添加顶级服务（parentId 为 0 或 null），避免重复
              if ((!service.parentId || service.parentId === 0) && !serviceIdSet.has(service.id)) {
                serviceIdSet.add(service.id)
                allServices.push(service)
              }
            })
          }
        })
        
        // 按 sortOrder 排序
        allServices.sort((a, b) => (a.sortOrder || 0) - (b.sortOrder || 0))
        
        // 更新服务列表 - 直接赋值新数组，确保响应式更新
        services.value = [...allServices]
        emit('update:services', services.value)
        
        // 使用 nextTick 确保 DOM 更新
        await nextTick()
        
        console.log('CertificationServiceList: 服务加载完成', {
          activeCategoryId: props.activeCategoryId,
          servicesCount: services.value.length,
          servicesArray: services.value,
          templateWillRender: true
        })
      } catch (error) {
        console.error('CertificationServiceList: 加载服务失败:', error)
        services.value = []
        emit('update:services', [])
      } finally {
        isLoading.value = false
      }
    }

    // 监听 activeCategoryId 变化
    watch(() => props.activeCategoryId, async (newId, oldId) => {
      console.log('CertificationServiceList: activeCategoryId 变化', { oldId, newId })
      // 先清空服务列表，确保视图更新
      services.value = []
      await nextTick()
      // 加载新数据
      await loadServices()
      // 强制触发响应式更新
      await nextTick()
      console.log('CertificationServiceList: 服务列表已更新', {
        servicesCount: services.value.length,
        services: services.value.map(s => ({ id: s.id, nameCn: s.nameCn })),
        templateWillRender: true
      })
    }, { immediate: true, flush: 'post' })

    // 监听 category 变化
    watch(() => props.category, async () => {
      if (props.activeCategoryId !== null) {
        await loadServices()
        await nextTick()
      }
    })

    const isImage = (content) => {
      if (!content) return false
      return /\.(jpg|jpeg|png|gif|bmp|webp|svg)$/i.test(content)
    }

    const isHtml = (content) => {
      if (!content) return false
      return /<[a-z][\s\S]*>/i.test(content)
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
      // 阻止错误传播到控制台（静默处理404）
      if (event && event.preventDefault) {
        event.preventDefault()
      }
      if (event && event.stopPropagation) {
        event.stopPropagation()
      }
      // 隐藏图片容器
      if (event && event.target && event.target.parentElement) {
        event.target.parentElement.style.display = 'none'
      }
      return false
    }

    const handleImageLoad = (event) => {
      if (event.target && event.target.parentElement) {
        event.target.parentElement.style.display = 'block'
      }
    }

    const getChildServices = (parentId) => {
      return props.allServices.filter(s => s.parentId === parentId)
        .sort((a, b) => (a.sortOrder || 0) - (b.sortOrder || 0))
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

    return {
      services,
      isLoading,
      currentLang,
      activeCategoryId: computed(() => props.activeCategoryId),
      category: computed(() => props.category),
      isImage,
      isHtml,
      getServiceImage,
      handleImageError,
      handleImageLoad,
      getChildServices,
      goToDetail,
      getSummary,
      formatDescription
    }
  }
}
</script>

<style scoped>
.category-title {
  font-size: 28px;
  color: #2c3e50;
  margin-bottom: 10px;
  font-weight: 600;
}

.category-subtitle {
  color: #909399;
  font-size: 16px;
  margin-bottom: 20px;
}

.service-card {
  margin-bottom: 30px;
  cursor: pointer;
  transition: transform 0.3s, box-shadow 0.3s;
  overflow: hidden;
}

.service-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.15);
}

.service-image-wrapper {
  width: 100%;
  height: 200px;
  overflow: hidden;
  margin-bottom: 15px;
  background: #f5f5f5;
  display: flex;
  align-items: center;
  justify-content: center;
}

.service-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.3s;
}

.service-card:hover .service-image {
  transform: scale(1.15);
}

.service-content {
  padding: 0 5px;
}

.service-content h3 {
  font-size: 20px;
  margin-bottom: 8px;
  color: #2c3e50;
  font-weight: 600;
}

.service-name-en {
  color: #909399;
  font-size: 14px;
  margin-bottom: 12px;
}

.service-summary {
  color: #606266;
  font-size: 14px;
  line-height: 1.6;
  margin: 10px 0;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
  text-overflow: ellipsis;
}

.service-desc {
  color: #666;
  line-height: 1.6;
  margin-top: 12px;
  font-size: 14px;
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
  overflow: hidden;
  text-overflow: ellipsis;
}

.service-desc :deep(img) {
  max-width: 100%;
  height: auto;
}

.child-services {
  margin-top: 15px;
  padding-top: 15px;
  border-top: 1px solid #ebeef5;
}

.child-service-list {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
}

.child-tag {
  cursor: pointer;
  transition: all 0.3s;
}

.child-tag:hover {
  transform: scale(1.05);
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
}
</style>

