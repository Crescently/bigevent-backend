package com.bigeventbackend.service;

import com.bigeventbackend.common.PageBean;
import com.bigeventbackend.pojo.entity.Article;

public interface ArticleService {
    void addArticle(Article article);

    PageBean<Article> getArticleList(Integer pageNum, Integer pageSize, Integer categoryId, String state);

}
