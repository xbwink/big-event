import request from '@/utils/request'

//获取文章列表
export const getArticleListsService = (params) => {
  return request.get('/article/list', { params })
}

//添加文章
export const artPublishService = (data) => request.post('/article/add', data)

//修改文章
export const artEditService = (data) => request.put('/article/edit', data)

//删除文章
export const artDelService = (id) =>
  request.delete('/article', { params: { id } })
