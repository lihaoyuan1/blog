package com.lhy.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lhy.blog.dto.QueryDto;
import com.lhy.blog.entity.Article;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ArticleMapper extends BaseMapper<Article> {

    IPage<Article> dynamicSearch(Page<Article> page, @Param("query") QueryDto query);

    IPage<Article> listByCategoryId(Page<Article> page, @Param("categoryId") Integer categoryId);

    IPage<Article> listDetailByCategoryId(Page<Article> page, @Param("categoryId") Integer categoryId);

    IPage<Article> listByTagId(Page<Article> page, @Param("tagId") Integer tagId);

    IPage<Article> listDetailByTagId(Page<Article> page, @Param("tagId") Integer tagId);

    Integer sumOfViewsByCategoryId(@Param("categoryId") Integer categoryId);

    Integer sumOfViewsByTagId(@Param("tagId") Integer tagId);

    List<Map<String, Object>> sumOfViewsOfAllCategory();

    List<Map<String, Object>> sumOfViewsOfAllTag();

    List<Article> getHotArticleList(@Param("size") Integer size);

    List<Article> getRecommendArticleList(@Param("size") Integer size);

    List<Article> getLatestArticleList(@Param("size") Integer size);

    Article getOneById(@Param("id") Integer id);

    Integer admireByArticleId(@Param("id") Integer id,
                              @Param("flag") Integer flag);

    Integer viewArticleById(@Param("id") Integer id);

    IPage<Article> searchArticles(Page<Article> page, @Param("searchText") String searchText);

}
