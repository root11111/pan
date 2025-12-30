<template>
  <div class="certification">
    <Header />
    
    <div class="page-banner slide-in-top">
      <div class="banner-content">
        <h1 class="fade-in-immediate">认证服务</h1>
        <p class="fade-in-immediate" style="animation-delay: 0.2s;">Certification Services</p>
      </div>
    </div>

    <div class="container" style="padding: 0px 20px 60px 20px;">
      <el-tabs v-model="activeCategoryId" @tab-change="handleCategoryChange">
        <el-tab-pane 
          v-for="category in tabCategories" 
          :key="category.id" 
          :label="currentLang === 'en' ? category.nameEn : category.nameCn" 
          :name="category.id"
        ></el-tab-pane>
      </el-tabs>

      <!-- 当没有选择任何分类（activeCategoryId为null）时，显示所有服务，不按分类分组 -->
      <div v-if="activeCategoryId === null" style="margin-top: 40px;">
        <el-row :gutter="30" style="margin-top: 20px;" v-if="getServicesByCategory(null).length > 0">
          <el-col :xs="24" :sm="12" :md="8" v-for="service in getServicesByCategory(null)" :key="service.id">
            <el-card class="service-card" shadow="hover" @click="goToDetail(service.id)">
              <div class="service-image-wrapper" v-if="service.image || (service.descriptionCn && isImage(service.descriptionCn))">
                <img 
                  :src="getServiceImage(service)" 
                  :alt="service.nameCn" 
                  class="service-image" 
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
          </el-col>
        </el-row>
        <div v-else style="text-align: center; padding: 40px; color: #909399;">
          暂无服务
        </div>
      </div>

      <!-- 按分类分组显示 -->
      <div v-else v-for="category in displayCategories" :key="category.id" style="margin-top: 40px;">
        <h2 class="category-title">{{ currentLang === 'en' ? category.nameEn : category.nameCn }}</h2>
        <p class="category-subtitle" v-if="category.nameEn && currentLang === 'zh'">{{ category.nameEn }}</p>
        <p class="category-subtitle" v-if="category.nameCn && currentLang === 'en'">{{ category.nameCn }}</p>
        
        <!-- 如果有子分类，显示子分类列表 -->
        <div v-if="category.children && category.children.length > 0" class="sub-categories" style="margin-top: 20px; margin-bottom: 20px;">
          <el-tag 
            v-for="subCategory in category.children" 
            :key="subCategory.id"
            :type="activeCategoryId === subCategory.id ? 'primary' : ''"
            style="margin-right: 10px; margin-bottom: 10px; cursor: pointer;"
            @click="activeCategoryId = subCategory.id"
          >
            {{ currentLang === 'en' ? subCategory.nameEn : subCategory.nameCn }}
          </el-tag>
        </div>
        
        <!-- 顶级服务 -->
        <el-row :gutter="30" style="margin-top: 20px;" v-if="getServicesByCategory(category.id).length > 0" class="service-list">
          <el-col :xs="24" :sm="12" :md="8" v-for="service in getServicesByCategory(category.id)" :key="service.id">
            <el-card class="service-card scale-in hover-lift" shadow="hover" @click="goToDetail(service.id)">
              <div class="service-image-wrapper" v-if="service.image || (service.descriptionCn && isImage(service.descriptionCn))">
                <img 
                  :src="getServiceImage(service)" 
                  :alt="service.nameCn" 
                  class="service-image" 
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
          </el-col>
        </el-row>
        <div v-else style="text-align: center; padding: 40px; color: #909399;">
          该分类下暂无服务
          <!-- 调试信息 -->
          <details v-if="getAllServicesByCategory(category.id).length > 0" style="margin-top: 20px; text-align: left; max-width: 800px; margin-left: auto; margin-right: auto;">
            <summary style="cursor: pointer; color: #409eff;">调试：该分类下有 {{ getAllServicesByCategory(category.id).length }} 个服务（包括子服务）</summary>
            <div style="margin-top: 10px; padding: 10px; background: #f5f5f5; border-radius: 4px;">
              <div v-for="s in getAllServicesByCategory(category.id)" :key="s.id" style="margin-bottom: 10px; padding: 10px; background: #fff; border-radius: 4px;">
                <p><strong>{{ s.nameCn }}</strong> (ID: {{ s.id }})</p>
                <p style="font-size: 12px; color: #666;">
                  categoryId: {{ s.categoryId }} (类型: {{ typeof s.categoryId }})<br>
                  parentId: {{ s.parentId }} (类型: {{ typeof s.parentId }})<br>
                  <span v-if="s.parentId && s.parentId !== 0 && s.parentId !== '0' && s.parentId !== null" style="color: #f56c6c;">
                    ⚠️ 这是子服务，不会被显示（parentId不为空）
                  </span>
                  <span v-else style="color: #67c23a;">
                    ✓ 这是顶级服务，应该被显示
                  </span>
                </p>
              </div>
            </div>
          </details>
        </div>
      </div>
      
      <!-- 如果没有分类数据 -->
      <div v-if="displayCategories.length === 0" style="text-align: center; padding: 60px; color: #909399;">
        <p>暂无分类数据</p>
      </div>
    </div>

    <Footer />
  </div>
</template>

<script>
import { ref, onMounted, computed, watch, nextTick } from 'vue'
import { initAllScrollAnimations } from '../utils/scrollAnimation'
import { useRouter, useRoute } from 'vue-router'
import Header from '../components/Header.vue'
import Footer from '../components/Footer.vue'
import { getCertifications, getCertificationCategories } from '../api/certification'
import { getImageUrl } from '../utils/image'
import { useI18n, getCurrentLang } from '../utils/i18n'

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
    const activeCategoryId = ref(null)
    const services = ref([])
    const categories = ref([])
    const categoryTreeData = ref([]) // 保存原始树形数据

    // 在树形数据中查找分类的辅助函数（统一使用数字比较，支持字符串和数字类型的ID）
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

    // 顶级分类（用于标签页显示）- 显示所有二级分类（ID=1的子分类），排除ID=1本身
    const topCategories = computed(() => {
      if (!categoryTreeData.value || categoryTreeData.value.length === 0) {
        return []
      }
      
      // 查找ID=1的"认证服务"分类
      const parentCategory = findCategoryInTree(categoryTreeData.value, 1)
      if (parentCategory && parentCategory.children && parentCategory.children.length > 0) {
        // 返回ID=1的所有子分类（二级分类），按 sortOrder 排序
        return parentCategory.children
          .sort((a, b) => (a.sortOrder || 0) - (b.sortOrder || 0))
      }
      
      // 如果没有找到ID=1或没有子分类，返回空数组（不显示任何分类）
      return []
    })

    // 标签页显示的分类 - 显示所有后台配置的分类（ID=1的子分类，排除ID=1本身）
    const tabCategories = computed(() => {
      // 直接返回topCategories，显示所有后台配置的二级分类
      return topCategories.value
    })

    // 显示的分类（用于内容区域显示）- 显示所有后台配置的分类
    const displayCategories = computed(() => {
      console.log('Certification: displayCategories 计算', {
        activeCategoryId: activeCategoryId.value,
        topCategoriesCount: topCategories.value.length,
        categoriesCount: categories.value.length,
        categoryTreeDataCount: categoryTreeData.value.length
      })
      
      if (activeCategoryId.value === null) {
        // 显示所有后台配置的二级分类（ID=1的子分类，排除ID=1本身）
        console.log('Certification: activeCategoryId 为 null，返回 topCategories', topCategories.value.length, '个分类')
        return topCategories.value
      }
      
      // 显示选中的分类
      let filtered = categories.value.filter(cat => {
        const catId = cat.id
        const activeId = activeCategoryId.value
        const match = catId == activeId || Number(catId) === Number(activeId) || String(catId) === String(activeId)
        if (match) {
          console.log('Certification: 找到匹配的分类', { catId, activeId, category: cat })
        }
        return match
      })
      
      console.log('Certification: 在扁平列表中查找，找到', filtered.length, '个分类')
      
      // 如果在扁平列表中找不到，在树形结构中查找
      if (filtered.length === 0 && categoryTreeData.value.length > 0) {
        console.log('Certification: 在树形结构中查找分类', activeCategoryId.value)
        const found = findCategoryInTree(categoryTreeData.value, activeCategoryId.value)
        if (found) {
          filtered = [found]
        }
      }
      
      return filtered
    })

    // 检查一个分类是否是另一个分类的子分类（递归查找）
    // 如果 childCategoryId 是 parentCategoryId 的子分类（直接或间接），返回 true
    const isChildCategory = (childCategoryId, parentCategoryId) => {
      if (!childCategoryId || !parentCategoryId) return false
      if (Number(childCategoryId) === Number(parentCategoryId)) return true
      
      // 首先在扁平列表中查找子分类
      let childCategory = categories.value.find(cat => Number(cat.id) === Number(childCategoryId))
      
      // 如果在扁平列表中找不到，尝试在树形结构中查找
      if (!childCategory && categoryTreeData.value.length > 0) {
        childCategory = findCategoryInTree(categoryTreeData.value, childCategoryId)
      }
      
      if (!childCategory) {
        console.warn(`未找到 categoryId=${childCategoryId} 的分类`)
        return false
      }
      
      // 递归向上查找父分类链
      let current = childCategory
      const visited = new Set() // 防止循环
      
      while (current && current.parentId && !visited.has(Number(current.id))) {
        visited.add(Number(current.id))
        
        // 检查直接父分类是否匹配
        if (Number(current.parentId) === Number(parentCategoryId)) {
          console.log(`分类 ${childCategoryId} 是分类 ${parentCategoryId} 的子分类（直接）`)
          return true
        }
        
        // 查找父分类
        const parentId = current.parentId
        current = categories.value.find(cat => Number(cat.id) === Number(parentId))
        if (!current) {
          // 如果在扁平列表中找不到，尝试在树形结构中查找
          current = findCategoryInTree(categoryTreeData.value, parentId)
          if (!current) {
            break
          }
        }
      }
      
      return false
    }

    // 获取指定分类下的所有服务（只返回顶级服务）
    // 包括直接属于该分类的服务，以及属于该分类的子分类的服务
    const getServicesByCategory = (categoryId) => {
      console.log('Certification: getServicesByCategory 被调用', {
        categoryId,
        categoryIdType: typeof categoryId,
        totalServices: services.value.length,
        services: services.value.map(s => ({ id: s.id, nameCn: s.nameCn, categoryId: s.categoryId, categoryIdType: typeof s.categoryId }))
      })
      
      // 当 categoryId 为 null 或 1（认证服务ID）时，返回所有顶级服务（不按分类过滤）
      if (!categoryId || Number(categoryId) === 1) {
        console.log('Certification: categoryId 为空或为1（认证服务），返回所有顶级服务')
        const allTopServices = services.value.filter(s => !s.parentId || s.parentId === 0 || s.parentId === '0' || s.parentId === null)
        return allTopServices.sort((a, b) => (a.sortOrder || 0) - (b.sortOrder || 0))
      }
      
      // 获取所有匹配分类的服务（包括子分类）
      let result = services.value.filter(s => {
        const serviceCategoryId = s.categoryId
        const catId = categoryId
        
        if (!serviceCategoryId || !catId) {
          console.log('Certification: 服务或分类ID为空', { serviceId: s.id, serviceCategoryId, catId })
          return false
        }
        
        // 直接匹配
        let matchCategory = false
        if (Number(serviceCategoryId) === Number(catId)) {
          matchCategory = true
          console.log('Certification: 直接匹配成功', { serviceId: s.id, serviceCategoryId, catId })
        } else if (String(serviceCategoryId) === String(catId)) {
          matchCategory = true
          console.log('Certification: 字符串匹配成功', { serviceId: s.id, serviceCategoryId, catId })
        } else if (serviceCategoryId == catId) {
          matchCategory = true
          console.log('Certification: 宽松匹配成功', { serviceId: s.id, serviceCategoryId, catId })
        }
        
        // 如果直接匹配失败，检查是否是子分类
        if (!matchCategory) {
          const isChild = isChildCategory(serviceCategoryId, catId)
          if (isChild) {
            console.log('Certification: 子分类匹配成功', { serviceId: s.id, serviceCategoryId, catId })
            matchCategory = true
          }
        }
        
        return matchCategory
      })
      
      console.log('Certification: 匹配后的服务数量（过滤前）', result.length, result.map(s => ({ id: s.id, nameCn: s.nameCn, categoryId: s.categoryId, parentId: s.parentId })))
      
      // 只返回顶级服务（parentId 为 0 或 null）
      result = result.filter(s => !s.parentId || s.parentId === 0 || s.parentId === '0' || s.parentId === null)
      
      console.log('Certification: 最终返回的顶级服务数量', result.length, result.map(s => ({ id: s.id, nameCn: s.nameCn, categoryId: s.categoryId })))
      
      // 按排序字段排序
      return result.sort((a, b) => (a.sortOrder || 0) - (b.sortOrder || 0))
    }

    // 获取指定分类下的所有服务（包括子服务，用于调试）
    const getAllServicesByCategory = (categoryId) => {
      return services.value.filter(s => {
        const serviceCategoryId = s.categoryId
        const catId = categoryId
        
        let matchCategory = false
        if (serviceCategoryId != null && catId != null) {
          if (Number(serviceCategoryId) === Number(catId)) {
            matchCategory = true
          } else if (String(serviceCategoryId) === String(catId)) {
            matchCategory = true
          } else if (serviceCategoryId == catId) {
            matchCategory = true
          }
        }
        
        return matchCategory
      })
    }

    // 保留原函数名以兼容旧代码
    const getTopServicesByCategory = getServicesByCategory

    const getChildServices = (parentId) => {
      return services.value.filter(s => s.parentId === parentId)
        .sort((a, b) => (a.sortOrder || 0) - (b.sortOrder || 0))
    }

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

    // 处理 URL 参数中的 categoryId
    const handleUrlParams = () => {
      const urlParams = new URLSearchParams(window.location.search)
      const categoryIdParam = urlParams.get('categoryId')
      console.log('Certification: handleUrlParams - URL参数:', { categoryIdParam, search: window.location.search })
      if (categoryIdParam) {
        const categoryId = Number(categoryIdParam)
        if (!isNaN(categoryId)) {
          activeCategoryId.value = categoryId
          console.log('Certification: handleUrlParams - 设置 activeCategoryId:', categoryId)
        } else {
          console.warn('Certification: handleUrlParams - categoryId 不是有效数字:', categoryIdParam)
          // 如果参数无效，设置为 null，显示所有服务
          activeCategoryId.value = null
        }
      } else {
        console.log('Certification: handleUrlParams - 没有 categoryId 参数，默认显示所有服务')
        // 确保默认显示所有服务（activeCategoryId 为 null）
        activeCategoryId.value = null
      }
    }

    onMounted(async () => {
      try {
        console.log('Certification: 开始加载数据...')
        console.log('Certification: 调用接口 - GET /api/certification/list')
        console.log('Certification: 调用接口 - GET /api/certification-category/tree')
        
        const [servicesRes, categoriesRes] = await Promise.all([
          getCertifications(),
          getCertificationCategories()
        ])
        
        console.log('Certification: 服务接口返回', {
          url: '/api/certification/list',
          code: servicesRes.code,
          dataLength: servicesRes.data?.length || 0,
          data: servicesRes.data
        })
        
        if (servicesRes.code === 200) {
          services.value = servicesRes.data || []
          console.log('Certification: 服务数据已加载，共', services.value.length, '条')
        } else {
          console.error('Certification: 服务数据加载失败:', servicesRes)
        }
        
        console.log('Certification: 分类接口返回', {
          url: '/api/certification-category/tree',
          code: categoriesRes.code,
          dataLength: categoriesRes.data?.length || 0,
          data: categoriesRes.data
        })
        
        if (categoriesRes.code === 200) {
          // 保存原始树形数据
          categoryTreeData.value = categoriesRes.data || []
          
          // 将树形结构转换为扁平列表（包括所有层级的分类），用于查找和匹配
          const flattenCategories = (cats) => {
            const result = []
            const flatten = (items) => {
              items.forEach(cat => {
                result.push(cat)
                if (cat.children && cat.children.length > 0) {
                  flatten(cat.children)
                }
              })
            }
            flatten(cats)
            return result
          }
          categories.value = flattenCategories(categoriesRes.data || [])
          console.log('Certification: 分类数据已加载，扁平化后共', categories.value.length, '个分类')
          
          // 处理 URL 参数
          handleUrlParams()
          console.log('Certification: URL 参数处理完成，当前 activeCategoryId:', activeCategoryId.value)
          
          // 如果 activeCategoryId 为 null（默认显示所有服务），确保 URL 中没有 categoryId 参数
          if (activeCategoryId.value === null) {
            const url = new URL(window.location.href)
            if (url.searchParams.has('categoryId')) {
              url.searchParams.delete('categoryId')
              window.history.replaceState({}, '', url)
              console.log('Certification: 清除 URL 中的 categoryId 参数，默认显示所有服务')
            }
          }
        } else {
          console.error('Certification: 分类数据加载失败:', categoriesRes)
        }
      } catch (error) {
        console.error('Certification: 加载数据失败:', error)
      }
      
      // 初始化滚动动画
      await nextTick()
      setTimeout(() => {
        initAllScrollAnimations()
      }, 100)
    })

    // 监听路由变化，当 URL 参数变化时更新 activeCategoryId
    watch(() => route.query.categoryId, (newCategoryId, oldCategoryId) => {
      console.log('Certification: 路由 query.categoryId 变化', {
        oldCategoryId,
        newCategoryId,
        newCategoryIdType: typeof newCategoryId,
        fullPath: route.fullPath,
        path: route.path,
        query: route.query,
        currentServicesCount: services.value.length,
        currentCategoriesCount: categories.value.length,
        currentActiveCategoryId: activeCategoryId.value,
        note: '页面已加载，数据已存在，无需重新调用接口，只需更新过滤条件'
      })
      
      if (newCategoryId) {
        const categoryId = Number(newCategoryId)
        if (!isNaN(categoryId)) {
          console.log('Certification: 路由变化，更新 activeCategoryId:', categoryId, '(之前是:', activeCategoryId.value, ')')
          activeCategoryId.value = categoryId
          console.log('Certification: activeCategoryId 已更新为:', activeCategoryId.value)
          
          // 等待响应式更新后检查
          setTimeout(() => {
            const servicesCount = getServicesByCategory(categoryId).length
            console.log('Certification: 当前显示的服务数量:', servicesCount)
            console.log('Certification: displayCategories 数量:', displayCategories.value.length)
          }, 100)
        } else {
          console.warn('Certification: 路由 categoryId 不是有效数字:', newCategoryId)
        }
      } else {
        console.log('Certification: 路由变化，清除 activeCategoryId')
        activeCategoryId.value = null
        console.log('Certification: 当前显示的服务数量（全部）:', services.value.length)
      }
    }, { immediate: true })

    // 处理分类切换
    const handleCategoryChange = (categoryId) => {
      activeCategoryId.value = categoryId
      // 更新 URL 参数
      const url = new URL(window.location.href)
      if (categoryId) {
        url.searchParams.set('categoryId', categoryId)
      } else {
        url.searchParams.delete('categoryId')
      }
      window.history.pushState({}, '', url)
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
      // 如果是HTML，直接返回
      if (isHtml(desc)) {
        return desc
      }
      // 如果是图片，返回空
      if (isImage(desc)) {
        return ''
      }
      // 截断描述，最多显示100个字符
      if (desc.length > 100) {
        return desc.substring(0, 100) + '...'
      }
      return desc
    }

    return {
      activeCategoryId,
      services,
      categories,
      topCategories,
      tabCategories,
      displayCategories,
      getTopServicesByCategory,
      getServicesByCategory,
      getAllServicesByCategory,
      getChildServices,
      isImage,
      isHtml,
      getServiceImage,
      handleCategoryChange,
      goToDetail,
      getSummary,
      formatDescription,
      getImageUrl,
      currentLang
    }
  }
}
</script>

<style scoped>
.certification {
  min-height: 100vh;
}

.page-banner {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: #fff;
  padding: 80px 20px;
  text-align: center;
}

.page-banner h1 {
  font-size: 42px;
  margin-bottom: 10px;
}

.page-banner p {
  font-size: 20px;
  opacity: 0.9;
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

  .service-card {
    padding: 20px 15px;
  }

  .service-card h3 {
    font-size: 18px;
  }
}
</style>

