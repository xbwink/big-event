package com.xb.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xb.common.BaseContext;
import com.xb.common.R;
import com.xb.entity.Category;
import com.xb.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xb
 * @description 文章相关控制器
 * @create 2023-08-02 10:22
 * @vesion 1.0
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 获取文章分类列表
     * @return
     */
    @GetMapping("/list")
    public R<List<Category>> getList(){
        //查询当前登录用户的文章分类
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getAuthorId, BaseContext.getCurrentId());
        return R.success(categoryService.list(wrapper));
    }

    /**
     * 添加文章分类
     * @return
     */
    @PostMapping
    public R<String> add(@RequestBody Category category){
        //给当前文章分类设置对应的用户
        category.setAuthorId(BaseContext.getCurrentId().toString());
        categoryService.save(category);
        return R.success("添加成功");
    }

    /**
     * 修改文章分类
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody Category category){

        categoryService.updateById(category);
        return R.success("修改成功");
    }

    /**
     * 删除文章分类
     * @return
     */
    @DeleteMapping
    public R<String> del(@RequestParam("id") Long categoryId){
        categoryService.removeById(categoryId);
        return R.success("删除成功");
    }




}
