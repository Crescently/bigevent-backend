package com.bigeventbackend.pojo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Category {

    @NotNull(groups = Update.class)
    private Integer id;//主键ID
    @NotEmpty
    private String categoryName;//分类名称
    @NotEmpty
    private String categoryAlias;//分类别名
    private Integer createUser;//创建人ID
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;//创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;//更新时间


    /**
     * 如果校验项没有指定分组，默认属于Default分组
     * 校验分组：Add表示新增数据时的校验，Update表示更新数据时的校验
     * 校验分组的作用是，在执行校验时，只对指定分组进行校验
     * 分组之间可以继承
     */
    public interface Add extends Default {
    }

    public interface Update extends Default {
    }
}
