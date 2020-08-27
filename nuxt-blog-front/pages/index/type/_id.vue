<template>
  <div>
    <el-card>
      <el-row>
        <el-col :span="4">
          <el-image
            :src="category.picture"
            :preview-src-list="bigImgList"
            style="border-radius: 4px;"
          />
        </el-col>
        <el-col :span="20">
          <div style="box-sizing: border-box;padding: 14px">
            <h2 class="title">
              {{ category.categoryName }}
            </h2>
            <p class="content">{{ category.description }}</p>
            <span style="color:#666666;font-size: 14px">
              浏览量： {{ category.views == null ? 0 : category.views }}
            </span>
            <span style="color: #00b5ad;font-size: 14px;margin-left: 14px">
              包含 {{ category.count }} 篇文章
            </span>
          </div>
        </el-col>
      </el-row>
    </el-card>
    <el-card style="margin-top: 14px">
      <template v-for="item in allArticlePage">
        <blog-item :key="item.id" :article="item" />
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
  </div>
</template>

<script>
import BlogItem from '../../../components/blog-item'
import NotFound from '../../../components/not-found'
export default {
  name: 'IdOfType',
  components: {
    BlogItem,
    NotFound
  },
  async asyncData({ $axios, params }) {
    const [categoryRes, allArticlePageRes] = await Promise.all([
      $axios.get('/category/getOne', { params: { id: params.id } }),
      $axios.get('/article/getByCategoryId', {
        params: { id: params.id, page: 1 }
      })
    ])
    return {
      category: categoryRes.data,
      allArticlePage: allArticlePageRes.data.records,
      pageSize: allArticlePageRes.data.size,
      pages: allArticlePageRes.data.pages
    }
  },
  methods: {
    handlePageChange(index) {
      this.$axios
        .get('/article/getByCategoryId', {
          params: { id: this.$route.params.id, page: index }
        })
        .then((res) => {
          this.allArticlePage = res.data.records
        })
    }
  }
}
</script>

<style scoped>
.title {
  font-size: 16px;
  letter-spacing: 1px;
  font-weight: 600;
}
.content {
  padding: 8px 0;
  font-size: 14px;
  line-height: 20px;
  color: #666666;
}
</style>
