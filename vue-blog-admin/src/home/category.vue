<template>
    <div style="margin: auto;max-width: 1020px;padding-top: 10px;padding-bottom: 50px">
        <el-row type="flex" justify="space-between" align="middle" style="padding: 14px 0 0 0">
            <h3 style="color: #212529;margin: 0">分类专栏</h3>
            <el-button type="danger" plain @click="$router.push({name: 'categoryAdd'})">新 建</el-button>
        </el-row>
        <p style="margin: 0;padding: 5px 0 10px 0;color: #999;font-size: 14px">提示：拖动可以进行排序</p>
        <div style="clear: both" />
        <el-divider />
        <el-row style="color: #999; font-size: 15px;padding: 14px 0">
            <el-col :span="2" style="text-align: center">排序</el-col>
            <el-col :span="4" style="text-align: center;">配图</el-col>
            <el-col :span="8">专栏名称</el-col>
            <el-col :span="6" style="text-align: center;">操作</el-col>
            <el-col :span="2" style="text-align: center">文章数</el-col>
            <el-col :span="2" style="text-align: center">浏览量</el-col>
        </el-row>
        <el-divider />
        <drag-able v-model="categoryList" :options="{chosenClass: 'chosenClass', dragClass: 'dragClass', animation: 500}" @end="handleEnd">
            <transition-group>
                <template v-for="(item, index) in categoryList">
                    <el-row :key="index" type="flex" align="middle" class="category-item">
                        <el-col :span="2" style="text-align: center;cursor: text">{{index+1}}</el-col>
                        <el-col :span="4">
                            <div style="width: 48px;height: 48px;line-height: 0;cursor: default;margin: auto">
                                <el-image :src="item.picture"
                                          lazy
                                          fit="cover"
                                          style="width: 100%;height: 100%;"
                                          :preview-src-list="previewSrcList"/>
                            </div>
                        </el-col>
                        <el-col :span="8" class="item-category-name">{{ item.categoryName }}</el-col>
                        <el-col :span="6" style="text-align: center">
                            <el-button style="color: #349edf"
                                       type="text"
                                       @click="$router.push({
                                           name: 'categoryManage',
                                            query: {
                                               id: item.id,
                                                name: item.categoryName
                                           }})"
                            >
                                管理</el-button>
                            <el-divider direction="vertical" />
                            <el-button style="color: #349edf" type="text" @click="$router.push({name: 'categoryUpdate', query: {id: item.id}})">编辑</el-button>
                            <el-divider direction="vertical" />
                            <el-popconfirm
                                    confirm-button-text="删除"
                                    cancel-button-text="取消"
                                    confirm-button-type="danger"
                                    icon="el-icon-info"
                                    icon-color="red"
                                    @onConfirm="handleDelete(item.id)"
                                    title="确定要删除吗?">
                                <el-button style="color: #F56C6C" type="text" slot="reference">删除</el-button>
                            </el-popconfirm>
                        </el-col>
                        <el-col :span="2" style="text-align: center;cursor: text">{{ item.count }}</el-col>
                        <el-col :span="2" style="text-align: center;cursor: text">{{ item.views }}</el-col>
                    </el-row>
                </template>
            </transition-group>
        </drag-able>
    </div>
</template>

<script>
    import DragAble from 'vuedraggable'
    export default {
        name: "category",
        data () {
            return{
                categoryList: [],
                previewSrcList: []
            }
        },
        methods: {
            handleDelete (id) {
                this.$emit('loadingStart', 1);
                this.post('/category/deleteCategory', {id: id}).then(res => {
                    if (res.success){
                        this.$message.success(res.message);
                        this.categoryList = res.data;
                    } else {
                        this.$message.error(res.message);
                    }
                    this.$emit('loadingComplete');
                });
            },
            getCategoryList () {
                this.get('/category/allCategory', {}).then(res => {
                    this.categoryList = res.data;
                    this.categoryList.forEach(item => {
                        this.previewSrcList.push(item.picture);
                    });
                    this.$emit('loadingComplete');
                })
            },
            handleEnd () {
                this.$emit('loadingStart', 1);
                let array = [];
                this.categoryList.forEach(item => array.push(item.id));
                this.post('/category/sortCategory', {ids: array}).then(res => {
                    if(res.success){
                        this.categoryList = res.data;
                        this.$message.success(res.message);
                    } else {
                        this.$message.error(res.message);
                    }
                    this.$emit('loadingComplete');
                })
            },
        },
        created() {
            this.$emit('loadingStart', 1);
            this.getCategoryList();
        },
        components: {
            DragAble
        },
    }
</script>

<style scoped>
    .el-divider--horizontal{
        margin: 0 !important;
    }
    .item-category-name{
        line-height: 32px;
        color: transparent;
        text-shadow: 0 0 0 #545454;
        font-weight: 700;
        font-size: 15px;
        cursor: pointer;
    }
    .category-item{
        border-bottom: 1px dotted #ddd;
        padding: 24px 0;
        cursor: move;
    }
    .chosenClass{
        background-color: #fef0f0;
    }
    .dragClass{
        background: rgba(0, 0, 0, 0.1);
    }
</style>