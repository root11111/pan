<template>
  <div id="app">
    <router-view v-slot="{ Component, route }">
      <!-- 后台路由不使用 transition，避免整页刷新 -->
      <transition v-if="!route.path.startsWith('/admin')" name="page" mode="out-in">
        <component :is="Component" :key="route.path" />
      </transition>
      <!-- 后台路由直接渲染，不添加 transition -->
      <component v-else :is="Component" :key="route.path" />
    </router-view>
    <ContactFloat />
  </div>
</template>

<script>
import ContactFloat from './components/ContactFloat.vue'

export default {
  name: 'App',
  components: {
    ContactFloat
  }
}
</script>

<style>
#app {
  min-height: 100vh;
  width: 100%;
  display: flex;
  flex-direction: column;
}

#app > *:not(.contact-float) {
  width: 100%;
}

/* 页面过渡动画 */
.page-enter-active,
.page-leave-active {
  transition: all 0.4s cubic-bezier(0.4, 0, 0.2, 1);
}

.page-enter-from {
  opacity: 0;
  transform: translateY(20px);
}

.page-leave-to {
  opacity: 0;
  transform: translateY(-20px);
}
</style>

