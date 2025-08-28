import { createRouter, createWebHistory} from "vue-router";

import Home from "../views/Home.vue"
import Login from "../views/Login.vue"
import Register from "../views/Register.vue"
import WriteArticle from "../views/WriteArticle.vue";


const routes = [
    { path: "/", redirect: "/home" },
    { path: "/home", component: Home },
    { path: "/login", component: Login },
    { path: "/register", component: Register },
    { path: "/article", component: WriteArticle },
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router