<template>
  <div class="wrapper">
    <div class="playing-wrapper">
      <span /><span /><span /><span /><span /><span /><span />
    </div>
    <div class="music-wrapper">
      <a-player
        v-if="musicList.length > 0"
        :music="musicList[0]"
        :list="musicList"
        :show-lrc="true"
        :mutex="false"
        repeat="repeat-all"
        :list-folded="true"
        style="margin: 0"
      />
    </div>
    <div style="clear: both" />
  </div>
</template>

<script>
import APlayer from 'vue-aplayer'
export default {
  name: 'Music',
  components: {
    APlayer
  },
  data() {
    return {
      musicList: []
    }
  },
  created() {
    this.$axios.get('/music/').then((res) => {
      this.musicList = res.data
    })
  }
}
</script>

<style scoped>
.wrapper {
  position: relative;
}
.playing-wrapper {
  position: relative;
  float: right;
  height: 90px;
  width: 80px;
}
.playing-wrapper span {
  position: absolute;
  bottom: 20px;
  width: 4px;
  background: #ffd04b;
  border-radius: 5px;
  animation: playing alternate linear infinite;
}
.playing-wrapper span:first-child {
  left: 20px;
  animation-duration: 0.4s;
}
.playing-wrapper span:nth-child(2) {
  left: 26px;
  animation-duration: 0.6s;
}
.playing-wrapper span:nth-child(3) {
  left: 32px;
  animation-duration: 0.4s;
  animation-delay: 0.6s;
}
.playing-wrapper span:nth-child(4) {
  left: 38px;
  animation-duration: 0.3s;
}
.playing-wrapper span:nth-child(5) {
  left: 44px;
  animation-duration: 0.5s;
}
.playing-wrapper span:nth-child(6) {
  left: 50px;
  animation-duration: 0.7s;
}
.playing-wrapper span:nth-child(7) {
  left: 56px;
  animation-duration: 0.6s;
  animation-delay: 0.6s;
}
@keyframes playing {
  0% {
    height: 1px;
  }
  100% {
    height: 50px;
  }
}
.music-wrapper {
  position: absolute;
  top: 0;
  right: 0;
  width: 0;
  overflow: hidden;
  z-index: 3;
  transition: 0.25s;
  opacity: 0;
}
.playing-wrapper:hover + .music-wrapper {
  width: 380px;
  opacity: 1;
}
.music-wrapper:hover {
  width: 380px;
  opacity: 1;
}
</style>
