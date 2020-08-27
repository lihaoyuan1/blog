<template>
  <div>
    <el-card>
      <div slot="header" style="position: relative">
        <div class="label hot">热门</div>
        <p class="title">热门文章</p>
      </div>
      <template v-for="item in hotArticles">
        <blog-item
          :key="item.id"
          :article="item"
          :preview-src-list="previewSrcList"
        />
      </template>
    </el-card>
    <el-card style="margin-top: 14px">
      <div slot="header" style="position: relative">
        <div class="label recommend">推荐</div>
        <el-row type="flex" align="middle" justify="space-between">
          <p class="title">推荐阅读</p>
          <el-button type="text" style="padding: 0">
            更多<i class="el-icon-d-arrow-right" />
          </el-button>
        </el-row>
      </div>
      <template v-for="item in recommendArticles">
        <blog-item
          :key="item.id"
          :article="item"
          :preview-src-list="previewSrcList"
        />
      </template>
    </el-card>
    <el-card style="margin-top: 14px">
      <div slot="header" style="position: relative">
        <div class="label new">最新</div>
        <el-row type="flex" align="middle" justify="space-between">
          <p class="title">最新文章</p>
          <el-button type="text" style="padding: 0">
            更多<i class="el-icon-d-arrow-right" />
          </el-button>
        </el-row>
      </div>
      <template v-for="item in latestArticles">
        <blog-item
          :key="item.id"
          :article="item"
          :preview-src-list="previewSrcList"
        />
      </template>
    </el-card>
  </div>
</template>

<script>
import BlogItem from '../../components/blog-item'
export default {
  name: 'Home',
  components: {
    BlogItem
  },
  async asyncData({ $axios }) {
    const [
      hotArticleRes,
      recommendArticleRes,
      latestArticleRes
    ] = await Promise.all([
      $axios.get('/article/hotArticle', { params: { size: 3 } }),
      $axios.get('/article/recommendTop', { params: { size: 5 } }),
      $axios.get('/article/latestTop', { params: { size: 8 } })
    ])
    const previewSrcList = []
    hotArticleRes.data.forEach((item) => previewSrcList.push(item.picture))
    recommendArticleRes.data.forEach((item) =>
      previewSrcList.push(item.picture)
    )
    latestArticleRes.data.forEach((item) => previewSrcList.push(item.picture))
    return {
      hotArticles: hotArticleRes.data,
      recommendArticles: recommendArticleRes.data,
      latestArticles: latestArticleRes.data,
      previewSrcList
    }
  }
}
</script>

<style scoped>
.title {
  display: inline-block;
  font-size: 16px;
  font-weight: 500;
  margin: 0 0 0 28px;
  letter-spacing: 1px;
  color: #ffd04b;
}
.label {
  position: absolute;
  width: 100px;
  height: 28px;
  line-height: 28px;
  letter-spacing: 1px;
  font-size: 13px;
  color: white;
  text-align: center;
  transform: rotate(-40deg);
  left: -48px;
  top: -14px;
}
.hot {
  background-color: #f56c6c;
}
.recommend {
  background-color: #67c23a;
}
.new {
  background-color: #e6a23c;
}
</style>
