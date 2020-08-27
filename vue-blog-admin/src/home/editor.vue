<template>
    <div>
        <div style="margin-bottom: 15px">
            <span style="width: 75px;display: inline-block">文章标题:</span>
            <el-input placeholder="标题" @change="handleSaveTitle" v-model="title" style="width: calc(100% - 183px)" />
            <el-button type="primary" style="float: right;" @click="check1">发表文章</el-button>
        </div>
        <div class="editor-container" id="editor-container">
            <button v-if="iframeLoaded&&!fullScreen" class="edit-toolbar-btn" @click="handleFullScreen">
                <full-screen />
            </button>
            <button v-if="iframeLoaded&&fullScreen" class="edit-toolbar-btn" @click="handleFullScreen">
                <full-screen-exit />
            </button>
        </div>
        <el-dialog title="发布文章"
                   :visible.sync="dialogVisible"
                   :close-on-click-modal="false"
                   :show-close="!btnDisabled"
                   :close-on-press-escape="false" width="580px">
            <el-divider style="margin: 0 !important;" />
            <el-row type="flex" align="middle">
                <el-col :span="3">文章标签:</el-col>
                <el-col :span="21">
                    <div>
                        <template v-for="item in selectTagList">
                            <el-tag :key="item.id" class="sub-tag" closable type="success" @close="deleteSelectTag(item)">{{item.tagName}}</el-tag>
                        </template>
                        <el-popover class="sub-tag" placement="bottom" width="400" trigger="click" v-if="selectTagList.length < 5">
                            <el-alert title="还可添加4个标签" type="info" :closable="false" show-icon />
                            <div>
                                <template v-for="item in tagList">
                                    <el-tag
                                            style="margin: 14px 14px 0 0;cursor: pointer"
                                            :key="item.id"
                                            @click="handleSelectTagList(item)"
                                            type="info">
                                        {{ item.tagName }}</el-tag>
                                </template>
                            </div>
                            <el-button size="small" slot="reference" style="padding: 9px">
                                <i class="el-icon-plus" style="font-weight: 800" />
                                添加文章标签
                            </el-button>
                        </el-popover>
                    </div>
                </el-col>
            </el-row>
            <el-row type="flex" align="middle" style="margin-top: 20px">
                <el-col :span="3">分类专栏:</el-col>
                <el-col :span="21">
                    <template v-for="item in selectCategoryList">
                        <el-tag
                                :key="item.id"
                                class="sub-tag"
                                :closable="selectCategoryList.length > 1"
                                type="success"
                                @close="deleteSelectCategoryList(item)">
                            {{item.categoryName}}</el-tag>
                    </template>
                    <el-tag class="sub-tag" type="info"><i class="el-icon-info" />&nbsp;选择分类专栏</el-tag>
                </el-col>
            </el-row>
            <el-row style="margin-top: 10px">
                <el-col :span="21" :offset="3" style="border: 1px solid #e8e8ee;padding-bottom: 16px;border-radius: 4px">
                    <el-alert title="最多选择三个分类专栏" type="info" :closable="false" />
                    <el-checkbox-group v-model="selectCategoryList" :max="3" :min="1">
                        <template v-for="item in categoryList">
                            <el-checkbox
                                    :label="item"
                                    :key="item.id"
                                    style="margin: 16px 0 0 16px">
                                {{ item.categoryName }}</el-checkbox>
                        </template>
                    </el-checkbox-group>
                </el-col>
            </el-row>
            <el-row type="flex" align="middle" style="margin-top: 20px">
                <el-col :span="3">文章类型:</el-col>
                <el-col :span="8">
                    <el-select v-model="type" placeholder="请选择">
                        <el-option label="原创" value="原创" />
                        <el-option label="翻译" value="翻译" />
                        <el-option label="转载" value="转载" />
                    </el-select>
                </el-col>
            </el-row>
            <el-row type="flex" align="middle" style="margin-top: 20px">
                <el-col :span="3">首页图片:</el-col>
                <el-col :span="21">
                    <el-input placeholder="上传图片" v-model="picture" :disabled="true" style="width: calc(100% - 88px)" />
                    <el-upload
                            :action="this.$uploadUrl"
                            :show-file-list="false"
                            accept="image/*"
                            :with-credentials="true"
                            :before-upload="(() => {this.btnDisabled = true})"
                            :on-success="handleSuccess"
                            :on-error="handleError"
                            style="display: inline-block">
                        <el-button type="text" style="margin: 0 15px;color: #67c23a">点击上传</el-button>
                    </el-upload>
                </el-col>
            </el-row>
            <el-row type="flex" align="middle" style="margin-top: 20px">
                <el-col :span="3">是否推荐:</el-col>
                <el-col :span="3"><el-switch v-model="recommendAble" active-color="#13CE66" inactive-color="#ff4949" /></el-col>
                <el-col :span="3">评论开启:</el-col>
                <el-col :span="3"><el-switch v-model="commentAble" active-color="#13CE66" inactive-color="#ff4949" /></el-col>
                <el-col :span="3">打赏开启:</el-col>
                <el-col :span="3"><el-switch v-model="rewardAble" active-color="#13CE66" inactive-color="#ff4949" /></el-col>
            </el-row>
            <el-divider />
            <span slot="footer">
                <el-button size="small" style="color: #333333" @click="dialogVisible = false" :disabled="btnDisabled">取 消</el-button>
                <el-button type="primary" plain size="small" @click="handlePush" :disabled="btnDisabled" :loading="btnLoading">发布文章</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    import Stackedit from "stackedit-js";
    import FullScreen from '../components/icon/fullscreen';
    import FullScreenExit from '../components/icon/fullscreen-exit';
    export default {
        name: "editor",
        components: {
            FullScreen, FullScreenExit
        },
        data () {
            return {
                categoryList: [],
                tagList: [],
                selectTagList: [],
                selectCategoryList: [],
                title: '',
                content: '',
                picture: '',
                type: '',
                recommendAble: false,
                rewardAble: true,
                commentAble: true,
                dialogVisible: false,
                btnLoading: false,
                btnDisabled: false,
                fullScreen: false,
                iframeLoaded: false
            }
        },
        methods: {
            handlePush () {
                if (this.selectCategoryList.length === 0){
                    this.$message.error("请至少选择一个分类专栏!");
                } else if (this.type === '') {
                    this.$message.error("请选择文章类型!");
                } else if (this.picture === '') {
                    this.$message.error("请上传文章配图!")
                } else {
                    this.btnLoading = true;
                    let categoryIds = [], tagIds = [];
                    for (let i=0; i<this.selectCategoryList.length; i++)
                        categoryIds.push(this.selectCategoryList[i].id);
                    for (let i=0; i<this.selectTagList.length; i++)
                        tagIds.push(this.selectTagList[i].id);
                    this.post('/article/createArticle', {
                        title: this.title,
                        content: this.content,
                        type: this.type,
                        rewardAble: this.rewardAble ? 1:0,
                        commentAble: this.commentAble ? 1:1,
                        recommendAble: this.recommendAble ? 1:0,
                        picture: this.picture,
                        categoryIds: categoryIds.join(),
                        tagIds: tagIds.join()
                    }).then(res => {
                        if (res.success) {
                            this.$message.success(res.message);
                            this.handleRemoveTitle();
                            this.handleRemoveContent();
                            this.timer = setTimeout(() => {
                                this.btnLoading = false;
                                this.$router.push({name: 'article'})
                            }, 1000)
                        } else {
                            this.btnLoading = false;
                            this.$message.error(res.message);
                        }
                    })
                }
            },
            deleteSelectCategoryList (obj) {
                for (let i=0; i<this.selectCategoryList.length; i++){
                    if (this.selectCategoryList[i].id === obj.id){
                        this.selectCategoryList.splice(i, 1);
                        return;
                    }
                }
            },
            handleSelectTagList (obj) {
                for (let i=0; i<this.selectTagList.length; i++){
                    if (this.selectTagList[i].id === obj.id)
                        return;
                }
                this.selectTagList.push(obj);
            },
            handleSuccess (res) {
                if (res.success){
                    this.$message.success("图片上传成功!");
                    this.picture = res.data;
                } else {
                    this.$message.error(res.message);
                }
                this.btnDisabled = false;
            },
            handleError () {
                this.$message.error("图片上传失败，请稍后重试!");
                this.btnDisabled = false;
            },
            deleteSelectTag (obj) {
                for (let i=0; i<this.selectTagList.length; i++){
                    if(this.selectTagList[i].id === obj.id){
                        this.selectTagList.splice(i, 1);
                        return;
                    }
                }
            },
            check1 () {
                if (this.title === '' || this.title === null) {
                    this.$message.error('标题不能为空哦！');
                } else if (this.title.length > 50) {
                    this.$message.error('标题太长哦！');
                } else if (this.content === '') {
                    this.$message.error('内容不能为空哦！');
                } else{
                    this.dialogVisible = true;
                }
            },
            handleSaveContent (value) {
                this.saveStorage('article_content', value);
            },
            handleSaveTitle (value) {
                this.saveStorage('article_title', value);
            },
            handleRemoveContent () {
                this.removeStorage('article_content');
            },
            handleRemoveTitle () {
                this.removeStorage('article_title');
            },
            loadList () {
                this.$emit('loadingStart', 2);
                this.get('/category/allCategorySimple', {}).then(res => {
                    if (res.success)
                        this.categoryList = res.data;
                    this.$emit('loadingComplete');
                });
                this.get('/tag/allTagSimple', {}).then(res => {
                    if (res.success)
                        this.tagList = res.data;
                    this.$emit('loadingComplete');
                })
            },
            handleFullScreen () {
                let root = document.getElementById('editor-container');
                let classList = root.classList;
                if (classList.contains('editor-container')){
                    classList.remove('editor-container');
                    classList.add('editor-container-fixed');
                } else {
                    classList.remove('editor-container-fixed');
                    classList.add('editor-container')
                }
                root.classList = classList;
                this.fullScreen = !this.fullScreen;
            }
        },
        created() {
            this.loadList();
            this.title = this.loadStorage("article_title");
            let content = this.loadStorage("article_content");
            this.content = content == null ? '':content;
        },
        mounted() {
            let stackedit = new Stackedit();
            stackedit.openFile({
                name: 'Filename',
                content: {
                    text: this.content
                }
            });
            stackedit.on('fileChange', (file) => {
                this.content = file.content.text;
                if (!this.iframeLoaded){
                    this.iframeLoaded = true;
                }
            });
        },
        watch: {
            content (value) {
                clearTimeout(this.timer);
                this.timer = setTimeout(() => {
                    this.handleSaveContent(value);
                }, 3000);
            }
        },
        destroyed() {
            clearTimeout(this.timer);
        }
    }
</script>

<style lang="scss">
    .el-dialog__title{
        color: #3c3c3c;
        font-weight: 600 !important;
    }
    .sub-tag{
        margin-left: 10px;
    }
    .sub-tag:nth-child(1){
        margin-left: 0;
    }
    .el-dialog{
        border-radius: 4px !important;
        border: 1px solid #e3e3e3;
    }
    .el-dialog__body{
        padding-top: 0 !important;
        padding-bottom: 0 !important;
    }
    .el-dialog__header{
        padding-bottom: 0 !important;
    }
    .el-dialog__footer{
        padding-top: 0 !important;
    }
</style>