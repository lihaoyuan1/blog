<template>
    <div style="margin: auto;max-width: 1020px;padding-bottom: 30px">
        <el-row type="flex" align="middle" style="padding: 16px 16px;background-color: #f2f5f7;font-size: 14px">
            <span style="color:#2C3E50;">筛选：</span>
            <el-select v-model="query.year" placeholder="年" style="width: 100px" size="small">
                <el-option label="不限" :value="null" />
                <el-option
                        v-for="item in year"
                        :key="item"
                        :label="item"
                        :value="item"/>
            </el-select>
            <el-select v-model="query.month" placeholder="月" style="width: 100px;margin-left: 10px" size="small">
                <el-option label="不限" :value="null" />
                <template v-if="selectYear != null && selectYear !== ''">
                    <el-option
                            v-for="item in 12"
                            :key="item"
                            :label="item"
                            :value="item"/>
                </template>
            </el-select>
            <el-select v-model="query.type" placeholder="文章类型" style="width: 120px;margin-left: 20px" size="small">
                <el-option label="不限" :value="null" />
                <el-option label="原创" value="原创" />
                <el-option label="转载" value="转载" />
                <el-option label="翻译" value="翻译" />
            </el-select>
            <el-select v-model="query.categoryId" placeholder="分类专栏" style="width: 180px;margin-left: 20px" size="small">
                <el-option label="不限" :value="null" />
                <el-option
                        v-for="item in category"
                        :key="item.id"
                        :label="item.categoryName"
                        :value="item.id"/>
            </el-select>
            <el-input placeholder="请输入关键词" v-model="query.keyWords" size="small" style="width: 290px;margin-left: 20px" />
            <el-button size="small" type="primary" style="margin-left: 20px" @click="handleSearch">搜 索</el-button>
        </el-row>
        <div style="border-bottom: 1px solid #f0f2f5;height: 42px; line-height: 42px;font-size: 14px;text-align: right">
            <span style="color: #999AAA">排序：</span>
            <el-button type="text" :class="sortType == null ? '':'btn-focus-off'" @click="handleSort(null)">默认</el-button>
            <el-button type="text" :class="sortType === 0 ? '':'btn-focus-off'" @click="handleSort(0)">按更新时间</el-button>
            <el-button type="text" :class="sortType === 1 ? '':'btn-focus-off'" @click="handleSort(1)">按访问量</el-button>
            <el-button type="text" :class="sortType === 2 ? '':'btn-focus-off'" @click="handleSort(2)">按点赞量</el-button>
        </div>
        <template v-for="item in articleList">
            <div :key="item.id" class="article-item-box">
                <el-row type="flex" align="middle">
                    <el-tag size="mini" type="danger" v-if="item.type === '原创'">{{ item.type }}</el-tag>
                    <el-tag size="mini" type="success" v-if="item.type === '转载'">{{ item.type }}</el-tag>
                    <el-tag size="mini" v-if="item.type === '翻译'">{{ item.type }}</el-tag>
                    <h4 style="margin: 0 0 0 10px"
                        @click="$router.push({
                                name: 'preview',
                                query: {
                                    id: item.id,
                                    from: 0
                                }
                            })"
                        class="hover-on"
                        v-html="item.title"
                    />
                </el-row>
                <el-row class="summary-box">
                    {{ item.summary }}
                </el-row>
                <el-row type="flex" align="middle">
                    <div style="width: 50%;font-size: 14px">
                        <i class="el-icon-date" style="color: #999AAA" />
                        <span style="margin-left: 4px">{{ item.createTime }}</span>
                        <i class="el-icon-view" style="color: #999AAA;margin-left: 16px" />
                        <span style="margin-left: 4px">{{ item.views }}</span>
                        <i class="el-icon-thumb" style="color: #999AAA;margin-left: 16px" />
                        <span style="margin-left: 4px">{{ item.admire }}</span>
                        <i class="el-icon-chat-square" style="color: #999AAA;margin-left: 16px" />
                        <span style="margin-left: 4px">{{ item.comments }}</span>
                    </div>
                    <div style="width: 50%; text-align: right">
                        <el-button type="text" size="mini" class="handle-btn"
                                   @click="handleIsTop(item.id)"
                                   style="color: #5893C2">{{item.isTop === 0 ? '置顶':'取消置顶'}}</el-button>
                        <el-button type="text" size="mini" class="handle-btn"
                                   @click="$router.push({name: 'articleEdit', query: {id: item.id}})"
                                   style="color: #5893C2">编辑</el-button>
                        <el-popconfirm
                                confirm-button-text="删除"
                                cancel-button-text="取消"
                                confirm-button-type="danger"
                                icon="el-icon-info"
                                icon-color="red"
                                style="margin-left: 10px"
                                @onConfirm="handleDelete(item.id)"
                                title="确定要删除吗?">
                            <el-button type="text" size="mini" slot="reference" class="handle-btn" style="color: #F56C6C">删除</el-button>
                        </el-popconfirm>
                    </div>
                </el-row>
            </div>
        </template>
        <el-pagination
                style="margin-top: 20px;text-align: center"
                background
                layout="prev, pager, next"
                v-if="this.total > 1"
                :page-size="20"
                :current-page.sync="currentPage"
                @current-change="handlePageChange"
                :page-count="total">
        </el-pagination>
    </div>
</template>

<script>
    export default {
        name: "article1",
        data () {
            return {
                articleList: [],
                total: 0,
                currentPage: 0,
                query: {
                    year: '',
                    month: '',
                    type: '',
                    categoryId: '',
                    keyWords: '',
                    sortType: null
                },
                year: [],
                category: []
            }
        },
        methods: {
            handleDelete (id) {
                this.$emit('loadingStart', 2);
                this.post('/article/delete', {id: id}).then(res => {
                    if (res.success){
                        this.$message.success(res.message);
                        this.getArticleList();
                    } else {
                        this.$message.error(res.message);
                        this.$emit('loadingComplete');
                    }
                    this.$emit('loadingComplete');
                })
            },
            handleIsTop (id) {
                this.$emit('loadingStart', 2);
                this.post('/article/isTop', {id: id}).then(() => {
                    this.$emit('loadingComplete');
                    this.getArticleList();
                })
            },
            handleSearch () {
                this.$emit('loadingStart', 1);
                this.getArticleList();
            },
            handlePageChange (){
                this.$emit('loadingStart', 1);
                this.getArticleList();
            },
            getArticleList () {
                this.get('/article/queryList', {
                    year: this.query.year,
                    month: this.query.month,
                    type: this.query.type,
                    categoryId: this.query.categoryId,
                    keyWords: this.query.keyWords,
                    sortType: this.query.sortType,
                    page: this.currentPage
                }).then(res => {
                    this.articleList = res.data.records;
                    this.total = res.data.pages;
                    this.currentPage = res.data.current;
                    this.$emit('loadingComplete');
                    if (this.query.keyWords){
                        this.articleList.forEach(item => {
                            let text = item.title;
                            item.title = text.replace(this.query.keyWords,
                                `<span style="color: #F56C6C">${this.query.keyWords}</span>`)
                        })
                    }
                })
            },
            getYears () {
                this.get('/article/getYears', {}).then(res => {
                    this.year = res.data;
                    this.$emit('loadingComplete');
                })
            },
            getCategory () {
                this.get("/category/allCategorySimple", {}).then(res => {
                    this.category = res.data;
                    this.$emit('loadingComplete');
                })
            },
            handleSort (type) {
                this.$emit('loadingStart', 1);
                this.query.sortType = type;
                this.getArticleList();
            },
        },
        created() {
            this.$emit('loadingStart', 3);
            this.getArticleList();
            this.getYears();
            this.getCategory();
        },
        computed: {
            selectYear () {
                return this.query.year;
            },
            sortType () {
                return this.query.sortType;
            }
        },
        watch: {
            selectYear (newValue) {
                if (newValue == null){
                    this.query.month = null;
                } else {
                    this.query.month = '';
                }
            }
        }
    }
</script>

<style scoped>
    .btn-focus-off{
        color: #999AAA;
    }
    .handle-btn{
        opacity: 0;
        font-size: 14px;
        transition: all .25s;
    }
    .article-item-box{
        padding: 10px 16px;
        border-bottom: 1px solid #f0f2f5;
        transition: all .25s;
    }
    .article-item-box:hover{
        background-color: rgba(0,0,0,0.05);
    }
    .article-item-box:hover .handle-btn{
        opacity: 1;
    }
    .summary-box{
        padding: 4px 0;
        font-size: 14px;
        color: #999AAA;
        line-height: 24px;
        display: -webkit-box;
        -webkit-box-orient: vertical;
        -webkit-line-clamp: 2;
        overflow: hidden;
        margin-top: 6px;
    }
    .hover-on{
        cursor: pointer;
        transition: all .25s;
    }
    .hover-on:hover{
        transform: translateX(8px);
        color: #FFC940;
    }
</style>