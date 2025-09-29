<template>
  <div class="activate-container">
    <el-card class="activate-card">
      <div v-if="loading" class="loading">
        <el-loading-spinner></el-loading-spinner>
        <p>正在验证激活码...</p>
      </div>

      <div v-else-if="success" class="result success">
        <el-icon class="icon"><CheckCircle /></el-icon>
        <h2>账号激活成功！</h2>
        <p>您的账号已成功激活，即将跳转到登录页面...</p>
        <el-button type="primary" @click="toLogin">立即登录</el-button>
      </div>

      <div v-else class="result error">
        <el-icon class="icon"><CloseCircle /></el-icon>
        <h2>激活失败</h2>
        <p>{{ errorMessage }}</p>
        <el-button type="primary" @click="toRegister">重新注册</el-button>
      </div>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { CheckCircle, CloseCircle } from '@element-plus/icons-vue';
import request from '../utils/request';

const router = useRouter();
const route = useRoute();
const loading = ref(true);
const success = ref(false);
const errorMessage = ref('激活码无效或已过期');

onMounted(async () => {
  const code = route.query.code;
  if (!code) {
    loading.value = false;
    return;
  }

  try {
    const response = await request.get('/user/activate', {
      params: { code }
    });
    if (response.data.code === 200) {
      success.value = true;
      // 3秒后自动跳转到登录页
      setTimeout(() => {
        router.push('/login');
      }, 3000);
    } else {
      errorMessage.value = response.data.message || errorMessage.value;
    }
  } catch (error) {
    errorMessage.value = '激活过程出错，请稍后重试';
  } finally {
    loading.value = false;
  }
});

const toLogin = () => {
  router.push('/login');
};

const toRegister = () => {
  router.push('/register');
};
</script>

<style scoped>
.activate-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background: linear-gradient(135deg, #1a2a3a 0%, #2c3e50 100%);
  padding: 20px;
}

.activate-card {
  width: 100%;
  max-width: 500px;
  padding: 40px;
  text-align: center;
  background: rgba(255, 255, 255, 0.95);
  border-radius: 12px;
  box-shadow: 0 8px 30px rgba(0, 0, 0, 0.2);
}

.loading {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 20px;
}

.result {
  padding: 20px 0;
}

.result .icon {
  font-size: 60px;
  margin-bottom: 20px;
}

.success .icon {
  color: #10b981;
}

.error .icon {
  color: #ef4444;
}

.result h2 {
  margin: 0 0 15px;
  font-size: 24px;
}

.result p {
  color: #666;
  margin: 0 0 30px;
  font-size: 16px;
}
</style>