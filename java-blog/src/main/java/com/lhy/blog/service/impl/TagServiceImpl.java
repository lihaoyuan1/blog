package com.lhy.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lhy.blog.entity.*;
import com.lhy.blog.mapper.ArticleMapper;
import com.lhy.blog.mapper.TagArticleMapper;
import com.lhy.blog.mapper.TagMapper;
import com.lhy.blog.service.TagService;
import com.lhy.blog.utils.IDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private TagArticleMapper tagArticleMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public Integer createTag(String tagName) {
        Tag tag = new Tag();
        Integer id = IDUtil.generateUID();
        tag.setId(id);
        tag.setTagName(tagName);
        return tagMapper.insert(tag);
    }

    @Override
    public List<Tag> getAll() {
        List<Tag> result = tagMapper.selectList(new QueryWrapper<>());
        List<Map<String, Object>> temp1 = tagMapper.countAllTagSize();
        List<Map<String, Object>> temp2 = articleMapper.sumOfViewsOfAllTag();
        Map<Integer, Integer> tagSizeMap = new LinkedHashMap<>(), viewsTagMap = new LinkedHashMap<>();
        temp1.forEach(item -> {
            Integer key = Integer.parseInt(item.get("tagId").toString());
            Integer value = Integer.parseInt(item.get("count").toString());
            tagSizeMap.put(key, value);
        });
        temp2.forEach(item -> {
            // 排除没有标签的文章，NP问题
            if (item.get("tagId") != null){
                Integer key = Integer.parseInt(item.get("tagId").toString());
                Integer value = Integer.parseInt(item.get("views").toString());
                viewsTagMap.put(key, value);
            }
        });
        result.forEach(item -> {
            item.setCount(tagSizeMap.get(item.getId()) == null ? 0:tagSizeMap.get(item.getId()));
            item.setViews(viewsTagMap.get(item.getId()) == null ? 0:viewsTagMap.get(item.getId()));
        });
        return result;
    }

    @Override
    public Tag getOne(Integer id) {
        Tag result = tagMapper.selectById(id);
        result.setCount(tagMapper.countTagSize(id));
        result.setViews(articleMapper.sumOfViewsByTagId(id));
        return result;
    }

    @Override
    public List<Tag> getAllSimple() {
        return tagMapper.selectList(new QueryWrapper<>());
    }

    @Override
    public Integer updateTag(Tag tag) {
        return tagMapper.updateById(tag);
    }

    @Override
    public Integer deleteTag(Integer id) {
        int rows = tagMapper.deleteById(id);
        if (rows < 1)
            return 0;
        QueryWrapper<TagArticle> wrapper = new QueryWrapper<>();
        wrapper.eq("tag_id", id);
        tagArticleMapper.delete(wrapper);
        return 1;
    }

    @Override
    public Integer countByTagName(String tagName) {
        QueryWrapper<Tag> wrapper = new QueryWrapper<>();
        wrapper.eq("tag_name", tagName);
        return tagMapper.selectCount(wrapper);
    }

}
