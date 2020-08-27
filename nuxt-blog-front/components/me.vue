<template>
  <el-card style="text-align: center">
    <img class="avatar" alt="" :src="author.avatar" />
    <p style="font-size: 15px;line-height: 28px">
      {{ author.nickname }}的个人博客
    </p>
    <p style="font-size: 15px;line-height: 28px">
      一个分享IT技术的个人原创博客网站
    </p>
    <div>
      <a target="_blank" href="https://github.com/">
        <icon-github />
      </a>
      <el-popover placement="bottom" trigger="click">
        <p style="text-align: center">QQ：{{ author.qq }}</p>
        <icon-q-q slot="reference" />
      </el-popover>
      <el-popover placement="bottom" trigger="click">
        <img :src="author.wechat" alt="0" width="120" />
        <icon-we-chat slot="reference" />
      </el-popover>
    </div>
  </el-card>
</template>

<script>
import IconGithub from '../components/icon/icon-github'
import IconQQ from '../components/icon/icon-qq'
import IconWeChat from '../components/icon/icon-wechat'
export default {
  name: 'Me',
  components: {
    IconGithub,
    IconQQ,
    IconWeChat
  },
  props: {
    author: { type: Object, default: null }
  }
}
</script>

<style scoped>
.avatar {
  width: 120px;
  height: 120px;
  border-radius: 60px;
  -webkit-animation: run 6s linear 0s infinite;
}
@-webkit-keyframes run {
  from {
    -webkit-transform: rotate(0deg);
  }
  to {
    -webkit-transform: rotate(360deg);
  }
}
</style>
