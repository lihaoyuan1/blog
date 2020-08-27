package com.lhy.blog.controller.admin;


import com.lhy.blog.entity.Category;
import com.lhy.blog.service.CategoryService;
import com.lhy.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/category")
public class AdminCategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/addCategory")
    public Result<?> addCategory(Category category) {
        Integer t = categoryService.countByCategoryName(category.getCategoryName());
        if (t > 0)
            return new Result<>(false, "专栏名称已存在!");
        Integer rows = categoryService.createCategory(category);
        if (rows == 0) {
            return new Result<>(false, "创建分类专栏失败，请稍后重试!");
        }
        return new Result<>(true, "创建分类专栏成功!");
    }

    @GetMapping("/allCategory")
    public Result<List<Category>> allCategory() {
        List<Category> categoryList = categoryService.getAll();
        return new Result<>(true, "获取列表成功!", categoryList);
    }

    @GetMapping("/allCategorySimple")
    public Result<List<Category>> allCategorySimple() {
        List<Category> categoryList = categoryService.getAllSimple();
        return new Result<>(true, "获取列表成功!", categoryList);
    }

    @GetMapping("/getOne/{id}")
    public Result<Category> getOne(@PathVariable("id") Integer id){
        Category category = categoryService.getOneById(id);
        return new Result<>(true, "获取信息成功!", category);
    }

    @PostMapping("/updateCategory")
    public Result<?> updateCategory(Category category){
        Integer t = categoryService.countByCategoryName(category.getCategoryName());
        if (t > 1)
            return new Result<>(false, "专栏名称已存在!");
        Integer rows = categoryService.updateCategory(category);
        if (rows == 0){
            return new Result<>(false, "编辑信息失败，请稍后重试!");
        }
        return new Result<>(true, "编辑信息成功!");
    }

    @PostMapping("/sortCategory")
    public Result<List<Category>> sortCategory(@RequestParam String ids){
        categoryService.sortCategory(ids);
        List<Category> categoryList = categoryService.getAll();
        return new Result<>(true, "排序成功!", categoryList);
    }

    @PostMapping("/deleteCategory")
    public Result<List<Category>> deleteCategory(@RequestParam Integer id){
        Integer rows = categoryService.deleteCategory(id);
        if (rows < 1){
            return new Result<>(false, "删除失败，请稍后再试!");
        }
        List<Category> result = categoryService.getAll();
        return new Result<>(true, "获取成功", result);
    }

}
