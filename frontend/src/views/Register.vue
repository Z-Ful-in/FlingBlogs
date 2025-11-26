<script setup>
import userApi from '../api/userApi.js'
import {ref} from "vue";
import {useRouter} from "vue-router";

const username = ref("")
const password = ref("")
const email = ref("")
const router = useRouter()

const handleRegister = async () => {
  try {
    const response = await userApi.register({
      username: username.value,
      password: password.value,
      email: email.value
    })
    if (response && response.message === '注册成功') {
      alert('注册成功！去登录吧')
      router.push('/login')
    } else {
      // 如果后端逻辑有变化，这里作为一个兜底
      alert(response.message || '注册成功')
      router.push('/login')
    }
  }catch (error) {
    alert('注册失败：' + (error.response?.data?.message || '未知错误'))
  }
}

</script>

<template>
  <div class="auth-container">
    <h2>注册 FlingBlogs</h2>
    <input v-model="email" type="email" placeholder="Email" />
    <input v-model="username" type="text" placeholder="用户名" />
    <input v-model="password" type="password" placeholder="密码" />
    <button @click="handleRegister">注册</button>
    <p>已有账号?<router-link to="/login">去登录</router-link></p>
  </div>

</template>

<style scoped>
.auth-container {
  display: flex;
  flex-direction: column;
  max-width: 300px;
  margin: 50px auto;
  gap: 10px;
}
input, button { padding: 8px; }
</style>