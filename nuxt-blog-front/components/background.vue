<template>
  <canvas id="space" class="canvas" />
</template>

<script>
import Star from '../assets/star'
export default {
  name: 'Background',
  mounted() {
    Star()
  }
}
</script>

<style scoped>
.canvas {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  z-index: -1;
}
</style>
