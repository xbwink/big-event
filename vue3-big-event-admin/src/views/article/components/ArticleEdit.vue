<script setup>
import { ref } from 'vue'
import ChannelSelect from '@/views/article/components/ChannelSelect.vue'
import { artPublishService, artEditService } from '@/api/article.js'
import axios from 'axios'

import { Plus } from '@element-plus/icons-vue'

import { QuillEditor } from '@vueup/vue-quill'
import '@vueup/vue-quill/dist/vue-quill.snow.css'

const imgUrl = ref('')
const onUploadFile = (uploadFile) => {
  imgUrl.value = URL.createObjectURL(uploadFile.raw)
  formModel.value.img = uploadFile.raw
}
const drawer = ref(false)

//表单model数据
const defaultModel = ref({
  title: '',
  categoryId: '',
  content: '',
  img: '',
  state: ''
})

const formModel = ref(defaultModel.value)

// 发布文章
const emit = defineEmits(['success'])
const onPublish = async (state) => {
  // 将已发布还是草稿状态，存入 state
  formModel.value.state = state

  // 转换 formData 数据
  const fd = new FormData()
  for (let key in formModel.value) {
    fd.append(key, formModel.value[key])
  }

  if (formModel.value.id) {
    console.log('编辑操作')

    await artEditService(fd)
    ElMessage.success('修改成功')
    emit('success')
  } else {
    // 添加请求
    await artPublishService(fd)
    ElMessage.success('添加成功')
    emit('success', 'add')
  }
  drawer.value = false
}

// 将网络图片地址转换为File对象
async function imageUrlToFile(url, fileName) {
  try {
    // 第一步：使用axios获取网络图片数据
    const response = await axios.get(url, { responseType: 'arraybuffer' })
    const imageData = response.data

    // 第二步：将图片数据转换为Blob对象
    const blob = new Blob([imageData], {
      type: response.headers['content-type']
    })

    // 第三步：创建一个新的File对象
    const file = new File([blob], fileName, { type: blob.type })

    return file
  } catch (error) {
    console.error('将图片转换为File对象时发生错误:', error)
    throw error
  }
}

const editorRef = ref()
//对外暴露一个方法，基于open传参区分添加还是修改
const open = async (row) => {
  drawer.value = true
  if (row.id) {
    formModel.value = { ...row }
    imgUrl.value = formModel.value.img
    // 提交给后台，需要的是 file 格式的，将网络图片，转成 file 格式
    // 网络图片转成 file 对象, 需要转换一下
    formModel.value.img = await imageUrlToFile(
      imgUrl.value,
      formModel.value.img
    )
    console.log('编辑回显')
  } else {
    formModel.value = { ...defaultModel }
    imgUrl.value = ''
    editorRef.value.setHTML('')
  }
}

defineExpose({
  open
})
</script>

<template>
  <!-- 抽屉 -->
  <el-drawer
    v-model="drawer"
    :title="formModel.id ? '编辑文章' : '添加文章'"
    direction="rtl"
    size="50%"
  >
    <el-form style="padding-left: 30px">
      <el-form-item label="文章标题" prop="title">
        <el-input placeholder="请输入标题" v-model="formModel.title"></el-input>
      </el-form-item>
      <el-form-item label="文章分类" prop="categoryId">
        <channel-select
          v-model="formModel.categoryId"
          style="width: 100%"
        ></channel-select>
      </el-form-item>
      <el-form-item label="文章封面" prop="img">
        <el-upload
          class="avatar-uploader"
          :auto-upload="false"
          :show-file-list="false"
          :on-change="onUploadFile"
        >
          <img v-if="imgUrl" :src="imgUrl" class="avatar" />
          <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
        </el-upload>
      </el-form-item>
      <el-form-item label="文章内容" prop="content">
        <div class="editor">
          <quill-editor
            theme="snow"
            v-model:content="formModel.content"
            contentType="html"
            ref="editorRef"
          >
          </quill-editor>
        </div>
      </el-form-item>
      <el-form-item style="padding-left: 10%">
        <el-button @click="onPublish('已发布')" type="primary">发布</el-button>
        <el-button @click="onPublish('草稿')" type="info">草稿</el-button>
      </el-form-item>
    </el-form>
  </el-drawer>
</template>

<style lang="scss" scoped>
.avatar-uploader {
  :deep() {
    .avatar {
      width: 178px;
      height: 178px;
      display: block;
    }
    .el-upload {
      border: 1px dashed var(--el-border-color);
      border-radius: 6px;
      cursor: pointer;
      position: relative;
      overflow: hidden;
      transition: var(--el-transition-duration-fast);
    }
    .el-upload:hover {
      border-color: var(--el-color-primary);
    }
    .el-icon.avatar-uploader-icon {
      font-size: 28px;
      color: #8c939d;
      width: 178px;
      height: 178px;
      text-align: center;
    }
  }
}
.editor {
  width: 100%;
  :deep(.ql-editor) {
    min-height: 200px;
  }
}
</style>
