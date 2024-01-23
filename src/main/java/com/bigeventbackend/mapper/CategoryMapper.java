package com.bigeventbackend.mapper;

import com.bigeventbackend.pojo.entity.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryMapper {
    @Insert("insert into category(category_name, category_alias, create_user, create_time, update_time) " + "values(#{categoryName},#{categoryAlias},#{createUser},#{createTime},#{updateTime})")
    void addCategory(Category category);

    @Select("select * from category where create_user = #{userId}")
    List<Category> getCategories(Integer userId);

    @Select("select * from category where id = #{id}")
    Category getCategoryById(Integer id);

    @Update("update category set category_name = #{categoryName}, category_alias = #{categoryAlias}, update_time = #{updateTime} where id = #{id} ")
    void updateCategory(Category category);

    @Delete("delete from category where id = #{id}")
    void deleteCategory(Integer id);
}
