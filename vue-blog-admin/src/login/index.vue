<template>
    <div id="login">
        <el-row type="flex" align="middle" justify="center" style="height: 100%;">
            <el-form :model="ruleForm" :rules="rules" ref="ruleForm" style="width: 320px">
                <el-form-item>
                    <h1 style="text-align: center;margin: 0">后台登陆</h1>
                </el-form-item>
                <el-form-item prop="username">
                    <el-input v-model="ruleForm.username" placeholder="Username" />
                </el-form-item>
                <el-form-item prop="password">
                    <el-input v-model="ruleForm.password" placeholder="Password" type="password" />
                </el-form-item>
                <el-form-item>
                    <el-button
                            @keyup.enter="handleSubmit('ruleForm')"
                            @click="handleSubmit('ruleForm')"
                            type="primary"
                            style="width: 100%"
                            :loading="loading">
                        登 录</el-button>
                </el-form-item>
            </el-form>
        </el-row>
        <transition-group name="fade">
            <template v-for="(item, index) in bgImgList">
                <div :key="index" class="bg-container" v-show="bgImgIndex === index">
                    <img :src="item" alt="">
                </div>
            </template>
        </transition-group>
    </div>
</template>

<script>
import md5 from 'js-md5'
export default {
    name: "index",
    data () {
        return {
            bgImgIndex: 0,
            loading: false,
            ruleForm: {username: '', password: ''},
            rules: {
                username: [
                    { required: true, message: "请输入用户名", trigger: "blur" }
                ],
                password: [
                    { required: true, message: "请输入密码", trigger: "blur" }
                ]
            },
            bgImgList: [
                'https://blog-lhy.oss-cn-hangzhou.aliyuncs.com/loginBackground/1.jpg',
                'https://blog-lhy.oss-cn-hangzhou.aliyuncs.com/loginBackground/2.jpg',
                'https://blog-lhy.oss-cn-hangzhou.aliyuncs.com/loginBackground/3.jpg'
            ]
        }
    },
    created() {
        this.keyEnter();
    },
    methods: {
        login () {
            this.post('/login/', {
                username: this.ruleForm.username,
                password: md5(this.ruleForm.password)
            }).then(res => {
                if (res.success){
                    this.$notify({
                        title: '成功',
                        message: res.message,
                        type: 'success'
                    });
                    this.$router.push({name: 'admin'});
                } else {
                    this.$notify.error({
                        title: '失败',
                        message: res.message
                    });
                }
                this.loading = false;
            })
        },
        handleSubmit (formName) {
            this.loading = true;
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    this.login();
                } else {
                    this.loading = false;
                }
            });
        },
        keyEnter () {
            document.onkeydown = () => {
                if (window.event.keyCode === 13){
                    this.handleSubmit('ruleForm')
                }
            };
        },
    },
    mounted() {
        this.interval = setInterval( () => {
            this.bgImgIndex = (this.bgImgIndex + 1) % 3;
        }, 5000);
        this.get('/login/check', {}).then(res => {
            if (res.success) {
                this.timeout = setTimeout(() => {
                    this.$router.push({name: 'admin'});
                }, 800);
            }
        })
    },
    destroyed() {
        clearInterval(this.interval);
    }
}
</script>

<style scoped>
#login{
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
}
.bg-container{
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    z-index: -1;
    line-height: 0;
}
.bg-container img{
    width: 100%;
    height: 100%;
}
.fade-enter-active, .fade-leave-active {
    transition: all 1s linear;
}
.fade-enter-to, .fade-leave{
    opacity: 1;
}
.fade-enter, .fade-leave-to {
    opacity: 0;
}
</style>