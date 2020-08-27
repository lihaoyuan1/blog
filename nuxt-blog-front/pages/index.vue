<template>
  <el-container>
    <el-header class="header">
      <blog-header />
    </el-header>
    <el-main class="main">
      <music style="margin-top: 14px" />
      <div style="float: left;width: 866px;margin-top: 14px">
        <nuxt-child />
      </div>
      <div id="right-box" style="float: right;width: 320px;margin-top: 14px;">
        <me :author="author" />
        <category-card
          :category-list="topCategories"
          style="margin-top: 14px"
        />
        <tag-card :tag-list="allTag" style="margin-top: 14px" />
        <rank-card :article-list="rankArticles" style="margin-top: 14px" />
        <guan-zhu :author="author" style="margin-top: 14px" />
      </div>
      <div style="clear: both" />
    </el-main>
    <el-footer class="footer">
      <blog-footer :author="author" :latest-article="latestArticle" />
    </el-footer>
    <back-ground />
    <el-backtop />
  </el-container>
</template>

<script>
import BlogHeader from '../components/blog-header'
import BlogFooter from '../components/blog-footer'
import BackGround from '../components/background'
import Music from '../components/music'
import CategoryCard from '../components/category-card'
import TagCard from '../components/tag-card'
import RankCard from '../components/rank-card'
import GuanZhu from '../components/guan-zhu'
import Me from '../components/me'

export default {
  name: 'Index',
  components: {
    BlogHeader,
    BlogFooter,
    BackGround,
    Music,
    CategoryCard,
    TagCard,
    RankCard,
    GuanZhu,
    Me
  },
  async asyncData({ $axios }) {
    const [
      authorRes,
      topCategoryRes,
      allTagRes,
      rankArticleRes,
      latestArticleRes
    ] = await Promise.all([
      $axios.get('/user/info'),
      $axios.get('/category/listTop'),
      $axios.get('/tag/all'),
      $axios.get('/article/hotArticle', { params: { size: 8 } }),
      $axios.get('/article/latestTop', { params: { size: 3 } })
    ])
    return {
      author: authorRes.data,
      topCategories: topCategoryRes.data,
      allTag: allTagRes.data,
      rankArticles: rankArticleRes.data,
      latestArticle: latestArticleRes.data
    }
  },
  data() {
    return {
      rightBoxOfX: 0,
      rightBox: null,
      doc: null
    }
  },
  mounted() {
    this.initVariables()
  },
  methods: {
    initVariables() {
      this.rightBox = document.getElementById('right-box')
      this.rightBoxOfX = this.rightBox.getBoundingClientRect().x
      this.doc = document.documentElement
      window.addEventListener('scroll', this.handleScroll, false)
    },
    handleScroll() {
      if (this.rightBox.clientHeight + 432 === this.doc.offsetHeight) {
        this.rightBox.style = 'float: right;width: 320px;margin-top: 14px'
        return
      }
      const threshold1 = 192 + this.rightBox.clientHeight - window.innerHeight
      const threshold2 = this.doc.scrollHeight - this.doc.clientHeight - 240
      if (this.doc.scrollTop <= threshold1) {
        this.rightBox.style = 'float: right;width: 320px;margin-top: 14px'
      } else if (this.doc.scrollTop <= threshold2) {
        this.rightBox.style = `position: fixed;left:${this.rightBoxOfX}px;bottom: 14px;width: 320px`
      } else {
        const bottom = 14 + this.doc.scrollTop - threshold2
        this.rightBox.style = `position: fixed;left:${this.rightBoxOfX}px;bottom: ${bottom}px;width: 320px`
      }
    }
  }
}
</script>

<style scoped>
.header {
  background: #545c64;
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  box-shadow: 0 5px 5px rgba(0, 0, 0, 0.1);
  z-index: 10;
}
.main {
  width: 1200px;
  margin: 60px auto 0 auto;
  padding: 0;
}
.footer {
  background-color: #545c64;
  height: 240px !important;
  margin-top: 14px;
}
</style>
