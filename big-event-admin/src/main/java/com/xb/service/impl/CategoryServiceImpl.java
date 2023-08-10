package com.xb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xb.entity.Category;
import com.xb.mapper.CategoryMapper;
import com.xb.service.CategoryService;
import org.springframework.stereotype.Service;

/**
 * @author xb
 * @description TODO
 * @create 2023-08-03 15:05
 * @vesion 1.0
 */

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
}
