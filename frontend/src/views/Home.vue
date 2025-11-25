<script setup>
import { ref, onMounted } from "vue";
import userApi  from "@/api/userApi.js";
import { useRouter } from "vue-router";

const router = useRouter();
const username = localStorage.getItem("username");
const myArticles = ref([])

const fetchMyArticles = async () => {
  try {
    const res = await userApi.getMyArticles();
    myArticles.value = res.data;
  }catch (error) {
    console.log("获取我的文章失败", error);
    if(error.response && error.response.status === 403) {
      alert("Token 失效或无权限, 请重新登录!")
      logout()
    }
  }
}

const logout = () => {
  localStorage.removeItem('token')
  localStorage.removeItem('username')
  router.push('/login')
}

onMounted(() => {
  if(!username){
    router.push('/login')
  } else {
    fetchMyArticles()
  }
})

</script>

<template>
  <div class="home">
    <h1>欢迎, {{ username }}</h1>
    <button @click="logout">退出登录</button>

    <h3>我的文章列表 (Token验证区域):</h3>
    <div v-if="myArticles.length === 0">暂无文章，或者获取失败</div>
    <ul v-else>
      <li v-for="article in myArticles" :key="article.id">
        {{ article.title }}
      </li>
    </ul>

    <div style="margin-top: 20px">
      <router-link to="/article">去写文章</router-link>
    </div>
  </div>
</template>

<style scoped>
.home { padding: 20px; }
</style>