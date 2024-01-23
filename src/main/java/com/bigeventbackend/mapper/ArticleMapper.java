package com.bigeventbackend.mapper;

import com.bigeventbackend.pojo.entity.Article;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ArticleMapper {
    @Insert("INSERT INTO article (title, content,cover_img,state,category_id,create_user,create_time,update_time) " +
            "VALUES (#{title}, #{content},#{coverImg},#{state},#{categoryId},#{createUser},#{createTime},#{updateTime})")
    void addArticle(Article article);

    List<Article> getArticleList(Integer userId, Integer categoryId, String state);
}
