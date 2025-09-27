<template>
  <div class="login-container">
    <el-card class="login-card">
      <div slot="header" class="login-header">
        <h2>寻光</h2>
        <p class="subtitle">摄影交流平台</p>
      </div>
      <el-form
          ref="registerFormRef"
          :model="registerForm"
          :rules="rules"
          class="login-form"
      >
        <el-form-item prop="userName">
          <el-input
              v-model="registerForm.userName"
              placeholder="请输入用户名"
              prefix-icon="User"
          ></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
              v-model="registerForm.password"
              type="password"
              placeholder="请输入密码"
              prefix-icon="Lock"
          ></el-input>
        </el-form-item>
        <el-form-item prop="email">
          <el-input
              v-model="registerForm.email"
              placeholder="邮箱"
              prefix-icon="Message"
          ></el-input>
        </el-form-item>
        <el-form-item prop="phoneNumber">
          <el-input
              v-model="registerForm.phoneNumber"
              placeholder="请输入手机号"
              prefix-icon="Phone"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button
              type="primary"
              class="login-btn"
              @click="handleRegister"
          >
            注册
          </el-button>
        </el-form-item>
        <el-form-item>
          <el-button
              type="primary"
              class="login-btn"
              @click="toLogin"
          >
            登录
          </el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>
<script setup lang="ts">
import { ref } from "vue";
import { ElLoading, ElMessage } from "element-plus";
import { useRouter } from "vue-router";
import type { FormInstance, FormRules } from "element-plus";
import request from '../utils/request'

const router = useRouter();
const registerFormRef = ref<FormInstance>();
const registerForm = ref({
  userName: '',
  password: '',
  email: '',
  phoneNumber: '',
});

// 表单验证规则
const rules = ref<FormRules>({
  userName: [
    { required: true, message: '用户名不能为空', trigger: 'blur' },
    { min: 3, max: 16, message: '用户名长度为3-16位', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '密码不能为空', trigger: 'blur' },
    { pattern: /^\w{6,16}$/, message: '密码为6-16位字母、数字或下划线', trigger: 'blur' }
  ],
  email: [
    { required: true, message: '邮箱不能为空', trigger: 'blur' },
    { type: 'email', message: '请输入正确的邮箱格式', trigger: ['blur', 'change'] }
  ],
  phoneNumber: [
    { required: true, message: '手机号不能为空', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号格式', trigger: 'blur' }
  ]
});

// 处理注册逻辑
const handleRegister = async () => {
  if (!registerFormRef.value) return;

  // 显示加载状态
  const loading = ElLoading.service({
    lock: true,
    text: '注册中...',
    background: 'rgba(0, 0, 0, 0.7)'
  });

  try {
    await registerFormRef.value.validate(async (valid) => {
      if (valid) {
        const response = await request.post("/user/register", registerForm.value);
        if (response.data.code === 200) {
          ElMessage.success('注册成功，请登录');
          router.push('/login');
        } else {
          ElMessage.error(response.data.message || '注册失败');
        }
      }
    });
  }catch (error) {
    if (error.response) {
      // 后端有响应（如 400/500）
      ElMessage.error(error.response.data.message || '注册失败');
    } else if (error.request) {
      // 无响应（网络错误）
      ElMessage.error('网络错误，请稍后重试');
    } else {
      // 其他错误
      ElMessage.error('请求出错，请重试');
    }
  } finally {
    loading.close();
  }
};

// 跳转到登录页面
const toLogin = () => {
  router.push('/login');
};
</script>
<style scoped>
/* 复用登录页面样式 */
body {
  overflow: hidden;
}

.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  height: 100vh;
  background: linear-gradient(135deg, #1a2a3a 0%, #2c3e50 100%);
  overflow: hidden;
  position: relative;
  padding: 20px;
  box-sizing: border-box;
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

.login-card {
  width: 100%;
  max-width: 550px;
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