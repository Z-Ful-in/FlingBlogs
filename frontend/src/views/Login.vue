<script setup>
import { ref, reactive } from 'vue'
import userApi from '@/api/userApi'
import { useRouter } from 'vue-router'
import { User, Lock } from '@element-plus/icons-vue'
import {ElMessage} from "element-plus";

const router = useRouter()
const loading = ref(false)
const form = reactive({
  username: '',
  password: ''
})

const handleLogin = async () => {
  if(!form.username || !form.password) {
    ElMessage.warning('请输入用户名和密码')
    return
  }
  loading.value = true
  try {
    const res = await userApi.login(form)
    if (res.token) {
      localStorage.setItem('token', res.token)
      localStorage.setItem('username', form.username)
      ElMessage.success(res.message || '登录成功')
      router.push('/home')
    } else {
      ElMessage.error(res.message || '登录失败')
    }
  } catch (error) {
    ElMessage.error(error.response?.data?.message || '请求出错')
  } finally {
    loading.value = false
  }
}

</script>

<template>
  <div class="auth-container">
    <el-card class="auth-card">
      <template #header>
        <h2 class="auth-title">登录 FlingBlogs</h2>
      </template>

      <el-form :model="form" size="large">
        <el-form-item>
          <el-input v-model="form.username" placeholder="用户名" :prefix-icon="User" />
        </el-form-item>
        <el-form-item>
          <el-input
              v-model="form.password"
              type="password"
              placeholder="密码"
              :prefix-icon="Lock"
              show-password
              @keyup.enter="handleLogin"
          />
        </el-form-item>
        <el-button type="primary" class="submit-btn" :loading="loading" @click="handleLogin">
          {{ loading ? '登录中...' : '立即登录' }}
        </el-button>
      </el-form>

      <div class="auth-footer">
        还没有账号? <router-link to="/register" class="link">去注册</router-link>
      </div>
    </el-card>
  </div>
</template>

<style scoped>
/* 登录注册页的通用样式，可以提取出来 */
.auth-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
}
.auth-card {
  width: 400px;
  border-radius: 8px;
}
.auth-title {
  text-align: center;
  margin: 0;
  color: #303133;
}
.submit-btn {
  width: 100%;
  font-weight: bold;
}
.auth-footer {
  margin-top: 15px;
  text-align: center;
  font-size: 14px;
  color: #606266;
}
.link {
  color: #409EFF;
  text-decoration: none;
}
</style>