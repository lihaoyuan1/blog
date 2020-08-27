package com.lhy.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "tag_article")
public class TagArticle {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "tag_id")
    private Integer tagId;

    @TableField(value = "article_id")
    private Integer articleId;

    public TagArticle(Integer tagId, Integer articleId){
        this.tagId = tagId;
        this.articleId = articleId;
    }

}
