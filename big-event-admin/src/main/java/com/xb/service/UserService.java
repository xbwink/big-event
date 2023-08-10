package com.xb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xb.dto.EditPwdDto;
import com.xb.dto.RegisterDto;
import com.xb.entity.User;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author xb
 * @description TODO
 * @create 2023-08-02 14:19
 * @vesion 1.0
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     * @param dto
     */
    void register(RegisterDto dto);

    /**
     * 用户登录
     * @param dto
     * @return
     */
    User login(RegisterDto dto);

    /**
     * 修改用户头像
     * @param file
     */
    void uploadAvatar(MultipartFile file);

    /**
     * 修改用户密码
     * @param dto
     */
    void uploadPwd(EditPwdDto dto);
}
