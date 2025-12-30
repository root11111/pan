import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import About from '../views/About.vue'
import Laboratory from '../views/Laboratory.vue'
import Certification from '../views/Certification.vue'
import CertificationDetail from '../views/CertificationDetail.vue'
import Honor from '../views/Honor.vue'
import Certificate from '../views/Certificate.vue'
import News from '../views/News.vue'
import NewsDetail from '../views/NewsDetail.vue'
import Message from '../views/Message.vue'
import Contact from '../views/Contact.vue'
import AdminLogin from '../views/admin/Login.vue'
import AdminLayout from '../views/admin/Layout.vue'
import AdminDashboard from '../views/admin/Dashboard.vue'
import AdminCompany from '../views/admin/Company.vue'
import AdminLaboratory from '../views/admin/Laboratory.vue'
import AdminCertification from '../views/admin/Certification.vue'
import AdminCertificationCategory from '../views/admin/CertificationCategory.vue'
import AdminNews from '../views/admin/News.vue'
import AdminMessage from '../views/admin/Message.vue'
import AdminChat from '../views/admin/Chat.vue'
import AdminMenuConfig from '../views/admin/MenuConfig.vue'
import AdminCertificate from '../views/admin/Certificate.vue'
import AdminHonor from '../views/admin/Honor.vue'
import AdminEnterpriseAdvantage from '../views/admin/EnterpriseAdvantage.vue'
import AdminCorporateCulture from '../views/admin/CorporateCulture.vue'
import AdminHomeConfig from '../views/admin/HomeConfig.vue'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/about',
    name: 'About',
    component: About
  },
  {
    path: '/laboratory',
    name: 'Laboratory',
    component: Laboratory
  },
  {
    path: '/certification',
    name: 'Certification',
    component: Certification
  },
  {
    path: '/certification/:id',
    name: 'CertificationDetail',
    component: CertificationDetail
  },
  {
    path: '/honor',
    name: 'Honor',
    component: Honor
  },
  {
    path: '/certificate',
    name: 'Certificate',
    component: Certificate
  },
  {
    path: '/news',
    name: 'News',
    component: News
  },
  {
    path: '/news/:id',
    name: 'NewsDetail',
    component: NewsDetail
  },
  {
    path: '/message',
    name: 'Message',
    component: Message
  },
  {
    path: '/contact',
    name: 'Contact',
    component: Contact
  },
  {
    path: '/admin/login',
    name: 'AdminLogin',
    component: AdminLogin
  },
  {
    path: '/admin',
    component: AdminLayout,
    redirect: '/admin/dashboard',
    meta: { requiresAuth: true },
    children: [
      {
        path: 'dashboard',
        name: 'AdminDashboard',
        component: AdminDashboard
      },
      {
        path: 'company',
        name: 'AdminCompany',
        component: AdminCompany
      },
      {
        path: 'advantage',
        name: 'AdminEnterpriseAdvantage',
        component: AdminEnterpriseAdvantage
      },
      {
        path: 'corporate-culture',
        name: 'AdminCorporateCulture',
        component: AdminCorporateCulture
      },
      {
        path: 'laboratory',
        name: 'AdminLaboratory',
        component: AdminLaboratory
      },
      {
        path: 'certification',
        name: 'AdminCertification',
        component: AdminCertification
      },
      {
        path: 'certification-category',
        name: 'AdminCertificationCategory',
        component: AdminCertificationCategory
      },
      {
        path: 'honor',
        name: 'AdminHonor',
        component: AdminHonor
      },
      {
        path: 'certificate',
        name: 'AdminCertificate',
        component: AdminCertificate
      },
      {
        path: 'home-config',
        name: 'AdminHomeConfig',
        component: AdminHomeConfig
      },
      {
        path: 'menu-config',
        name: 'AdminMenuConfig',
        component: AdminMenuConfig
      },
      {
        path: 'news',
        name: 'AdminNews',
        component: AdminNews
      },
      {
        path: 'message',
        name: 'AdminMessage',
        component: AdminMessage
      },
      {
        path: 'chat',
        name: 'AdminChat',
        component: AdminChat
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes,
  scrollBehavior(to, from, savedPosition) {
    if (savedPosition) {
      return savedPosition
    } else if (to.hash) {
      // 如果有hash锚点，滚动到对应元素
      return {
        el: to.hash,
        behavior: 'smooth',
        top: 80 // 考虑顶部固定菜单的高度
      }
    } else {
      return { top: 0 }
    }
  }
})

// 路由守卫
router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('admin_token')
  
  if (to.meta?.requiresAuth && !token) {
    next('/admin/login')
  } else if (to.path === '/admin/login' && token) {
    next('/admin/dashboard')
  } else {
    next()
  }
})

export default router
