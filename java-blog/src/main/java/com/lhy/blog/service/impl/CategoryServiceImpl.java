package com.lhy.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lhy.blog.entity.Category;
import com.lhy.blog.entity.CategoryArticle;
import com.lhy.blog.mapper.ArticleMapper;
import com.lhy.blog.mapper.CategoryArticleMapper;
import com.lhy.blog.mapper.CategoryMapper;
import com.lhy.blog.service.CategoryService;
import com.lhy.blog.utils.IDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CategoryArticleMapper categoryArticleMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public Integer createCategory(Category category) {
        Integer id = IDUtil.generateUID();
        Integer count = count();
        category.setId(id);
        category.setSequence(count + 1);
        return categoryMapper.insert(category);
    }

    @Override
    public Integer updateCategory(Category category) {
        return categoryMapper.updateById(category);
    }

    @Override
    public void sortCategory(String ids) {
        List<String> idList = Arrays.asList(ids.split(","));
        Category category = new Category();
        for (int i=0; i<idList.size(); i++){
            Integer id = Integer.parseInt(idList.get(i));
            category.setId(id);
            category.setSequence(i+1);
            categoryMapper.updateById(category);
        }
    }

    @Override
    public Category getOneById(Integer id) {
        Category category = categoryMapper.selectById(id);
        category.setCount(categoryMapper.countCategorySize(id));
        category.setViews(articleMapper.sumOfViewsByCategoryId(id));
        return category;
    }

    @Override
    public List<Category> getAll() {
        List<Category> categoryList = fullCategoryList();
        categoryList.sort(new Comparator<Category>() {
            @Override
            public int compare(Category o1, Category o2) {
                return o1.getSequence().compareTo(o2.getSequence());
            }
        });
        return categoryList;
    }

    @Override
    public List<Category> getAllSimple() {
        List<Category> categoryList = categoryMapper.selectList(new QueryWrapper<>());
        categoryList.sort(new Comparator<Category>() {
            @Override
            public int compare(Category o1, Category o2) {
                return o1.getSequence().compareTo(o2.getSequence());
            }
        });
        return categoryMapper.selectList(new QueryWrapper<>());
    }

    @Override
    public Integer count() {
        return categoryMapper.selectCount(new QueryWrapper<>());
    }

    @Override
    public Integer deleteCategory(Integer id) {
        int rows = categoryMapper.deleteById(id);
        if (rows < 1)
            return 0;
        QueryWrapper<CategoryArticle> wrapper = new QueryWrapper<>();
        wrapper.select("id", "sequence").eq("category_id", id);
        categoryArticleMapper.delete(wrapper);
        List<Category> categoryList = categoryMapper.selectList(new QueryWrapper<>());
        categoryList.sort(new Comparator<Category>() {
            @Override
            public int compare(Category o1, Category o2) {
                return o1.getSequence().compareTo(o2.getSequence());
            }
        });
        for (int i=0; i<categoryList.size(); i++){
            Category category = categoryList.get(i);
            if (category.getSequence() != i+1){
                category.setSequence(i+1);
                categoryMapper.updateById(category);
            }
        }
        return 1;
    }

    @Override
    public Integer countByCategoryName(String categoryName) {
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        wrapper.eq("category_name", categoryName);
        return categoryMapper.selectCount(wrapper);
    }

    @Override
    public List<Category> listTop() {
        List<Category> categoryList = fullCategoryList();
        categoryList.sort(new Comparator<Category>() {
            @Override
            public int compare(Category o1, Category o2) {
                return o2.getCount().compareTo(o1.getCount());
            }
        });
        return categoryList.subList(0, 7);
    }

    @Override
    public List<Category> allCategoryOfDetail() {
        return fullCategoryList();
    }

    private List<Category> fullCategoryList(){
        List<Category> result = categoryMapper.selectList(new QueryWrapper<>());
        List<Map<String, Object>> temp1 = categoryMapper.countAllCategorySize();
        List<Map<String, Object>> temp2 = articleMapper.sumOfViewsOfAllCategory();
        Map<Integer, Integer> categorySizeMap = new LinkedHashMap<>(), categoryViewsMap = new LinkedHashMap<>();
        temp1.forEach(item -> {
            Integer key = Integer.parseInt(item.get("categoryId").toString());
            Integer value = Integer.parseInt(item.get("count").toString());
            categorySizeMap.put(key, value);
        });
        temp2.forEach(item -> {
            Integer key = Integer.parseInt(item.get("categoryId").toString());
            Integer value = Integer.parseInt(item.get("views").toString());
            categoryViewsMap.put(key, value);
        });
        result.forEach(item -> {
            item.setCount(categorySizeMap.get(item.getId()) == null ? 0:categorySizeMap.get(item.getId()));
            item.setViews(categoryViewsMap.get(item.getId()) == null ? 0:categoryViewsMap.get(item.getId()));
        });
        return result;
    }

}
