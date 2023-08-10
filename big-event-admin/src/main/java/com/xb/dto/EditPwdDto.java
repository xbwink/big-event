package com.xb.dto;

import lombok.Data;

/**
 * @author xb
 * @description TODO
 * @create 2023-08-10 9:34
 * @vesion 1.0
 */
@Data
public class EditPwdDto {

    private String oldPassword;
    private String newPassword;

}
