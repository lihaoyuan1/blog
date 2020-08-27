package com.lhy.blog.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@TableName(value = "article")
public class Article {

    @TableId(value = "id")
    private Integer id;

    @TableField(value = "title")
    private String title;

    @TableField(value = "content")
    private String content;

    @TableField(value = "summary")
    private String summary;

    @TableField(value = "type")
    private String type;

    @TableField(value = "user_id")
    private String userId;

    @TableField(value = "reward_able")
    private Integer rewardAble;

    @TableField(value = "comment_able")
    private Integer commentAble;

    @TableField(value = "recommend_able")
    private Integer recommendAble;

    @TableField(value = "is_top")
    private Long isTop;

    @TableField(value = "views")
    private Integer views;

    @TableField(value = "admire")
    private Integer admire;

    @TableField(value = "picture")
    private String picture;

    @TableField(value = "create_time")
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date createTime;

    @TableField(value = "update_time")
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date updateTime;

    @TableField(exist = false)
    private List<Tag> tagList;

    @TableField(exist = false)
    private Integer comments;

    @TableField(exist = false)
    private String nickname;

    @TableField(exist = false)
    private String avatar;
}
