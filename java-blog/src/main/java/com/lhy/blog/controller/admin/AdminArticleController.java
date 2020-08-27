package com.lhy.blog.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lhy.blog.dto.QueryDto;
import com.lhy.blog.entity.Article;
import com.lhy.blog.entity.Category;
import com.lhy.blog.entity.Tag;
import com.lhy.blog.service.ArticleService;
import com.lhy.blog.service.TokenService;
import com.lhy.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping("/admin/article")
public class AdminArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/createArticle")
    public Result<?> createArticle(Article article, @RequestParam String categoryIds,
                                   @RequestParam String tagIds, HttpServletRequest request){
        String token = tokenService.getCookie(request);
        String userId = tokenService.decodeToken(token);
        Integer rows = articleService.saveArticle(article, categoryIds, tagIds, userId);
        if (rows < 1){
            return new Result<>(false, "发表文章失败，请稍后重试!");
        }
        return new Result<>(true, "发表文章成功!");
    }

    @PostMapping("/updateArticle")
    public Result<?> updateArticle(Article article, @RequestParam String categoryIds,
                                   @RequestParam String tagIds){
        articleService.updateArticle(article, categoryIds, tagIds);
        return new Result<>(true, "发表文章成功!");
    }

    @GetMapping("/queryList")
    public Result<IPage<Article>> queryList(QueryDto queryDto,
                                            @RequestParam Integer page,
                                            @RequestParam(defaultValue = "20", required = false) Integer size){
        Page<Article> articlePage = new Page<>(page, size);
        IPage<Article> articleIPage = articleService.listArticle(queryDto, articlePage);
        articleIPage.getRecords().removeIf(Objects::isNull);
        return new Result<>(true, "获取列表成功!", articleIPage);
    }

    @PostMapping("/isTop")
    public Result<?> isTop(@RequestParam Integer id){
        articleService.handleIsTop(id);
        return new Result<>(true, "操作成功");
    }

    @GetMapping("/getYears")
    public Result<Set<Integer>> getOptions(){
        Set<Integer> years = articleService.getYears();
        return new Result<>(true, "获取成功!", years);
    }

    @PostMapping("/delete")
    public Result<?> deleteById(@RequestParam Integer id){
        int rows = articleService.deleteArticle(id);
        if (rows < 1) {
            return new Result<>(false, "删除失败，请稍后再试!");
        }
        return new Result<>(true, "删除成功!");
    }

    @GetMapping("/getOne")
    public Result<Article> getOne(@RequestParam Integer id){
        Article result = articleService.getArticleById(id);
        return new Result<>(true, "获取成功!", result);
    }

    @GetMapping("/categoryByArticleId")
    public Result<List<Category>> categoryByArticleId(@RequestParam Integer id){
        List<Category> result = articleService.allCategorySimpleByArticleId(id);
        return new Result<>(true, "获取成功!", result);
    }

    @GetMapping("/tagByArticleId")
    public Result<List<Tag>> tagByArticleId(@RequestParam Integer id){
        List<Tag> result = articleService.allTagSimpleByArticleId(id);
        return new Result<>(true, "获取成功!", result);
    }

    @GetMapping("/listByCategoryId")
    public Result<IPage<Article>> listByCategoryId(@RequestParam Integer categoryId,
                                                   @RequestParam Integer page,
                                                   @RequestParam(defaultValue = "20", required = false) Integer size){
        Page<Article> articlePage = new Page<>(page, size);
        IPage<Article> result = articleService.listArticleByCategoryId(categoryId, articlePage);
        return new Result<>(true, "获取成功!", result);
    }

    @GetMapping("listByTagId")
    public Result<IPage<Article>> listByTagId(@RequestParam Integer tagId,
                                              @RequestParam Integer page,
                                              @RequestParam(defaultValue = "20", required = false) Integer size){
        Page<Article> articlePage = new Page<>(page, size);
        IPage<Article> result = articleService.listArticleByTagId(tagId, articlePage);
        return new Result<>(true, "获取成功!", result);
    }

    @PostMapping("releaseFromCategory")
    public Result<?> releaseFromCategory(@RequestParam Integer categoryId,
                                         @RequestParam Integer articleId){
        Integer rows = articleService.releaseFromCategory(articleId, categoryId);
        if (rows < 1)
            return new Result<>(false, "移除失败，请稍后再试!");
        return new Result<>(true, "移除成功!");
    }

    @PostMapping("releaseFromTag")
    public Result<?> releaseFromTag(@RequestParam Integer tagId,
                                    @RequestParam Integer articleId){
        Integer rows = articleService.releaseFromTag(tagId, articleId);
        if (rows < 1)
            return new Result<>(false, "移除失败，请稍后再试!");
        return new Result<>(true, "移除成功!");
    }

}
