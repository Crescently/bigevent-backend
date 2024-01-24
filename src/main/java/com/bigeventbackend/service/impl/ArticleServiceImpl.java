package com.bigeventbackend.service.impl;

import com.bigeventbackend.common.PageBean;
import com.bigeventbackend.mapper.ArticleMapper;
import com.bigeventbackend.pojo.entity.Article;
import com.bigeventbackend.service.ArticleService;
import com.bigeventbackend.utils.LoginUserInfoUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public void addArticle(Article article) {
        article.setCreateTime(LocalDateTime.now());
        article.setUpdateTime(LocalDateTime.now());
        article.setCreateUser(LoginUserInfoUtil.getUserId());

        articleMapper.addArticle(article);
    }

    @Override
    public PageBean<Article> getArticleList(Integer pageNum, Integer pageSize, Integer categoryId, String state) {
        //
        PageBean<Article> articlePageBean = new PageBean<>();

        // 开启分页查询 PageHelper
        PageHelper.startPage(pageNum, pageSize);

        Integer userId = LoginUserInfoUtil.getUserId();

        List<Article> articleList = articleMapper.getArticleList(userId, categoryId, state);
        // 在Page类中声明了方法，可以获取PageHelper查询到的记录条数和当前页数据
        Page<Article> articlePage = (Page<Article>) articleList;

        articlePageBean.setTotal(articlePage.getTotal());
        articlePageBean.setItems(articlePage.getResult());

        return articlePageBean;
    }

    @Override
    public void updateArticle(Article article) {
        article.setUpdateTime(LocalDateTime.now());
        articleMapper.updateArticle(article);
    }

    @Override
    public void deleteArticle(Integer id) {
        articleMapper.deleteArticle(id);
    }

    @Override
    public Article getArticleById(Integer id) {
        return articleMapper.getArticleById(id);
    }
}
