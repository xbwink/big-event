package com.xb.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author xb
 * @description TODO
 * @create 2023-08-05 16:42
 * @vesion 1.0
 */
@Data
public class Article {

    private String id;
    private String title;
    private String content;
    private String img;
    private LocalDateTime publishDate;
    private String state;
    private String categoryId;
    private String authorId;

}
