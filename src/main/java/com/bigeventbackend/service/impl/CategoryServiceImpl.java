package com.bigeventbackend.service.impl;

import com.bigeventbackend.mapper.CategoryMapper;
import com.bigeventbackend.pojo.entity.Category;
import com.bigeventbackend.service.CategoryService;
import com.bigeventbackend.utils.LoginUserInfoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public void addCategory(Category category) {
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        category.setCreateUser(LoginUserInfoUtil.getUserId());

        categoryMapper.addCategory(category);
    }

    @Override
    public List<Category> getCategories() {
        return categoryMapper.getCategories(LoginUserInfoUtil.getUserId());
    }

    @Override
    public Category getCategoryById(Integer id) {
        return  categoryMapper.getCategoryById(id);
    }

    @Override
    public void updateCategory(Category category) {
        category.setUpdateTime(LocalDateTime.now());
        categoryMapper.updateCategory(category);
    }

    @Override
    public void deleteCategory(Integer id) {
        categoryMapper.deleteCategory(id);
    }
}
