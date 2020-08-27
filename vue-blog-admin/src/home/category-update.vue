<template>
    <div style="margin: auto;max-width: 1020px;padding-top: 10px;padding-bottom: 50px">
        <div>
            <el-button type="text" style="color: #999;font-size: 15px" @click="$router.go(-1)">
                <i class="el-icon-arrow-left" />&nbsp;返回分类专栏
            </el-button>
        </div>
        <el-row type="flex" align="middle" style="margin-top: 20px">
            <el-col :span="3">分类专栏名称:&nbsp;</el-col>
            <el-col :span="12">
                <el-input type="text" v-model="category.categoryName" placeholder="请输入分类专栏名称" />
            </el-col>
        </el-row>
        <el-row type="flex" align="top" style="margin-top: 20px">
            <el-col :span="3">分类专栏简介:&nbsp;</el-col>
            <el-col :span="12">
                <el-input type="textarea"
                          maxlength="280"
                          v-model="category.description"
                          show-word-limit
                          rows="5"
                          placeholder="请输入简介内容">
                </el-input>
            </el-col>
        </el-row>
        <el-row type="flex" align="top" style="margin-top: 20px">
            <el-col :span="3">分类专栏配图:&nbsp;</el-col>
            <el-col :span="10">
                <el-upload :action="this.$uploadUrl"
                           :show-file-list="false"
                           accept="image/*"
                           :with-credentials="true"
                           class="picture-upload"
                           :on-success="handleSuccess"
                           :on-error="handleError">
                    <el-image v-if="category.picture" :src="category.picture"
                              @load="btnDisabled = false"
                              @error="btnDisabled = false"
                              fit="cover" alt=""/>
                    <i v-else class="el-icon-plus avatar-uploader-icon" />
                </el-upload>
            </el-col>
        </el-row>
        <el-row type="flex" align="top" style="margin-top: 20px">
            <el-col :offset="3" :span="10">
                <el-button type="danger" plain @click="check">提 交</el-button>
            </el-col>
        </el-row>
    </div>
</template>

<script>
    export default {
        name: "category-update",
        data () {
            return {
                category: {},
                btnDisabled: false
            }
        },
        methods: {
            check () {
                if (!this.category.categoryName) {
                    this.$message.error('专栏名称不能为空哦！');
                } else if (!this.category.description) {
                    this.$message.error("专栏简介不能为空哦！");
                } else if (!this.category.picture) {
                    this.$message.error("请上传专栏配图！");
                } else {
                    this.$emit('loadingStart', 1);
                    this.post('/category/updateCategory', this.category).then(res => {
                        this.$emit('loadingComplete');
                        if (res.success) {
                            this.$message.success(res.message);
                            this.$router.push({name: 'category'});
                        } else {
                            this.$message.error(res.message);
                        }
                    })
                }
            },
            handleSuccess (res) {
                if (res.success){
                    this.$message.success("图片上传成功!");
                    this.category.picture = res.data;
                } else {
                    this.$message.error(res.message);
                    this.btnDisabled = false;
                }
            },
            handleError () {
                this.$message.error("图片上传失败，请稍后重试!");
                this.btnDisabled = false;
            },
            loadData () {
                this.$emit('loadingStart', 1);
                let id = this.$route.query.id;
                this.get(`/category/getOne/${id}`, {}).then(res => {
                    this.category = res.data;
                    this.$emit('loadingComplete');
                })
            },
            keyEnter () {
                document.onkeydown = () => {
                    if (window.event.keyCode === 13 && this.dialogVisible){
                        this.check();
                    }
                };
            }
        },
        created() {
            this.keyEnter();
            this.loadData();
        }
    }
</script>

<style scoped>
    .picture-upload{
        line-height: 120px;
        text-align: center;
        width: 120px;
        height: 120px;
        border-radius: 4px;
        border: 1px dotted #999;
        font-size: 32px;
        color: #999;
    }
</style>