// src/router/index.js
import { createRouter, createWebHistory } from 'vue-router'
import Login from '../views/login.vue'
import Index from "../views/index.vue";
import Register from '../views/register.vue'

const routes = [
    {
        path: '/',
        redirect: '/login'
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
    },
    {
        path: '/index',
        name: 'Index',
        component: Index
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router