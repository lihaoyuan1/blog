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
        <div class="markdown-body" v-html="htmlContent" />
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
                htmlContent: '',
                previewSrcList: [],
                md: ''
            }
        },
        methods: {
            loadData () {
                this.$emit('loadingStart', 1);
                this.get('/article/getOne', {id: this.articleId}).then(res => {
                    this.article = res.data;
                    this.previewSrcList.push(this.article.picture);
                    this.htmlContent = this.md.render(this.article.content);
                    this.$emit('loadingComplete');
                })
            },
            initMarkdownIt () {
                const hljs = require('highlight.js')
                const katex = require('@iktakahiro/markdown-it-katex')
                const sub = require('markdown-it-sub')
                const sup = require('markdown-it-sup')
                const footNote = require('markdown-it-footnote')
                const defList = require('markdown-it-deflist')
                const abbr = require('markdown-it-abbr')
                const mark = require('markdown-it-mark')
                let that = this
                this.md = require('markdown-it')({
                    html: true,
                    xhtmlOut: true,
                    breaks: true,
                    linkify: true,
                    typographer: true,
                    quotes: '“”‘’',
                    highlight(str, lang) {
                        if (lang && hljs.getLanguage(lang)) {
                            try {
                                return `<pre class="hljs"><code>${
                                    hljs.highlight(lang, str, true).value
                                }</code></pre>`
                                // eslint-disable-next-line no-empty
                            } catch (__) {}
                        }
                        return (
                            '<pre class="hljs"><code>' +
                            that.md.utils.escapeHtml(str) +
                            '</code></pre>'
                        )
                    }
                })
                    .use(katex)
                    .use(sub)
                    .use(sup)
                    .use(footNote)
                    .use(defList)
                    .use(abbr)
                    .use(mark)
            }
        },
        created() {
            this.initMarkdownIt();
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
    @import "~highlight.js/styles/atom-one-dark.css";
    @import "~github-markdown-css/github-markdown.css";
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
</style>