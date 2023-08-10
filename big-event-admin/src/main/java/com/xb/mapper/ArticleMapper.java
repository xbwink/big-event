package com.xb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xb.dto.PageParamsDto;
import com.xb.entity.Article;
import com.xb.vo.ArticleVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author xb
 * @description TODO
 * @create 2023-08-05 16:46
 * @vesion 1.0
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

    List<ArticleVo> pageQuery(PageParamsDto dto);

}
