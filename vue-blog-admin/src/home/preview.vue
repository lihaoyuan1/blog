<template>
    <div style="margin: auto;max-width: 800px;position: relative">
        <el-breadcrumb separator-class="el-icon-arrow-right" v-if="from === 0">
            <el-breadcrumb-item :to="{name: 'article'}">文章管理</el-breadcrumb-item>
            <el-breadcrumb-item>{{article.title}}</el-breadcrumb-item>
        </el-breadcrumb>
        <el-breadcrumb separator-class="el-icon-arrow-right" v-else>
            <el-breadcrumb-item :to="{name: 'category'}">分类专栏</el-breadcrumb-item>
            <el-breadcrumb-item :to="{name: 'categoryManage', query:{id: source, name: sourceName}}">{{sourceName}}</el-breadcrumb-item>
            <el-breadcrumb-item>{{article.title}}</el-breadcrumb-item>
        </el-breadcrumb>
        <el-button
                type="danger"
                palin
                size="mini"
                class="edit"
                @click="$router.push({
                    name: 'articleEdit',
                    query: {
                        id: article.id
                    }})">编 辑</el-button>
        <h1 style="font-size: 22px;text-align: center;margin-bottom: 10px">{{article.title}}</h1>
        <div class="author-info">
            <span>作者：{{article.nickname}}</span>
            <span>日期：{{article.createTime}}</span>
            <span>浏览：{{article.views}}</span>
            <span>点赞：{{article.admire}}</span>
        </div>
        <div class="dotted-divider" />
        <el-image
                :src="article.picture"
                :preview-src-list="previewSrcList"
                style="width: 100%;height: auto"/>
        <div class="dotted-divider" />
        <le-preview :is-md="true" :value="article.content" hljs-css="atomOneDark" />
    </div>
</template>

<script>
    export default {
        name: "preview",
        components: {
        },
        data () {
            return{
                article: {},
                previewSrcList: []
            }
        },
        methods: {
            loadData () {
                this.$emit('loadingStart', 1);
                this.get('/article/getOne', {id: this.articleId}).then(res => {
                    this.article = res.data;
                    this.previewSrcList.push(this.article.picture);
                    this.$emit('loadingComplete');
                })
            }
        },
        created() {
            this.loadData();
        },
        computed: {
            articleId () {
                return this.$route.query.id;
            },
            from () {
                return this.$route.query.from;
            },
            source () {
                return this.$route.query.source;
            },
            sourceName () {
                return this.$route.query.sourceName;
            }
        }
    }
</script>

<style scoped>
    .author-info{
        color: #888;
        font-size: 14px;
        text-align: center;
    }
    .author-info span:not(:first-child){
        margin-left: 10px;
    }
    .dotted-divider{
        height: 1px;
        border-top: 1px dotted #ccc;
        margin: 10px 0;
    }
    .edit{
        position: absolute;
        top: 0;
        right: 0;
    }
    code {
        color: red !important;
    }
</style>
