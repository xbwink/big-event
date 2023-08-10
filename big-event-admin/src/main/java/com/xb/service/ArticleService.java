package com.xb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xb.dto.AddOrEditArticleDto;
import com.xb.dto.PageParamsDto;
import com.xb.entity.Article;
import com.xb.vo.ArticlePageVo;

/**
 * @author xb
 * @description TODO
 * @create 2023-08-05 16:47
 * @vesion 1.0
 */
public interface ArticleService extends IService<Article> {

    ArticlePageVo pageQuery(PageParamsDto dto);


    void addArticle(AddOrEditArticleDto dto);

}
