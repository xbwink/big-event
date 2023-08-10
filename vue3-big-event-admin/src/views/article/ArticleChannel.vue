<script setup>
import { ref } from 'vue'
import { getCategorysService } from '@/api/category.js'
import { Edit, Delete } from '@element-plus/icons-vue'
import ChannelEdit from '@/views/article/components/ChannelEdit.vue'
import { delCategoryService } from '@/api/category.js'

const tableData = ref([])
const loading = ref(false)
const dialog = ref()

//获取列表信息
const getList = async () => {
  loading.value = true
  const res = await getCategorysService()
  tableData.value = res.data.data
  loading.value = false
}

const onAddChannel = () => {
  dialog.value.open({})
}
const onEditChannel = (row) => {
  dialog.value.open(row)
}

//删除
const onDelChannel = async (row) => {
  await ElMessageBox.confirm('你确定要退删除该分类信息吗', '温馨提示', {
    type: 'warning',
    confirmButtonText: '确认',
    cancelButtonText: '取消'
  })
  console.log(row.id)
  const res = await delCategoryService(row.id)
  ElMessage.success(res.data.data)
  getList()
}

const onSuccess = () => {
  getList()
}

getList()
</script>

<template>
  <page-container title="文章分类">
    <template #extra>
      <el-button type="primary" @click="onAddChannel"> 添加分类 </el-button>
    </template>

    <el-table v-loading="loading" :data="tableData" style="width: 100%">
      <el-table-column type="index" label="序号" width="100" />
      <el-table-column prop="categoryName" label="分类名称" />
      <el-table-column prop="categoryAlias" label="分类别名" />
      <el-table-column label="操作" width="100">
        <template #default="{ row }">
          <el-button
            :icon="Edit"
            circle
            plain
            type="primary"
            @click="onEditChannel(row)"
          ></el-button>
          <el-button
            :icon="Delete"
            circle
            plain
            type="danger"
            @click="onDelChannel(row)"
          ></el-button>
        </template>
      </el-table-column>
      <template #empty>
        <el-empty description="没有数据" />
      </template>
    </el-table>
    <!-- 弹窗 -->
    <channel-edit ref="dialog" @success="onSuccess"></channel-edit>
  </page-container>
</template>

<style lang="scss" scoped></style>
