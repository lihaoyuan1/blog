<template>
    <div  style="margin: auto;max-width: 1020px;padding-top: 10px;padding-bottom: 50px">
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{name: 'category'}">分类专栏</el-breadcrumb-item>
            <el-breadcrumb-item>{{categoryName}}</el-breadcrumb-item>
        </el-breadcrumb>
        <el-table
                :data="articleList"
                :header-cell-style="{background:'#eef1f6',color:'#606266'}"
                highlight-current-row
                style="width: 100%;margin-top: 14px">
            <el-table-column
                    type="index"
                    align="center"
                    width="120">
            </el-table-column>
            <el-table-column label="标题">
                <template slot-scope="scope">
                    <span class="title">{{scope.row.title}}</span>
                </template>
            </el-table-column>
            <el-table-column
                    label="操作"
                    align="center"
                    width="320">
                <template slot-scope="scope">
                    <el-button style="color: #349edf" size="mini"
                               @click="$router.push({name: 'preview', query:{id: scope.row.id, source: categoryId, sourceName: categoryName, from: 1}})"
                               type="text">查看</el-button>
                    <el-divider direction="vertical" />
                    <el-button type="text" size="mini" class="handle-btn"
                               @click="handleIsTop(scope.row.id)"
                               style="color: #349edf">{{scope.row.isTop === 0 ? '置顶':'取消置顶'}}</el-button>
                    <el-divider direction="vertical" />
                    <el-popconfirm
                            confirm-button-text="移除"
                            cancel-button-text="取消"
                            confirm-button-type="danger"
                            icon="el-icon-info"
                            icon-color="red"
                            @onConfirm="release(scope.row.id)"
                            title="确定要移除吗?">
                        <el-button type="text" size="mini" slot="reference" class="handle-btn" style="color: #F56C6C">移除</el-button>
                    </el-popconfirm>
                </template>
            </el-table-column>
        </el-table>
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
        name: "category-manage",
        data () {
            return{
                articleList: [],
                total: 0,
                currentPage: 0,
            }
        },
        methods: {
            release (articleId){
                this.$emit('loadingStart', 2);
                this.post('/article/releaseFromCategory', {
                    categoryId: this.categoryId,
                    articleId: articleId
                }).then(res => {
                    if (res.success){
                        this.$emit('loadingComplete');
                        this.loadData();
                        this.$message.success(res.message);
                    } else {
                        this.$message.error(res.message);
                        this.$emit('loadingComplete');
                    }
                })
            },
            handleIsTop (id) {
                this.$emit('loadingStart', 2);
                this.post('/article/isTop', {id: id}).then(() => {
                    this.$emit('loadingComplete');
                    this.loadData();
                })
            },
            handlePageChange (){
                this.$emit('loadingStart', 1);
                this.loadData();
            },
            loadData () {
                this.get('/article/listByCategoryId', {
                    categoryId: this.categoryId,
                    page: this.currentPage
                }).then(res => {
                    this.articleList = res.data.records;
                    this.total = res.data.pages;
                    this.currentPage = res.data.current;
                    this.$emit('loadingComplete');
                })
            }
        },
        created() {
            this.$emit('loadingStart', 1);
            this.loadData();
        },
        computed: {
            categoryId () {
                return this.$route.query.id;
            },
            categoryName () {
                return this.$route.query.name;
            }
        }
    }
</script>

<style scoped>
    .title{
        display: inline-block;
        font-weight: 400;
        color: #212529;
        font-size: 16px;
        white-space: nowrap;
        text-overflow: ellipsis;
        max-width: 660px;
        overflow: hidden;
    }
</style>