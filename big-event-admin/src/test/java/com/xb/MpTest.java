package com.xb;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xb.entity.Article;
import com.xb.entity.User;
import com.xb.mapper.UserMapper;
import com.xb.service.ArticleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author xb
 * @description TODO
 * @create 2023-08-02 11:57
 * @vesion 1.0
 */
@SpringBootTest
public class MpTest {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ArticleService articleService;

    @Test
    public void testUserMapper(){
        List<User> users = userMapper.selectList(null);
        System.out.println(users);
    }

    @Test
    public void testPage(){
        //创建分页构造器
        Page pageInfo = new Page(1, 5);
        //创建条件构造器
        LambdaQueryWrapper<Article> lqw = new LambdaQueryWrapper<>();
        String state = "";
        lqw.eq(lqw.isEmptyOfEntity(), Article::getState,state);
        //执行查询
        Page page = articleService.page(pageInfo,lqw);
        System.out.println(page);
    }

}
