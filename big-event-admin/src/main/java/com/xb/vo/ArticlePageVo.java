package com.xb.vo;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author xb
 * @description TODO
 * @create 2023-08-06 16:42
 * @vesion 1.0
 */
@Data
@Builder
public class ArticlePageVo {

    private List<ArticleVo> list;

    private Long total;

}
