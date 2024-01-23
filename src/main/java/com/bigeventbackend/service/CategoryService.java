package com.bigeventbackend.service;

import com.bigeventbackend.pojo.entity.Category;

import java.util.List;

public interface CategoryService {
    void addCategory(Category category);

    List<Category> getCategories();

    Category getCategoryById(Integer id);

    void updateCategory(Category category);

    void deleteCategory(Integer id);
}
