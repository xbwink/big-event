<script setup>
import { ref } from 'vue'
import { editUserPasswordService } from '@/api/user.js'
import { ElMessage } from 'element-plus'
import { useUserStore } from '@/stores'
import { useRouter } from 'vue-router'

const defaultModel = {
  oldPassword: '',
  newPassword: '',
  confirmPassword: ''
}

const formModel = ref(defaultModel)

// 整个表单的校验规则
const rules = {
  oldPassword: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 3, max: 10, message: '密码必须为3-10的字符', trigger: 'blur' }
  ],
  newPassword: [
    { required: true, message: '请输入新密码', trigger: 'blur' },
    { min: 3, max: 10, message: '新密码必须为3-10的字符', trigger: 'blur' },
    {
      validator: (rule, value, callback) => {
        if (value === formModel.value.oldPassword) {
          callback(new Error('原密码和新密码不能一样!'))
        } else {
          callback()
        }
      },
      trigger: 'blur'
    }
  ],
  confirmPassword: [
    { required: true, message: '请再次确认新密码', trigger: 'blur' },
    { min: 3, max: 10, message: '新密码必须为3-10的字符', trigger: 'blur' },
    {
      validator: (rule, value, callback) => {
        if (value !== formModel.value.newPassword) {
          callback(new Error('两次输入密码不一致!'))
        } else {
          callback()
        }
      },
      trigger: 'blur'
    }
  ]
}

const form = ref()
const userStore = useUserStore()
const router = useRouter()
const onSubmit = async () => {
  //提交前进行校验
  await form.value.validate()
  await editUserPasswordService(formModel.value)
  ElMessage.success('更换密码成功')
  //清除token及user信息
  userStore.removeToken()
  userStore.setUser({})
  router.push('/')
}

const onReset = () => {
  form.value.resetFields()
}
</script>

<template>
  <div>
    <page-container title="重置密码">
      <el-row>
        <el-col :span="12">
          <el-form
            :model="formModel"
            :rules="rules"
            ref="form"
            label-width="100px"
            size="large"
          >
            <el-form-item label="原密码" prop="oldPassword">
              <el-input
                v-model="formModel.oldPassword"
                type="password"
              ></el-input>
            </el-form-item>
            <el-form-item label="新密码" prop="newPassword">
              <el-input
                v-model="formModel.newPassword"
                type="password"
              ></el-input>
            </el-form-item>
            <el-form-item label="确认密码" prop="confirmPassword">
              <el-input
                v-model="formModel.confirmPassword"
                type="password"
              ></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="onSubmit">修改密码</el-button>
              <el-button @click="onReset()">重置</el-button>
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>
    </page-container>
  </div>
</template>
