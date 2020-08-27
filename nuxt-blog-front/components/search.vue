<template>
  <div class="wrapper">
    <span>
      <input
        v-model="searchText"
        placeholder="这是个神奇的搜索框..."
        @keyup.enter="search"
      />
    </span>
    <span>
      <button @click="search">
        <i class="el-icon-search" />
      </button>
    </span>
  </div>
</template>

<script>
export default {
  name: 'Search',
  data() {
    return {
      searchText: ''
    }
  },
  computed: {
    searchTextInRoute() {
      return this.$route.params.searchText
    }
  },
  mounted() {
    document.onkeydown = () => {
      if (window.event.keyCode === 13) {
        this.search()
      }
    }
    if (this.searchTextInRoute) {
      this.searchText = this.searchTextInRoute
    }
  },
  methods: {
    search() {
      if (this.searchText && this.searchText.length < 20) {
        this.$router.push({
          name: 'index-search-searchText',
          params: { searchText: this.searchText }
        })
      }
    }
  }
}
</script>

<style scoped>
.wrapper {
  float: right;
  margin-top: 12px;
  height: 36px;
  border: 1px solid #909399;
  padding: 4px 10px 4px 20px;
  border-radius: 20px;
  box-sizing: border-box;
}
.wrapper:hover {
  border: 1px solid #fff;
  transition: 0.25s;
}
.wrapper:hover button {
  color: #fff;
  transition: 0.25s;
}
.wrapper input {
  display: inline-block;
  padding: 0;
  height: 28px;
  border: none;
  background: none;
  outline: none;
  color: white;
}
.wrapper:focus-within {
  border: 1px solid #fff;
}
.wrapper:focus-within button {
  color: #fff;
}
.wrapper button {
  padding: 0;
  height: 28px;
  width: 28px;
  border-radius: 14px;
  background: none;
  border: none;
  color: #909399;
  outline: none;
  cursor: pointer;
}
</style>
