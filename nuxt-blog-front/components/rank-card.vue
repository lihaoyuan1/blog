<template>
  <el-card>
    <div slot="header">
      <i class="el-icon-trophy" />
      点击排行
    </div>
    <template v-for="(item, index) in articleList">
      <p
        :key="item.id"
        class="title"
        @click="
          $router.push({
            name: 'index-article-id',
            params: { id: item.id }
          })
        "
      >
        {{ item.title }}
      </p>
      <el-divider v-if="index < articleList.length - 1" :key="index" />
    </template>
  </el-card>
</template>

<script>
export default {
  name: 'RankCard',
  props: {
    articleList: {
      type: Array,
      default: null
    }
  }
}
</script>

<style scoped>
.title {
  padding: 8px 10px;
  cursor: pointer;
  font-size: 14px;
  transition: 0.25s;
  border-radius: 2px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.title:hover {
  background: #dcdfe6;
}
.el-divider--horizontal {
  margin: 0;
}
</style>
