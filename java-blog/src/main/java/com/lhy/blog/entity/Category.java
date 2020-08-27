package com.lhy.blog.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "category")
public class Category {

    @TableId(value = "id")
    private Integer id;

    @TableField(value = "category_name")
    private String categoryName;

    @TableField(value = "description")
    private String description;

    @TableField(value = "picture")
    private String picture;

    @TableField(value = "sequence")
    private Integer sequence;

    @TableField(exist = false)
    private Integer views = 0;

    @TableField(exist = false)
    private Integer count = 0;
}
