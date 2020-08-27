<template>
  <el-card>
    <div slot="header">
      <i class="el-icon-collection-tag" />
      所有标签
    </div>
    <template v-for="tag in tagList">
      <el-tag
        :key="tag.id"
        :type="tagColor[parseInt(Math.random() * 5, 0)]"
        size="medium"
        style="margin: 5px;cursor: pointer"
        @click="
          $router.push({
            name: 'index-tag-id',
            params: { id: tag.id }
          })
        "
        >{{ tag.tagName + ' ' + tag.count }}</el-tag
      >
    </template>
  </el-card>
</template>

<script>
export default {
  name: 'TagCard',
  props: {
    tagList: {
      type: Array,
      default: null
    }
  },
  data() {
    return {
      tagColor: ['', 'success', 'info', 'warning', 'danger']
    }
  }
}
</script>

<style scoped></style>
