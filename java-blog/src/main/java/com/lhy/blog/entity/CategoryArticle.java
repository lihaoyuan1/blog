package com.lhy.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "category_article")
public class CategoryArticle {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "category_id")
    private Integer categoryId;

    @TableField(value = "article_id")
    private Integer articleId;

    public CategoryArticle(Integer categoryId, Integer articleId){
        this.categoryId = categoryId;
        this.articleId = articleId;
    }

}
