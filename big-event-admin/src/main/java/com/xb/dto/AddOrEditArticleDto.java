package com.xb.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author xb
 * @description TODO
 * @create 2023-08-08 10:56
 * @vesion 1.0
 */
@Data
public class AddOrEditArticleDto {
    private String id;
    private String title;
    private String categoryId;
    private String content;
    private MultipartFile img;
    private String state;
}
