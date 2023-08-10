<script setup>
import { ref } from 'vue'
import { Edit, Delete } from '@element-plus/icons-vue'
import ChannelSelect from '@/views/article/components/ChannelSelect.vue'
import { getArticleListsService, artDelService } from '@/api/article.js'
import ArticleEdit from './components/ArticleEdit.vue'
const articleList = ref([
  {
    id: 5961,
    title: '新的文章啊',
    publishDate: '2022-07-10 14:53:52.604',
    state: '已发布',
    categoryName: '体育'
  },
  {
    id: 5962,
    title: '新的文章啊',
    publishDate: '2022-07-10 14:54:30.904',
    state: '草稿',
    categoryName: '体育'
  }
])

//定义请求参数对象
const params = ref({
  pageNo: 1,
  pageSize: 10,
  categoryId: '',
  state: ''
})

const total = ref(0)

const loading = ref(false)

const getList = async () => {
  loading.value = true
  const res = await getArticleListsService(params.value)
  articleList.value = res.data.data.list
  total.value = parseInt(res.data.data.total)
  loading.value = false
}

getList()

const articleEditRef = ref()

//添加文章
const addArcicle = () => {
  articleEditRef.value.open({})
}

//修改文章
const editArticle = (row) => {
  articleEditRef.value.open(row)
  console.log(row)
}

//删除文章
const delArticle = async (row) => {
  await ElMessageBox.confirm('你确定要退删除该文章吗', '温馨提示', {
    type: 'warning',
    confirmButtonText: '确认',
    cancelButtonText: '取消'
  })
  await artDelService(row.id)
  ElMessage.success('删除成功')
  getList()
}

const onSearch = () => {
  getList()
}

const onReset = () => {
  params.value.pageNo = 1
  params.value.categoryId = ''
  params.value.state = ''
  getList()
}

const onSizeChange = (size) => {
  params.value.pageNo = 1
  params.value.pageSize = size
  console.log(size)
  getList()
}

const onCurrentChange = (page) => {
  params.value.pageNo = page
  getList()
}

// 添加修改成功
const onSuccess = (type) => {
  if (type === 'add') {
    // 如果是添加，需要跳转渲染最后一页，编辑直接渲染当前页
    const lastPage = Math.ceil((total.value + 1) / params.value.pagesize)
    params.value.pageNo = lastPage
  }
  getList()
}
</script>

<template>
  <!-- 头部 -->
  <page-container title="文章管理">
    <template #extra>
      <el-button type="primary" @click="addArcicle"> 发布文章 </el-button>
    </template>

    <!-- 表单部分 -->
    <el-form :inline="true">
      <el-form-item label="文章分类:">
        <channel-select v-model="params.categoryId"></channel-select>
      </el-form-item>
      <el-form-item label="发布状态">
        <el-select v-model="params.state">
          <el-option label="已发布" value="已发布"></el-option>
          <el-option label="草稿" value="草稿"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSearch">搜索</el-button>
        <el-button @click="onReset">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 表格部分 -->
    <el-table :data="articleList" v-loading="loading">
      <el-table-column label="文章标题" prop="title">
        <template #default="{ row }">
          <el-link type="primary" :underline="false">{{ row.title }}</el-link>
        </template>
      </el-table-column>
      <el-table-column label="分类" prop="categoryName"></el-table-column>
      <el-table-column label="发表时间" prop="publishDate"> </el-table-column>
      <el-table-column label="状态" prop="state"></el-table-column>
      <el-table-column label="操作">
        <template #default="{ row }">
          <el-button
            type="primary"
            plain
            :icon="Edit"
            circle
            @click="editArticle(row)"
          />
          <el-button
            type="danger"
            plain
            :icon="Delete"
            circle
            @click="delArticle(row)"
          />
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页条 -->
    <el-pagination
      v-model:current-page="params.pageNo"
      v-model:page-size="params.pagesize"
      :page-sizes="[2, 3, 4, 5, 10]"
      layout="jumper, total, sizes, prev, pager, next"
      background
      :total="total"
      @size-change="onSizeChange"
      @current-change="onCurrentChange"
      style="margin-top: 20px; justify-content: flex-end"
    />
    <!-- 添加修改的抽屉 -->
    <article-edit ref="articleEditRef" @success="onSuccess"></article-edit>
  </page-container>
</template>

<style lang="scss" scoped></style>
