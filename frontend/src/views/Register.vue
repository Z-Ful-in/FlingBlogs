<script setup>
import { ref, reactive } from 'vue'
import userApi from '@/api/userApi'
import { useRouter } from 'vue-router'
import {ElMessage} from "element-plus";

const router = useRouter()
const loading = ref(false)
const form = reactive({
  username: '',
  password: '',
  email: ''
})

const handleRegister = async () => {
  if(!form.username || !form.password) {
    ElMessage.warning('请填写完整信息')
    return
  }
  loading.value = true
  try {
    const res = await userApi.register(form)
    ElMessage.success(res.message || '注册成功！请登录')
    router.push('/login')
  } catch (error) {
    ElMessage.error(error.response?.data?.message || '注册失败')
  } finally {
    loading.value = false
  }
}
</script>

<template>
  <div class="auth-container">
    <el-card class="auth-card">
      <template #header>
        <h2 class="auth-title">注册新账户</h2>
      </template>

      <el-form :model="form" size="large">
        <el-form-item>
          <el-input v-model="form.username" placeholder="用户名" :prefix-icon="User" />
        </el-form-item>
        <el-form-item>
          <el-input v-model="form.email" placeholder="邮箱 (可选)" :prefix-icon="Message" />
        </el-form-item>
        <el-form-item>
          <el-input
              v-model="form.password"
              type="password"
              placeholder="密码"
              :prefix-icon="Lock"
              show-password
              @keyup.enter="handleRegister"
          />
        </el-form-item>
        <el-button type="primary" class="submit-btn" :loading="loading" @click="handleRegister">
          注册
        </el-button>
      </el-form>

      <div class="auth-footer">
        已有账号? <router-link to="/login" class="link">去登录</router-link>
      </div>
    </el-card>
  </div>

</template>

<style scoped>
/* 复用 Login 的样式，实际开发中应提取到全局 CSS 中 */
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