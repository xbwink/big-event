package com.xb.controller;

import com.xb.common.BaseContext;
import com.xb.common.R;
import com.xb.dto.EditPwdDto;
import com.xb.dto.RegisterDto;
import com.xb.entity.User;
import com.xb.service.UserService;
import com.xb.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xb
 * @description 用户相关控制器
 * @create 2023-08-02 10:22
 * @vesion 1.0
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public R<String> register(@RequestBody RegisterDto dto){
        userService.register(dto);
        return R.success("注册成功");
    }

    @PostMapping("/login")
    public R<String> login(@RequestBody RegisterDto dto){
        User user = userService.login(dto);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        String token = JwtUtil.createJWT(
                "xbwink",
                7200000,
                claims);

        return R.success(token);
    }

    /**
     * 获取用户信息
     * @return
     */
    @GetMapping("/userInfo")
    public R<User> getUser(){
        User user = userService.getById(BaseContext.getCurrentId());
        user.setPassword(null);
        return R.success(user);
    }

    /**
     * 更新用户信息
     * @return
     */
    @PutMapping("/userInfo")
    public R<String> editUser(@RequestBody User user){
        userService.updateById(user);
        return R.success("修改成功");
    }

    /**
     * 更新用户头像
     * @param file
     * @return
     */
    @PostMapping("/upload/avatar")
    public R<String> editAvatar(@RequestParam("file") MultipartFile file){
        userService.uploadAvatar(file);
        return R.success("修改成功");
    }

    /**
     * 更新用户密码
     * @param dto
     * @return
     */
    @PutMapping("/upload/pwd")
    public R<String> editAvatar(@RequestBody EditPwdDto dto){
        userService.uploadPwd(dto);
        return R.success("修改成功");
    }

}
