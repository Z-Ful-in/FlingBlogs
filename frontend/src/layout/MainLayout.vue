<script setup>
import { useRouter } from 'vue-router';
import { ref } from 'vue';

const router = useRouter();

const username = localStorage.getItem('username') || 'User';

const handleLogout = () => {
  localStorage.removeItem('token');
  localStorage.removeItem('username');
  ElMessage.success('已退出登录');
  router.push('/login');
};
</script>

<template>
  <el-container class="layout-container">
    <el-header class="header">
      <div class="logo">
        <el-icon :size="24" color="#409EFF"><EditPen /></el-icon>
        <span>FlingBlogs</span>
      </div>

      <el-menu
          :default-active="$route.path"
          class="header-menu"
          mode="horizontal"
          router
          :ellipsis="false"
      >
        <el-menu-item index="/home">我的文章</el-menu-item>
        <el-menu-item index="/write">写文章</el-menu-item>
      </el-menu>

      <div class="user-area">
        <el-dropdown>
          <span class="el-dropdown-link">
            {{ username }}
            <el-icon class="el-icon--right"><arrow-down /></el-icon>
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item @click="handleLogout">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </el-header>

    <el-main class="main-content">
      <router-view v-slot="{ Component }">
        <transition name="fade" mode="out-in">
          <component :is="Component" />
        </transition>
      </router-view>
    </el-main>
  </el-container>
</template>

<style scoped>
.layout-container {
  min-height: 100vh;
  background-color: #f5f7fa;
}

.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  background-color: #fff;
  border-bottom: solid 1px #dcdfe6;
  padding: 0 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
}

.logo {
  display: flex;
  align-items: center;
  font-size: 20px;
  font-weight: bold;
  color: #303133;
  gap: 8px;
}

.header-menu {
  border-bottom: none !important;
  flex-grow: 1;
  justify-content: center;
  background-color: transparent;
}

.user-area {
  display: flex;
  align-items: center;
}

.el-dropdown-link {
  cursor: pointer;
  display: flex;
  align-items: center;
  color: #409EFF;
  font-weight: 500;
}

.main-content {
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
  width: 100%;
}

/* 简单的页面切换动画 */
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.2s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>