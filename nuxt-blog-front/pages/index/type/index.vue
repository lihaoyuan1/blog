<template>
  <el-card>
    <div slot="header">
      <p class="title">全部分类</p>
    </div>
    <template v-for="item in allCategory">
      <category-item
        :key="item.id"
        :category="item"
        :preview-src-list="previewSrcList"
      />
    </template>
  </el-card>
</template>

<script>
import CategoryItem from '../../../components/category-item'
export default {
  name: 'Category',
  components: {
    CategoryItem
  },
  async asyncData({ $axios }) {
    const [allCategoryRes] = await Promise.all([$axios.get('/category/all')])
    const previewSrcList = []
    allCategoryRes.data.forEach((item) => previewSrcList.push(item.picture))
    return {
      allCategory: allCategoryRes.data,
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
  margin: 0;
  letter-spacing: 1px;
  color: #ffd04b;
}
</style>
