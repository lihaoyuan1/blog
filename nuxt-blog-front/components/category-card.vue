<template>
  <el-card>
    <div slot="header" style="position: relative">
      <el-row type="flex" align="middle" justify="space-between">
        <p class="title">
          <i class="el-icon-notebook-1" />
          分类专栏
        </p>
        <el-button type="text" style="padding: 0">
          更多<i class="el-icon-d-arrow-right" />
        </el-button>
      </el-row>
    </div>
    <div class="category-wrapper">
      <template v-for="(item, index) in categoryList">
        <div
          :key="index"
          class="item"
          @click="
            $router.push({
              name: 'index-type-id',
              params: { id: item.id }
            })
          "
        >
          {{ item.categoryName }}
          <el-tag size="mini" style="float: right">{{ item.count }}</el-tag>
        </div>
        <el-divider v-if="index < categoryList.length - 1" :key="item.id" />
      </template>
    </div>
  </el-card>
</template>

<script>
export default {
  name: 'CategoryCard',
  props: {
    categoryList: {
      type: Array,
      default: null
    }
  }
}
</script>

<style scoped>
.category-wrapper {
  border: 1px solid #dcdfe6;
  border-radius: 4px;
  font-size: 14px;
}
.item {
  padding: 8px 10px;
  cursor: pointer;
  font-size: 14px;
  transition: all 0.25s;
}
.item:hover {
  background: #dcdfe6;
}
.el-divider--horizontal {
  margin: 0;
}
</style>
