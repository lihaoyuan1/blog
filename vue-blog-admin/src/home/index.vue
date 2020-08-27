<template>
    <el-container>
        <el-header class="admin-header">
            <el-row type="flex" align="middle" style="height: 100%">
                <el-col :span="12">
                    <el-row type="flex" align="middle">
                        <el-col style="text-align: center;line-height: 0;width: 50px">
                            <img src="../assets/logo-icon.png" alt=""/>
                        </el-col>
                        <el-col>
                            <h2 style="margin: 0">博客后台管理系统</h2>
                        </el-col>
                    </el-row>
                </el-col>
                <el-col :span="12">
                    <el-menu
                            mode="horizontal"
                            style="float: right"
                            :default-active="$route.path"
                            active-text-color="#FFC940">
                        <el-menu-item
                                @click="$router.push({name: 'editor'})"
                                index="/admin/editor">
                            <i class="el-icon-edit" />
                            <span slot="title">写博客</span>
                        </el-menu-item>
                        <el-submenu index="me">
                            <template slot="title">
                                <el-avatar :size="40" :src="userInfo.avatar" />
                                {{userInfo.nickname}}
                            </template>
                            <el-menu-item index="personal">
                                个人中心
                            </el-menu-item>
                            <el-menu-item index="logout" @click="logout">
                                退出登录
                            </el-menu-item>
                        </el-submenu>
                    </el-menu>
                </el-col>
            </el-row>
        </el-header>
        <el-container>
            <el-aside class="admin-aside">
                <el-menu
                        :default-active="$route.path"
                        background-color="#545c64"
                        text-color="#fff"
                        active-text-color="#ffd04b"
                        :router="true"
                        style="height: 100%">
                    <el-menu-item index="/admin/home">
                        <i class="el-icon-s-home" />
                        <span slot="title">首页</span>
                    </el-menu-item>
                    <el-menu-item index="/admin/editor">
                        <i class="el-icon-edit" />
                        <span slot="title">写博客</span>
                    </el-menu-item>
                    <el-menu-item index="/admin/article">
                        <i class="el-icon-reading" />
                        <span slot="title">文章管理</span>
                    </el-menu-item>
                    <el-menu-item index="/admin/category">
                        <i class="el-icon-notebook-1" />
                        <span slot="title">分类专栏</span>
                    </el-menu-item>
                    <el-menu-item index="/admin/tag">
                        <i class="el-icon-collection-tag" />
                        <span slot="title">标签专栏</span>
                    </el-menu-item>
                </el-menu>
            </el-aside>
            <el-main class="admin-main vld-parent">
                <el-card shadow="hover" class="main-wrapper">
                    <router-view @loadingStart="loadingStart" @loadingComplete="loadingComplete" />
                </el-card>
            </el-main>
            <transition name="face">
                <el-row v-if="loadingShow" type="flex" justify="center" align="middle" class="loading">
                    <loading-circle size="48px" />
                </el-row>
            </transition>
        </el-container>
    </el-container>
</template>

<script>
import {Circle as LoadingCircle} from 'vue-loading-spinner'
export default {
    name: "index",
    data() {
        return{
            userInfo: {nickname: '', avatar: ''},
            loading: 0,
            loadingShow: false
        }
    },
    components: {
        LoadingCircle
    },
    methods: {
        loadingStart (value) {
            this.loading = value;
        },
        loadingComplete () {
            this.loading -= 1;
        },
        logout () {
            this.get('/login/logout', {}).then(res => {
                if(res.success){
                    this.sessionRemove('token');
                    this.$notify({
                        title: '成功',
                        message: res.message,
                        type: 'success'
                    });
                    this.$router.push({name: 'login'})
                }
            });
        },
        loadUserInfo () {
            this.get('/login/userInfo', {}).then(res => {
                this.userInfo = res.data;
                this.$notify({
                    title: '成功',
                    message: "您好"+this.userInfo.nickname+"，欢迎回来",
                    type: 'success'
                });
            })
        },
    },
    created() {
        this.loadUserInfo();
    },
    watch: {
        loading (newValue) {
            if (newValue > 0){
                this.loadingShow = true;
            } else {
                this.loadingShow = false;
            }
        }
    },
    destroyed() {
        clearTimeout(this.timer);
    }
}
</script>

<style scoped>
.admin-header{
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    z-index: 1;
    background: #fff;
    box-shadow: 0 0 5px rgba(0,0,0,0.2);
}
.admin-aside {
    position: fixed;
    width: 240px !important;
    top: 60px;
    left: 0;
    bottom: 0;
}
.admin-main {
    min-height: calc(100vh - 60px);
    background: rgba(0,0,0,0.1);
    padding: 14px;
    margin-top: 60px;
    margin-left: 240px;
}
.main-wrapper{
    min-height: calc(100vh - 90px);
}
.loading{
    position: absolute;
    top: 60px;
    left: 240px;
    right: 0;
    bottom: 0;
    background-color: rgba(255,255,255,0.65);
    z-index: 1501;
}
.fade-enter-active, .fade-leave-active {
    transition: opacity .5s;
}
.fade-enter, .fade-leave-to {
    opacity: 0;
}
</style>