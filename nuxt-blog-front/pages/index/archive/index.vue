<template>
  <el-collapse v-model="activeName" accordion>
    <el-timeline>
      <template v-for="archive in archives">
        <el-timeline-item
          :key="archive.yearAndMonth"
          :timestamp="archive.yearAndMonth"
          :color="activeName === archive.yearAndMonth ? '#0bbd87' : ''"
          placement="top"
        >
          <el-card>
            <el-collapse-item
              :title="
                archive.articles[0].title + ` 共 ${archive.articles.length} 篇`
              "
              :name="archive.yearAndMonth"
            >
              <template v-for="item in archive.articles">
                <div :key="item.id">
                  <el-link
                    style="margin-left: 10px"
                    @click="
                      $router.push({
                        name: 'index-article-id',
                        params: { id: item.id }
                      })
                    "
                  >
                    {{ item.title }}
                  </el-link>
                  <el-tag
                    size="mini"
                    :type="tagColor[parseInt(Math.random() * 5, 0)]"
                    style="margin-left: 10px"
                  >
                    {{ item.type }}
                  </el-tag>
                  <span style="float: right;">
                    {{ item.createTime }}
                  </span>
                </div>
              </template>
            </el-collapse-item>
          </el-card>
        </el-timeline-item>
      </template>
    </el-timeline>
  </el-collapse>
</template>

<script>
export default {
  name: 'Archives',
  async asyncData({ $axios }) {
    const [archivesRes] = await Promise.all([$axios.get('/article/archive')])
    return {
      archives: archivesRes.data
    }
  },
  data() {
    return {
      tagColor: ['', 'success', 'info', 'warning', 'danger'],
      activeName: ''
    }
  },
  mounted() {
    this.activeName = this.archives[0].yearAndMonth
  }
}
</script>

<style scoped>
.divider-horizontal {
  margin: 10px 0;
}
.el-collapse {
  border-top: none !important;
  border-bottom: none !important;
}
</style>
