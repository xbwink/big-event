package com.xb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xb.common.BaseContext;
import com.xb.dto.AddOrEditArticleDto;
import com.xb.dto.PageParamsDto;
import com.xb.entity.Article;
import com.xb.mapper.ArticleMapper;
import com.xb.service.ArticleService;
import com.xb.utils.MinioUtil;
import com.xb.vo.ArticlePageVo;
import com.xb.vo.ArticleVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author xb
 * @description TODO
 * @create 2023-08-05 16:47
 * @vesion 1.0
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    private MinioUtil minioUtil;

    /**
     * 分页查询
     * @param dto
     * @return
     */
    @Override
    public ArticlePageVo pageQuery(PageParamsDto dto) {
        String authId = String.valueOf(BaseContext.getCurrentId());//作者id
        dto.setAuthId(authId);
        //1、计算limit查询条件(pageNo-1)*pageSize
        int pageNo = (dto.getPageNo()-1)*dto.getPageSize();
        dto.setPageNo(pageNo);
        List<ArticleVo> articleVos = articleMapper.pageQuery(dto);
        //2、查询符合条件记录条数
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(dto.getCategoryId()!=null && !dto.getCategoryId().isEmpty(),Article::getCategoryId,dto.getCategoryId());
        wrapper.eq(dto.getState()!=null && !dto.getState().isEmpty(),Article::getState,dto.getState());
        wrapper.eq(Article::getAuthorId,authId);
        Long count = articleMapper.selectCount(wrapper);

        //3、构建返回结果对象
        ArticlePageVo vo = ArticlePageVo.builder()
                .list(articleVos)
                .total(count)
                .build();
        return vo;
    }

    /**
     * 添加或者修改文章
     * @param dto
     */
    @Override
    public void addArticle(AddOrEditArticleDto dto) {
        //1、拷贝属性执行添加
        Article article = new Article();
        BeanUtils.copyProperties(dto,article);
        article.setAuthorId(BaseContext.getCurrentId().toString());

        //2、上传图片至minio并且添加下列前缀存入数据库
        String prefix = "http://localhost:9000/bigevent/";
        String fileName = minioUtil.upload(dto.getImg());
        article.setImg(prefix+fileName);

        //3、插入数据库
        if(article.getId() == null){
            article.setPublishDate(LocalDateTime.now());
            save(article);
            return;
        }
        updateById(article);

    }


}
