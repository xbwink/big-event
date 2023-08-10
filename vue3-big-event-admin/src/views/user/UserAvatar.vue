<script setup>
import { useUserStore } from '@/stores'
import { ref } from 'vue'
import { Plus, Upload } from '@element-plus/icons-vue'
import { editUserAvatarService } from '@/api/user.js'
import { onMounted } from 'vue'

import axios from 'axios'
import { ElMessage } from 'element-plus'

const userStore = useUserStore()
const imageUrl = ref(userStore.user.avatar)
const imageFile = ref()
const upload = ref()

const onUploadFile = (uploadFile) => {
  imageUrl.value = URL.createObjectURL(uploadFile.raw)
  imageFile.value = uploadFile.raw
}

const submitUpload = async () => {
  // 转换 formData 数据
  const fd = new FormData()
  fd.append('file', imageFile.value)
  await editUserAvatarService(fd)
  userStore.getUser()
  ElMessage.success('修改成功')
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

//加载页面执行
onMounted(async () => {
  imageFile.value = await imageUrlToFile(imageUrl.value, imageUrl.value)
})
</script>

<template>
  <page-container title="更换头像">
    <el-upload
      ref="upload"
      class="avatar-uploader"
      :auto-upload="false"
      :show-file-list="false"
      :on-change="onUploadFile"
    >
      <img v-if="imageUrl" :src="imageUrl" class="avatar" />
      <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
    </el-upload>
    <br />
    <el-button
      type="primary"
      :icon="Plus"
      size="large"
      @click="upload.$el.querySelector('input').click()"
    >
      选择图片
    </el-button>
    <el-button type="success" :icon="Upload" size="large" @click="submitUpload">
      上传头像
    </el-button>
  </page-container>
</template>

<style lang="scss" scoped>
.avatar-uploader {
  :deep() {
    .avatar {
      width: 278px;
      height: 278px;
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
      width: 278px;
      height: 278px;
      text-align: center;
    }
  }
}
</style>
