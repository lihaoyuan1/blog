package com.lhy.blog.service;

import com.lhy.blog.entity.Comment;
import com.lhy.blog.vo.CommentVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface CommentService {

    Integer create(Comment comment, HttpServletRequest request);

    List<CommentVO> getAllCommentByArticleId(Integer articleId);

}
