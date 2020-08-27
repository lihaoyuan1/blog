package com.lhy.blog.service;

import com.lhy.blog.entity.Tag;

import java.util.List;

public interface TagService {

    Integer createTag(String tagName);

    List<Tag> getAll();

    Tag getOne(Integer id);

    List<Tag> getAllSimple();

    Integer updateTag(Tag tag);

    Integer deleteTag(Integer id);

    Integer countByTagName(String tagName);

}
