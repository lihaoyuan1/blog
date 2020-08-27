package com.lhy.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "comment")
public class Comment {

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @TableField(value = "type")
    private Integer type;

    @TableField(value = "nickname")
    private String nickname;

    @TableField(value = "email")
    private String email;

    @TableField(value = "avatar")
    private String avatar;

    @TableField(value = "ipv4")
    private String ipv4;

    @TableField(value = "content")
    private String content;

    @TableField(value = "admire")
    private Integer admire;

    @TableField(value = "article_id")
    private Integer articleId;

    @TableField(value = "parent_id")
    private String parentId;

    @TableField(value = "parent_nickname")
    private String parentNickName;

    @TableField(value = "create_time")
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date createTime;

}
