<template>
  <div class="login-container">
    <el-card class="login-card">
      <div slot="header" class="login-header">
        <h2>寻光</h2>
        <p class="subtitle">摄影交流平台</p>
      </div>
      <el-form
          ref="loginFormRef"
          :model="loginForm"
          :rules="rules"
          class="login-form"
      >
        <el-form-item prop="username">
          <el-input
              v-model="loginForm.userName"
              placeholder="请输入用户名"
              prefix-icon="User"
          ></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
              v-model="loginForm.password"
              type="password"
              placeholder="请输入密码"
              prefix-icon="Lock"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button
              type="primary"
              class="login-btn"
              @click="handleLogin"
          >
            登录
          </el-button>
        </el-form-item>
        <el-form-item>
          <el-button
              type="primary"
              class="login-btn"
              @click="toRegister"
          >
            注册
          </el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup lang='ts'>
import { ref,  } from 'vue'
import {ElLoading, ElMessage} from "element-plus";
import { useRouter } from 'vue-router'
import type { FormInstance, FormRules } from 'element-plus'
import request from '../utils/request'

const router = useRouter()
const loginFormRef = ref<FormInstance>()
const loginForm = ref({
  userName: '',
  password: '',
})

const rules = ref<FormRules>({
  userName: [
    { required: true, message: '用户名不能为空', trigger: 'blur' },
  ],
  password: [
    { required: true, message: '密码不能为空', trigger: 'blur' },
    { pattern: /^\w{6,16}$/, message: '密码为6-16位字母、数字或下划线', trigger: 'blur' },
  ]
})

const handleLogin = async () => {
  if (!loginFormRef.value) return

  // 显示加载状态
  const loading = ElLoading.service({
    lock: true,
    text: '登录中...',
    background: 'rgba(0, 0, 0, 0.7)'
  })

  try {
    await loginFormRef.value.validate(async (valid) => {
      if (valid) {
        const response = await request.post("/user/login", loginForm.value)

        if (response.data.code === 200) {
          ElMessage.success('登录成功')
          localStorage.setItem('token', response.data.data)
          router.push('/index')
        } else {
          ElMessage.error(response.data.message || '登录失败')
        }
      }
    })
  } catch (error) {
    console.error("登录请求失败:", error)
    ElMessage.error('网络错误，请稍后重试')
  } finally {
    loading.close()
  }
}

const toRegister = async () => {
  router.push('/register')
}

</script>

<style scoped>
/* 主要修改了登录卡片的宽度限制和响应式适配 */
body {
  overflow: hidden;
}

.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  height: 100vh; /* 新增：固定高度为视口高度 */
  background: linear-gradient(135deg, #1a2a3a 0%, #2c3e50 100%);
  overflow: hidden;
  position: relative;
  padding: 20px;
  box-sizing: border-box; /* 新增：确保内边距不影响总高度 */
}

.login-container::before {
  content: '';
  position: absolute;
  width: 100%;
  height: 100%;
  background-image:
      linear-gradient(rgba(255, 255, 255, 0.03) 1px, transparent 1px),
      linear-gradient(90deg, rgba(255, 255, 255, 0.03) 1px, transparent 1px);
  background-size: 20px 20px;
  pointer-events: none;
}

/* 关键修改：使用max-width替代固定width，同时增大基础宽度 */
.login-card {
  width: 100%;
  max-width: 550px; /* 增大最大宽度，减少两边留白 */
  background: rgba(255, 255, 255, 0.95);
  border-radius: 12px;
  box-shadow: 0 8px 30px rgba(0, 0, 0, 0.2);
  backdrop-filter: blur(10px);
  border: 1px solid rgba(255, 255, 255, 0.1);
  overflow: hidden;
  transition: all 0.3s ease;
}

.login-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 12px 40px rgba(0, 0, 0, 0.25);
}

.login-header {
  text-align: center;
  padding: 28px 0 15px;
  background: linear-gradient(135deg, #34495e 0%, #2c3e50 100%);
  color: white;
}

.login-header h2 {
  font-size: 26px;
  margin: 0 0 8px;
  font-weight: 600;
  letter-spacing: 1px;
}

.subtitle {
  margin: 0;
  font-size: 14px;
  color: rgba(255, 255, 255, 0.8);
  letter-spacing: 0.5px;
}

/* 增大表单内边距，让内容更舒展 */
.login-form {
  padding: 35px 50px;
}

.el-input {
  border-radius: 8px;
  border: 1px solid #dce1e8;
  transition: all 0.3s ease;
}

.el-input:focus-within {
  border-color: #3498db;
  box-shadow: 0 0 0 3px rgba(52, 152, 219, 0.1);
}

.el-input__wrapper {
  border-radius: 8px;
  height: 50px;
  background-color: #f9fafb;
}

.el-input__prefix {
  color: #7f8c8d;
}

.login-btn {
  width: 100%;
  height: 50px;
  font-size: 16px;
  border-radius: 8px;
  background: linear-gradient(135deg, #3498db 0%, #2980b9 100%);
  border: none;
  transition: all 0.3s ease;
}

.login-btn:hover {
  background: linear-gradient(135deg, #2980b9 0%, #3498db 100%);
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(52, 152, 219, 0.3);
}

.login-btn:active {
  transform: translateY(0);
}

.el-form-item {
  margin-bottom: 22px;
}

/* 优化响应式适配逻辑 */
@media (max-width: 768px) {
  .login-card {
    max-width: 90%;
  }

  .login-form {
    padding: 30px 30px;
  }
}

@media (max-width: 480px) {
  .login-form {
    padding: 25px 20px;
  }

  .login-header h2 {
    font-size: 22px;
  }
}
</style>