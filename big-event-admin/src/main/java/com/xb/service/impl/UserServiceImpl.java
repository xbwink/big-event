package com.xb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xb.common.BaseContext;
import com.xb.common.CustomException;
import com.xb.dto.EditPwdDto;
import com.xb.dto.RegisterDto;
import com.xb.entity.User;
import com.xb.mapper.UserMapper;
import com.xb.service.UserService;
import com.xb.utils.MinioUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author xb
 * @description TODO
 * @create 2023-08-02 14:19
 * @vesion 1.0
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MinioUtil minioUtil;

    /**
     * 用户注册
     * @param dto
     */
    @Override
    public void register(RegisterDto dto) {
        //查询当前登录名称是否已经被注册
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getName,dto.getUsername());
        User user = getOne(wrapper);
        if(user != null){
            throw new CustomException("当前用户名已存在");
        }

        //复制dto属性到user对象
        user = User.builder()
                .name(dto.getUsername())
                .password(dto.getPassword())
                .build();

        //将密码进行加密处理
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        //提交到数据库
        save(user);
    }

    /**
     * 用户登录
     * @param dto
     * @return
     */
    @Override
    public User login(RegisterDto dto) {
        String username = dto.getUsername();
        String password = dto.getPassword();

        //1、根据用户名查询数据库中的数据
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getName,username);
        User user = getOne(wrapper);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (user == null) {
            //账号不存在
            throw new CustomException("账号不存在");
        }

        //密码比对
        //这里使用security进行加密
        if (!passwordEncoder.matches(password,user.getPassword())) {
            //密码错误
            throw new CustomException("密码错误");
        }

        //3、返回实体对象
        return user;
    }

    /**
     * 修改头像
     * @param file
     */
    @Override
    public void uploadAvatar(MultipartFile file) {
        //1、构建user对象
        User user = new User();
        String userId = BaseContext.getCurrentId().toString();

        //2、上传图片至minio并且添加下列前缀存入数据库
        String upload = minioUtil.upload(file);
        String prefix = "http://localhost:9000/bigevent/";

        //3、设置属性值
        user.setId(userId);
        user.setAvatar(prefix+upload);

        updateById(user);
    }

    @Override
    public void uploadPwd(EditPwdDto dto) {
        //1、从数据库查出用户密码
        User user = getById(BaseContext.getCurrentId());
        //2、检查密码是否输入正确
        if(!passwordEncoder.matches(dto.getOldPassword(),user.getPassword())){
            //密码错误
            throw new CustomException("原密码错误");
        }
        //3、修改密码
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        updateById(user);
    }

}
