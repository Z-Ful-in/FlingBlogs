<script setup>
import {ref } from "vue";
import userApi from "@/api/userApi.js";
import {useRouter} from "vue-router";

const router = useRouter();
const title = ref("");
const content = ref("");
const isPrivate = ref(false);
const loading = ref(false);

const handleSubmit = async () => {
  if(!title.value.trim() || !content.value.trim()) {
    alert("标题和内容不能为空");
    return;
  }
  loading.value = true;
  try {
    const res = await userApi.writeArticle({
      title: title.value,
      content: content.value,
      isPrivate: isPrivate.value
    });
    alert(res.message || "文章提交成功");
    router.push('/home');
  } catch (error) {
    alert("文章提交失败: " + (error.response?.message || "未知错误"));
    console.log(error);
  }finally {
    loading.value = false;
  }
};

</script>

<template>
  <div class="write-container">
    <div class="card">
      <div class="header">
        <h2>写文章</h2>
        <router-link to="/home" class="back-link">返回首页</router-link>
      </div>

      <div class="form-group">
        <label>文章标题</label>
        <input
            v-model="title"
            type="text"
            placeholder="请输入文章标题..."
            class="input-field"
        />
      </div>

      <div class="form-group">
        <label>文章内容</label>
        <textarea
            v-model="content"
            placeholder="在此输入正文..."
            rows="10"
            class="textarea-field"
        ></textarea>
      </div>

      <div class="form-footer">
        <label class="checkbox-label">
          <input type="checkbox" v-model="isPrivate" />
          <span class="checkbox-text">设为私密文章</span>
        </label>

        <button
            @click="handleSubmit"
            class="submit-btn"
            :disabled="loading"
        >
          {{ loading ? '发布中...' : '发布文章' }}
        </button>
      </div>
    </div>
  </div>

</template>

<style scoped>
.write-container {
  display: flex;
  justify-content: center;
  padding: 40px 20px;
  min-height: 100vh;
  background-color: #f9f9f9;
}

.card {
  width: 100%;
  max-width: 800px;
  background: white;
  padding: 40px;
  border-radius: 12px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.05);
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px;
  border-bottom: 1px solid #eee;
  padding-bottom: 15px;
}

h2 {
  margin: 0;
  font-size: 24px;
  color: #333;
}

.back-link {
  color: #666;
  text-decoration: none;
  font-size: 14px;
}

.back-link:hover {
  color: #42b883;
}

.form-group {
  margin-bottom: 25px;
}

.form-group label {
  display: block;
  margin-bottom: 8px;
  font-weight: bold;
  color: #555;
}

.input-field,
.textarea-field {
  width: 100%;
  padding: 12px;
  border: 1px solid #ddd;
  border-radius: 6px;
  font-size: 16px;
  transition: border-color 0.3s;
  box-sizing: border-box; /* 确保 padding 不会撑破宽度 */
}

.input-field:focus,
.textarea-field:focus {
  outline: none;
  border-color: #42b883;
}

.textarea-field {
  resize: vertical; /* 允许垂直拉伸 */
  line-height: 1.6;
}

.form-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 10px;
}

.checkbox-label {
  display: flex;
  align-items: center;
  cursor: pointer;
  user-select: none;
}

.checkbox-text {
  margin-left: 8px;
  color: #666;
}

.submit-btn {
  background-color: #42b883;
  color: white;
  border: none;
  padding: 12px 30px;
  border-radius: 6px;
  font-size: 16px;
  font-weight: bold;
  cursor: pointer;
  transition: background-color 0.3s;
}

.submit-btn:hover {
  background-color: #3aa876;
}

.submit-btn:disabled {
  background-color: #a8dcc5;
  cursor: not-allowed;
}
</style>