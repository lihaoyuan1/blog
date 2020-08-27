<template>
  <div class="blog-header">
    <img src="../static/logo.png" alt="" style="float: left;margin-top: 10px" />
    <el-menu
      mode="horizontal"
      background-color="#545c64"
      :router="true"
      :default-active="$route.path"
      text-color="#fff"
      active-text-color="#ffd04b"
      style="border: none;float: left;"
    >
      <el-menu-item index="/">
        <i class="el-icon-s-home" />
        <span slot="title">首 页</span>
      </el-menu-item>
      <el-menu-item index="/type">
        <i class="el-icon-notebook-1" />
        <span slot="title">分 类</span>
      </el-menu-item>
      <el-menu-item index="/archive">
        <i class="el-icon-collection" />
        <span slot="title">归 档</span>
      </el-menu-item>
      <el-menu-item index="/about">
        <i class="el-icon-position" />
        <span slot="title">关于我</span>
      </el-menu-item>
    </el-menu>
    <search />
  </div>
</template>

<script>
import Search from '../components/search'
export default {
  name: 'BlogHeader',
  components: {
    Search
  }
}
</script>

<style scoped>
.blog-header {
  margin: 0 auto;
  width: 1280px;
}
</style>
