package com.bigeventbackend.controller;

import com.bigeventbackend.common.Result;
import com.bigeventbackend.pojo.entity.Category;
import com.bigeventbackend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public Result addCategory(@RequestBody @Validated(Category.Add.class) Category category) {
        categoryService.addCategory(category);
        return Result.success();
    }

    @GetMapping
    public Result<List<Category>> getAllCategories() {
        List<Category> categoryList = categoryService.getCategories();
        return Result.success(categoryList);
    }

    @GetMapping("/detail")
    public Result<Category> getCategoryById(Integer id) {
        Category category = categoryService.getCategoryById(id);
        return Result.success(category);
    }

    @PutMapping
    public Result updateCategory(@RequestBody @Validated(Category.Update.class) Category category) {
        categoryService.updateCategory(category);
        return Result.success();
    }

    @DeleteMapping
    public Result deleteCategory(Integer id) {
        categoryService.deleteCategory(id);
        return Result.success();
    }
}
