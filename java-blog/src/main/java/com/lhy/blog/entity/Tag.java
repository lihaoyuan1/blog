package com.lhy.blog.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "tag")
public class Tag {

    @TableId(value = "id")
    private Integer id;

    @TableField(value = "tag_name")
    private String tagName;

    @TableField(exist = false)
    private Integer count = 0;

    @TableField(exist = false)
    private Integer views = 0;

}
