import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter);

import login from '../login/index';
import admin from '../home/index';
import home from '../home/home';
import editor from "../home/editor";
import category from "../home/category";
import categoryAdd from '../home/category-add'
import categoryUpdate from '../home/category-update'
import tag from '../home/tag'
import article from "../home/article"
import articleEdit from '../home/article-edit'
import categoryManage from '../home/category-manage'
import preview from "../home/preview";

const routes = [
    {
        path: '/',
        redirect: '/login'
    },
    {
        path: '/login',
        name: 'login',
        component: login
    },
    {
        path: '/admin',
        name: 'admin',
        component: admin,
        redirect: '/admin/home',
        children: [
            {
                path: 'home',
                name: 'home',
                component: home
            },
            {
                path: 'editor',
                name: 'editor',
                component: editor
            },
            {
                path: 'category',
                name: 'category',
                component: category
            },
            {
                path: 'categoryAdd',
                name: 'categoryAdd',
                component: categoryAdd
            },
            {
                path: 'categoryUpdate',
                name: 'categoryUpdate',
                component: categoryUpdate
            },
            {
                path: 'tag',
                name: 'tag',
                component: tag
            },
            {
                path: 'article',
                name: 'article',
                component: article
            },
            {
                path: 'articleEdit',
                name: 'articleEdit',
                component: articleEdit
            },
            {
                path: 'categoryManage',
                name: 'categoryManage',
                component: categoryManage
            },
            {
                path: 'preview',
                name: 'preview',
                component: preview
            }
        ]
    }
];

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
});

export default router