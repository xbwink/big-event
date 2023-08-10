<script setup>
import { ref } from 'vue'
import { getCategorysService } from '@/api/category.js'

//父传子数据

defineProps({
  modelValue: {
    type: [Number, String]
  }
})

const emit = defineEmits(['update:modelValue'])

const channelList = ref([])

//获取分类列表
const getList = async () => {
  const res = await getCategorysService()
  channelList.value = res.data.data
}

getList()
</script>

<template>
  <el-select
    :modelValue="modelValue"
    @update:modelValue="emit('update:modelValue', $event)"
  >
    <el-option
      v-for="item in channelList"
      :key="item.id"
      :label="item.categoryName"
      :value="item.id"
    ></el-option>
  </el-select>
</template>
