package com.xb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xb
 * @description TODO
 * @create 2023-08-02 10:59
 * @vesion 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {

    private String username;
    private String password;
    private String repassword;

}
