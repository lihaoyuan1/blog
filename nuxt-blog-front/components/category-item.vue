<template>
  <div>
    <el-row type="flex" align="middle">
      <el-col :span="4" style="line-height: 0">
        <el-image
          :src="category.picture"
          :preview-src-list="previewSrcList"
          style="border-radius: 4px;"
          lazy
        />
      </el-col>
      <el-col :span="20">
        <div style="box-sizing: border-box;padding: 14px">
          <el-link
            class="title"
            @click="
              $router.push({
                name: 'index-type-id',
                params: { id: category.id }
              })
            "
          >
            {{ category.categoryName }}
          </el-link>
          <p class="content">{{ category.description }}</p>
          <div>
            <span style="color:#666666;font-size: 14px">
              浏览量： {{ category.views }}
            </span>
            <span style="color: #00b5ad;font-size: 14px;margin-left: 14px">
              包含 {{ category.count }} 篇文章
            </span>
            <el-button
              size="mini"
              plain
              style="margin-left: 14px"
              @click="
                $router.push({
                  name: 'index-type-id',
                  params: { id: category.id }
                })
              "
            >
              查看详情<i class="el-icon-d-arrow-right" />
            </el-button>
          </div>
        </div>
      </el-col>
    </el-row>
    <el-divider />
  </div>
</template>

<script>
export default {
  name: 'CategoryItem',
  props: {
    category: { type: Object, default: null },
    previewSrcList: { type: Array, default: null }
  }
}
</script>

<style scoped>
.el-divider--horizontal {
  margin: 14px 0;
}
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
