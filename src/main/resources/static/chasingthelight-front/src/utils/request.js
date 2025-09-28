import axios from 'axios'
import router from '../router';
import { ElMessage } from 'element-plus';

const request = axios.create({
    baseURL: 'http://localhost:8080',
    timeout: 5000,
    withCredentials: true
});

// 请求拦截器：添加token到请求头
// 修改请求拦截器，登录接口不添加token
request.interceptors.request.use(
    config => {
        // 排除登录接口
        if (!config.url?.includes('/user/login') && !config.url?.includes('/user/register')) {
            const token = localStorage.getItem('token');
            if (token) {
                config.headers.Authorization = `Bearer ${token}`;
            }
        }
        return config;
    },
    error => {
        return Promise.reject(error);
    }
);

// 响应拦截器：处理token过期
request.interceptors.response.use(
    response => response,
    error => {
        if (error.response && error.response.status === 401) {
            // token过期或无效，跳转回登录页
            localStorage.removeItem('token');
            router.push('/login');
            ElMessage.error('登录已过期，请重新登录');
        }
        return Promise.reject(error);
    }
);

export default request;