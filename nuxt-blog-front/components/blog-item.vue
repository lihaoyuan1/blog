<template>
  <div>
    <el-row type="flex" align="middle">
      <el-avatar :src="article.avatar" shape="square" :size="40" />
      <el-link
        style="margin-left: 10px"
        @click="
          $router.push({
            name: 'index-article-id',
            params: { id: article.id }
          })
        "
      >
        <strong>{{ article.title }}</strong>
      </el-link>
    </el-row>
    <el-row type="flex" align="middle" justify="space-between">
      <el-col :span="17">
        <div class="summary">
          {{ article.summary }}
        </div>
      </el-col>
      <el-col :span="7">
        <div class="pic-right">
          <el-image
            :src="article.picture"
            :preview-src-list="previewSrcList"
            class="image"
            lazy
          />
        </div>
      </el-col>
    </el-row>
    <el-row style="margin-left: 40px; font-size: 14px;">
      <span style="color: #00b5ad">
        <i class="el-icon-user" /> {{ article.nickname }}
      </span>
      <span style="color:#909399;margin-left: 10px">
        <i class="el-icon-time" /> {{ article.createTime }}
      </span>
      <span style="color:#909399;margin-left: 10px">
        <i class="el-icon-view" /> {{ article.views }}
      </span>
      <span style="color: #909399;margin-left: 10px">
        <i class="el-icon-thumb" /> {{ article.admire }}
      </span>
    </el-row>
    <el-divider />
  </div>
</template>

<script>
export default {
  name: 'BlogItem',
  props: {
    article: {
      type: Object,
      default: null
    },
    previewSrcList: {
      type: Array,
      default: null
    }
  }
}
</script>

<style scoped>
.el-divider--horizontal {
  margin: 14px 0;
}
.summary {
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 5;
  word-break: break-all;
  overflow: hidden;
  font-size: 15px;
  line-height: 24px;
  color: #666666;
  padding: 0 5px 0 40px;
}
.pic-right {
  line-height: 0;
}
.image {
  transition: all 0.25s;
}
.image:hover {
  box-shadow: 0 0 8px rgba(0, 0, 0, 0.6);
}
</style>
