<script setup>
import { ElMessage } from 'element-plus'
import { ref } from 'vue'
import { addCategorysService, editCategorysService } from '@/api/category.js'
const dialogVisible = ref(false)
const emit = defineEmits(['success'])

const form = ref()
const formData = ref({
  categoryName: '',
  categoryAlias: ''
})
const rules = {
  categoryName: [
    { required: true, message: '请输入分类名称', trigger: 'blur' },
    {
      pattern: /^\S{1,10}$/,
      message: '分类名必须是1-10位的非空字符',
      trigger: 'blur'
    }
  ],
  categoryAlias: [
    { required: true, message: '请输入分类别名', trigger: 'blur' },
    {
      pattern: /^[a-zA-Z0-9]{1,15}$/,
      message: '分类别名必须是1-15位的字母数字',
      trigger: 'blur'
    }
  ]
}

//对外提供方法
const open = async (row) => {
  dialogVisible.value = true
  formData.value = { ...row }
  console.log(formData.value)
}

const onSubmit = async () => {
  //注册前进行校验
  await form.value.validate()
  const isEdit = formData.value.id
  const res = isEdit
    ? await editCategorysService(formData.value)
    : await addCategorysService(formData.value)
  ElMessage.success(res.data.data)

  //隐藏表单
  dialogVisible.value = false

  //通知父组件进行回显
  emit('success')
}

defineExpose({
  open
})
</script>

<template>
  <el-dialog
    v-model="dialogVisible"
    :title="formData.id ? '修改分类' : '添加分类'"
    width="30%"
  >
    <el-form
      :model="formData"
      ref="form"
      :rules="rules"
      style="padding: 0 30px"
    >
      <el-form-item label="分类名称" prop="categoryName">
        <el-input v-model="formData.categoryName"></el-input>
      </el-form-item>
      <el-form-item label="分类别名" prop="categoryAlias">
        <el-input v-model="formData.categoryAlias"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="onSubmit"> 确认 </el-button>
      </span>
    </template>
  </el-dialog>
</template>
