<template>
  <el-card>
    <div slot="header" v-if="articles.length > 0">
      <strong>搜索结果：</strong>
      共包含搜索结果
      <strong style="color: #ffd04b;font-size: 18px">
        {{ articles.length }}
      </strong>
      条
    </div>
    <div slot="header" v-else>
      <strong>暂无搜索结果</strong>
    </div>
    <template v-for="item in articles">
      <blog-item :key="item.id" :article="item" />
    </template>
    <el-pagination
      v-if="page > 1"
      background
      :page-size="pageSize"
      layout="prev, pager, next"
      :page-count="page"
      style="text-align: center"
      @current-change="handlePageChange"
    />
    <not-found v-if="articles.length === 0" />
  </el-card>
</template>

<script>
import BlogItem from '../../../components/blog-item'
import NotFound from '../../../components/not-found'
export default {
  name: 'Index',
  components: {
    BlogItem,
    NotFound
  },
  data() {
    return {
      page: 1,
      pageSize: 0,
      articles: []
    }
  },
  computed: {
    searchText() {
      return this.$route.params.searchText
    }
  },
  watch: {
    searchText() {
      this.$axios
        .get('/article/search', {
          params: { searchText: this.searchText, page: this.page }
        })
        .then((res) => {
          this.articles = res.data.records
          this.page = res.data.pages
          this.pageSize = res.data.size
        })
    }
  },
  created() {
    if (this.searchText) {
      this.$axios
        .get('/article/search', {
          params: { searchText: this.searchText, page: this.page }
        })
        .then((res) => {
          this.articles = res.data.records
          this.page = res.data.pages
          this.pageSize = res.data.size
        })
    }
  },
  methods: {
    handlePageChange(index) {
      this.$axios
        .get('/article/search', {
          params: { searchText: this.searchText, page: index }
        })
        .then((res) => {
          this.articles = res.data.records
        })
    }
  }
}
</script>

<style scoped></style>
