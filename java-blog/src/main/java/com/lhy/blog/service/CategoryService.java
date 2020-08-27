package com.lhy.blog.service;

import com.lhy.blog.entity.Category;

import java.util.List;
import java.util.Map;

public interface CategoryService {

    Integer createCategory(Category category);

    Integer updateCategory(Category category);

    void sortCategory(String ids);

    Category getOneById(Integer id);

    List<Category> getAll();

    List<Category> getAllSimple();

    Integer count();

    Integer deleteCategory(Integer id);

    Integer countByCategoryName(String categoryName);

    List<Category> listTop();

    List<Category> allCategoryOfDetail();

}
