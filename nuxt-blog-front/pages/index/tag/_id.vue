<template>
  <el-card>
    <div slot="header">
      <span style="font-weight: 600;color: #ffd04b;">
        标签：{{ tag.tagName }}
      </span>
      <span style="margin-left: 14px;color: #00b5ad">
        共包含
        <span style="font-size: 20px">{{ tag.count }}</span>
        篇文章
      </span>
      <span style="margin-left: 14px">点击量：{{ tag.views }}</span>
    </div>
    <template v-for="item in allArticlePage">
      <blog-item
        :key="item.id"
        :article="item"
        :preview-src-list="previewSrcList"
      />
    </template>
    <el-pagination
      v-if="pages > 1"
      background
      :page-size="pageSize"
      layout="prev, pager, next"
      :page-count="pages"
      style="text-align: center"
      @current-change="handlePageChange"
    />
    <not-found v-if="allArticlePage.length === 0" />
  </el-card>
</template>

<script>
import BlogItem from '../../../components/blog-item'
import NotFound from '../../../components/not-found'
export default {
  name: 'IdOfTag',
  components: {
    BlogItem,
    NotFound
  },
  async asyncData({ $axios, params }) {
    const [tagRes, allArticlePageRes] = await Promise.all([
      $axios.get('/tag/getOne', { params: { id: params.id } }),
      $axios.get('/article/getByTagId', {
        params: { id: params.id, page: 1 }
      })
    ])
    const previewSrcList = []
    allArticlePageRes.data.records.forEach((item) =>
      previewSrcList.push(item.picture)
    )
    return {
      tag: tagRes.data,
      allArticlePage: allArticlePageRes.data.records,
      pageSize: allArticlePageRes.data.size,
      pages: allArticlePageRes.data.pages,
      previewSrcList
    }
  },
  methods: {
    handlePageChange(index) {
      this.$axios
        .get('/article/getByTagId', {
          params: { id: this.$route.params.id, page: index }
        })
        .then((res) => {
          this.allArticlePage = res.data.records
          this.allArticlePage.forEach((item) => {
            this.previewSrcList.push(item.picture)
          })
        })
    }
  }
}
</script>

<style scoped></style>
