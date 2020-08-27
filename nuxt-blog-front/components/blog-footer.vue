<template>
  <div class="blog-footer">
    <el-row style="height: 179px" type="flex" justify="center" align="middle">
      <el-col :span="4" style="line-height: 0;text-align: center">
        <el-image
          :src="require('../static/me-qrcode.png')"
          style="width: 120px;border-radius: 5px"
        />
      </el-col>
      <el-col :span="1" style="height: 120px">
        <el-divider direction="vertical" class="divider-vertical" />
      </el-col>
      <el-col :span="4" style="text-align: center">
        <p class="footer-header">
          最新博客
        </p>
        <template v-for="item in latestArticle">
          <p
            :key="item.id"
            class="footer-item footer-item-hover"
            @click="
              $router.push({
                name: 'index-article-id',
                params: { id: item.id }
              })
            "
          >
            {{ item.title }}
          </p>
        </template>
      </el-col>
      <el-col :span="1" style="height: 120px;text-align: center">
        <el-divider direction="vertical" class="divider-vertical" />
      </el-col>
      <el-col :span="4" style="text-align: center">
        <p class="footer-header">
          联系我
        </p>
        <p class="footer-item">Email: {{ author.email }}</p>
        <p class="footer-item">QQ: {{ author.qq }}</p>
      </el-col>
      <el-col :span="1" style="height: 120px;text-align: center">
        <el-divider direction="vertical" class="divider-vertical" />
      </el-col>
      <el-col :span="9" style="text-align: center">
        <p class="footer-header">Lihaoyuan</p>
        <p class="footer-item">
          这是我的个人博客，会分享关于编程的相关内容，希望可以给来到这儿的人有所帮助...
        </p>
      </el-col>
    </el-row>
    <el-divider class="divider-horizontal" />
    <el-row
      style="height: 60px;margin: 0 auto;width: 1280px;color: #909399;font-size: 14px"
      type="flex"
      justify="center"
      align="middle"
    >
      <span>Copyright ©</span>
      <a
        class="footer-item footer-item-hover"
        style="margin: 0 5px"
        target="_blank"
        href="http://www.lihaoyuan.online"
        >www.lihaoyuan.online</a
      >
      <span>Designed By Lihaoyuan</span>
      <span style="margin-left: 10px">备案号：鄂ICP备20002092号</span>
    </el-row>
  </div>
</template>

<script>
export default {
  name: 'BlogFooter',
  props: {
    author: { type: Object, default: null },
    latestArticle: { type: Array, default: null }
  }
}
</script>

<style scoped>
.divider-vertical {
  height: 100%;
  background-color: #909399;
}
.divider-horizontal {
  margin: 0;
  background-color: #909399;
}
.footer-item {
  font-size: 14px;
  color: #909399;
  cursor: default;
  line-height: 28px;
  margin: 0;
  transition: all 0.25s;
}
.footer-item-hover {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.footer-item-hover:hover {
  color: #ffd04b;
  cursor: pointer;
}
.footer-header {
  color: white;
  font-size: 15px;
  margin: 0;
  line-height: 28px;
}
.blog-footer {
  width: 1280px;
  margin: 0 auto;
}
</style>
