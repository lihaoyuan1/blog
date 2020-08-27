import Vue from 'vue'
import App from './App.vue'
import router from "./router"
import './plugins/axios'
import './plugins/element'
import './plugins/good-storage'
import './plugins/echarts'

Vue.config.productionTip = false;

Vue.prototype.$uploadUrl = 'http://www.lihaoyuan.online/lhy-blog/admin/oss/upload';

new Vue({
  router,
  render: h => h(App),
}).$mount('#app');
