<template>
    <div style="margin: auto;max-width: 1020px;padding-top: 10px;padding-bottom: 50px">
        <el-row type="flex" justify="space-between" align="middle" style="padding: 14px 0 0 0">
            <h3 style="color: #212529;margin: 0">标签专栏</h3>
            <el-button type="danger" plain @click="dialogVisible = true">新 建</el-button>
        </el-row>
        <el-divider />
        <el-table :data="tagList" stripe style="width: 100%">
            <el-table-column
                    prop="id"
                    label="序号"
                    align="center"
                    width="120">
                <template slot-scope="scope">
                    {{scope.$index+1}}
                </template>
            </el-table-column>
            <el-table-column
                    prop="name"
                    label="名称">
                <template slot-scope="scope">
                    {{scope.row.tagName}}
                </template>
            </el-table-column>
            <el-table-column
                    label="操作"
                    align="center"
                    width="300">
                <template slot-scope="scope">
                    <el-button style="color: #349edf" type="text" @click="handleUpdate(scope.row.id, scope.row.tagName)">编辑</el-button>
                    <el-divider direction="vertical" />
                    <el-popconfirm
                        confirm-button-text="删除"
                        cancel-button-text="取消"
                        confirm-button-type="danger"
                        icon="el-icon-info"
                        icon-color="red"
                        @onConfirm="handleDelete(scope.row.id)"
                        title="确定要删除吗?">
                        <el-button style="color: #F56C6C" type="text" slot="reference">删除</el-button>
                    </el-popconfirm>
                </template>
            </el-table-column>
            <el-table-column
                    prop="count"
                    label="文章数"
                    align="center"
                    width="120">
                <template slot-scope="scope">
                    {{scope.row.count}}
                </template>
            </el-table-column>
            <el-table-column
                    prop="count"
                    align="center"
                    label="浏览量"
                    width="120">
                <template slot-scope="scope">
                    {{scope.row.views}}
                </template>
            </el-table-column>
        </el-table>
        <el-dialog
                title="创建标签"
                :visible.sync="dialogVisible"
                width="420px"
                :center="true"
                :close-on-click-modal="false"
                @close="id = tagName = ''">
            <el-input v-model="tagName" placeholder="标签名" style="margin: 18px 0" />
            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button
                        type="primary"
                        :loading="btnLoading > 0"
                        @click="handleSubmit"
                        @keyup.enter="handleSubmit">提 交</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        name: "tag",
        data () {
            return {
                dialogVisible: false,
                tagList: [],
                id: '',
                tagName: '',
                btnLoading: 0,
            }
        },
        created() {
            this.$emit('loadingStart', 1);
            this.getTagList();
            this.keyEnter();
        },
        methods: {
            handleDelete (id) {
                this.$emit("loadingStart", 1);
                this.post("/tag/deleteTag", {id: id}).then(res => {
                    if (res.success){
                        this.$message.success(res.message);
                        this.tagList = res.data;
                    } else {
                        this.$message.error(res.message);
                    }
                    this.$emit('loadingComplete');
                })
            },
            handleUpdate (id, tagName) {
                this.id = id; this.tagName = tagName; this.dialogVisible = true;
            },
            getTagList () {
                this.get('/tag/allTag', {}).then(res => {
                    this.tagList = res.data;
                    this.$emit('loadingComplete');
                })
            },
            handleSubmit () {
                if (this.tagName) {
                    this.$emit('loadingStart', 2);
                    if (!this.id) {
                        this.btnLoading = true;
                        this.post('/tag/addTag', {tagName: this.tagName}).then(res => {
                            if (res.success) {
                                this.$message.success(res.message);
                            } else {
                                this.$message.error(res.message);
                            }
                            this.btnLoading = false; this.dialogVisible = false;
                            this.$emit('loadingComplete');
                            this.getTagList();
                        })
                    } else {
                        this.btnLoading = true;
                        this.post('/tag/updateTag', {id: this.id, tagName: this.tagName}).then(res => {
                            if (res.success) {
                                this.$message.success(res.message);
                            } else {
                                this.$message.error(res.message);
                            }
                            this.btnLoading = false; this.dialogVisible = false;
                            this.$emit('loadingComplete');
                            this.getTagList();
                        })
                    }
                }
            },
            keyEnter () {
                document.onkeydown = () => {
                    if (window.event.keyCode === 13 && this.dialogVisible){
                        this.handleSubmit()
                    }
                };
            },
        }
    }
</script>

<style scoped>
    .el-divider--horizontal{
        margin-bottom: 0 !important;
    }
</style>