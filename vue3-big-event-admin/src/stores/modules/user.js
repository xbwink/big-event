import { defineStore } from 'pinia'
import { ref } from 'vue'
import { getUserInfo } from '@/api/user'

export const useUserStore = defineStore(
  'user',
  () => {
    const token = ref('')
    const user = ref({
      id: '',
      name: '',
      nickName: '',
      email: '',
      avatar: ''
    })

    const getUser = async () => {
      const res = await getUserInfo()
      user.value = res.data.data
    }

    const setUser = (newUser) => {
      user.value = newUser
    }

    const setToken = (newValue) => {
      token.value = newValue
    }

    const removeToken = () => {
      token.value = ''
    }

    return {
      token,
      setToken,
      removeToken,
      user,
      getUser,
      setUser
    }
  },
  {
    persist: true //持久化
  }
)
