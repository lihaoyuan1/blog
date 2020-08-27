package com.lhy.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lhy.blog.dto.QueryDto;
import com.lhy.blog.entity.*;
import com.lhy.blog.mapper.*;
import com.lhy.blog.oss.OSSUtils;
import com.lhy.blog.service.ArticleService;
import com.lhy.blog.service.RedisService;
import com.lhy.blog.service.TokenService;
import com.lhy.blog.utils.IDUtil;
import com.lhy.blog.vo.ArchiveVO;
import javafx.scene.shape.Arc;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private CategoryArticleMapper categoryArticleMapper;

    @Autowired
    private TagArticleMapper tagArticleMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisService redisService;

    @Autowired
    private TokenService tokenService;

    @Override
    public Article getArticleById(Integer id) {
        Article article = articleMapper.getOneById(id);
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("article_id", id);
        Integer comments = commentMapper.selectCount(wrapper);
        List<Tag> tags = tagMapper.selectByArticleId(id);
        article.setTagList(tags);
        article.setComments(comments);
        return article;
    }

    @Override
    public IPage<Article> listArticle(QueryDto queryDto, Page<Article> page) {
        IPage<Article> articleIPage = articleMapper.dynamicSearch(page, queryDto);
        articleIPage.getRecords().forEach(item -> item.setComments(calcCountOfComment(item.getId())));
        return articleIPage;
    }

    @Override
    public IPage<Article> listArticleByCategoryId(Integer categoryId, Page<Article> page) {
        return articleMapper.listByCategoryId(page, categoryId);
    }

    @Override
    public IPage<Article> listArticleDetailByCategoryId(Integer categoryId, Page<Article> page) {
        return articleMapper.listDetailByCategoryId(page, categoryId);
    }

    @Override
    public IPage<Article> listArticleByTagId(Integer tagId, Page<Article> page) {
        return null;
    }

    @Override
    public IPage<Article> listArticleDetailByTagId(Integer tagId, Page<Article> page) {
        return articleMapper.listDetailByTagId(page, tagId);
    }

    @Override
    public Integer saveArticle(Article article, String categoryIds, String tagIds, String userId) {
        Integer id = IDUtil.generateUID();
        String content = article.getContent();
        article.setId(id);
        article.setSummary(content.length() <= 500 ? content:content.substring(0, 500));
        article.setUserId(userId);
        article.setViews(0);
        article.setAdmire(0);
        article.setIsTop((long) 0);
        int rows = articleMapper.insert(article);
        if (rows < 1)
            return rows;
        insertCategory(categoryIds, id);
        insertTag(tagIds, id);
        return 1;
    }

    @Override
    public void updateArticle(Article article, String categoryIds, String tagIds) {
        String content = article.getContent();
        article.setSummary(content.length() <= 500 ? content:content.substring(0, 500));
        articleMapper.updateById(article);
        QueryWrapper<CategoryArticle> wrapper1 = new QueryWrapper<>();
        QueryWrapper<TagArticle> wrapper2 = new QueryWrapper<>();
        wrapper1.eq("article_id", article.getId());
        wrapper2.eq("article_id", article.getId());
        categoryArticleMapper.delete(wrapper1);
        tagArticleMapper.delete(wrapper2);
        insertCategory(categoryIds, article.getId());
        insertTag(tagIds, article.getId());
    }

    @Override
    public Integer deleteArticle(Integer id) {
        int rows = articleMapper.deleteById(id);
        if (rows < 1)
            return rows;
        QueryWrapper<CategoryArticle> wrapper1 = new QueryWrapper<>();
        QueryWrapper<TagArticle> wrapper2 = new QueryWrapper<>();
        wrapper1.eq("article_id", id);
        wrapper2.eq("article_id", id);
        categoryArticleMapper.delete(wrapper1);
        tagArticleMapper.delete(wrapper2);
        return 1;
    }

    @Override
    public Set<Integer> getYears() {
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("create_time");
        List<Article> articleList = articleMapper.selectList(queryWrapper);
        Set<Integer> years  = new HashSet<>();
        for (Article article:articleList){
            Date date = article.getCreateTime();
            years.add(date.getYear() + 1900);
        }
        return years;
    }

    @Override
    public List<Article> allArticleSimple() {
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.select(Article.class, info->!info.getColumn().equals("content"));
        return articleMapper.selectList(wrapper);
    }

    @Override
    public void handleIsTop(Integer id) {
        Article article = articleMapper.selectById(id);
        long t = article.getIsTop();
        long timeMillis = System.currentTimeMillis();
        article.setIsTop(t == 0 ? timeMillis:0);
        articleMapper.updateById(article);
    }

    @Override
    public List<Category> allCategorySimpleByArticleId(Integer id) {
        return categoryMapper.selectByArticleId(id);
    }

    @Override
    public List<Tag> allTagSimpleByArticleId(Integer id) {
        return tagMapper.selectByArticleId(id);
    }

    @Override
    public Integer releaseFromCategory(Integer articleId, Integer categoryId) {
        QueryWrapper<CategoryArticle> wrapper = new QueryWrapper<>();
        wrapper.eq("article_id", articleId).eq("category_id", categoryId);
        return categoryArticleMapper.delete(wrapper);
    }

    @Override
    public Integer releaseFromTag(Integer articleId, Integer tagId) {
        QueryWrapper<TagArticle> wrapper = new QueryWrapper<>();
        wrapper.eq("article_id", articleId).eq("tag_id", tagId);
        return tagArticleMapper.delete(wrapper);
    }

    @Override
    public List<Article> hotArticleList(Integer size) {
        return articleMapper.getHotArticleList(size);
    }

    @Override
    public List<Article> recommendArticleTop(Integer size) {
        return articleMapper.getRecommendArticleList(size);
    }

    @Override
    public List<Article> latestArticleTop(Integer size) {
        return articleMapper.getLatestArticleList(size);
    }


    @Override
    public IPage<Article> recommendArticlePage(Page<Article> page) {
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.eq("recommend_able", 1).orderByDesc("views", "create_time");
        IPage<Article> articleIPage = articleMapper.selectPage(page, wrapper);
        User user = getUserInfo();
        articleIPage.getRecords().forEach(item -> {
            item.setComments(calcCountOfComment(item.getId()));
            item.setNickname(user.getNickname());
            item.setAvatar(user.getAvatar());
        });
        return articleIPage;
    }

    @Override
    public void uploadByBatch() throws IOException {
        List<Article> articleList = articleMapper.selectList(new QueryWrapper<>());
        File file = new File("F:\\picture\\1920_1080");
        File[] tempList = file.listFiles();
        for (int i=0; i<articleList.size(); i++){
            assert tempList != null;
            FileInputStream fileInputStream = new FileInputStream(tempList[i]);
            MultipartFile multipartFile = new MockMultipartFile(tempList[i].getName(), tempList[i].getName(),
                    ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
            int idx = Objects.requireNonNull(multipartFile.getOriginalFilename()).lastIndexOf('.');
            String type = multipartFile.getOriginalFilename().substring(idx);
            String fileName = System.currentTimeMillis() + type;
            String objectName = "blog/" + fileName;
            OSSUtils.upload(multipartFile, objectName);
            String url = "https://blog-lhy.oss-cn-hangzhou.aliyuncs.com/" + objectName;
            articleList.get(i).setPicture(url);
            articleMapper.updateById(articleList.get(i));
            System.out.println("已处理: " + i);
        }
    }

    @Override
    public Integer admireArticle(Integer id, Integer flag) {
        return articleMapper.admireByArticleId(id, flag);
    }

    @Override
    public Integer viewArticle(Integer id) {
        return articleMapper.viewArticleById(id);
    }

    @Override
    public List<ArchiveVO> getArchive() {
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.select("id", "title", "type", "create_time");
        List<Article> articleList = articleMapper.selectList(wrapper);
        Map<String, List<Article>> temp = new LinkedHashMap<>();
        for (Article article : articleList) {
            String yearAndMonth = (article.getCreateTime().getYear()+1900) + "年" +
                    (article.getCreateTime().getMonth()+1) + "月";
            if (!temp.containsKey(yearAndMonth)){
                temp.put(yearAndMonth, new ArrayList<>());
            }
            temp.get(yearAndMonth).add(article);
        }
        List<ArchiveVO> result = new ArrayList<>();
        temp.forEach((key, value) -> {
            ArchiveVO archiveVO = new ArchiveVO();
            archiveVO.setYearAndMonth(key);
            archiveVO.setArticles(value);
            result.add(archiveVO);
        });
        return result;
    }

    @Override
    public IPage<Article> search(String searchText, Page<Article> page) {
        return articleMapper.searchArticles(page, searchText);
    }

    private Integer calcCountOfComment(Integer articleId){
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("article_id", articleId);
        return commentMapper.selectCount(wrapper);
    }

    private void insertCategory(String categoryIds, Integer articleId){
        if (categoryIds != null && !"".equals(categoryIds)){
            String[] ids1 = categoryIds.split(",");
            for (String str:ids1){
                Integer cid = Integer.parseInt(str);
                categoryArticleMapper.insert(new CategoryArticle(cid, articleId));
            }
        }
    }

    private void insertTag(String tagIds, Integer articleId){
        if (tagIds != null && !"".equals(tagIds)){
            String[] ids2 = tagIds.split(",");
            for (String str:ids2){
                Integer tid = Integer.parseInt(str);
                tagArticleMapper.insert(new TagArticle(tid, articleId));
            }
        }
    }

    private User getUserInfo(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.select(User.class, info -> !info.getColumn().equals("password") );
        wrapper.eq("identity", 1);
        return userMapper.selectOne(wrapper);
    }

}
