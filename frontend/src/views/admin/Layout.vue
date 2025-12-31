<template>
  <el-container class="admin-layout">
    <el-aside width="200px" class="admin-sidebar">
      <div class="logo">
        <h3>管理后台</h3>
      </div>
      <el-menu
        :default-active="activeMenu"
        class="admin-menu"
        :router="false"
      >
        <el-menu-item index="/admin/dashboard" @click="handleMenuClick('/admin/dashboard', $event)">
          <el-icon><Odometer /></el-icon>
          <span>仪表盘</span>
        </el-menu-item>
        <el-menu-item index="/admin/home-config" @click="handleMenuClick('/admin/home-config', $event)">
          <el-icon><HomeFilled /></el-icon>
          <span>首页配置</span>
        </el-menu-item>
        <el-sub-menu index="/admin/about-group">
          <template #title>
            <el-icon><InfoFilled /></el-icon>
            <span>关于我们</span>
          </template>
          <el-menu-item index="/admin/company" @click="handleMenuClick('/admin/company', $event)">
            <span>公司简介</span>
          </el-menu-item>
          <el-menu-item index="/admin/advantage" @click="handleMenuClick('/admin/advantage', $event)">
            <span>企业优势</span>
          </el-menu-item>
          <el-menu-item index="/admin/corporate-culture" @click="handleMenuClick('/admin/corporate-culture', $event)">
            <span>企业文化</span>
          </el-menu-item>
        </el-sub-menu>
        <el-menu-item index="/admin/laboratory" @click="handleMenuClick('/admin/laboratory', $event)">
          <el-icon><School /></el-icon>
          <span>实验室管理</span>
        </el-menu-item>
        <el-sub-menu index="/admin/certification-group">
          <template #title>
            <el-icon><List /></el-icon>
            <span>认证服务</span>
          </template>
          <el-menu-item index="/admin/certification-category" @click="handleMenuClick('/admin/certification-category', $event)">
            <span>分类管理</span>
          </el-menu-item>
          <el-menu-item index="/admin/certification" @click="handleMenuClick('/admin/certification', $event)">
            <span>服务管理</span>
          </el-menu-item>
        </el-sub-menu>
        <el-menu-item index="/admin/honor" @click="handleMenuClick('/admin/honor', $event)">
          <el-icon><Trophy /></el-icon>
          <span>荣誉资质</span>
        </el-menu-item>
        <el-menu-item index="/admin/certificate" @click="handleMenuClick('/admin/certificate', $event)">
          <el-icon><DocumentCopy /></el-icon>
          <span>证书管理</span>
        </el-menu-item>
        <el-menu-item index="/admin/news" @click="handleMenuClick('/admin/news', $event)">
          <el-icon><Document /></el-icon>
          <span>新闻管理</span>
        </el-menu-item>
        <el-menu-item index="/admin/message" @click="handleMenuClick('/admin/message', $event)">
          <el-icon><Message /></el-icon>
          <span>留言管理</span>
        </el-menu-item>
        <el-menu-item index="/admin/chat" @click="handleMenuClick('/admin/chat', $event)">
          <el-icon><ChatDotRound /></el-icon>
          <span>实时聊天</span>
        </el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header class="admin-header">
        <div class="header-left">
          <span>蓝泰检测</span>
        </div>
        <div class="header-right">
          <el-button type="primary" size="small" @click="goToFrontend">
            <el-icon style="margin-right: 5px;"><Link /></el-icon>
            进入前台
          </el-button>
          <span>{{ nickname }}</span>
          <el-button type="danger" size="small" @click="handleLogout">退出</el-button>
        </div>
      </el-header>
      <el-main class="admin-main">
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import { ref, onMounted, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessageBox } from 'element-plus'
import { Odometer, OfficeBuilding, School, List, Document, Message, ChatDotRound, Link, Setting, DocumentCopy, Trophy, InfoFilled, HomeFilled } from '@element-plus/icons-vue'

export default {
  name: 'AdminLayout',
  components: {
    Odometer,
    OfficeBuilding,
    School,
    List,
    Document,
    Message,
    ChatDotRound,
    Link,
    Setting,
    DocumentCopy,
    Trophy,
    InfoFilled,
    HomeFilled
  },
  setup() {
    const route = useRoute()
    const router = useRouter()
    const activeMenu = ref('/admin/dashboard')
    const nickname = ref('')

    onMounted(() => {
      activeMenu.value = route.path
      nickname.value = localStorage.getItem('admin_nickname') || '管理员'
      
      // 在菜单容器上添加全局点击监听器（捕获阶段），拦截所有链接点击
      const menuContainer = document.querySelector('.admin-menu')
      if (menuContainer) {
        menuContainer.addEventListener('click', (e) => {
          // 查找点击的元素是否是链接或其父元素是链接
          const link = e.target.closest('a')
          if (link) {
            e.preventDefault()
            e.stopPropagation()
            e.stopImmediatePropagation()
            
            // 获取路径
            const href = link.getAttribute('href') || link.href
            if (href && href !== '#' && href !== 'javascript:void(0)') {
              // 提取路径（移除域名等）
              const path = href.replace(/^https?:\/\/[^\/]+/, '') || href
              if (path && path !== route.path && path.startsWith('/')) {
                router.push(path).catch(err => {
                  if (err.name !== 'NavigationDuplicated') {
                    console.error('路由导航错误:', err)
                  }
                })
              }
            }
            return false
          }
        }, true) // 使用捕获阶段，确保优先执行
      }
      
      // 定期检查和移除所有链接的 href 属性
      const removeHrefs = () => {
        const links = document.querySelectorAll('.admin-menu .el-menu-item a')
        links.forEach(link => {
          if (link.href && !link.href.includes('javascript:')) {
            const path = link.getAttribute('href') || link.href.replace(/^https?:\/\/[^\/]+/, '')
            link.removeAttribute('href')
            link.setAttribute('data-path', path)
            link.style.cursor = 'pointer'
            // 确保链接不会触发导航
            link.onclick = (e) => {
              e.preventDefault()
              e.stopPropagation()
              e.stopImmediatePropagation()
              return false
            }
          }
        })
      }
      
      // 立即执行一次
      setTimeout(removeHrefs, 50)
      
      // 使用 MutationObserver 监听 DOM 变化，持续移除 href
      const observer = new MutationObserver(() => {
        removeHrefs()
      })
      
      if (menuContainer) {
        observer.observe(menuContainer, {
          childList: true,
          subtree: true,
          attributes: true,
          attributeFilter: ['href']
        })
      }
    })

    // 监听路由变化，更新激活菜单
    watch(() => route.path, (newPath) => {
      activeMenu.value = newPath
    })

    // 处理菜单点击，阻止默认行为并使用 Vue Router 导航
    const handleMenuClick = (path, event) => {
      // 彻底阻止所有默认行为和事件冒泡
      if (event) {
        if (typeof event.preventDefault === 'function') {
          event.preventDefault()
        }
        if (typeof event.stopPropagation === 'function') {
          event.stopPropagation()
        }
        if (typeof event.stopImmediatePropagation === 'function') {
          event.stopImmediatePropagation()
        }
      }
      
      // 立即移除当前菜单项内所有链接的 href
      if (event && event.target) {
        const menuItem = event.target.closest('.el-menu-item')
        if (menuItem) {
          const links = menuItem.querySelectorAll('a')
          links.forEach(link => {
            if (link.href && !link.href.includes('javascript:')) {
              link.removeAttribute('href')
              link.style.cursor = 'pointer'
            }
          })
        }
      }
      
      if (path && path !== route.path) {
        // 使用 Vue Router 进行客户端导航
        router.push(path).catch(err => {
          // 忽略重复导航错误
          if (err.name !== 'NavigationDuplicated') {
            console.error('路由导航错误:', err)
          }
        })
      }
      
      // 返回 false 进一步阻止默认行为
      return false
    }

    const goToFrontend = () => {
      // 在新标签页打开前台首页
      window.open('/', '_blank')
    }

    const handleLogout = () => {
      ElMessageBox.confirm('确定要退出登录吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        localStorage.removeItem('admin_token')
        localStorage.removeItem('admin_username')
        localStorage.removeItem('admin_nickname')
        router.push('/admin/login')
      })
    }

    return {
      activeMenu,
      nickname,
      goToFrontend,
      handleLogout,
      handleMenuClick
    }
  }
}
</script>

<style scoped>
.admin-layout {
  height: 100vh;
}

.admin-sidebar {
  background: #304156;
  color: #fff;
}

.logo {
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-bottom: 1px solid #434a55;
}

.logo h3 {
  margin: 0;
  color: #fff;
  font-size: 18px;
}

.admin-menu {
  border: none;
  background: #304156;
}

.admin-menu .el-menu-item {
  color: #bfcbd9;
}

.admin-menu .el-menu-item:hover {
  background: #263445;
}

.admin-menu .el-menu-item.is-active {
  background: #409eff;
  color: #fff;
}

/* 完全阻止菜单项内的链接默认行为，使用 Vue Router 导航 */
.admin-menu :deep(.el-menu-item a),
.admin-menu :deep(.el-sub-menu .el-menu-item a) {
  pointer-events: none !important;
  text-decoration: none !important;
  color: inherit !important;
  cursor: pointer !important;
}

/* 阻止所有链接的点击行为 */
.admin-menu :deep(.el-menu-item a[href]),
.admin-menu :deep(.el-sub-menu .el-menu-item a[href]) {
  pointer-events: none !important;
}

/* 确保菜单项本身可以点击 */
.admin-menu :deep(.el-menu-item),
.admin-menu :deep(.el-sub-menu .el-menu-item) {
  cursor: pointer !important;
}

/* 阻止菜单项内的任何链接跳转 */
.admin-menu :deep(.el-menu-item) a,
.admin-menu :deep(.el-sub-menu .el-menu-item) a {
  pointer-events: none !important;
}

/* 子菜单样式 */
.admin-menu :deep(.el-sub-menu__title) {
  color: #bfcbd9 !important;
}

.admin-menu :deep(.el-sub-menu__title:hover) {
  background: #263445;
  color: #fff !important;
}

/* 子菜单标题中的图标和文字 */
.admin-menu :deep(.el-sub-menu__title .el-icon) {
  color: #bfcbd9 !important;
}

.admin-menu :deep(.el-sub-menu__title span) {
  color: #bfcbd9 !important;
}

.admin-menu :deep(.el-sub-menu__title:hover .el-icon) {
  color: #fff !important;
}

.admin-menu :deep(.el-sub-menu__title:hover span) {
  color: #fff !important;
}

/* 子菜单项样式 - 使用深度选择器确保样式生效 */
.admin-menu :deep(.el-sub-menu .el-menu-item) {
  background: #304156 !important;
  color: #bfcbd9 !important;
  padding-left: 50px !important;
}

.admin-menu :deep(.el-sub-menu .el-menu-item:hover) {
  background: #263445 !important;
  color: #fff !important;
}

.admin-menu :deep(.el-sub-menu .el-menu-item.is-active) {
  background: #409eff !important;
  color: #fff !important;
}

/* 子菜单项中的文字和图标 - 使用深度选择器 */
.admin-menu :deep(.el-sub-menu .el-menu-item span) {
  color: inherit !important;
}

/* 子菜单项中的所有元素 */
.admin-menu :deep(.el-sub-menu .el-menu-item *) {
  color: inherit !important;
}

/* 子菜单打开时的标题样式 */
.admin-menu :deep(.el-sub-menu.is-opened > .el-sub-menu__title) {
  color: #fff !important;
}

.admin-menu :deep(.el-sub-menu.is-opened > .el-sub-menu__title .el-icon) {
  color: #fff !important;
}

.admin-menu :deep(.el-sub-menu.is-opened > .el-sub-menu__title span) {
  color: #fff !important;
}

/* 子菜单图标样式 */
.admin-menu .el-sub-menu .el-sub-menu__icon {
  color: #bfcbd9 !important;
}

.admin-menu .el-sub-menu.is-opened .el-sub-menu__icon {
  color: #fff !important;
}

.admin-header {
  background: #fff;
  border-bottom: 1px solid #e4e7ed;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
}

.header-right {
  display: flex;
  align-items: center;
  gap: 15px;
}

.admin-main {
  background: #f0f2f5;
  padding: 20px;
}
</style>

