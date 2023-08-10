<script setup>
import { User, Lock } from '@element-plus/icons-vue'
import { ref, watch } from 'vue'
import { userRegisterService } from '@/api/user.js'
import { userLoginService } from '../../api/user'
import { useUserStore } from '@/stores'
import { useRouter } from 'vue-router'

const isRegister = ref(false)
const form = ref()

// 整个用于提交的form对象
const formModel = ref({
  username: '',
  password: '',
  repassword: ''
})

// 整个表单的校验规则
const rules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 10, message: '用户名必须为3-10的字符', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 3, max: 10, message: '密码必须为3-10的字符', trigger: 'blur' }
  ],
  repassword: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 3, max: 10, message: '密码必须为3-10的字符', trigger: 'blur' },
    {
      validator: (rule, value, callback) => {
        if (value !== formModel.value.password) {
          callback(new Error('两次输入密码不一致!'))
        } else {
          callback()
        }
      },
      trigger: 'blur'
    }
  ]
}

const register = async () => {
  //注册前进行校验
  await form.value.validate()
  console.log('开始注册请求')
  const res = await userRegisterService(formModel.value)
  ElMessage.success(res.data.data)
  // 切换到登录
  isRegister.value = false
}

const userStore = useUserStore()
const router = useRouter()

const login = async () => {
  //登录前进行校验
  await form.value.validate()
  console.log('开始登录请求')
  const res = await userLoginService(formModel.value)
  //存储token
  userStore.setToken(res.data.data)
  ElMessage.success('登录成功')
  router.push('/')
}

//监测当切换登录或注册时清空表单数据
watch(isRegister, () => {
  formModel.value = {
    username: '',
    password: '',
    repassword: ''
  }
})
</script>

<template>
  <div>
    <el-row class="login-page">
      <el-col :span="12" class="bg"></el-col>
      <el-col :span="6" :offset="3" class="form">
        <el-form
          ref="form"
          size="large"
          :model="formModel"
          :rules="rules"
          autocomplete="off"
          v-if="isRegister"
        >
          <el-form-item>
            <h1>注册</h1>
          </el-form-item>
          <el-form-item prop="username">
            <el-input
              :prefix-icon="User"
              placeholder="请输入用户名"
              v-model.trim="formModel.username"
            ></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input
              :prefix-icon="Lock"
              type="password"
              placeholder="请输入密码"
              v-model.trim="formModel.password"
            ></el-input>
          </el-form-item>
          <el-form-item prop="repassword">
            <el-input
              :prefix-icon="Lock"
              type="password"
              placeholder="请输入再次密码"
              v-model.trim="formModel.repassword"
            ></el-input>
          </el-form-item>
          <el-form-item>
            <el-button
              class="button"
              type="primary"
              @click="register"
              auto-insert-space
            >
              注册
            </el-button>
          </el-form-item>
          <el-form-item class="flex">
            <el-link type="info" :underline="false" @click="isRegister = false">
              ← 返回
            </el-link>
          </el-form-item>
        </el-form>
        <el-form
          ref="form"
          size="large"
          :model="formModel"
          :rules="rules"
          autocomplete="off"
          v-else
        >
          <el-form-item>
            <h1>登录</h1>
          </el-form-item>
          <el-form-item prop="username">
            <el-input
              :prefix-icon="User"
              placeholder="请输入用户名"
              v-model.trim="formModel.username"
            ></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input
              name="password"
              :prefix-icon="Lock"
              type="password"
              placeholder="请输入密码"
              v-model.trim="formModel.password"
            ></el-input>
          </el-form-item>
          <el-form-item class="flex">
            <div class="flex">
              <el-checkbox>记住我</el-checkbox>
              <el-link type="primary" :underline="false">忘记密码？</el-link>
            </div>
          </el-form-item>
          <el-form-item>
            <el-button
              class="button"
              type="primary"
              auto-insert-space
              @click="login()"
              >登录</el-button
            >
          </el-form-item>
          <el-form-item class="flex">
            <el-link type="info" :underline="false" @click="isRegister = true">
              注册 →
            </el-link>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </div>
</template>

<style lang="scss" scoped>
.login-page {
  height: 100vh;
  background-color: #fff;
  .bg {
    background:
      url('@/assets/logo2.png') no-repeat 60% center / 240px auto,
      url('@/assets/login_bg.jpg') no-repeat center / cover;
    border-radius: 0 20px 20px 0;
  }
  .form {
    display: flex;
    flex-direction: column;
    justify-content: center;
    user-select: none;
    .title {
      margin: 0 auto;
    }
    .button {
      width: 100%;
    }
    .flex {
      width: 100%;
      display: flex;
      justify-content: space-between;
    }
  }
}
</style>
