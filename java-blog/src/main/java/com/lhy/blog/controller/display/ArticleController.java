package com.lhy.blog.controller.display;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lhy.blog.entity.Article;
import com.lhy.blog.service.ArticleService;
import com.lhy.blog.vo.ArchiveVO;
import com.lhy.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/hotArticle")
    public Result<?> hotArticle(@RequestParam Integer size){
        List<Article> result = articleService.hotArticleList(size);
        return new Result<>(true, "获取成功", result);
    }

    @GetMapping("/recommendTop")
    public Result<?> recommendTop(@RequestParam Integer size){
        List<Article> result = articleService.recommendArticleTop(size);
        return new Result<>(true, "获取成功", result);
    }

    @GetMapping("/latestTop")
    public Result<?> latestTop(@RequestParam Integer size){
        List<Article> result = articleService.latestArticleTop(size);
        return new Result<>(true, "获取成功", result);
    }

    @GetMapping("recommendPage")
    public Result<?> recommendPage(@RequestParam Integer page,
                                   @RequestParam(defaultValue = "20", required = false) Integer size){
        Page<Article> articlePage = new Page<>(page, size);
        IPage<Article> result = articleService.recommendArticlePage(articlePage);
        return new Result<>(true, "获取成功", result);
    }

    @GetMapping("/getByCategoryId")
    public Result<?> getByCategoryId(@RequestParam Integer id,
                                     @RequestParam Integer page,
                                     @RequestParam(defaultValue = "10", required = false) Integer size){
        Page<Article> articlePage = new Page<>(page, size);
        IPage<Article> result = articleService.listArticleDetailByCategoryId(id, articlePage);
        return new Result<>(true, "获取成功!", result);
    }

    @GetMapping("/getByTagId")
    public Result<?> getByTagId(@RequestParam Integer id,
                                @RequestParam Integer page,
                                @RequestParam(defaultValue = "10", required = false) Integer size){
        Page<Article> articlePage = new Page<>(page, size);
        IPage<Article> result = articleService.listArticleDetailByTagId(id, articlePage);
        return new Result<>(true, "获取成功!", result);
    }

    @GetMapping("/getOne")
    public Result<?> getOne(@RequestParam Integer id){
        articleService.viewArticle(id);
        Article result = articleService.getArticleById(id);
        return new Result<>(true, "获取成功!", result);
    }

    @PostMapping("/admire")
    public Result<?> admire(@RequestParam Integer id,
                            @RequestParam Integer flag){
        Integer raw = articleService.admireArticle(id, flag);
        if (raw < 1)
            return new Result<>(false, "操作失败!");
        return new Result<>(true, "操作成功!");
    }

    @GetMapping("/archive")
    public Result<?> archive(){
        List<ArchiveVO> result = articleService.getArchive();
        return new Result<>(true, "获取成功!", result);
    }

    @GetMapping("/search")
    public Result<?> search(@RequestParam String searchText,
                            @RequestParam Integer page,
                            @RequestParam(defaultValue = "10", required = false) Integer size){
        Page<Article> articlePage = new Page<>(page, size);
        IPage<Article> result = articleService.search(searchText, articlePage);
        return new Result<>(true, "获取成功!", result);
    }

}
