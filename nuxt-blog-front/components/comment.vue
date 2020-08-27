<template>
  <div>
    <el-card style="margin-top: 14px">
      <div slot="header">
        <h3 style="color: #ffd04b;margin:0">评论</h3>
      </div>
      <div
        v-if="commentList.length === 0"
        style="font-size: 14px;color: #909399"
      >
        <i class="el-icon-info" />
        暂无评论，快来发表评论吧！
      </div>
      <template v-for="comment in commentList">
        <el-row :key="comment.id" type="flex" align="middle">
          <el-col :span="2">
            <el-avatar shape="square" :src="comment.avatar" />
          </el-col>
          <el-col :span="22">
            <el-row>
              <strong style="font-size: 15px">{{ comment.nickname }}</strong>
              &nbsp;
              <el-tag v-if="comment.type" size="mini" effect="dark">
                博主
              </el-tag>
              <span style="color: #909399;font-size: 13px">
                {{ comment.createTime }}
              </span>
            </el-row>
            <el-row style="line-height: 24px;font-size: 14px">
              {{ comment.content }}
            </el-row>
            <el-row>
              <el-button
                type="text"
                size="small"
                style="padding: 0;color: #909399"
                @click="handleReplay(comment.id, comment.nickname)"
              >
                回复
              </el-button>
            </el-row>
          </el-col>
        </el-row>
        <template v-for="item in comment.children">
          <el-row
            :key="item.id"
            type="flex"
            align="middle"
            style="position: relative;"
          >
            <el-col
              :span="1"
              style="position: absolute;top: 0;bottom: 0;text-align: center"
            >
              <el-divider direction="vertical" class="divider-vertical" />
            </el-col>
            <el-col :span="2" :offset="2">
              <el-avatar shape="square" :src="item.avatar" />
            </el-col>
            <el-col :span="20">
              <el-row>
                <strong style="font-size: 15px">
                  {{ item.nickname }}
                  <span style="color: #00b5ad">
                    <el-tag v-if="item.type" size="mini" effect="dark">
                      博主
                    </el-tag>
                    @ {{ item.parentNickName }}
                  </span>
                </strong>
                &nbsp;
                <span style="color: #909399;font-size: 13px">
                  {{ item.createTime }}
                </span>
              </el-row>
              <el-row style="line-height: 24px;font-size: 14px">
                {{ item.content }}
              </el-row>
              <el-row>
                <el-button
                  type="text"
                  size="small"
                  style="padding: 0;color: #909399"
                  @click="handleReplay(item.id, item.nickname)"
                  >回复</el-button
                >
              </el-row>
            </el-col>
          </el-row>
        </template>
      </template>
    </el-card>
    <el-card id="comment" style="margin-top: 14px">
      <el-row
        v-if="replay"
        type="flex"
        align="middle"
        style="font-size: 14px;padding: 5px 0"
      >
        <span style="color: #999999;">回复：</span>
        <span>{{ replay }}</span>
        <el-button
          plain
          size="mini"
          type="text"
          style="margin-left: 10px;padding: 0 8px"
          @click="handleCancelReplay"
        >
          取 消
        </el-button>
      </el-row>
      <el-form ref="commentForm" :model="commentForm" :rules="commentRules">
        <el-form-item prop="content">
          <el-input
            v-model="commentForm.content"
            type="textarea"
            maxlength="80"
            show-word-limit
            :rows="4"
          />
        </el-form-item>
        <el-form-item style="float: left" prop="nickname">
          <el-input
            v-model="commentForm.nickname"
            placeholder="昵称"
            prefix-icon="el-icon-user"
            size="small"
          />
        </el-form-item>
        <el-form-item style="float: left;margin-left: 14px" prop="email">
          <el-input
            v-model="commentForm.email"
            placeholder="邮箱"
            prefix-icon="el-icon-message"
            size="small"
          />
        </el-form-item>
        <el-form-item style="float: left;margin-left: 14px">
          <el-button
            type="primary"
            icon="el-icon-edit"
            size="small"
            @click="handleSubmit('commentForm')"
          >
            发布
          </el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import qs from 'qs'
export default {
  name: 'Comment',
  props: {
    articleId: { type: Number, default: null }
  },
  data() {
    return {
      commentList: [],
      commentRules: {
        content: [
          { required: true, message: '请输入评论内容!', trigger: 'blur' }
        ],
        nickname: [
          { required: true, message: '请输入昵称！', trigger: 'blur' }
        ],
        email: [
          { required: true, message: '请输入邮箱!', trigger: 'blur' },
          {
            type: 'email',
            message: '请输入正确的邮箱地址',
            trigger: ['blur', 'change']
          }
        ]
      },
      commentForm: {
        content: '',
        nickname: '',
        email: '',
        type: 0
      },
      avatar: [
        'https://blog-lhy.oss-cn-hangzhou.aliyuncs.com/avatar/20170507160427_CPyBR.jpeg',
        'https://blog-lhy.oss-cn-hangzhou.aliyuncs.com/avatar/20170507160504_MYKS3.jpeg',
        'https://blog-lhy.oss-cn-hangzhou.aliyuncs.com/avatar/20170507160547_FQcvE.jpeg',
        'https://blog-lhy.oss-cn-hangzhou.aliyuncs.com/avatar/20170507160618_ATMGm.thumb.700_0.jpeg',
        'https://blog-lhy.oss-cn-hangzhou.aliyuncs.com/avatar/20170507160645_23nWC.thumb.700_0.jpeg',
        'https://blog-lhy.oss-cn-hangzhou.aliyuncs.com/avatar/20170507160711_uU3Zy.thumb.700_0.jpeg',
        'https://blog-lhy.oss-cn-hangzhou.aliyuncs.com/avatar/20170507160739_8tNSP.jpeg',
        'https://blog-lhy.oss-cn-hangzhou.aliyuncs.com/avatar/20170507160820_JuXzL.jpeg'
      ],
      replay: ''
    }
  },
  mounted() {
    this.loadComments()
  },
  methods: {
    loadComments() {
      this.$axios
        .get('/comment/getComments', {
          params: { articleId: this.articleId }
        })
        .then((res) => {
          this.commentList = res.data
        })
    },
    handleReplay(parentId, parentNickName) {
      this.commentForm.parentId = parentId
      this.commentForm.parentNickName = parentNickName
      this.replay = parentNickName
    },
    handleCancelReplay() {
      if (this.commentForm.parentId) delete this.commentForm.parentId
      if (this.commentForm.parentNickName)
        delete this.commentForm.parentNickName
      this.replay = ''
    },
    handleSubmit(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (!this.commentForm.avatar)
            this.commentForm.avatar = this.avatar[
              parseInt(Math.random() * 8, 0)
            ]
          if (!this.commentForm.articleId)
            this.commentForm.articleId = this.articleId
          this.submitComment()
        }
      })
    },
    submitComment() {
      this.$axios
        .post('/comment/create', qs.stringify(this.commentForm))
        .then((res) => {
          this.commentForm.content = ''
          if (this.commentForm.parentId) delete this.commentForm.parentId
          if (this.commentForm.parentNickName)
            delete this.commentForm.parentNickName
          this.replay = ''
          if (res.success) {
            this.commentList = res.data
          }
        })
    }
  }
}
</script>

<style scoped>
.divider-vertical {
  height: 100%;
}
</style>
