<template>
  <el-container class="admin-layout">
    <el-aside width="200px" class="admin-sidebar">
      <div class="logo">
        <h3>管理后台</h3>
      </div>
      <el-menu
        :default-active="activeMenu"
        router
        class="admin-menu"
      >
        <el-menu-item index="/admin/dashboard">
          <el-icon><Odometer /></el-icon>
          <span>仪表盘</span>
        </el-menu-item>
        <el-menu-item index="/admin/home-config">
          <el-icon><HomeFilled /></el-icon>
          <span>首页配置</span>
        </el-menu-item>
        <el-sub-menu index="/admin/about-group">
          <template #title>
            <el-icon><InfoFilled /></el-icon>
            <span>关于我们</span>
          </template>
          <el-menu-item index="/admin/company">
            <span>公司简介</span>
          </el-menu-item>
          <el-menu-item index="/admin/advantage">
            <span>企业优势</span>
          </el-menu-item>
          <el-menu-item index="/admin/corporate-culture">
            <span>企业文化</span>
          </el-menu-item>
        </el-sub-menu>
        <el-menu-item index="/admin/laboratory">
          <el-icon><School /></el-icon>
          <span>实验室管理</span>
        </el-menu-item>
        <el-sub-menu index="/admin/certification-group">
          <template #title>
            <el-icon><List /></el-icon>
            <span>认证服务</span>
          </template>
          <el-menu-item index="/admin/certification-category">
            <span>分类管理</span>
          </el-menu-item>
          <el-menu-item index="/admin/certification">
            <span>服务管理</span>
          </el-menu-item>
        </el-sub-menu>
        <el-menu-item index="/admin/honor">
          <el-icon><Trophy /></el-icon>
          <span>荣誉资质</span>
        </el-menu-item>
        <el-menu-item index="/admin/certificate">
          <el-icon><DocumentCopy /></el-icon>
          <span>证书管理</span>
        </el-menu-item>
        <el-menu-item index="/admin/news">
          <el-icon><Document /></el-icon>
          <span>新闻管理</span>
        </el-menu-item>
        <el-menu-item index="/admin/message">
          <el-icon><Message /></el-icon>
          <span>留言管理</span>
        </el-menu-item>
        <el-menu-item index="/admin/chat">
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
    })

    // 监听路由变化，更新激活菜单
    watch(() => route.path, (newPath) => {
      activeMenu.value = newPath
    })

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
      handleLogout
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

