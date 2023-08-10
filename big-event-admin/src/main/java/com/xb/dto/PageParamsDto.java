package com.xb.dto;

import lombok.Data;

/**
 * @author xb
 * @description TODO
 * @create 2023-08-06 14:44
 * @vesion 1.0
 */
@Data
public class PageParamsDto {
    private Integer pageNo;
    private Integer pageSize;
    private String categoryId;
    private String state;
    private String authId;
}
