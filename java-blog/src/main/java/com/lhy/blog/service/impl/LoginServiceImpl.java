package com.lhy.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lhy.blog.entity.User;
import com.lhy.blog.mapper.UserMapper;
import com.lhy.blog.service.LoginService;
import com.lhy.blog.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean usernameExist(String username) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.select("id").eq("username", username);
        User user = userMapper.selectOne(wrapper);
        return user != null;
    }

    @Override
    public User check(String username, String password) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username).eq("password", password);
        return userMapper.selectOne(wrapper);
    }

    @Override
    public User getUserInfo(String userId) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.select("id", "username", "nickname", "email", "avatar", "identity", "update_time")
                .eq("id", userId);
        return userMapper.selectOne(wrapper);
    }

    @Override
    public Map<String, Object> getUserInfo() {
        return userMapper.getAuthorInfo();
    }
}
