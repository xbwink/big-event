import request from '@/utils/request'

//获取文章分类列表
export const getCategorysService = () => {
  return request.get('/category/list')
}

//添加
export const addCategorysService = ({
  categoryName,
  categoryAlias,
  userId
}) => {
  return request.post('/category', { categoryName, categoryAlias, userId })
}

//修改
export const editCategorysService = ({
  id,
  categoryName,
  categoryAlias,
  userId
}) => {
  return request.put('/category', { id, categoryName, categoryAlias, userId })
}

//删除
export const delCategoryService = (id) => {
  return request.delete('/category', { params: { id } })
}
