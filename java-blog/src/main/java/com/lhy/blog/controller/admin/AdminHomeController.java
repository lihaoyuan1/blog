package com.lhy.blog.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.lhy.blog.entity.Article;
import com.lhy.blog.service.ArticleService;
import com.lhy.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/adminHome")
public class AdminHomeController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/topInfo")
    public Result<JSONObject> topInfo(){
        List<Article> articleList = articleService.allArticleSimple();
        JSONObject result = new JSONObject();
        int views = 0, count = articleList.size();
        for (int i=0; i<count; i++){
            int temp = articleList.get(i).getViews();
            views += temp;
        }
        result.put("views", views);
        result.put("count", count);
        return new Result<>(true, "获取成功", result);
    }

}
