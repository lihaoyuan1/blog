package com.lhy.blog.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CommentVO {

    private String id;

    private Integer type;

    private String nickname;

    private String avatar;

    private String content;

    private Integer admire;

    private String parentId;

    private String parentNickName;

    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date createTime;

    List<CommentVO> children;

}
