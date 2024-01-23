package com.bigeventbackend.controller;

import com.bigeventbackend.common.PageBean;
import com.bigeventbackend.common.Result;
import com.bigeventbackend.pojo.entity.Article;
import com.bigeventbackend.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping
    public Result addArticle(@RequestBody @Validated Article article) {
        articleService.addArticle(article);
        return Result.success();
    }

    @GetMapping
    public Result<PageBean<Article>> getArticleList(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) String state
    ) {
        PageBean<Article> articlePageBean = articleService.getArticleList(pageNum, pageSize, categoryId, state);
        return Result.success(articlePageBean);
    }

}
