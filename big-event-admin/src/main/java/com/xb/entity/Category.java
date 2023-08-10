package com.xb.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xb
 * @description 文章分类
 * @create 2023-08-03 15:02
 * @vesion 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    private String id;
    private String categoryName;
    private String categoryAlias;
    private String authorId;

}
