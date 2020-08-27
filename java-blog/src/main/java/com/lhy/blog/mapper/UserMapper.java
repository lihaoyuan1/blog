package com.lhy.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lhy.blog.entity.User;

import java.util.Map;

public interface UserMapper extends BaseMapper<User> {

    Map<String, Object> getAuthorInfo();

}
