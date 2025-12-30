<template>
    <el-header class="header">
      <div class="header-content">
        <div class="logo" @click="goToHome">
          <img v-if="companyInfo?.logo" :src="getImageUrl(companyInfo.logo, 'force')" alt="Logo" />
          <span class="logo-text">{{ currentLang === 'en' ? (companyInfo?.nameEn || 'Lantai Testing') : (companyInfo?.nameCn || '蓝泰检测') }}</span>
        </div>
      
      <!-- 桌面端菜单 -->
      <el-menu
        :default-active="activeIndex"
        class="nav-menu desktop-menu"
        mode="horizontal"
        @select="handleSelect"
      >
        <el-menu-item index="/">{{ t('home') }}</el-menu-item>
        <el-sub-menu index="about">
          <template #title>{{ t('about') }}</template>
          <el-menu-item index="/about">{{ t('aboutCompany') }}</el-menu-item>
          <el-menu-item index="/about#advantage">{{ t('aboutAdvantage') }}</el-menu-item>
          <el-menu-item index="/about#culture">{{ t('aboutCulture') }}</el-menu-item>
        </el-sub-menu>
        <el-menu-item index="/laboratory">{{ t('laboratory') }}</el-menu-item>
        <el-sub-menu index="certification">
          <template #title>{{ t('certification') }}</template>
          <!-- 动态加载分类菜单 -->
          <template v-for="category in certificationCategories" :key="category.id">
            <!-- 有子分类的二级分类 -->
            <el-sub-menu v-if="hasChildren(category.id)" :index="`category-${category.id}`">
              <template #title>{{ currentLang === 'en' ? category.nameEn : category.nameCn }}</template>
              <!-- 三级分类 -->
              <el-menu-item 
                v-for="child in getChildren(category.id)" 
                :key="child.id"
                :index="`/certification?categoryId=${child.id}`"
              >
                {{ currentLang === 'en' ? child.nameEn : child.nameCn }}
              </el-menu-item>
            </el-sub-menu>
            <!-- 没有子分类的二级分类 -->
            <el-menu-item 
              v-else 
              :index="`/certification?categoryId=${category.id}`"
            >
              {{ currentLang === 'en' ? category.nameEn : category.nameCn }}
            </el-menu-item>
          </template>
          <el-divider />
          <el-menu-item index="/certification">{{ t('allServices') }}</el-menu-item>
        </el-sub-menu>
        <el-menu-item v-if="isMenuEnabled('honor')" index="/honor">{{ t('honor') }}</el-menu-item>
        <el-menu-item v-if="isMenuEnabled('certificate')" index="/certificate">{{ t('certificate') }}</el-menu-item>
        <el-menu-item index="/news">{{ t('news') }}</el-menu-item>
      </el-menu>

      <!-- 联系我们按钮 - 单独显示，不被折叠 -->
      <el-button 
        type="primary" 
        class="contact-btn-header"
        @click="goToContact"
      >
        {{ t('contactUs') }}
      </el-button>

      <!-- 移动端菜单按钮 -->
      <div class="mobile-menu-toggle" @click="mobileMenuVisible = !mobileMenuVisible">
        <el-icon><Menu /></el-icon>
      </div>

      <!-- 移动端菜单 -->
      <el-drawer
        v-model="mobileMenuVisible"
        :title="t('navigationMenu')"
        direction="rtl"
        size="280px"
        class="mobile-drawer"
        :z-index="1002"
        append-to-body
      >
        <el-menu
          :default-active="activeIndex"
          class="mobile-menu"
          @select="handleMobileSelect"
        >
          <el-menu-item index="/">{{ t('home') }}</el-menu-item>
          <el-sub-menu index="about">
            <template #title>{{ t('about') }}</template>
            <el-menu-item index="/about">{{ t('aboutCompany') }}</el-menu-item>
            <el-menu-item index="/about#advantage">{{ t('aboutAdvantage') }}</el-menu-item>
            <el-menu-item index="/about#culture">{{ t('aboutCulture') }}</el-menu-item>
          </el-sub-menu>
          <el-menu-item index="/laboratory">{{ t('laboratory') }}</el-menu-item>
          <el-sub-menu index="certification">
            <template #title>{{ t('certification') }}</template>
            <!-- 动态加载分类菜单 -->
            <template v-for="category in certificationCategories" :key="category.id">
              <!-- 有子分类的二级分类 -->
              <el-sub-menu v-if="hasChildren(category.id)" :index="`category-${category.id}`">
                <template #title>{{ currentLang === 'en' ? category.nameEn : category.nameCn }}</template>
                <!-- 三级分类 -->
                <el-menu-item 
                  v-for="child in getChildren(category.id)" 
                  :key="child.id"
                  :index="`/certification?categoryId=${child.id}`"
                >
                  {{ currentLang === 'en' ? child.nameEn : child.nameCn }}
                </el-menu-item>
              </el-sub-menu>
              <!-- 没有子分类的二级分类 -->
              <el-menu-item 
                v-else 
                :index="`/certification?categoryId=${category.id}`"
              >
                {{ currentLang === 'en' ? category.nameEn : category.nameCn }}
              </el-menu-item>
            </template>
            <el-divider />
            <el-menu-item index="/certification">{{ t('allServices') }}</el-menu-item>
          </el-sub-menu>
          <el-menu-item v-if="isMenuEnabled('honor')" index="/honor">{{ t('honor') }}</el-menu-item>
          <el-menu-item v-if="isMenuEnabled('certificate')" index="/certificate">{{ t('certificate') }}</el-menu-item>
          <el-menu-item index="/news">{{ t('news') }}</el-menu-item>
          <el-divider />
          <el-menu-item @click="goToContact" class="contact-menu-item">
            <el-icon style="margin-right: 8px;"><Phone /></el-icon>
            {{ t('contactUs') }}
          </el-menu-item>
        </el-menu>
      </el-drawer>

      <div class="lang-switch">
        <el-button 
          text 
          :type="currentLang === 'zh' ? 'primary' : ''"
          @click="switchLang('zh')"
        >
          中文
        </el-button>
        <el-divider direction="vertical" />
        <el-button 
          text 
          :type="currentLang === 'en' ? 'primary' : ''"
          @click="switchLang('en')"
        >
          EN
        </el-button>
      </div>
    </div>
  </el-header>
</template>

<script>
import { ref, onMounted, computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { Menu, Phone } from '@element-plus/icons-vue'
import { getCompanyInfo } from '../api/company'
import { getAllCertificationCategories } from '../api/certification'
import { getEnabledMenus } from '../api/menuConfig'
import { getImageUrl, processImageUrls } from '../utils/image'
import { useI18n, getCurrentLang, setLang } from '../utils/i18n'

export default {
  name: 'Header',
  components: {
    Menu,
    Phone
  },
  setup() {
    const router = useRouter()
    const route = useRoute()
    const activeIndex = ref('/')
    const companyInfo = ref(null)
    const mobileMenuVisible = ref(false)
    const allCategories = ref([])
    const categoryTreeData = ref([])
    const menuConfigs = ref([])
    const { lang: currentLang, t } = useI18n()

    // 将扁平数据转换为树形结构（参考后台分类管理页面的方式）
    const buildCategoryTree = (data) => {
      const map = {}
      const roots = []
      
      // 创建映射
      data.forEach(item => {
        map[item.id] = { ...item, children: [] }
      })
      
      // 构建树形结构
      data.forEach(item => {
        const node = map[item.id]
        if (item.parentId && item.parentId !== 0 && map[item.parentId]) {
          map[item.parentId].children.push(node)
        } else {
          roots.push(node)
        }
      })
      
      // 排序
      const sortNodes = (nodes) => {
        nodes.sort((a, b) => (a.sortOrder || 0) - (b.sortOrder || 0))
        nodes.forEach(node => {
          if (node.children && node.children.length > 0) {
            sortNodes(node.children)
          }
        })
        return nodes
      }
      
      return sortNodes(roots)
    }

    // 在树形数据中查找分类
    const findCategoryInTree = (tree, id) => {
      for (const node of tree) {
        if (Number(node.id) === Number(id)) {
          return node
        }
        if (node.children && node.children.length > 0) {
          const found = findCategoryInTree(node.children, id)
          if (found) return found
        }
      }
      return null
    }

    // 获取认证服务分类（ID=1下的所有子分类，即二级分类）
    const certificationCategories = computed(() => {
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
      
      return []
    })

    // 检查分类是否有子分类
    const hasChildren = (categoryId) => {
      const category = findCategoryInTree(categoryTreeData.value, categoryId)
      return category && category.children && category.children.length > 0
    }

    // 获取分类的子分类
    const getChildren = (categoryId) => {
      const category = findCategoryInTree(categoryTreeData.value, categoryId)
      if (category && category.children && category.children.length > 0) {
        return category.children.sort((a, b) => (a.sortOrder || 0) - (b.sortOrder || 0))
      }
      return []
    }

    onMounted(async () => {
      activeIndex.value = route.path
      currentLang.value = getCurrentLang()
      try {
        const [companyRes, categoryRes, menuRes] = await Promise.all([
          getCompanyInfo(),
          getAllCertificationCategories(),
          getEnabledMenus()
        ])
        if (companyRes.code === 200) {
          companyInfo.value = processImageUrls(companyRes.data, ['logo'])
        }
        if (categoryRes.code === 200) {
          // 保存扁平列表数据
          allCategories.value = categoryRes.data || []
          // 转换为树形结构（参考后台分类管理页面的方式）
          categoryTreeData.value = buildCategoryTree(categoryRes.data || [])
        } else {
          console.error('Header: 分类数据加载失败', categoryRes)
        }
        if (menuRes.code === 200) {
          menuConfigs.value = menuRes.data || []
        } else {
          console.error('Header: 菜单配置加载失败', menuRes)
        }
      } catch (error) {
        console.error('Header: 获取数据失败:', error)
      }
    })

    // 判断菜单是否启用
    const isMenuEnabled = (menuKey) => {
      const menu = menuConfigs.value.find(m => m.menuKey === menuKey)
      return menu && menu.enabled
    }

    const handleSelect = (key) => {
      console.log('Header: handleSelect 被调用', { key, type: typeof key, startsWithSlash: key?.startsWith('/') })
      if (key && key.startsWith('/')) {
        console.log('Header: 准备跳转到', key)
        router.push(key).then(() => {
          console.log('Header: 跳转成功', key)
        }).catch((error) => {
          console.error('Header: 跳转失败', key, error)
        })
      } else {
        console.warn('Header: key 不是以 / 开头，不进行跳转', key)
      }
    }

    const handleMobileSelect = (key) => {
      console.log('Header: handleMobileSelect 被调用', { key, type: typeof key, startsWithSlash: key?.startsWith('/') })
      if (key && key.startsWith('/')) {
        console.log('Header: 准备跳转到（移动端）', key)
        router.push(key).then(() => {
          console.log('Header: 跳转成功（移动端）', key)
          mobileMenuVisible.value = false
        }).catch((error) => {
          console.error('Header: 跳转失败（移动端）', key, error)
        })
      } else {
        console.warn('Header: key 不是以 / 开头，不进行跳转（移动端）', key)
      }
    }

    const goToContact = () => {
      router.push('/contact')
    }

    const goToHome = () => {
      router.push('/')
    }

    const switchLang = (lang) => {
      setLang(lang)
      currentLang.value = lang
      // 刷新页面以应用语言更改
      window.location.reload()
    }

    return {
      activeIndex,
      companyInfo,
      mobileMenuVisible,
      currentLang,
      certificationCategories,
      hasChildren,
      getChildren,
      isMenuEnabled,
      handleSelect,
      handleMobileSelect,
      goToContact,
      goToHome,
      switchLang,
      getImageUrl,
      t
    }
  }
}
</script>

<style scoped>
.header {
  background: rgba(255, 255, 255, 0.7);
  backdrop-filter: blur(20px) saturate(180%);
  -webkit-backdrop-filter: blur(20px) saturate(180%);
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  border-bottom: 1px solid rgba(255, 255, 255, 0.3);
  padding: 0;
  height: 80px !important;
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  width: 100%;
  z-index: 1000;
  transition: background 0.3s ease;
}

.header::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: linear-gradient(to bottom, rgba(255, 255, 255, 0.1), transparent);
  pointer-events: none;
  z-index: -1;
}

.header-content {
  max-width: 1200px;
  margin: 0 auto;
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 100%;
  padding: 0 20px;
  position: relative;
  z-index: 1001;
}

.logo {
  display: flex;
  align-items: center;
  gap: 10px;
  font-size: 24px;
  font-weight: 600;
  color: #2c3e50;
  flex-shrink: 0;
  cursor: pointer;
  transition: opacity 0.3s ease;
}

.logo:hover {
  opacity: 0.8;
}

.logo img {
  height: 50px;
  width: auto;
}

.logo-text {
  white-space: nowrap;
}

.nav-menu {
  flex: 1;
  border-bottom: none;
  margin-left: 40px;
  display: flex;
  align-items: center;
  background: transparent !important;
}

.nav-menu .el-menu-item,
.nav-menu .el-sub-menu__title {
  font-size: 16px !important;
  height: 80px;
  line-height: 80px;
  display: flex;
  align-items: center;
  padding: 0 20px;
  background: transparent !important;
  border: none !important;
  border-bottom: none !important;
  color: #2c3e50;
  transition: color 0.3s ease;
}

.nav-menu .el-menu-item:hover,
.nav-menu .el-sub-menu__title:hover {
  background: transparent !important;
  background-color: transparent !important;
  color: var(--el-color-primary);
}

.nav-menu .el-menu-item.is-active {
  background: transparent !important;
  background-color: transparent !important;
  color: var(--el-color-primary);
  border-bottom: 2px solid var(--el-color-primary);
}

.nav-menu .el-menu-item.is-active::after {
  display: none !important;
}

.nav-menu .el-sub-menu {
  background: transparent !important;
}

.nav-menu .el-sub-menu .el-sub-menu__title {
  background: transparent !important;
  background-color: transparent !important;
  font-size: 16px !important;
}

.nav-menu .el-sub-menu.is-opened .el-sub-menu__title {
  background: transparent !important;
  background-color: transparent !important;
}

/* 覆盖Element Plus菜单的所有背景样式 */
.nav-menu .el-menu-item,
.nav-menu .el-sub-menu__title,
.nav-menu .el-sub-menu {
  background-color: transparent !important;
  background-image: none !important;
}

.nav-menu .el-menu-item:focus,
.nav-menu .el-menu-item:hover,
.nav-menu .el-sub-menu__title:focus,
.nav-menu .el-sub-menu__title:hover {
  background-color: transparent !important;
  background-image: none !important;
}

.nav-menu .el-menu-item.is-active,
.nav-menu .el-menu-item.is-active:focus,
.nav-menu .el-menu-item.is-active:hover {
  background-color: transparent !important;
  background-image: none !important;
}

/* 移除菜单项的所有边框和阴影 */
.nav-menu .el-menu-item,
.nav-menu .el-sub-menu__title {
  box-shadow: none !important;
  border-radius: 0 !important;
}

.nav-menu .el-sub-menu {
  display: flex;
  align-items: center;
}

.nav-menu .el-sub-menu__title {
  display: flex;
  align-items: center;
}

.contact-btn-header {
  margin-left: 20px;
  flex-shrink: 0;
  white-space: nowrap;
}

.lang-switch {
  display: flex;
  align-items: center;
  margin-left: 20px;
  flex-shrink: 0;
}

.mobile-menu-toggle {
  display: none;
  font-size: 24px;
  cursor: pointer;
  color: #2c3e50;
  margin-left: 15px;
  z-index: 1001;
  position: relative;
}

.mobile-menu {
  border: none;
  padding: 10px 0;
}

.mobile-menu .el-menu-item,
.mobile-menu .el-sub-menu__title {
  height: auto;
  min-height: 50px;
  line-height: 1.5;
  padding: 15px 20px;
  margin: 8px 15px;
  border: 1px solid rgba(255, 255, 255, 0.3);
  border-radius: 12px;
  background: rgba(255, 255, 255, 0.6);
  backdrop-filter: blur(10px);
  -webkit-backdrop-filter: blur(10px);
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  text-align: left;
  font-size: 15px;
  font-weight: 500;
  color: #303133;
}

.mobile-menu .el-menu-item:hover,
.mobile-menu .el-sub-menu__title:hover {
  background: rgba(64, 158, 255, 0.2);
  border-color: rgba(64, 158, 255, 0.5);
  color: #409eff;
  transform: translateX(-3px);
  box-shadow: 0 4px 12px rgba(64, 158, 255, 0.2);
}

.mobile-menu .el-menu-item.is-active {
  background: linear-gradient(135deg, rgba(64, 158, 255, 0.8) 0%, rgba(103, 194, 58, 0.8) 100%);
  backdrop-filter: blur(10px);
  -webkit-backdrop-filter: blur(10px);
  border-color: rgba(64, 158, 255, 0.5);
  color: #fff;
}

.mobile-menu .el-sub-menu {
  width: 100%;
}

.mobile-menu .el-sub-menu .el-sub-menu__title {
  width: 100%;
}

.mobile-menu .el-sub-menu.is-opened .el-sub-menu__title {
  background: linear-gradient(135deg, rgba(64, 158, 255, 0.8) 0%, rgba(103, 194, 58, 0.8) 100%);
  backdrop-filter: blur(10px);
  -webkit-backdrop-filter: blur(10px);
  border-color: rgba(64, 158, 255, 0.5);
  color: #fff;
}

.mobile-menu .el-sub-menu .el-menu {
  position: relative;
  background: rgba(245, 247, 250, 0.6);
  backdrop-filter: blur(10px);
  -webkit-backdrop-filter: blur(10px);
  border-radius: 8px;
  margin: 8px 15px;
  padding: 8px;
  z-index: 10;
  border: 1px solid rgba(255, 255, 255, 0.3);
  box-shadow: inset 0 2px 4px rgba(0, 0, 0, 0.06);
}

.mobile-menu .el-sub-menu .el-menu .el-menu-item {
  min-height: 45px;
  padding: 12px 16px;
  font-size: 14px;
  background: rgba(255, 255, 255, 0.6);
  backdrop-filter: blur(10px);
  -webkit-backdrop-filter: blur(10px);
  border: 1px solid rgba(255, 255, 255, 0.3);
  margin: 4px 0;
}

.mobile-menu .el-sub-menu .el-menu .el-menu-item:hover {
  background: rgba(236, 245, 255, 0.8);
  border-color: rgba(64, 158, 255, 0.5);
  color: #409eff;
}

.mobile-menu .el-divider {
  margin: 12px 15px;
  border-color: rgba(255, 255, 255, 0.3);
}

.mobile-menu .contact-menu-item {
  background: linear-gradient(135deg, rgba(64, 158, 255, 0.9) 0%, rgba(103, 194, 58, 0.9) 100%) !important;
  backdrop-filter: blur(10px);
  -webkit-backdrop-filter: blur(10px);
  border-color: rgba(64, 158, 255, 0.6) !important;
  color: #fff !important;
  font-weight: 600;
  margin-top: 10px;
}

.mobile-menu .contact-menu-item:hover {
  background: linear-gradient(135deg, rgba(64, 158, 255, 1) 0%, rgba(103, 194, 58, 1) 100%) !important;
  transform: translateX(-3px);
  box-shadow: 0 4px 16px rgba(64, 158, 255, 0.4);
}

/* 移动端抽屉菜单样式 - 右侧弹出，毛玻璃效果 */
.mobile-drawer {
  position: fixed !important;
  top: 0 !important;
  left: 0 !important;
  right: 0 !important;
  bottom: 0 !important;
  z-index: 1002 !important;
}

.mobile-drawer :deep(.el-overlay) {
  position: fixed !important;
  top: 0 !important;
  left: 0 !important;
  right: 0 !important;
  bottom: 0 !important;
  z-index: 1001 !important;
  background: rgba(0, 0, 0, 0.3) !important;
  backdrop-filter: blur(10px);
  -webkit-backdrop-filter: blur(10px);
}

.mobile-drawer :deep(.el-drawer) {
  top: 0 !important;
  bottom: 0 !important;
  right: 0 !important;
  left: auto !important;
  width: 280px !important;
  height: 100vh !important;
  z-index: 1002 !important;
  position: fixed !important;
  display: flex !important;
  flex-direction: column !important;
  background: rgba(255, 255, 255, 0.85) !important;
  backdrop-filter: blur(20px) saturate(180%);
  -webkit-backdrop-filter: blur(20px) saturate(180%);
  box-shadow: -4px 0 20px rgba(0, 0, 0, 0.15);
  border-left: 1px solid rgba(255, 255, 255, 0.3);
}

.mobile-drawer :deep(.el-drawer__header) {
  margin-bottom: 0;
  padding: 20px;
  border-bottom: 1px solid rgba(255, 255, 255, 0.3);
  flex-shrink: 0;
  background: rgba(255, 255, 255, 0.5);
  backdrop-filter: blur(10px);
  -webkit-backdrop-filter: blur(10px);
}

.mobile-drawer :deep(.el-drawer__body) {
  padding: 0 !important;
  overflow-y: auto !important;
  overflow-x: hidden !important;
  flex: 1;
  display: flex;
  flex-direction: column;
}

/* 移动端适配 */
@media (max-width: 768px) {
  .header {
    height: 60px !important;
  }

  .header-content {
    padding: 0 15px;
  }

  .header-content {
    justify-content: flex-start;
    gap: 8px;
  }

  .logo {
    font-size: 18px;
    flex: 1;
    min-width: 0;
  }

  .logo img {
    height: 40px;
  }

  /* 移动端显示公司名称 */
  .logo-text {
    display: block !important;
    font-size: 16px;
    max-width: 150px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }

  .desktop-menu {
    display: none !important;
  }

  .mobile-menu-toggle {
    display: block;
    z-index: 1001;
    flex-shrink: 0;
  }

  .contact-btn-header {
    display: none;
  }

  .lang-switch {
    margin-left: 10px;
    flex-shrink: 0;
  }

  .lang-switch .el-button {
    font-size: 14px;
    padding: 5px;
  }
}

@media (max-width: 480px) {
  .header {
    height: 60px !important;
  }

  .header-content {
    padding: 0 10px;
  }

  .logo {
    font-size: 16px;
  }

  .logo img {
    height: 35px;
  }

  .logo-text {
    display: block !important;
    font-size: 14px;
    max-width: 80px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }

  .mobile-menu-toggle {
    font-size: 20px;
    margin-left: 10px;
  }

  .lang-switch {
    display: flex !important;
    margin-left: 8px;
    flex-shrink: 0;
  }

  .lang-switch .el-button {
    font-size: 12px;
    padding: 4px 6px;
    min-width: auto;
  }

  .lang-switch .el-divider {
    margin: 0 4px;
  }

  /* 小屏幕下抽屉菜单从底部弹出 */
  .mobile-drawer :deep(.el-drawer) {
    bottom: 0 !important;
    top: auto !important;
    max-height: 80vh !important;
  }

  .mobile-drawer :deep(.el-drawer__body) {
    max-height: calc(80vh - 80px) !important;
  }
}
</style>

