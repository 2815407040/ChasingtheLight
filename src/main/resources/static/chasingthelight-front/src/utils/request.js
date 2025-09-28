import axios from 'axios'

const request = axios.create({
    baseURL: 'http://localhost:8080',
    timeout: 5000,
    withCredentials: true // 关键：允许跨域请求携带凭证
})

export default request