package com.lhy.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lhy.blog.entity.Category;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CategoryMapper extends BaseMapper<Category> {

    List<Category> selectByArticleId(@Param("articleId") Integer articleId);

    Integer countCategorySize(@Param("categoryId") Integer categoryId);

    List<Map<String, Object>> countAllCategorySize();

}
