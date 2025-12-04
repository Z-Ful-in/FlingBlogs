<script setup>
import { ref, onMounted } from 'vue'
import userApi from '@/api/userApi'
import { useRouter } from 'vue-router'
import { Document, Lock } from '@element-plus/icons-vue'
import {ElMessage} from "element-plus";

const router = useRouter()
// 假设后端已经修复了 DTO 问题，这里拿到的 article.author 是一个对象
const myArticles = ref([])
const loading = ref(true)

const fetchMyArticles = async () => {
  loading.value = true
  try {
    const res = await userApi.getMyArticles()
    myArticles.value = res
  } catch (error) {
    console.error(error)
    if (error.response && error.response.status === 403) {
      ElMessage.error('登录已过期，请重新登录')
      router.push('/login')
    }
  } finally {
    loading.value = false
  }
}

const goToWrite = () => {
  router.push('/write')
}

onMounted(() => {
  fetchMyArticles()
})
</script>

<template>
  <div class="home-container" v-loading="loading">
    <div class="page-header">
      <h2>我的文章库</h2>
      <el-button type="primary" :icon="Document" @click="goToWrite">写新文章</el-button>
    </div>

    <el-empty v-if="!loading && myArticles.length === 0" description="你还没有写过文章">
      <el-button type="primary" @click="goToWrite">去创作</el-button>
    </el-empty>

    <el-row :gutter="20" v-else>
      <el-col :span="24" v-for="article in myArticles" :key="article.id">
        <el-card shadow="hover" class="article-card">
          <div class="article-header">
            <h3 class="article-title">
              <el-icon v-if="article.isPrivate" class="private-icon"><Lock /></el-icon>
              {{ article.title }}
            </h3>
            <span class="article-date" v-if="article.createdAt">
              {{ new Date(article.createdAt).toLocaleDateString() }}
            </span>
          </div>
          <div class="article-excerpt">
            {{ article.content.substring(0, 150) }}...
          </div>
          <div class="article-footer">
            <el-button type="primary" link>阅读全文</el-button>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<style scoped>
.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.article-card {
  margin-bottom: 20px;
  transition: all 0.3s;
}

.article-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 10px;
}

.article-title {
  margin: 0;
  font-size: 18px;
  color: #303133;
  display: flex;
  align-items: center;
}

.private-icon {
  margin-right: 5px;
  color: #F56C6C;
}

.article-date {
  font-size: 13px;
  color: #909399;
}

.article-excerpt {
  color: #606266;
  font-size: 14px;
  line-height: 1.6;
  margin-bottom: 15px;
}

.article-footer {
  display: flex;
  justify-content: flex-end;
  border-top: 1px solid #ebeef5;
  padding-top: 10px;
}
</style>