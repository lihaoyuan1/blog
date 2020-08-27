<template>
  <div>
    <el-card>
      <div slot="header" style="position: relative">
        <el-row type="flex" justify="center" style="text-align: center">
          <el-tag v-if="article.type === '原创'" type="danger">
            {{ article.type }}
          </el-tag>
          <el-tag v-if="article.type === '转载'" type="success">
            {{ article.type }}
          </el-tag>
          <el-tag v-if="article.type === '翻译'">
            {{ article.type }}
          </el-tag>
          <span class="title">{{ article.title }}</span>
        </el-row>
        <div class="author-info">
          <span>作者：{{ article.nickname }}</span>
          <span>日期：{{ article.createTime }}</span>
          <span>浏览：{{ article.views }}</span>
          <span>点赞：{{ article.admire }}</span>
        </div>
      </div>
      <el-image
        :src="article.picture"
        :preview-src-list="previewSrcList"
        class="image"
      />
      <div
        class="markdown-body content-wrapper"
        v-html="md.render(article.content)"
      />
      <el-row style="margin-top: 14px" type="flex" align="middle">
        <span style="font-size: 18px;font-weight: 600">标签：</span>
        <template v-for="item in article.tagList">
          <el-tag
            :key="item.id"
            :type="tagColor[parseInt(Math.random() * 5, 0)]"
            style="margin-left: 10px"
          >
            {{ item.tagName }}
          </el-tag>
        </template>
      </el-row>
      <el-row type="flex" justify="center" align="middle">
        <el-col :span="3">
          <vue-clap-button @clap="handleClap" @cancel="handleClapCancel" />
        </el-col>
        <el-col v-if="article.rewardAble" :span="3">
          <el-popover placement="bottom" trigger="click">
            <div
              style="padding: 14px 14px 0 14px;border: 1px solid #ffd04b;border-radius: 5px"
            >
              <el-row type="flex">
                <el-col
                  style="padding: 5px;border: 1px solid #EBEEF5;border-radius: 5px;line-height: 0;"
                >
                  <img src="../../../static/me-qrcode.png" width="120" alt="" />
                </el-col>
                <el-col
                  style="padding: 5px;border: 1px solid #EBEEF5;border-radius: 5px;line-height: 0;text-align: center;margin-left: 5px"
                >
                  <img src="../../../static/zhifubao.jpg" width="120" alt="" />
                </el-col>
              </el-row>
              <el-row style="color: #ffd04b;line-height: 28px">
                <el-col :span="12" style="text-align: center">
                  微 信
                </el-col>
                <el-col :span="12" style="text-align: center;color: #ffd04b">
                  支 付 宝
                </el-col>
              </el-row>
            </div>
            <el-button slot="reference" type="primary" plain round>
              赞 赏
            </el-button>
          </el-popover>
        </el-col>
      </el-row>
      <div class="shareStatement" style="font-size: 14px">
        <el-row type="flex" align="middle">
          <el-col :span="16">
            <ul style="margin: 0">
              <li>
                作者：{{ article.nickname }}
                <el-link
                  type="success"
                  @click="$router.push({ name: 'index-about' })"
                >
                  （联系作者）
                </el-link>
              </li>
              <li>发表时间：{{ article.createTime }}</li>
              <li>版权声明：自由转载-非商用-非衍生-保留署名</li>
              <li>公众号转载：请在文章添加作者公众号二维码</li>
            </ul>
          </el-col>
          <el-col :span="8" style="line-height: 0;text-align: right">
            <img src="../../../static/me-qrcode.png" width="120" alt="" />
          </el-col>
        </el-row>
      </div>
    </el-card>
    <comment v-if="article.commentAble" :article-id="article.id" />
  </div>
</template>

<script>
import 'highlight.js/styles/atom-one-dark.css'
import 'github-markdown-css/github-markdown.css'
import '../../../assets/css/katex.css'
import qs from 'qs'
import vueClapButton from '../../../components/clap-button/components/vue-clap-button'
import Comment from '../../../components/comment'
export default {
  name: 'ArticleId',
  components: {
    vueClapButton,
    Comment
  },
  async asyncData({ $axios, params }) {
    const [articleRes] = await Promise.all([
      $axios.get('/article/getOne', { params: { id: params.id } })
    ])
    return {
      article: articleRes.data,
      previewSrcList: [articleRes.data.picture]
    }
  },
  data() {
    return {
      tagColor: ['', 'success', 'info', 'warning', 'danger'],
      targetNickname: null
    }
  },
  computed: {
    articleId() {
      return this.$route.query.id
    }
  },
  watch: {
    articleId(newVal) {
      this.$axios
        .get('/article/getOne', {
          params: { id: newVal }
        })
        .then((res) => {
          this.article = res.data
        })
    }
  },
  created() {
    this.initMarkdownIt()
  },
  methods: {
    handleClapCancel() {
      this.$axios.post(
        '/article/admire',
        qs.stringify({
          id: this.article.id,
          flag: -1
        })
      )
    },
    handleClap() {
      this.$axios.post(
        '/article/admire',
        qs.stringify({
          id: this.article.id,
          flag: 1
        })
      )
    },
    initMarkdownIt() {
      const hljs = require('highlight.js')
      const katex = require('@iktakahiro/markdown-it-katex')
      const sub = require('markdown-it-sub')
      const sup = require('markdown-it-sup')
      const footNote = require('markdown-it-footnote')
      const defList = require('markdown-it-deflist')
      const abbr = require('markdown-it-abbr')
      const mark = require('markdown-it-mark')
      const that = this
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
  }
}
</script>

<style scoped>
.title {
  font-size: 22px;
  margin-bottom: 10px;
  font-weight: 700;
  margin-left: 10px;
}
.author-info {
  color: #888;
  font-size: 14px;
  text-align: center;
}
.author-info span:not(:first-child) {
  margin-left: 10px;
}
.content-wrapper {
  margin-top: 14px;
}
.image {
  transition: all 0.25s;
}
.image:hover {
  box-shadow: 0 0 8px rgba(0, 0, 0, 0.6);
}
.shareStatement {
  margin-top: 20px;
  border-top: 2px solid #909399;
  border-bottom: 2px solid #909399;
  padding: 40px;
  line-height: 30px;
  color: #2c662d;
  background: #fcfff5;
}
</style>
