package com.lhy.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lhy.blog.entity.Tag;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TagMapper extends BaseMapper<Tag> {

    List<Tag> selectByArticleId(@Param("articleId") Integer articleId);

    Integer countTagSize(@Param("tagId") Integer tagId);

    List<Map<String, Object>> countAllTagSize();

}
