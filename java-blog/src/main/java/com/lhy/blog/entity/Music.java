package com.lhy.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@TableName(value = "music")
public class Music {

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @TableField(value = "artist")
    private String artist;

    @TableField(value = "lrc")
    @JsonProperty("lrc")
    private String lrcUrl;

    @TableField(value = "pic")
    @JsonProperty("pic")
    private String pictureUrl;

    @TableField(value = "src")
    @JsonProperty("src")
    private String musicSrc;

    @TableField(value = "title")
    private String title;
}
