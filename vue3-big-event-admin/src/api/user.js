import request from '@/utils/request'

//用户注册请求
export const userRegisterService = ({ username, password, repassword }) => {
  return request.post('/user/register', { username, password, repassword })
}

//用户登录请求
export const userLoginService = ({ username, password }) => {
  return request.post('/user/login', { username, password })
}

//获取用户信息
export const getUserInfo = () => {
  return request.get('/user/userInfo')
}

//更新用户信息
export const editUserInfoService = ({
  id,
  name,
  nickName,
  password,
  email,
  avatar
}) => {
  return request.put('/user/userInfo', {
    id,
    name,
    nickName,
    password,
    email,
    avatar
  })
}

//更新用户头像
export const editUserAvatarService = (data) => {
  return request.post('/user/upload/avatar', data)
}

//更新用户密码
export const editUserPasswordService = (data) => {
  return request.put('/user/upload/pwd', data)
}
