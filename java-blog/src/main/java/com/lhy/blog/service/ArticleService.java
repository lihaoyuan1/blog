package com.lhy.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lhy.blog.dto.QueryDto;
import com.lhy.blog.entity.Article;
import com.lhy.blog.entity.Category;
import com.lhy.blog.entity.Tag;
import com.lhy.blog.vo.ArchiveVO;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface ArticleService {

    Article getArticleById(Integer id);

    IPage<Article> listArticle(QueryDto queryDto, Page<Article> page);

    IPage<Article> listArticleByCategoryId(Integer categoryId, Page<Article> page);

    IPage<Article> listArticleDetailByCategoryId(Integer categoryId, Page<Article> page);

    IPage<Article> listArticleByTagId(Integer tagId, Page<Article> page);

    IPage<Article> listArticleDetailByTagId(Integer tagId, Page<Article> page);

    Integer saveArticle(Article article, String categoryIds, String tagIds, String userId);

    void updateArticle(Article article, String categoryIds, String tagIds);

    Integer deleteArticle(Integer id);

    Set<Integer> getYears();

    List<Article> allArticleSimple();

    void handleIsTop(Integer id);

    List<Category> allCategorySimpleByArticleId(Integer id);

    List<Tag> allTagSimpleByArticleId(Integer id);

    Integer releaseFromCategory(Integer articleId, Integer categoryId);

    Integer releaseFromTag(Integer articleId, Integer tagId);

    List<Article> hotArticleList(Integer size);

    List<Article> recommendArticleTop(Integer size);

    List<Article> latestArticleTop(Integer size);

    IPage<Article> recommendArticlePage(Page<Article> page);

    void uploadByBatch() throws IOException;

    Integer admireArticle(Integer id, Integer flag);

    Integer viewArticle(Integer id);

    List<ArchiveVO> getArchive();

    IPage<Article> search(String searchText, Page<Article> page);

}
