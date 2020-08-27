package com.lhy.blog.controller.display;

import com.lhy.blog.entity.Category;
import com.lhy.blog.service.CategoryService;
import com.lhy.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/listTop")
    public Result<?> listTop(){
        List<Category> result = categoryService.listTop();
        return new Result<>(true, "获取成功", result);
    }

    @GetMapping("/all")
    public Result<?> all(){
        List<Category> result = categoryService.allCategoryOfDetail();
        return new Result<>(true, "获取成功", result);
    }

    @GetMapping("/getOne")
    public Result<?> getOne(@RequestParam Integer id){
        Category result = categoryService.getOneById(id);
        return new Result<>(true, "获取成功", result);
    }

}
