package com.xb.controller;

import com.xb.common.R;
import com.xb.dto.AddOrEditArticleDto;
import com.xb.dto.PageParamsDto;
import com.xb.service.ArticleService;
import com.xb.vo.ArticlePageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author xb
 * @description 文章相关控制器
 * @create 2023-08-02 10:22
 * @vesion 1.0
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;



    /**
     * 分页查询
     *
     * @param dto
     * @return
     */
    @GetMapping("/list")
    public R<ArticlePageVo> page(PageParamsDto dto) {
//        分页构造器
//        Page pageInfo = new Page(pageNo, pageSize);
//        //创建条件构造器
//        LambdaQueryWrapper<Article> lqw = new LambdaQueryWrapper<>();
//        lqw.eq(!categoryId.isEmpty(),Article::getCategoryId,categoryId);
//        lqw.eq(!state.isEmpty(), Article::getState,state);
//        //执行查询
//        articleService.page(pageInfo,lqw);
//        PageHelper.startPage(dto.getPageNo(),dto.getPageSize());
        ArticlePageVo vo = articleService.pageQuery(dto);
        return R.success(vo);
    }

    /**
     * 添加文章
     * @param dto
     * @return
     */
    @PostMapping("/add")
    public R<String> addArticle(AddOrEditArticleDto dto) {
        articleService.addArticle(dto);
        return R.success("添加成功");
    }

    /**
     * 修改文章
     * @param dto
     * @return
     */
    @PutMapping("/edit")
    public R<String> editArticle(AddOrEditArticleDto dto) {
        articleService.addArticle(dto);
        return R.success("修改成功");
    }

    /**
     * 删除文章
     * @param id
     * @return
     */
    @DeleteMapping
    public R<String> deleteArticle(String id){
        articleService.removeById(id);
        return R.success("删除成功");
    }


}
