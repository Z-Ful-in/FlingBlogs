import { createRouter, createWebHistory} from "vue-router";
import MainLayout from '@/layout/MainLayout.vue'


const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            redirect: '/login' // 默认跳转登录
        },
        // 登录注册不需要导航栏，直接放在根目录下
        {
            path: '/login',
            name: 'login',
            component: () => import('../views/Login.vue')
        },
        {
            path: '/register',
            name: 'register',
            component: () => import('../views/Register.vue')
        },
        {
            path: '/',
            component: MainLayout,
            children: [
                {
                    path: 'home', // 访问 /home
                    name: 'home',
                    component: () => import('../views/Home.vue')
                },
                {
                    path: 'write', // 访问 /write
                    name: 'write',
                    component: () => import('../views/WriteArticle.vue')
                }
            ]
        }
    ]
})

export default router