<template>
  <el-card style="text-align: center">
    <el-divider class="divider-horizontal">
      <span style="color: #ffd04b;font-size: 15px">扫码关注我</span>
    </el-divider>
    <img :src="author.wechat" class="img" alt="" />
    <p>Email: {{ author.email }}</p>
    <p>QQ: {{ author.qq }}</p>
  </el-card>
</template>

<script>
export default {
  name: 'GuanZhu',
  props: {
    author: { type: Object, default: null }
  }
}
</script>

<style scoped>
.divider-horizontal {
  margin: 14px 0;
}
img {
  width: 160px;
  height: 160px;
}
</style>
