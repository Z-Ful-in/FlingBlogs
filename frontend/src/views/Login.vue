<script setup>
import userApi from '../api/userApi.js'
import {ref} from "vue";
import {useRouter} from "vue-router";

const username = ref("")
const password = ref("")
const router = useRouter()

const handleLogin = async () => {
  try{
    const res = await userApi.login(
        {
          username: username.value,
          password: password.value
        })

    // 后端成功时返回token和message, 失败时返回message
    if(res.token){
      // 存token
      localStorage.setItem("token", res.token)
      localStorage.setItem("username", username.value)

      alert(res.message)
      router.push('/home')
    } else {
      alert( res.message)
    }
  }catch (error){
    alert("请求出错")
    console.log(error)
  }
}

</script>

<template>
  <div class="auth-container">
    <h2>登录</h2>
    <input v-model="username" placeholder="用户名" />
    <input v-model="password" type="password" placeholder="密码" />
    <button @click="handleLogin">登录</button>
    <p>没有账号? <router-link to="/register">去注册</router-link></p>
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