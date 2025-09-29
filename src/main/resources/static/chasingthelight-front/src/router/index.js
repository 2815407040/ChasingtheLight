// src/router/index.js
import { createRouter, createWebHistory } from 'vue-router'
import Login from '../views/login.vue'
import Layout from "../views/index.vue";
import Register from '../views/register.vue'
import PopularCameras from "../views/PopularCameras.vue";
import Home from '../views/home.vue'
import SearchCamera from '../views/searchCamera.vue'

const routes = [
    {
        path: '/index',
        component: Layout,
        children: [
            {
                path: '',
                name: 'Home',
                component: Home
            },
            {
                path: '/popularCameras',
                name: 'PopularCameras',
                component: PopularCameras
            },
            {
                path: '/searchCamera',
                name: 'SearchCamera',
                component: SearchCamera
            }
        ]
    },
    {
        path: '/login',
        name: 'Login',
        component: Login
    },
    {
        path: '/register',
        name: 'Register',
        component: Register
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

// 路由守卫
router.beforeEach((to, from, next) => {
    // 判断目标页面是否需要认证
    if (to.meta.requiresAuth) {
        const token = localStorage.getItem('token');
        if (token) {
            next(); // 有token，放行
        } else {
            next('/login'); // 无token，跳转登录页
        }
    } else {
        next(); // 无需认证的页面直接放行
    }
});

export default router;