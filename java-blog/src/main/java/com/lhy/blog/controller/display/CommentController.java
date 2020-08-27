package com.lhy.blog.controller.display;

import com.lhy.blog.entity.Comment;
import com.lhy.blog.service.CommentService;
import com.lhy.blog.vo.CommentVO;
import com.lhy.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/create")
    public Result<?> create(Comment comment, HttpServletRequest request){
        int rows = commentService.create(comment, request);
        if (rows < 1)
            return new Result<>(false, "添加失败!");
        List<CommentVO> result = commentService.getAllCommentByArticleId(comment.getArticleId());
        return new Result<>(true, "添加成功!", result);
    }

    @GetMapping("/getComments")
    public Result<?> getComments(@RequestParam Integer articleId){
        List<CommentVO> result = commentService.getAllCommentByArticleId(articleId);
        return new Result<>(true, "获取成功!", result);
    }

}
