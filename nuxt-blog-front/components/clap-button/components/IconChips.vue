<template>
  <div v-if="active">
    <template v-for="i in chips">
      <div
        :key="i"
        class="chips-wrap"
        :style="{
          top: `${-size - 10 + sizeHalf}px`,
          left: `${sizeHalf}px`,
          height: `${size * 2 + 20}px`,
          transform: `rotate(${degOffset * i}deg)`
        }"
      >
        <icon-base
          class="chip iconchipEffect animated"
          :width="chipSize"
          :height="chipSize"
          :icon-color="randomColor()"
          :style="{
            left: `${-chipSize / 2}px`,
            width: `${chipSize}px`,
            height: `${chipSize}px`
          }"
        >
          <icon-chip />
        </icon-base>
      </div>
    </template>
  </div>
</template>

<script>
import { throttle } from '../assets/utils'
import IconChip from './icons/IconChip'
import IconBase from './IconBase'

export default {
  name: 'IconChips',
  components: {
    IconChip,
    IconBase
  },
  props: ['size'],
  data() {
    return {
      chipSize: 10,
      chips: [0, 1, 2, 3, 4, 5],
      active: false
    }
  },
  computed: {
    degOffset() {
      return 360 / this.chips.length
    },
    sizeHalf() {
      return this.size / 2
    },
    offset() {
      return this.size / 10
    }
  },
  created() {
    const duration = 1000
    this.show = throttle(() => {
      this.active = true

      setTimeout(() => {
        this.active = false
      }, duration)
    }, duration)
  },
  methods: {
    boom() {
      this.show()
    },
    randomColor() {
      const list = ['#67C23A', '#E6A23C', '#F56C6C', '#909399', '#409EFF']
      return list[Math.floor(Math.random() * list.length)]
    }
  }
}
</script>

<style scoped>
.chips-wrap {
  position: absolute;
  width: 0;
  border-radis: 50%;
}

.chip {
  position: absolute;
  top: 75%;
  animation-timing-function: cubic-bezier(0.22, 0.61, 0.36, 1);
}

@keyframes iconchipEffect {
  0% {
    top: 50%;
  }

  to {
    top: 100%;
    opacity: 0.5;
    transform: scale3d(0.3, 0.3, 0.3);
  }
}

.iconchipEffect {
  animation-name: iconchipEffect;
}

.animated {
  animation-duration: 1s;
  animation-fill-mode: both;
}
</style>
