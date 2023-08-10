import { createApp } from 'vue'

import App from './App.vue'
import router from './router'
import '@/assets/main.scss'
import pinia from '@/stores/index'
//引入Elmessage和Elloading的css样式文件
import 'element-plus/theme-chalk/el-loading.css'
import 'element-plus/theme-chalk/el-message.css'

const app = createApp(App)
app.use(pinia)
app.use(router)

app.mount('#app')
